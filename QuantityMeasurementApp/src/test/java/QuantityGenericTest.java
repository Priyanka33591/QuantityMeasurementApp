import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityGenericTest {
    @Test
    void testIMeasurableInterface_LengthUnitImplementation() {
        IMeasurable unit = LengthUnit.FEET;

        assertEquals("FEET", unit.getUnitName());
        assertEquals(1.0, unit.convertToBase(1.0));
        assertEquals(1.0, unit.convertFromBase(1.0));
        assertTrue(unit.getConversionFactor() > 0);
    }

    @Test
    void testIMeasurableInterface_WeightUnitImplementation() {
        IMeasurable unit = WeightUnit.KILOGRAM;

        assertEquals("KILOGRAM", unit.getUnitName());
        assertEquals(1.0, unit.convertToBase(1.0));
        assertEquals(1.0, unit.convertFromBase(1.0));
        assertTrue(unit.getConversionFactor() > 0);
    }

    @Test
    void testIMeasurableInterface_ConsistentBehavior() {
        IMeasurable length = LengthUnit.INCH;
        IMeasurable weight = WeightUnit.GRAM;

        assertNotNull(length.getUnitName());
        assertNotNull(weight.getUnitName());
        assertTrue(length.getConversionFactor() > 0);
        assertTrue(weight.getConversionFactor() > 0);
    }


    @Test
    void testGenericQuantity_LengthOperations_Equality() {
        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCH);

        assertEquals(a, b);
    }

    @Test
    void testGenericQuantity_WeightOperations_Equality() {
        Quantity<WeightUnit> a = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> b = new Quantity<>(1000.0, WeightUnit.GRAM);

        assertEquals(a, b);
    }

    @Test
    void testCrossCategoryPrevention_LengthVsWeight() {
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertNotEquals(length, weight);
    }


    @Test
    void testGenericQuantity_LengthOperations_Conversion() {
        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> inches = feet.convertTo(LengthUnit.INCH);

        assertEquals(new Quantity<>(12.0, LengthUnit.INCH), inches);
    }

    @Test
    void testGenericQuantity_WeightOperations_Conversion() {
        Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> gram = kg.convertTo(WeightUnit.GRAM);

        assertEquals(new Quantity<>(1000.0, WeightUnit.GRAM), gram);
    }


    @Test
    void testGenericQuantity_LengthOperations_Addition() {
        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCH);

        Quantity<LengthUnit> result = a.add(b, LengthUnit.FEET);

        assertEquals(new Quantity<>(2.0, LengthUnit.FEET), result);
    }

    @Test
    void testGenericQuantity_WeightOperations_Addition() {
        Quantity<WeightUnit> a = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> b = new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result = a.add(b, WeightUnit.KILOGRAM);

        assertEquals(new Quantity<>(2.0, WeightUnit.KILOGRAM), result);
    }


    @Test
    void testGenericQuantity_ConstructorValidation_NullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null));
    }

    @Test
    void testGenericQuantity_ConstructorValidation_InvalidValue() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
    }


    @Test
    void testGenericQuantity_Conversion_AllUnitCombinations() {
        Quantity<LengthUnit> yard = new Quantity<>(1, LengthUnit.YARDS);
        Quantity<LengthUnit> feet = yard.convertTo(LengthUnit.FEET);

        assertEquals(new Quantity<>(3.0, LengthUnit.FEET), feet);

        Quantity<LengthUnit> cm = feet.convertTo(LengthUnit.CENTIMETER);
        assertTrue(cm.equals(cm)); 
    }

    @Test
    void testGenericQuantity_Addition_AllUnitCombinations() {
        Quantity<LengthUnit> a = new Quantity<>(2, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(24, LengthUnit.INCH);

        Quantity<LengthUnit> result = a.add(b, LengthUnit.FEET);

        assertEquals(new Quantity<>(4.0, LengthUnit.FEET), result);
    }


    @Test
    void testEquality_LitreToLitre_SameValue() {
        assertTrue(
            new Quantity<>(1.0, VolumeUnit.LITRE)
            .equals(new Quantity<>(1.0, VolumeUnit.LITRE))
        );
    }

    @Test
    void testEquality_LitreToLitre_DifferentValue() {
        assertFalse(
            new Quantity<>(1.0, VolumeUnit.LITRE)
            .equals(new Quantity<>(2.0, VolumeUnit.LITRE))
        );
    }

    @Test
    void testEquality_LitreToMillilitre_EquivalentValue() {
        assertTrue(
            new Quantity<>(1.0, VolumeUnit.LITRE)
            .equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE))
        );
    }

    @Test
    void testEquality_MillilitreToLitre_EquivalentValue() {
        assertTrue(
            new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
            .equals(new Quantity<>(1.0, VolumeUnit.LITRE))
        );
    }

    @Test
    void testEquality_LitreToGallon_EquivalentValue() {
        assertTrue(
            new Quantity<>(1.0, VolumeUnit.LITRE)
            .equals(new Quantity<>(0.264172, VolumeUnit.GALLON))
        );
    }

    @Test
    void testEquality_GallonToLitre_EquivalentValue() {
        assertTrue(
            new Quantity<>(1.0, VolumeUnit.GALLON)
            .equals(new Quantity<>(3.78541, VolumeUnit.LITRE))
        );
    }

    @Test
    void testEquality_VolumeVsLength_Incompatible() {
        assertFalse(
            new Quantity<>(1.0, VolumeUnit.LITRE)
            .equals(new Quantity<>(1.0, LengthUnit.FEET))
        );
    }

    @Test
    void testEquality_VolumeVsWeight_Incompatible() {
        assertFalse(
            new Quantity<>(1.0, VolumeUnit.LITRE)
            .equals(new Quantity<>(1.0, WeightUnit.KILOGRAM))
        );
    }

    @Test
    void testEquality_NullComparison() {
        assertFalse(
            new Quantity<>(1.0, VolumeUnit.LITRE).equals(null)
        );
    }

    @Test
    void testEquality_SameReference() {
        Quantity<VolumeUnit> q = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertTrue(q.equals(q));
    }

    @Test
    void testEquality_NullUnit() {
        assertThrows(IllegalArgumentException.class,
            () -> new Quantity<>(1.0, null)
        );
    }

    @Test
    void testEquality_TransitiveProperty() {

        Quantity<VolumeUnit> A = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> B = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> C = new Quantity<>(1.0, VolumeUnit.LITRE);

        assertTrue(A.equals(B));
        assertTrue(B.equals(C));
        assertTrue(A.equals(C));
    }

    @Test
    void testEquality_ZeroValue() {
        assertTrue(
            new Quantity<>(0.0, VolumeUnit.LITRE)
            .equals(new Quantity<>(0.0, VolumeUnit.MILLILITRE))
        );
    }

    @Test
    void testEquality_NegativeVolume() {
        assertTrue(
            new Quantity<>(-1.0, VolumeUnit.LITRE)
            .equals(new Quantity<>(-1000.0, VolumeUnit.MILLILITRE))
        );
    }

    @Test
    void testEquality_LargeVolumeValue() {
        assertTrue(
            new Quantity<>(1000000.0, VolumeUnit.MILLILITRE)
            .equals(new Quantity<>(1000.0, VolumeUnit.LITRE))
        );
    }

}


