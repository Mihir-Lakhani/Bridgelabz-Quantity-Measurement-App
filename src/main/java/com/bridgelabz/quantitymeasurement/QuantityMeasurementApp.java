package com.bridgelabz.quantitymeasurement;

public class QuantityMeasurementApp {

    public static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (!(other instanceof Feet feet)) return false;
            return Double.compare(value, feet.value) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    public static void main(String[] args) {
        Feet first = new Feet(1.0);
        Feet second = new Feet(1.0);
        System.out.println("1.0 ft equals 1.0 ft: " + first.equals(second));
    }
}
