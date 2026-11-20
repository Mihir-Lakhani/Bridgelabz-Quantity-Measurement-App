package com.bridgelabz.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static com.bridgelabz.quantitymeasurement.QuantityMeasurementApp.QuantityLength;
import static com.bridgelabz.quantitymeasurement.QuantityMeasurementApp.QuantityLength.LengthUnit.*;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {
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
