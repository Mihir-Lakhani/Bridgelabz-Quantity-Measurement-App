package com.bridgelabz.quantitymeasurement;

public class QuantityMeasurementApp {
    public static class QuantityLength {
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
            return targetUnit.fromBase(unit.toBase(value));
        }

        public QuantityLength add(QuantityLength other) {
            if (other == null) throw new IllegalArgumentException("Other length is required");
            return new QuantityLength(value + other.convertTo(unit), unit);
        }

        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
            if (other == null) throw new IllegalArgumentException("Other length is required");
            if (targetUnit == null) throw new IllegalArgumentException("Target unit is required");
            return new QuantityLength(convertTo(targetUnit) + other.convertTo(targetUnit), targetUnit);
        }

        public double getValue() { return value; }
        public LengthUnit getUnit() { return unit; }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (!(other instanceof QuantityLength length)) return false;
            return Double.compare(unit.toBase(value), length.unit.toBase(length.value)) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(unit.toBase(value));
        }
    }

    public static void main(String[] args) {
        QuantityLength foot = new QuantityLength(1, LengthUnit.FEET);
        QuantityLength inches = new QuantityLength(12, LengthUnit.INCHES);
        System.out.println("1 foot equals 12 inches: " + foot.equals(inches));
    }
}
