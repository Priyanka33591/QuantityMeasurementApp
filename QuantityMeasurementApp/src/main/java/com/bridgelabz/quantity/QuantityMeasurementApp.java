package com.bridgelabz.quantity;

public class QuantityMeasurementApp {

    // ===== Generic comparison method =====
    public static boolean demonstrateLengthComparison(
            double value1, Length.LengthUnit unit1,
            double value2, Length.LengthUnit unit2) {

        Length l1 = new Length(value1, unit1);
        Length l2 = new Length(value2, unit2);

        boolean result = l1.equals(l2);
        System.out.println("Are lengths equal? " + result);
        return result;
    }

    // ===== Main =====
    public static void main(String[] args) {

        // Feet and Inches
        demonstrateLengthComparison(1.0, Length.LengthUnit.FEET,
                                    12.0, Length.LengthUnit.INCHES);

        // Yards and Inches
        demonstrateLengthComparison(1.0, Length.LengthUnit.YARDS,
                                    36.0, Length.LengthUnit.INCHES);

        // Centimeters and Inches
        demonstrateLengthComparison(1.0, Length.LengthUnit.CENTIMETERS,
                                    0.393701, Length.LengthUnit.INCHES);

        // Feet and Yards
        demonstrateLengthComparison(3.0, Length.LengthUnit.FEET,
                                    1.0, Length.LengthUnit.YARDS);

        // Centimeters and Feet
        demonstrateLengthComparison(30.48, Length.LengthUnit.CENTIMETERS,
                                    1.0, Length.LengthUnit.FEET);
    }
}