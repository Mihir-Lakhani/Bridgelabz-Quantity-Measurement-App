package com.bridgelabz.quantitymeasurement.service;

import com.bridgelabz.quantitymeasurement.IMeasurable;
import com.bridgelabz.quantitymeasurement.Quantity;
import com.bridgelabz.quantitymeasurement.model.MeasurementEntity;
import com.bridgelabz.quantitymeasurement.model.MeasurementRecord;
import com.bridgelabz.quantitymeasurement.repository.JpaMeasurementRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class QuantityMeasurementService {
    private final JpaMeasurementRepository repository;

    public QuantityMeasurementService(JpaMeasurementRepository repository) { this.repository = repository; }

    private void save(String operation, double result, String unit) {
        repository.save(new MeasurementEntity(operation, result, unit));
    }

    public <U extends Enum<U> & IMeasurable> boolean compare(Quantity<U> first, Quantity<U> second) {
        boolean equal = first.equals(second);
        save("COMPARE", equal ? 1 : 0, first.getUnit().name());
        return equal;
    }

    public <U extends Enum<U> & IMeasurable> double convert(Quantity<U> quantity, U target) {
        double result = quantity.convertTo(target);
        save("CONVERT", result, target.name());
        return result;
    }

    public <U extends Enum<U> & IMeasurable> Quantity<U> add(Quantity<U> first, Quantity<U> second, U target) {
        Quantity<U> result = first.add(second, target);
        save("ADD", result.getValue(), target.name());
        return result;
    }

    public List<MeasurementRecord> history() {
        return repository.findAll().stream().map(MeasurementEntity::toRecord).toList();
    }

    public void recordError(String unit) { save("ERROR", 0, unit == null ? "UNKNOWN" : unit); }

    public <U extends Enum<U> & IMeasurable> Quantity<U> subtract(Quantity<U> first, Quantity<U> second, U target) {
        Quantity<U> result = first.subtract(second, target);
        save("SUBTRACT", result.getValue(), target.name());
        return result;
    }

    public <U extends Enum<U> & IMeasurable> double divide(Quantity<U> first, Quantity<U> second) {
        double result = first.divide(second);
        save("DIVIDE", result, first.getUnit().name());
        return result;
    }
}
