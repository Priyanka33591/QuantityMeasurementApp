public class QuantityMeasurementApp {
    public static void main(String[] args) {
        Length feet = new Length(1.0, Length.LengthUnit.FEET);
        Length inch = new Length(12.0, Length.LengthUnit.INCH);

        System.out.println("UC6 result (default Length.LengthUnit): " + feet.add(inch));

        System.out.println("Target FEET: " + Length.add(feet, inch, Length.LengthUnit.FEET));
        System.out.println("Target INCH: " + Length.add(feet, inch, Length.LengthUnit.INCH));
        System.out.println("Target YARDS: " + Length.add(feet, inch, Length.LengthUnit.YARDS));
        System.out.println("Target CM: " + Length.add(feet, inch, Length.LengthUnit.CENTIMETER));

    }
}
