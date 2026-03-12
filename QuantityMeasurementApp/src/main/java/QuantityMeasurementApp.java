public class QuantityMeasurementApp {

    public static void main(String[] args) {
        // Quantity<LengthUnit> l1 = new Quantity<>(1, LengthUnit.FEET);
        // Quantity<LengthUnit> l2 = new Quantity<>(12, LengthUnit.INCH);

        // System.out.println("Length equal? " + l1.equals(l2));

        // Quantity<WeightUnit> w1 = new Quantity<>(1, WeightUnit.KILOGRAM);
        // Quantity<WeightUnit> w2 = new Quantity<>(1000, WeightUnit.GRAM);

        // System.out.println("Weight equal? " + w1.equals(w2));

        // System.out.println("Convert 1 foot to inches: " + l1.convertTo(LengthUnit.INCH));

        // System.out.println("Add lengths: " + l1.add(l2));

        // System.out.println("Add weights in grams: " + w1.add(w2, WeightUnit.GRAM));

        // Quantity<VolumeUnit> v1 = new Quantity<>(1, VolumeUnit.LITRE);
        // Quantity<VolumeUnit> v2 = new Quantity<>(2, VolumeUnit.MILLILITRE);
        // System.out.println(v1.equals(v2));

        // Quantity<VolumeUnit> v3 = new Quantity<>(1, VolumeUnit.LITRE);
        // Quantity<VolumeUnit> v4 = new Quantity<>(1000, VolumeUnit.MILLILITRE);
        // System.out.println(v3.equals(v4));

        // System.out.println(v1.convertTo(VolumeUnit.GALLON));
        // System.out.println(v2.add(v3));
        // System.out.println(v3.add(v4, VolumeUnit.GALLON));

        // System.out.println("Subtract: " + l1.subtract(l2));
        // System.out.println("Subtract in inches: " + l1.subtract(l2, LengthUnit.INCH));

        // System.out.println("Division ratio: " + w1.divide(w2));

        Quantity<TemperatureUnit> t1 = new Quantity<>(0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> t2 = new Quantity<>(32, TemperatureUnit.FAHRENHEIT);

        System.out.println(t1.equals(t2));       
        System.out.println(t1.convertTo(TemperatureUnit.FAHRENHEIT)); 

        //System.out.println(t1.add(t2));
    }
}