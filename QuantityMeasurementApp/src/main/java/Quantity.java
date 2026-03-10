import java.util.Objects;

public class Quantity<U extends IMeasurable> {
    private final double value;
    private final U unit;

    public Quantity(double value, U unit){
        if(unit ==  null) throw new IllegalArgumentException("Unit cannot be null!");
        if(!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite!");

        this.value = value;
        this.unit = unit;
    }

    private double toBaseValue(){
        double base = unit.convertToBase(value);
        return Math.round(base * 100000.0) / 100000.0;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Quantity<?> other = (Quantity<?>) obj;
        if(!this.unit.getClass().equals(other.unit.getClass())) return false;

        return Double.compare(this.toBaseValue(), other.toBaseValue()) == 0;
    }

    @Override
    public int hashCode(){
        return Objects.hash(toBaseValue(), unit.getClass());
    }

    public Quantity<U> convertTo(U targetUnit){
        if(targetUnit == null){ throw new IllegalArgumentException("Target unit cannot be null!");}
        double base = this.toBaseValue();
        double converted = targetUnit.convertFromBase(base);
        double rounded = Math.round(converted * 100.0)/ 100.0;

        return new Quantity<>(rounded, targetUnit);
    }

    public Quantity<U> add(Quantity<U> other){
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit){
        if(other == null) throw new IllegalArgumentException("Other quantity cannot be null!");
        if(!this.unit.getClass().equals(other.unit.getClass())) throw new IllegalArgumentException("Cannot add different measurements");

        double sum = this.toBaseValue() + other.toBaseValue();
        double converted = targetUnit.convertFromBase(sum);

        double rounded = Math.round(converted * 100.0) / 100.0;

        return new Quantity<>(rounded, targetUnit);
    }

    @Override
    public String toString(){
        return String.format("%.2f %s", value, unit.getUnitName());
    }
}
