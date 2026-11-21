package com.bridgelabz.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static com.bridgelabz.quantitymeasurement.QuantityMeasurementApp.QuantityLength;
import static com.bridgelabz.quantitymeasurement.QuantityMeasurementApp.QuantityLength.LengthUnit.*;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {
    @Test
    void addsLengthsInFirstUnit() {
        QuantityLength result = new QuantityLength(1, FEET).add(new QuantityLength(12, INCHES));
        assertEquals(2, result.getValue(), 0.000001);
        assertEquals(FEET, result.getUnit());
    }

    @Test
    void convertsBetweenLengthUnits() {
        assertEquals(12, new QuantityLength(1, FEET).convertTo(INCHES), 0.000001);
        assertEquals(3, new QuantityLength(1, YARDS).convertTo(FEET), 0.000001);
        assertEquals(30.48, new QuantityLength(1, FEET).convertTo(CENTIMETERS), 0.000001);
    }

    @Test
    void comparesYardsAndCentimeters() {
        assertEquals(new QuantityLength(1, YARDS), new QuantityLength(3, FEET));
        assertEquals(new QuantityLength(30.48, CENTIMETERS), new QuantityLength(1, FEET));
    }

    @Test
    void comparesFeetAndInches() {
        QuantityLength foot = new QuantityLength(1, FEET);
        QuantityLength inches = new QuantityLength(12, INCHES);
        assertEquals(foot, inches);
        assertEquals(foot.hashCode(), inches.hashCode());
        assertNotEquals(foot, new QuantityLength(11, INCHES));
        assertNotEquals(foot, null);
    }
}
