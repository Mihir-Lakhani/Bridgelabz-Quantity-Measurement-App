package com.bridgelabz.quantitymeasurement;

public enum VolumeUnit implements IMeasurable {
    LITRE(1.0), MILLILITRE(0.001), GALLON(3.78541);

    private final double litresPerUnit;

    VolumeUnit(double litresPerUnit) { this.litresPerUnit = litresPerUnit; }
    public double toBase(double value) { return value * litresPerUnit; }
    public double fromBase(double litres) { return litres / litresPerUnit; }
}
