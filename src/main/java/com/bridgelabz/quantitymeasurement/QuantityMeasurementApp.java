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

    public static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (!(other instanceof Inches inches)) return false;
            return Double.compare(value, inches.value) == 0;
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
        System.out.println("12.0 in equals 12.0 in: "
                + new Inches(12.0).equals(new Inches(12.0)));
    }
}
