import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WeightTest {
    @Test
    void testEquality_KilogramToKilogram_SameValue(){
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(1.0, WeightUnit.KILOGRAM);

        assertTrue(w1.equals(w2));
    }

    @Test
    void testEquality_KilogramToKilogram_DifferentValue(){
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(2.0, WeightUnit.KILOGRAM);

        assertFalse(w1.equals(w2));
    }

     @Test
    void testEquality_GramToKilogram_EquivalentValue() {
        assertEquals(new Weight(1000.0, WeightUnit.GRAM),
                     new Weight(1.0, WeightUnit.KILOGRAM));
    }

    @Test
    void testEquality_WeightVsLength_Incompatible() {
        Weight weight = new Weight(1.0, WeightUnit.KILOGRAM);
        Length length = new Length(1.0, LengthUnit.FEET);

        assertFalse(weight.equals(length));
    }

    @Test
    void testEquality_NullComparison() {
        assertFalse(new Weight(1.0, WeightUnit.KILOGRAM).equals(null));
    }

    @Test
    void testEquality_SameReference() {
        Weight w = new Weight(1.0, WeightUnit.KILOGRAM);
        assertTrue(w.equals(w));
    }

    @Test
    void testConversion_PoundToKilogram() {
        Weight result =
                new Weight(2.20462, WeightUnit.POUND)
                        .convertTo(WeightUnit.KILOGRAM);

        assertEquals(new Weight(1.0, WeightUnit.KILOGRAM), result);
    }

    @Test
    void testConversion_KilogramToPound() {
        Weight result =
                new Weight(1.0, WeightUnit.KILOGRAM)
                        .convertTo(WeightUnit.POUND);

        assertEquals(new Weight(2.20462, WeightUnit.POUND), result);
    }

    @Test
    void testConversion_SameUnit() {
        Weight result =
                new Weight(5.0, WeightUnit.KILOGRAM)
                        .convertTo(WeightUnit.KILOGRAM);

        assertEquals(new Weight(5.0, WeightUnit.KILOGRAM), result);
    }

     @Test
    void testConversion_ZeroValue() {
        assertEquals(new Weight(0.0, WeightUnit.GRAM),
                new Weight(0.0, WeightUnit.KILOGRAM)
                        .convertTo(WeightUnit.GRAM));
    }

    @Test
    void testConversion_NegativeValue() {
        assertEquals(new Weight(-1000.0, WeightUnit.GRAM),
                new Weight(-1.0, WeightUnit.KILOGRAM)
                        .convertTo(WeightUnit.GRAM));
    }

    @Test
    void testConversion_RoundTrip() {
        Weight original = new Weight(1.5, WeightUnit.KILOGRAM);
        Weight result =
                original.convertTo(WeightUnit.GRAM)
                        .convertTo(WeightUnit.KILOGRAM);

        assertEquals(original, result);
    }

     
    @Test
    void testAddition_WithZero() {
        assertEquals(new Weight(5.0, WeightUnit.KILOGRAM),
                new Weight(5.0, WeightUnit.KILOGRAM)
                        .add(new Weight(0.0, WeightUnit.GRAM)));
    }

    @Test
    void testAddition_NegativeValues() {
        assertEquals(new Weight(3.0, WeightUnit.KILOGRAM),
                new Weight(5.0, WeightUnit.KILOGRAM)
                        .add(new Weight(-2000.0, WeightUnit.GRAM)));
    }
}
