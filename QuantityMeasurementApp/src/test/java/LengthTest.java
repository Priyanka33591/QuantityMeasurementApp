import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LengthTest {
    private static final double EPSILON = 1e-6;

   @Test
    void testConversion_FeetToInches(){
        assertEquals(12.0, Length.convert(1.0, Length.LengthUnit.FEET, Length.LengthUnit.INCH), EPSILON);
    }

   @Test
    void testConversion_InchesToFeet(){
        assertEquals(2.0, Length.convert(24.0, Length.LengthUnit.INCH, Length.LengthUnit.FEET), EPSILON);
    }

    @Test
    void testConversion_YardsToInches(){
        assertEquals(36.0, Length.convert(1.0, Length.LengthUnit.YARDS, Length.LengthUnit.INCH), EPSILON);
    }

    @Test
    void testConversion_InchesToYards(){
        assertEquals(2.0, Length.convert(72.0, Length.LengthUnit.INCH, Length.LengthUnit.YARDS), EPSILON);
    }

    @Test
    void testConversion_CentimetersToInches(){
        assertEquals(1.0, Length.convert(2.54, Length.LengthUnit.CENTIMETER, Length.LengthUnit.INCH), EPSILON);
    }

    @Test
    void testConversion_FeetToYards(){
        assertEquals(2.0, Length.convert(6.0, Length.LengthUnit.FEET, Length.LengthUnit.YARDS), EPSILON);
    }

    @Test
    void testConversion_RoundTrip_PreservesValue(){
        double temp = 5.5;

        double inches = Length.convert(temp, Length.LengthUnit.FEET, Length.LengthUnit.INCH);
        double back = Length.convert(inches, Length.LengthUnit.INCH, Length.LengthUnit.FEET);
        assertEquals(temp, back, EPSILON);
    }

    @Test
    void testConversion_ZeroValue(){
        assertEquals(0.0, Length.convert(0.0, Length.LengthUnit.FEET, Length.LengthUnit.INCH), EPSILON);
    }

    @Test
    void testConversion_NegativeValue(){
        assertEquals(-12.0, Length.convert(-1.0, Length.LengthUnit.FEET, Length.LengthUnit.INCH), EPSILON);
    }

    @Test
    void testConversion_InvalidUnit_Throws(){
       assertThrows(IllegalArgumentException.class, () -> Length.convert(1.0, null, Length.LengthUnit.FEET));
    }

    @Test
    void testConversion_NaNOrInfinite_Throws() {
        assertThrows(IllegalArgumentException.class, () -> Length.convert(Double.NaN, Length.LengthUnit.FEET, Length.LengthUnit.INCH));
        assertThrows(IllegalArgumentException.class, () -> Length.convert(Double.POSITIVE_INFINITY, Length.LengthUnit.FEET, Length.LengthUnit.INCH));
    }
}