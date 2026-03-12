public enum VolumeUnit implements IMeasurable{
    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541);

    private final double conversionFactor;
    private static final SupportsArithmetic sm = () -> true;

    VolumeUnit(double conversionFactor){
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double convertToBase(double value){
        return value * conversionFactor;
    }

    @Override
    public double convertFromBase(double value){
        return value / conversionFactor;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public String getUnitName(){
        return this.name();
    }

}
