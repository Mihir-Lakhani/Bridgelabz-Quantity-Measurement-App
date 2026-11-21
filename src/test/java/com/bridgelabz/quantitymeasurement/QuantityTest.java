package com.bridgelabz.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {
    @Test
    void convertsTemperatureButRejectsAbsoluteTemperatureArithmetic() {
        Quantity<TemperatureUnit> freezingC = new Quantity<>(0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> freezingF = new Quantity<>(32, TemperatureUnit.FAHRENHEIT);
        assertEquals(freezingC, freezingF);
        assertEquals(32, freezingC.convertTo(TemperatureUnit.FAHRENHEIT), 0.000001);
        assertThrows(UnsupportedOperationException.class, () -> freezingC.add(freezingF));
        assertThrows(UnsupportedOperationException.class, () -> freezingC.divide(freezingF));
    }

    @Test
    void subtractsAndDividesCompatibleQuantities() {
        Quantity<LengthUnit> tenFeet = new Quantity<>(10, LengthUnit.FEET);
        Quantity<LengthUnit> sixInches = new Quantity<>(6, LengthUnit.INCHES);
        assertEquals(9.5, tenFeet.subtract(sixInches).getValue(), 0.000001);
        assertEquals(20, tenFeet.divide(sixInches), 0.000001);
        assertThrows(ArithmeticException.class,
                () -> tenFeet.divide(new Quantity<>(0, LengthUnit.FEET)));
    }

    @Test
    void volumeCanBeComparedConvertedAndAdded() {
        Quantity<VolumeUnit> litre = new Quantity<>(1, VolumeUnit.LITRE);
        Quantity<VolumeUnit> millilitres = new Quantity<>(1000, VolumeUnit.MILLILITRE);
        assertEquals(litre, millilitres);
        assertEquals(2, litre.add(millilitres).getValue(), 0.000001);
        assertEquals(3.78541, new Quantity<>(1, VolumeUnit.GALLON).convertTo(VolumeUnit.LITRE), 0.000001);
    }

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
