package com.bridgelabz.quantitymeasurement.repository;

import com.bridgelabz.quantitymeasurement.model.MeasurementRecord;
import java.util.ArrayList;
import java.util.List;

public class CacheMeasurementRepository implements MeasurementRepository {
    private final List<MeasurementRecord> records = new ArrayList<>();

    public MeasurementRecord save(String operation, double result, String unit) {
        MeasurementRecord record = new MeasurementRecord(records.size() + 1, operation, result, unit);
        records.add(record);
        return record;
    }

    public List<MeasurementRecord> findAll() { return List.copyOf(records); }
}
