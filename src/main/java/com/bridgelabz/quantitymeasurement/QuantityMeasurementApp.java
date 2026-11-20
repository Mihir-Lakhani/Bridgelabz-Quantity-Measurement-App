package com.bridgelabz.quantitymeasurement;

public class QuantityMeasurementApp {
    public static class QuantityLength {
        public enum LengthUnit {
            FEET(1.0), INCHES(1.0 / 12.0), YARDS(3.0), CENTIMETERS(1.0 / 30.48);

            private final double feetPerUnit;

            LengthUnit(double feetPerUnit) {
                this.feetPerUnit = feetPerUnit;
            }

            public double getFeetPerUnit() { return feetPerUnit; }
        }

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit is required");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite");
            this.value = value;
            this.unit = unit;
        }

        public double convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) throw new IllegalArgumentException("Target unit is required");
            return value * unit.getFeetPerUnit() / targetUnit.getFeetPerUnit();
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (!(other instanceof QuantityLength length)) return false;
            return Double.compare(value * unit.getFeetPerUnit(),
                    length.value * length.unit.getFeetPerUnit()) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value * unit.getFeetPerUnit());
        }
    }

    public static void main(String[] args) {
        QuantityLength foot = new QuantityLength(1, QuantityLength.LengthUnit.FEET);
        QuantityLength inches = new QuantityLength(12, QuantityLength.LengthUnit.INCHES);
        System.out.println("1 foot equals 12 inches: " + foot.equals(inches));
    }
}
