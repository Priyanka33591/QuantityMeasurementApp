package com.bridgelabz.quantity;
import java.util.Objects;

public class Length {

    // ===== Instance variables =====
    private final double value;
    private final LengthUnit unit;

    // ===== Enum for supported units =====
    public enum LengthUnit {
        FEET(12.0),     // 1 foot = 12 inches
        INCHES(1.0);    // base unit

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }

    // ===== Constructor with validation =====
    public Length(double value, LengthUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (value < 0) {
            throw new IllegalArgumentException("Length cannot be negative");
        }
        this.value = value;
        this.unit = unit;
    }

    // ===== Convert to base unit (inches) =====
    private double convertToBaseUnit() {
        return this.value * this.unit.getConversionFactor();
    }

    // ===== Compare helper =====
    public boolean compare(Length that) {
        if (that == null) return false;

        double thisInches = this.convertToBaseUnit();
        double thatInches = that.convertToBaseUnit();

        // tolerance for floating point comparison
        return Math.abs(thisInches - thatInches) < 0.0001;
    }

    // ===== equals() override =====
    @Override
    public boolean equals(Object o) {

        // Reflexive
        if (this == o) return true;

        // Null + type safety
        if (o == null || getClass() != o.getClass()) return false;

        Length that = (Length) o;
        return compare(that);
    }

    // ===== hashCode() (must when equals overridden) =====
    @Override
    public int hashCode() {
        return Objects.hash(convertToBaseUnit());
    }

    // ===== Optional: getters =====
    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }
}
