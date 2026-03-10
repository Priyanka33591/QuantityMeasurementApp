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
