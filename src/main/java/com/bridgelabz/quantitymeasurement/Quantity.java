package com.bridgelabz.quantitymeasurement;

public class Quantity<U extends Enum<U> & IMeasurable> {
    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (!Double.isFinite(value) || unit == null) throw new IllegalArgumentException("Valid value and unit required");
        this.value = value;
        this.unit = unit;
    }

    public double convertTo(U targetUnit) {
        if (targetUnit == null) throw new IllegalArgumentException("Target unit required");
        return targetUnit.fromBase(unit.toBase(value));
    }

    public Quantity<U> add(Quantity<U> other) { return add(other, unit); }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        if (other == null || targetUnit == null) throw new IllegalArgumentException("Other quantity and target unit required");
        return new Quantity<>(convertTo(targetUnit) + other.convertTo(targetUnit), targetUnit);
    }

    public double getValue() { return value; }
    public U getUnit() { return unit; }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Quantity<?> quantity)) return false;
        return unit.getClass() == quantity.unit.getClass()
                && Double.compare(unit.toBase(value), quantity.unit.toBase(quantity.value)) == 0;
    }

    @Override
    public int hashCode() { return 31 * unit.getClass().hashCode() + Double.hashCode(unit.toBase(value)); }
}
