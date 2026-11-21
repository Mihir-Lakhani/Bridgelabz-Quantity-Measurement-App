package com.bridgelabz.quantitymeasurement;

public class QuantityMeasurementApp {
    public static void main(String[] args) {
        Quantity<LengthUnit> foot = new Quantity<>(1, LengthUnit.FEET);
        Quantity<LengthUnit> inches = new Quantity<>(12, LengthUnit.INCHES);
        System.out.println("1 foot equals 12 inches: " + foot.equals(inches));
        System.out.println("Sum: " + foot.add(inches).getValue() + " feet");
    }
}
