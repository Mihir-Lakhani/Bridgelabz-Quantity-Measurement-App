package com.bridgelabz.quantitymeasurement;

public enum LengthUnit {
    FEET(1.0), INCHES(1.0 / 12.0), YARDS(3.0), CENTIMETERS(1.0 / 30.48);

    private final double feetPerUnit;

    LengthUnit(double feetPerUnit) {
        this.feetPerUnit = feetPerUnit;
    }

    public double toBase(double value) {
        return value * feetPerUnit;
    }

    public double fromBase(double feet) {
        return feet / feetPerUnit;
    }
}
