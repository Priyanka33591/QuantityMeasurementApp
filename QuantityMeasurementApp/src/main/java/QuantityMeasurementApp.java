public class QuantityMeasurementApp {
    public static void main(String[] args) {
        Weight kg = new Weight(1, WeightUnit.KILOGRAM);
        Weight g = new Weight(1000, WeightUnit.GRAM);
        Weight lb = new Weight(2.20462, WeightUnit.POUND);

        System.out.println("1kg == 1000g : " + kg.equals(g));
        System.out.println("1kg == 2.20462lb : " + kg.equals(lb));

        System.out.println("1kg + 500g = " + kg.add(new Weight(500, WeightUnit.GRAM)));
        System.out.println("1kg + 1lb (in pounds) = " +
        Weight.add(kg, new Weight(1, WeightUnit.POUND), WeightUnit.POUND));

    }
}
