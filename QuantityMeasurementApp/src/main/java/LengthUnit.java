public enum LengthUnit implements IMeasurable{
    FEET(1.0),
    INCH(1.0/12),
    YARDS(3.0),
    CENTIMETER(0.03280839895);

    private final double convertToFeet;

    private static final SupportsArithmetic sm = () -> true;

    LengthUnit(double convertToFeet){
        this.convertToFeet = convertToFeet;
    }

    @Override
    public double convertToBase(double value){
        return value * convertToFeet;
    }

    @Override
    public double convertFromBase(double value){
        return value/convertToFeet;
    }

    @Override
    public double getConversionFactor(){ return convertToFeet; }

    @Override
    public String getUnitName() {
        return this.name();
    }
}
