package com.bridgelabz.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityWeightTest {
    @Test
    void comparesConvertsAndAddsWeight() {
        QuantityWeight kilogram = new QuantityWeight(1, WeightUnit.KILOGRAM);
        QuantityWeight grams = new QuantityWeight(1000, WeightUnit.GRAM);
        assertEquals(kilogram, grams);
        assertEquals(1000, kilogram.convertTo(WeightUnit.GRAM), 0.000001);
        assertEquals(2, kilogram.add(grams).getValue(), 0.000001);
        assertEquals(0.453592, new QuantityWeight(1, WeightUnit.POUND).convertTo(WeightUnit.KILOGRAM), 0.000001);
    }
}
