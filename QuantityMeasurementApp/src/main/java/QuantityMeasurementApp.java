public class QuantityMeasurementApp {
    public static void main(String[] args) {
        Length feet = new Length(1.0, LengthUnit.FEET);
        Length inch = new Length(12.0, LengthUnit.INCH);

        System.out.println("UC6 result (default LengthUnit): " + feet.add(inch));

        System.out.println("Target FEET: " + Length.add(feet, inch, LengthUnit.FEET));
        System.out.println("Target INCH: " + Length.add(feet, inch, LengthUnit.INCH));
        System.out.println("Target YARDS: " + Length.add(feet, inch, LengthUnit.YARDS));
        System.out.println("Target CM: " + Length.add(feet, inch, LengthUnit.CENTIMETER));

    }
}
