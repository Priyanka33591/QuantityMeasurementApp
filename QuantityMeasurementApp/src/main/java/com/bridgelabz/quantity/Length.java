package com.bridgelabz.quantity;

import java.util.Objects;

public class Length {

    // ================= Instance Variables =================
    private final double value;
    private final LengthUnit unit;

    // ================= Enum (Base Unit = Inches) =================
    public enum LengthUnit {
        FEET(12.0),            // 1 ft = 12 in
        INCHES(1.0),           // base unit
        YARDS(36.0),           // 1 yd = 36 in
        CENTIMETERS(0.393701); // 1 cm = 0.393701 in

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }

    // ================= Constructor =================
    public Length(double value, LengthUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.value = value;
        this.unit = unit;
    }

    // ================= Convert to Base Unit =================
    private double toInches() {
        return value * unit.getConversionFactor();
    }

    // ================= Compare Helper =================
    public boolean compare(Length that) {
        if (that == null) return false;
        double diff = Math.abs(this.toInches() - that.toInches());
        return diff < 0.0001; // floating tolerance
    }

    // ================= equals Override =================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Length)) return false;
        Length that = (Length) o;
        return compare(that);
    }

    // ================= hashCode =================
    @Override
    public int hashCode() {
        return Objects.hash(Math.round(toInches() * 10000));
    }

    // ================= Getters =================
    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }
}