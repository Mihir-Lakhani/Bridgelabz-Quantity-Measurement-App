package com.bridgelabz.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    @Test
    void inchesAreComparedSeparatelyFromFeet() {
        QuantityMeasurementApp.Inches inches = new QuantityMeasurementApp.Inches(12.0);
        assertEquals(inches, new QuantityMeasurementApp.Inches(12.0));
        assertNotEquals(inches, new QuantityMeasurementApp.Inches(6.0));
        assertNotEquals(inches, new QuantityMeasurementApp.Feet(12.0));
    }

    @Test
    void equalFeetHaveEqualValuesAndHashCodes() {
        QuantityMeasurementApp.Feet first = new QuantityMeasurementApp.Feet(1.0);
        QuantityMeasurementApp.Feet second = new QuantityMeasurementApp.Feet(1.0);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void differentFeetAreNotEqual() {
        assertNotEquals(new QuantityMeasurementApp.Feet(1.0),
                new QuantityMeasurementApp.Feet(2.0));
    }

    @Test
    void feetAreNotEqualToNullOrAnotherType() {
        QuantityMeasurementApp.Feet feet = new QuantityMeasurementApp.Feet(1.0);

        assertNotEquals(null, feet);
        assertNotEquals("1.0", feet);
        assertEquals(feet, feet);
    }
}
