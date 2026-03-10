import java.util.Objects;

public class Length {
    private final double value;
    private final LengthUnit unit;

    public enum LengthUnit {
        FEET(1.0),
        INCH(1.0/12),
        YARDS(3.0),
        CENTIMETER(0.03280839895);

        private final double convertToFeet;

        LengthUnit(double convertToFeet){
            this.convertToFeet = convertToFeet;
        }

        public double toFeet(double value){
            return value * convertToFeet;
        }

        public double fromFeet(double value){
            return value/convertToFeet;
        }

        public double getFeetFactor(){ return convertToFeet; }

    }

    private static final double EPSILON = 1e-6;

    public Length(double value, LengthUnit unit){
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");

        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid numeric value");

        this.value = value;
        this.unit = unit;
    }

    private double toBaseValue(){
        return unit.toFeet(value);
    }

    public static double convert(double value, LengthUnit source, LengthUnit target) {
        if (source == null || target == null) throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid numeric value");

        double feetValue = source.toFeet(value);
        return target.fromFeet(feetValue);
    }

    public Length convertTo(LengthUnit targetUnit) {
        double converted = convert(this.value, this.unit, targetUnit);
        return new Length(converted, targetUnit);
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Length other = (Length) obj;
        return Double.compare(this.toBaseValue(), other.toBaseValue()) < EPSILON;
    }

    @Override
    public int hashCode(){
        return Objects.hash(toBaseValue());
    }
}
