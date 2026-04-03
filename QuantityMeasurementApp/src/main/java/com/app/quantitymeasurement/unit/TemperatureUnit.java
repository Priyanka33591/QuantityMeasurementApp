package com.app.quantitymeasurement.unit;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS, FAHRENHEIT, KELVIN;

    public double toBase(double value) {
        return switch (this) {
            case CELSIUS -> value;
            case FAHRENHEIT -> (value - 32) * 5 / 9;
            case KELVIN -> value - 273.15;
        };
    }

    public double fromBase(double baseValue) {
        return switch (this) {
            case CELSIUS -> baseValue;
            case FAHRENHEIT -> (baseValue * 9 / 5) + 32;
            case KELVIN -> baseValue + 273.15;
        };
    }

    @Override
    public boolean supportsArithmetic() {
        return false;
    }
}