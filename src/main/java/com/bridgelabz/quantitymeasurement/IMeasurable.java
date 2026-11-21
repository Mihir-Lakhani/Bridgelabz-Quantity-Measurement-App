package com.bridgelabz.quantitymeasurement;

public interface IMeasurable {
    double toBase(double value);
    double fromBase(double value);

    default void validateOperationSupport(char operation) {
        // Length, weight and volume support arithmetic by default.
    }
}
