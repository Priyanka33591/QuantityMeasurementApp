public class QuantityMeasurementApp {
    public static void demonstrateLengthConversion(double value, Length.LengthUnit from, Length.LengthUnit to) {
        double result = Length.convert(value, from, to);
        System.out.println(value + " " + from + " = " + result + " " + to);
    }

    public static void demonstrateLengthConversion(Length q, Length.LengthUnit to) {
        Length converted = q.convertTo(to);
        System.out.println(q + " = " + converted);
    }

    public static void main(String[] args) {
        QuantityMeasurementApp.demonstrateLengthConversion(1.0, Length.LengthUnit.FEET, Length.LengthUnit.INCH);
        QuantityMeasurementApp.demonstrateLengthConversion(3.0, Length.LengthUnit.YARDS, Length.LengthUnit.FEET);
        QuantityMeasurementApp.demonstrateLengthConversion(36.0, Length.LengthUnit.INCH, Length.LengthUnit.YARDS);
        QuantityMeasurementApp.demonstrateLengthConversion(1.0, Length.LengthUnit.CENTIMETER, Length.LengthUnit.INCH);

        Length q = new Length(2.0, Length.LengthUnit.YARDS);
        QuantityMeasurementApp.demonstrateLengthConversion(q, Length.LengthUnit.INCH);
    }
}