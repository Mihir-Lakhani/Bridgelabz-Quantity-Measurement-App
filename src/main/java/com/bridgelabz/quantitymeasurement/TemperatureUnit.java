package com.bridgelabz.quantitymeasurement;

public enum TemperatureUnit implements IMeasurable {
    CELSIUS {
        public double toBase(double value) { return value; }
        public double fromBase(double value) { return value; }
    },
    FAHRENHEIT {
        public double toBase(double value) { return (value - 32) * 5 / 9; }
        public double fromBase(double value) { return value * 9 / 5 + 32; }
    };

    @Override
    public void validateOperationSupport(char operation) {
        throw new UnsupportedOperationException("Arithmetic on absolute temperatures is not supported");
    }
}
