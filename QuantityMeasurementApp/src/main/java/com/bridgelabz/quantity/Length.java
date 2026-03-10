package com.bridgelabz.quantity;

public class Length {

    private double value;
    private LengthUnit unit;

    public enum LengthUnit {

        INCHES(1.0),
        FEET(12.0),
        YARDS(36.0),
        CENTIMETERS(0.393701);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }

    public Length(double value, LengthUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    private double convertToBaseUnit() {

        double inches = value * unit.getConversionFactor();

        return Math.round(inches * 100.0) / 100.0;
    }

    private boolean compare(Length thatLength) {

        return this.convertToBaseUnit() ==
                thatLength.convertToBaseUnit();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (o == null || !(o instanceof Length))
            return false;

        Length other = (Length) o;

        return compare(other);
    }

    public Length convertTo(LengthUnit targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double inches = convertToBaseUnit();

        double result = inches / targetUnit.getConversionFactor();

        result = Math.round(result * 100.0) / 100.0;

        return new Length(result, targetUnit);
    }

    @Override
    public String toString() {

        return String.format("%.2f %s", value, unit);
    }
}