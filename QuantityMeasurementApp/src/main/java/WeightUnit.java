public enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double toKGFactor;

    WeightUnit(double toKGFactor){
        this.toKGFactor = toKGFactor;
    }

    public double convertToKg(double value){
        return value * toKGFactor;
    }

    public double convertFromKg(double value){
        return value/toKGFactor;
    }

    public double getToKgFactor() { return toKGFactor; }
}
