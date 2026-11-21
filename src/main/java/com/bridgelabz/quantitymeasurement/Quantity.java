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
        return new Quantity<>(calculate(other, targetUnit, '+'), targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) { return subtract(other, unit); }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        return new Quantity<>(calculate(other, targetUnit, '-'), targetUnit);
    }

    public double divide(Quantity<U> other) {
        return calculate(other, unit, '/');
    }

    private double calculate(Quantity<U> other, U targetUnit, char operation) {
        if (other == null || targetUnit == null) throw new IllegalArgumentException("Other quantity and target unit required");
        unit.validateOperationSupport(operation);
        double left = convertTo(targetUnit);
        double right = other.convertTo(targetUnit);
        return switch (operation) {
            case '+' -> left + right;
            case '-' -> left - right;
            case '/' -> {
                if (right == 0) throw new ArithmeticException("Cannot divide by zero");
                yield left / right;
            }
            default -> throw new IllegalArgumentException("Unsupported operation");
        };
    }

    public double getValue() { return value; }
    public U getUnit() { return unit; }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Quantity<?> quantity)) return false;
        return unit.getDeclaringClass() == quantity.unit.getDeclaringClass()
                && Double.compare(unit.toBase(value), quantity.unit.toBase(quantity.value)) == 0;
    }

    @Override
    public int hashCode() { return 31 * unit.getDeclaringClass().hashCode() + Double.hashCode(unit.toBase(value)); }
}
