package com.bridgelabz.quantitymeasurement;

public enum WeightUnit {
    KILOGRAM(1.0), GRAM(0.001), POUND(0.453592);

    private final double kilogramsPerUnit;

    WeightUnit(double kilogramsPerUnit) { this.kilogramsPerUnit = kilogramsPerUnit; }
    public double toBase(double value) { return value * kilogramsPerUnit; }
    public double fromBase(double kilograms) { return kilograms / kilogramsPerUnit; }
}
