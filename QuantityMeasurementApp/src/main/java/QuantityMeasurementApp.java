public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Quantity<LengthUnit> l1 = new Quantity<>(1, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(12, LengthUnit.INCH);

        System.out.println("Length equal? " + l1.equals(l2));

        Quantity<WeightUnit> w1 = new Quantity<>(1, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000, WeightUnit.GRAM);

        System.out.println("Weight equal? " + w1.equals(w2));

        System.out.println("Convert 1 foot to inches: " + l1.convertTo(LengthUnit.INCH));

        System.out.println("Add lengths: " + l1.add(l2));

        System.out.println("Add weights in grams: " + w1.add(w2, WeightUnit.GRAM));
    }
}