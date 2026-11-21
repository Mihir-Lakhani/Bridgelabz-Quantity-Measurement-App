package com.bridgelabz.quantitymeasurement.repository;

import com.bridgelabz.quantitymeasurement.model.MeasurementRecord;
import java.util.List;

public interface MeasurementRepository {
    MeasurementRecord save(String operation, double result, String unit);
    List<MeasurementRecord> findAll();
}
