package com.bridgelabz.quantity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.bridgelabz.quantity.QuantityMeasurementApp.Feet;
import com.bridgelabz.quantity.QuantityMeasurementApp.Inches;

public class QuantityMeasurementAppTest {

    /* =======================
       FEET TESTS
       ======================= */

    @Test
    void testFeetEquality_SameValue() {
        assertTrue(QuantityMeasurementApp.compareFeet(1.0, 1.0));
    }

    @Test
    void testFeetEquality_DifferentValue() {
        assertFalse(QuantityMeasurementApp.compareFeet(1.0, 2.0));
    }

    @Test
    void testFeetEquality_NullComparison() {
        Feet feet = new Feet(1.0);
        assertFalse(feet.equals(null));
    }

    @Test
    void testFeetEquality_SameReference() {
        Feet feet = new Feet(1.0);
        assertTrue(feet.equals(feet));
    }

    @Test
    void testFeetEquality_NonNumericInput() {
        Feet feet = new Feet(1.0);
        assertFalse(feet.equals("1.0"));
    }

    /* =======================
       INCH TESTS
       ======================= */

    @Test
    void testInchEquality_SameValue() {
        assertTrue(QuantityMeasurementApp.compareInches(1.0, 1.0));
    }

    @Test
    void testInchEquality_DifferentValue() {
        assertFalse(QuantityMeasurementApp.compareInches(1.0, 2.0));
    }

    @Test
    void testInchEquality_NullComparison() {
        Inches inch = new Inches(1.0);
        assertFalse(inch.equals(null));
    }

    @Test
    void testInchEquality_SameReference() {
        Inches inch = new Inches(1.0);
        assertTrue(inch.equals(inch));
    }

    @Test
    void testInchEquality_NonNumericInput() {
        Inches inch = new Inches(1.0);
        assertFalse(inch.equals("1.0"));
    }
}
