import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityGenericTest {
    private static final double EPSILON = 0.0001;

    @Test
    void testSubtraction_SameUnit_FeetMinusFeet(){
        Quantity<LengthUnit> q1 = new Quantity<LengthUnit>(10.0, LengthUnit.FEET); 
        Quantity<LengthUnit> q2 = new Quantity<LengthUnit>(5.0, LengthUnit.FEET); 

        assertEquals(new Quantity<>(5.0, LengthUnit.FEET), q1.subtract(q2));
    }

    @Test
    void testSubtraction_SameUnit_LitreMinusLitre(){
        Quantity<VolumeUnit> q1 = new Quantity<VolumeUnit>(10.0, VolumeUnit.LITRE); 
        Quantity<VolumeUnit> q2 = new Quantity<VolumeUnit>(5.0, VolumeUnit.LITRE); 

        assertEquals(new Quantity<>(5.0, VolumeUnit.LITRE), q1.subtract(q2));
    }

    @Test
    void testSubtraction_CrossUnit_FeetMinusInches(){
        Quantity<LengthUnit> q1 = new Quantity<LengthUnit>(10.0, LengthUnit.FEET); 
        Quantity<LengthUnit> q2 = new Quantity<LengthUnit>(6.0, LengthUnit.INCH); 

        assertEquals(new Quantity<>(9.5, LengthUnit.FEET), q1.subtract(q2));
    }

    @Test
    void testSubtraction_CrossUnit_InchesMinusFeet(){
        Quantity<LengthUnit> q1 = new Quantity<LengthUnit>(120.0, LengthUnit.INCH); 
        Quantity<LengthUnit> q2 = new Quantity<LengthUnit>(5.0, LengthUnit.FEET); 

        assertEquals(new Quantity<>(60.0, LengthUnit.INCH), q1.subtract(q2));
    }

    @Test
    void testSubtraction_ExplicitTargetUnit_Feet(){
        Quantity<LengthUnit> q1 = new Quantity<LengthUnit>(10.0, LengthUnit.FEET); 
        Quantity<LengthUnit> q2 = new Quantity<LengthUnit>(6.0, LengthUnit.INCH); 

        assertEquals(new Quantity<>(114.0, LengthUnit.INCH), q1.subtract(q2, LengthUnit.INCH));
    }

    @Test
    void testSubtraction_ExplicitTargetUnit_Millilitre(){
        Quantity<VolumeUnit> q1 = new Quantity<VolumeUnit>(5.0, VolumeUnit.LITRE); 
        Quantity<VolumeUnit> q2 = new Quantity<VolumeUnit>(2000.0, VolumeUnit.MILLILITRE); 

        assertEquals(new Quantity<>(3000.0, VolumeUnit.MILLILITRE), q1.subtract(q2, VolumeUnit.MILLILITRE));
    }

    @Test
    void testSubtraction_ResultingInNegative(){
        Quantity<LengthUnit> q1 = new Quantity<LengthUnit>(5.0, LengthUnit.FEET); 
        Quantity<LengthUnit> q2 = new Quantity<LengthUnit>(10.0, LengthUnit.FEET); 

        assertEquals(new Quantity<>(-5.0, LengthUnit.FEET), q1.subtract(q2));
    }

    @Test
    void testSubtraction_ResultingInZero(){
        Quantity<LengthUnit> q1 = new Quantity<LengthUnit>(10.0, LengthUnit.FEET); 
        Quantity<LengthUnit> q2 = new Quantity<LengthUnit>(120.0, LengthUnit.INCH); 

        assertEquals(new Quantity<>(0.0, LengthUnit.FEET), q1.subtract(q2));
    }

    @Test
    void testSubtraction_WithZeroOperand(){
        Quantity<LengthUnit> q1 = new Quantity<LengthUnit>(5.0, LengthUnit.FEET); 
        Quantity<LengthUnit> q2 = new Quantity<LengthUnit>(0.0, LengthUnit.INCH); 

        assertEquals(new Quantity<>(5.0, LengthUnit.FEET), q1.subtract(q2));
    }

    @Test
    void testSubtraction_WithNegativeValues(){
        Quantity<LengthUnit> q1 = new Quantity<LengthUnit>(5.0, LengthUnit.FEET); 
        Quantity<LengthUnit> q2 = new Quantity<LengthUnit>(-2.0, LengthUnit.FEET); 

        assertEquals(new Quantity<>(7.0, LengthUnit.FEET), q1.subtract(q2));
    }

    @Test
    void testSubtraction_NonCommutative(){
        Quantity<LengthUnit> q1 = new Quantity<LengthUnit>(10.0, LengthUnit.FEET); 
        Quantity<LengthUnit> q2 = new Quantity<LengthUnit>(5.0, LengthUnit.FEET); 

        assertNotEquals(q1.subtract(q2), q2.subtract(q1));
    }

    @Test
    void testSubtraction_WithLargeValues(){
        Quantity<WeightUnit> q1 = new Quantity<WeightUnit>(1e6, WeightUnit.KILOGRAM); 
        Quantity<WeightUnit> q2 = new Quantity<WeightUnit>(5e5, WeightUnit.KILOGRAM); 

        assertEquals(new Quantity<>(5e5, WeightUnit.KILOGRAM), q1.subtract(q2));
    }

    
    @Test
    void testSubtraction_NullOperand(){
        assertThrows(IllegalArgumentException.class, () -> new Quantity<>(10.0, LengthUnit.FEET).subtract(null));
    }

    @Test
    void testSubtraction_NullTargetUnit(){
        Quantity<LengthUnit> q1 = new Quantity<LengthUnit>(10.0, LengthUnit.FEET); 
        Quantity<LengthUnit> q2 = new Quantity<LengthUnit>(5.0, LengthUnit.FEET); 

        assertThrows(IllegalArgumentException.class, () -> q1.subtract(q2, null));
    }

    @Test
    void testSubtraction_CrossCategory(){
        Quantity<LengthUnit> q1 = new Quantity<LengthUnit>(10.0, LengthUnit.FEET); 
        Quantity<WeightUnit> q2 = new Quantity<WeightUnit>(5.0, WeightUnit.KILOGRAM); 

        assertThrows(IllegalArgumentException.class, () -> q1.subtract(q2));
    }

    @Test
    void testSubtraction_ChainedOperations(){
        Quantity<LengthUnit> q1 = new Quantity<LengthUnit>(10.0, LengthUnit.FEET); 
        Quantity<LengthUnit> q2 = new Quantity<LengthUnit>(2.0, LengthUnit.FEET); 
        Quantity<LengthUnit> q3 = new Quantity<LengthUnit>(1.0, LengthUnit.FEET); 

        assertEquals(new Quantity<>(7.0, LengthUnit.FEET), q1.subtract(q2).subtract(q3));
    }
    
    @Test
    void testDivision_SameUnit_FeetDividedByFeet(){
        Quantity<LengthUnit> q1 = new Quantity<LengthUnit>(10.0, LengthUnit.FEET); 
        Quantity<LengthUnit> q2 = new Quantity<LengthUnit>(2.0, LengthUnit.FEET); 

        assertEquals(5.0, q1.divide(q2));
    }

    @Test
    void testDivision_SameUnit_LitreDividedByLitre(){
        Quantity<VolumeUnit> q1 = new Quantity<VolumeUnit>(10.0, VolumeUnit.LITRE); 
        Quantity<VolumeUnit> q2 = new Quantity<VolumeUnit>(5.0, VolumeUnit.LITRE); 

        assertEquals(2.0, q1.divide(q2));
    }

    @Test
    void testDivision_CrossUnit_FeetDividedByInches(){
        Quantity<LengthUnit> q1 = new Quantity<LengthUnit>(24.0, LengthUnit.INCH); 
        Quantity<LengthUnit> q2 = new Quantity<LengthUnit>(2.0, LengthUnit.FEET); 

        assertEquals(1.0, q1.divide(q2));
    }

    @Test
    void testDivision_CrossUnit_KilogramDividedByGram(){
        Quantity<WeightUnit> q1 = new Quantity<WeightUnit>(2.0, WeightUnit.KILOGRAM); 
        Quantity<WeightUnit> q2 = new Quantity<WeightUnit>(2000.0, WeightUnit.GRAM); 

        assertEquals(1.0, q1.divide(q2));
    }

    @Test 
    void testDivision_RatioGreaterThanOne(){
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET); 
        Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET); 
        double result = a.divide(b); 
        
        assertEquals(5.0, result, EPSILON);
    }

    @Test 
    void testDivision_RatioLessThanOne() { 
        Quantity<LengthUnit> a = new Quantity<>(5.0, LengthUnit.FEET); 
        Quantity<LengthUnit> b = new Quantity<>(10.0, LengthUnit.FEET); 
        double result = a.divide(b); 
        
        assertEquals(0.5, result, EPSILON); 
    } 
        
    
    @Test 
    void testDivision_RatioEqualToOne() { 
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET); 
        Quantity<LengthUnit> b = new Quantity<>(10.0, LengthUnit.FEET); 
        double result = a.divide(b); 
        
        assertEquals(1.0, result, EPSILON); 
    }

    @Test
    void testDivision_NonCommutative(){
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET); 
        Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET); 

        assertNotEquals(q1.divide(q2), q2.divide(q1));
    }

    @Test 
    void testDivision_ByZero(){
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET); 
        Quantity<LengthUnit> q2 = new Quantity<>(0.0, LengthUnit.FEET); 

        assertThrows(ArithmeticException.class, () -> q1.divide(q2));
    }

}


