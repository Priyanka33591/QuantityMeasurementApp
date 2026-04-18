package com.app.quantitymeasurement.unit;

public interface IMeasurable {


    //This method converts any unit into the base unit (Feet)
    double toBase(double value);


    //This method converts from base unit (Feet) to the target unit.
    double fromBase(double baseValue);

    default boolean supportsArithmetic() {
        return true;
    }

    default void validateOperation(String op) {
        if (!supportsArithmetic()) {
            throw new UnsupportedOperationException(op + " not supported");
        }
    }
}