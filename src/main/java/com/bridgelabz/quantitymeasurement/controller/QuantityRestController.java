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
                case "LENGTH" -> calculateForUnit(operation, request, LengthUnit.class);
                case "WEIGHT" -> calculateForUnit(operation, request, WeightUnit.class);
                case "VOLUME" -> calculateForUnit(operation, request, VolumeUnit.class);
                case "TEMPERATURE" -> calculateForUnit(operation, request, TemperatureUnit.class);
                default -> throw new IllegalArgumentException("Unknown category: " + request.category());
            };
        } catch (IllegalArgumentException | ArithmeticException | UnsupportedOperationException error) {
            service.recordError(request.firstUnit());
            throw error;
        }
    }

    private <U extends Enum<U> & IMeasurable> Object calculateForUnit(
            String operation, OperationRequest request, Class<U> unitType) {
        U firstUnit = Enum.valueOf(unitType, request.firstUnit().toUpperCase(Locale.ROOT));
        Quantity<U> first = new Quantity<>(request.firstValue(), firstUnit);
        U target = request.targetUnit() == null ? firstUnit
                : Enum.valueOf(unitType, request.targetUnit().toUpperCase(Locale.ROOT));

        if (operation.equals("convert")) {
            return Map.of("result", service.convert(first, target), "unit", target.name());
        }

        if (request.secondValue() == null || request.secondUnit() == null) {
            throw new IllegalArgumentException("Second value and unit are required");
        }
        U secondUnit = Enum.valueOf(unitType, request.secondUnit().toUpperCase(Locale.ROOT));
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
        Class<? extends Enum<?>> unitType = switch (type.toUpperCase(Locale.ROOT)) {
            case "LENGTH" -> LengthUnit.class;
            case "WEIGHT" -> WeightUnit.class;
            case "VOLUME" -> VolumeUnit.class;
            case "TEMPERATURE" -> TemperatureUnit.class;
            default -> throw new IllegalArgumentException("Unknown category: " + type);
        };
        return service.history().stream().filter(record -> {
            for (Object unit : unitType.getEnumConstants()) {
                if (((Enum<?>) unit).name().equals(record.unit())) return true;
            }
            return false;
        }).toList();
    }
}
