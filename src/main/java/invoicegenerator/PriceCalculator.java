package invoicegenerator;

public class PriceCalculator {

    private static final double PRICE_PER_KWH = 0.51;

    private PriceCalculator() {}

    public static double getPriceForKwh(long kwh) {
        return PRICE_PER_KWH * kwh;
    }

}
