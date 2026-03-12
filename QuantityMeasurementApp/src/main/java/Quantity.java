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
       return createQuantityFromBase(this.toBaseValue(), targetUnit);
    }

    private enum ArithmeticOperation{
        ADD,
        SUBTRACT,
        DIVIDE,
        MULTIPLY;
    }

    private double operations(Quantity<U> other, ArithmeticOperation op){
        if(other == null) throw new IllegalArgumentException("Other quantity cannot be null");
        this.unit.validOperationSupport(op.name());
        if(!this.unit.getClass().equals(other.unit.getClass())) throw new IllegalArgumentException("Incompatible measurement category");

        double base1 = this.toBaseValue();
        double base2 = other.toBaseValue();

        if(!Double.isFinite(base1) || !Double.isFinite(base2)) throw new IllegalArgumentException("Non-finite numeric value");

        if(op == ArithmeticOperation.DIVIDE && base2 == 0.0){
            throw new ArithmeticException("Division by zero Quantity");
        }

        switch (op) {
            case ADD:
                return base1 + base2;
            case SUBTRACT:
                return base1 - base2;
            case DIVIDE:
                return base1 / base2;
            default:
                throw new IllegalArgumentException("Unknown arithemetic operation!");
        }
    }

    private Quantity<U> createQuantityFromBase(double value, U targetUnit){
        if(targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");
        double converted = targetUnit.convertFromBase(value);
        double rounded = Math.round(converted * 100.0)/100.0;

        return new Quantity<>(rounded, targetUnit);
    }

    public Quantity<U> add(Quantity<U> other){
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit){
        double sum = operations(other, ArithmeticOperation.ADD);
        return createQuantityFromBase(sum, targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other){
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit){
        double diff = operations(other, ArithmeticOperation.SUBTRACT);
        return createQuantityFromBase(diff, targetUnit);
    }

    public double divide(Quantity<U> other){
        return operations(other, ArithmeticOperation.DIVIDE);
    }

    @Override
    public String toString(){
        return String.format("%.2f %s", value, unit.getUnitName());
    }

}
