package com.bridgelabz.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {
    @Test
    void lengthCanBeComparedConvertedAndAdded() {
        Quantity<LengthUnit> foot = new Quantity<>(1, LengthUnit.FEET);
        Quantity<LengthUnit> inches = new Quantity<>(12, LengthUnit.INCHES);
        assertEquals(foot, inches);
        assertEquals(foot.hashCode(), inches.hashCode());
        assertEquals(30.48, foot.convertTo(LengthUnit.CENTIMETERS), 0.000001);
        assertEquals(2, foot.add(inches).getValue(), 0.000001);
        assertEquals(2.0 / 3.0, foot.add(inches, LengthUnit.YARDS).getValue(), 0.000001);
    }

    @Test
    void weightUsesSameQuantityClassButCannotEqualLength() {
        Quantity<WeightUnit> kilogram = new Quantity<>(1, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> grams = new Quantity<>(1000, WeightUnit.GRAM);
        assertEquals(kilogram, grams);
        assertEquals(2, kilogram.add(grams).getValue(), 0.000001);
        assertNotEquals(kilogram, new Quantity<>(1, LengthUnit.FEET));
    }
}
