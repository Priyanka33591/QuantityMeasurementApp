package com.bridgelabz.quantity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.bridgelabz.quantity.QuantityMeasurementApp.Feet;

public class QuantityMeasurementAppTest {

    @Test
    void testEquality_SameValue() {
        Feet feet1 = new Feet(1.0);
        Feet feet2 = new Feet(1.0);

        assertTrue(feet1.equals(feet2));
    }

    @Test
    void testEquality_DifferentValue() {
        Feet feet1 = new Feet(1.0);
        Feet feet2 = new Feet(2.0);

        assertFalse(feet1.equals(feet2));
    }

    @Test
    void testEquality_NullComparison() {
        Feet feet1 = new Feet(1.0);

        assertFalse(feet1.equals(null));
    }

    @Test
    void testEquality_NonNumericInput() {
        Feet feet1 = new Feet(1.0);

        assertFalse(feet1.equals("1.0"));
    }

    @Test
    void testEquality_SameReference() {
        Feet feet1 = new Feet(1.0);

        assertTrue(feet1.equals(feet1));
    }
}
