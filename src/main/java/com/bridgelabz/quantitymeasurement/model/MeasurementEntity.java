package com.bridgelabz.quantitymeasurement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "measurements")
public class MeasurementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String operation;
    private double result;
    private String unit;

    protected MeasurementEntity() { }

    public MeasurementEntity(String operation, double result, String unit) {
        this.operation = operation;
        this.result = result;
        this.unit = unit;
    }

    public MeasurementRecord toRecord() {
        return new MeasurementRecord(id, operation, result, unit);
    }
}
