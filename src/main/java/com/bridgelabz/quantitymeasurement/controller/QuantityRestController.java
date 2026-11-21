package com.bridgelabz.quantitymeasurement.controller;

import com.bridgelabz.quantitymeasurement.*;
import com.bridgelabz.quantitymeasurement.model.MeasurementRecord;
import com.bridgelabz.quantitymeasurement.model.OperationRequest;
import com.bridgelabz.quantitymeasurement.service.QuantityMeasurementService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityRestController {
    private final QuantityMeasurementService service;

    public QuantityRestController(QuantityMeasurementService service) { this.service = service; }

    @PostMapping("/{operation}")
    public Object calculate(@PathVariable String operation, @Valid @RequestBody OperationRequest request) {
        try {
            return switch (request.category().toUpperCase(Locale.ROOT)) {
                case "LENGTH" -> calculateForUnit(operation, request,
                        LengthUnit.valueOf(request.firstUnit().toUpperCase(Locale.ROOT)),
                        request.secondUnit() == null ? null : LengthUnit.valueOf(request.secondUnit().toUpperCase(Locale.ROOT)),
                        request.targetUnit() == null ? null : LengthUnit.valueOf(request.targetUnit().toUpperCase(Locale.ROOT)));
                case "WEIGHT" -> calculateForUnit(operation, request,
                        WeightUnit.valueOf(request.firstUnit().toUpperCase(Locale.ROOT)),
                        request.secondUnit() == null ? null : WeightUnit.valueOf(request.secondUnit().toUpperCase(Locale.ROOT)),
                        request.targetUnit() == null ? null : WeightUnit.valueOf(request.targetUnit().toUpperCase(Locale.ROOT)));
                case "VOLUME" -> calculateForUnit(operation, request,
                        VolumeUnit.valueOf(request.firstUnit().toUpperCase(Locale.ROOT)),
                        request.secondUnit() == null ? null : VolumeUnit.valueOf(request.secondUnit().toUpperCase(Locale.ROOT)),
                        request.targetUnit() == null ? null : VolumeUnit.valueOf(request.targetUnit().toUpperCase(Locale.ROOT)));
                case "TEMPERATURE" -> calculateForUnit(operation, request,
                        TemperatureUnit.valueOf(request.firstUnit().toUpperCase(Locale.ROOT)),
                        request.secondUnit() == null ? null : TemperatureUnit.valueOf(request.secondUnit().toUpperCase(Locale.ROOT)),
                        request.targetUnit() == null ? null : TemperatureUnit.valueOf(request.targetUnit().toUpperCase(Locale.ROOT)));
                default -> throw new IllegalArgumentException("Unknown category: " + request.category());
            };
        } catch (IllegalArgumentException | ArithmeticException | UnsupportedOperationException error) {
            service.recordError(request.firstUnit());
            throw error;
        }
    }

    private <U extends Enum<U> & IMeasurable> Object calculateForUnit(
            String operation, OperationRequest request, U firstUnit, U secondUnit, U targetUnit) {
        Quantity<U> first = new Quantity<>(request.firstValue(), firstUnit);
        U target = targetUnit == null ? firstUnit : targetUnit;

        if (operation.equals("convert")) {
            return Map.of("result", service.convert(first, target), "unit", target.name());
        }

        if (request.secondValue() == null || request.secondUnit() == null) {
            throw new IllegalArgumentException("Second value and unit are required");
        }
        Quantity<U> second = new Quantity<>(request.secondValue(), secondUnit);

        return switch (operation) {
            case "compare" -> Map.of("equal", service.compare(first, second));
            case "add", "add-with-target-unit" -> Map.of("result", service.add(first, second, target).getValue(), "unit", target.name());
            case "subtract", "subtract-with-target-unit" -> Map.of("result", service.subtract(first, second, target).getValue(), "unit", target.name());
            case "divide" -> Map.of("result", service.divide(first, second));
            default -> throw new IllegalArgumentException("Unknown operation: " + operation);
        };
    }

    @GetMapping("/history")
    public List<MeasurementRecord> history() { return service.history(); }

    @GetMapping("/history/operation/{operation}")
    public List<MeasurementRecord> historyByOperation(@PathVariable String operation) {
        return service.history().stream().filter(record -> record.operation().equalsIgnoreCase(operation)).toList();
    }

    @GetMapping("/count/{operation}")
    public long count(@PathVariable String operation) { return historyByOperation(operation).size(); }

    @GetMapping("/history/errored")
    public List<MeasurementRecord> erroredHistory() { return historyByOperation("ERROR"); }

    @GetMapping("/history/type/{type}")
    public List<MeasurementRecord> historyByType(@PathVariable String type) {
        List<String> units = switch (type.toUpperCase(Locale.ROOT)) {
            case "LENGTH" -> java.util.Arrays.stream(LengthUnit.values()).map(Enum::name).toList();
            case "WEIGHT" -> java.util.Arrays.stream(WeightUnit.values()).map(Enum::name).toList();
            case "VOLUME" -> java.util.Arrays.stream(VolumeUnit.values()).map(Enum::name).toList();
            case "TEMPERATURE" -> java.util.Arrays.stream(TemperatureUnit.values()).map(Enum::name).toList();
            default -> throw new IllegalArgumentException("Unknown category: " + type);
        };
        return service.history().stream().filter(record -> units.contains(record.unit())).toList();
    }
}
