package com.bridgelabz.quantitymeasurement;

public class QuantityWeight {
    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (!Double.isFinite(value) || unit == null) throw new IllegalArgumentException("Valid weight and unit required");
        this.value = value;
        this.unit = unit;
    }

    public double convertTo(WeightUnit target) {
        if (target == null) throw new IllegalArgumentException("Target unit required");
        return target.fromBase(unit.toBase(value));
    }

    public QuantityWeight add(QuantityWeight other) {
        if (other == null) throw new IllegalArgumentException("Other weight required");
        return new QuantityWeight(value + other.convertTo(unit), unit);
    }

    public double getValue() { return value; }
    public WeightUnit getUnit() { return unit; }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof QuantityWeight weight)) return false;
        return Double.compare(unit.toBase(value), weight.unit.toBase(weight.value)) == 0;
    }

    @Override
    public int hashCode() { return Double.hashCode(unit.toBase(value)); }
}
