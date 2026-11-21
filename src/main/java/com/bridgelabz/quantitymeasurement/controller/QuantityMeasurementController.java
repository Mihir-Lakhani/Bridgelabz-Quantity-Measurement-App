package com.bridgelabz.quantitymeasurement.controller;

import com.bridgelabz.quantitymeasurement.IMeasurable;
import com.bridgelabz.quantitymeasurement.Quantity;
import com.bridgelabz.quantitymeasurement.model.MeasurementRecord;
import com.bridgelabz.quantitymeasurement.service.QuantityMeasurementService;
import java.util.List;

public class QuantityMeasurementController {
    private final QuantityMeasurementService service;

    public QuantityMeasurementController(QuantityMeasurementService service) { this.service = service; }

    public <U extends Enum<U> & IMeasurable> boolean compare(Quantity<U> first, Quantity<U> second) {
        return service.compare(first, second);
    }

    public <U extends Enum<U> & IMeasurable> double convert(Quantity<U> quantity, U target) {
        return service.convert(quantity, target);
    }

    public <U extends Enum<U> & IMeasurable> Quantity<U> add(Quantity<U> first, Quantity<U> second, U target) {
        return service.add(first, second, target);
    }

    public List<MeasurementRecord> history() { return service.history(); }
}
