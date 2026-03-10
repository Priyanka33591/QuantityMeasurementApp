import java.util.Objects;

public class Weight {
    private final double value;
    private final WeightUnit unit;

    private static final double EPSILON = 0.000001;

    public Weight(double value, WeightUnit unit){
        if(unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if(!Double.isFinite(value)) throw new IllegalArgumentException("Invalid number");

        this.value = value;
        this.unit = unit;
    }

    public double toBaseValue(){
        return unit.convertToKg(value);
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Weight other = (Weight) obj;
        return Math.abs(this.toBaseValue() - other.toBaseValue()) < EPSILON;
    }

    @Override
    public int hashCode() {
        long rounded = Math.round(toBaseValue() / EPSILON);
        return Objects.hash(rounded);
    }

    public Weight convertTo(WeightUnit targetUnit) {
        if(targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");

        double base = this.toBaseValue();
        double converted = targetUnit.convertFromKg(base);

        double rounded = Math.round(converted * 1000.0) / 1000.0;

        return new Weight(rounded, targetUnit);
    }

    public Weight add(Weight other) {
        return add(this, other, this.unit);
    }

    public static Weight add(Weight w1, Weight w2, WeightUnit targetUnit) {
        if(w1 == null || w2 == null) throw new IllegalArgumentException("Quantity cannot be null");
        if(targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");

        double sumKg = w1.toBaseValue() + w2.toBaseValue();

        double converted = targetUnit.convertToKg(sumKg);
        double rounded = Math.round(converted * 1000.0) / 1000.0;

        return new Weight(rounded, targetUnit);
    }

    @Override
    public String toString() {
        return String.format("%.3f %s", value, unit);
    }
}

