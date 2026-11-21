package com.bridgelabz.quantitymeasurement.service;

import com.bridgelabz.quantitymeasurement.IMeasurable;
import com.bridgelabz.quantitymeasurement.Quantity;
import com.bridgelabz.quantitymeasurement.model.MeasurementRecord;
import com.bridgelabz.quantitymeasurement.repository.MeasurementRepository;
import java.util.List;

public class QuantityMeasurementService {
    private final MeasurementRepository repository;

    public QuantityMeasurementService(MeasurementRepository repository) { this.repository = repository; }

    public <U extends Enum<U> & IMeasurable> boolean compare(Quantity<U> first, Quantity<U> second) {
        boolean equal = first.equals(second);
        repository.save("COMPARE", equal ? 1 : 0, first.getUnit().name());
        return equal;
    }

    public <U extends Enum<U> & IMeasurable> double convert(Quantity<U> quantity, U target) {
        double result = quantity.convertTo(target);
        repository.save("CONVERT", result, target.name());
        return result;
    }

    public <U extends Enum<U> & IMeasurable> Quantity<U> add(Quantity<U> first, Quantity<U> second, U target) {
        Quantity<U> result = first.add(second, target);
        repository.save("ADD", result.getValue(), target.name());
        return result;
    }

    public List<MeasurementRecord> history() { return repository.findAll(); }
}
