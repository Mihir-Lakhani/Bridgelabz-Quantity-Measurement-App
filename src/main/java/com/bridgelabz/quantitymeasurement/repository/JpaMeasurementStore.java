package com.bridgelabz.quantitymeasurement.repository;

import com.bridgelabz.quantitymeasurement.model.MeasurementEntity;
import com.bridgelabz.quantitymeasurement.model.MeasurementRecord;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class JpaMeasurementStore implements MeasurementRepository {
    private final JpaMeasurementRepository repository;

    public JpaMeasurementStore(JpaMeasurementRepository repository) { this.repository = repository; }

    public MeasurementRecord save(String operation, double result, String unit) {
        return repository.save(new MeasurementEntity(operation, result, unit)).toRecord();
    }

    public List<MeasurementRecord> findAll() {
        return repository.findAll().stream().map(MeasurementEntity::toRecord).toList();
    }
}
