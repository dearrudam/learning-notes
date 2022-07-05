///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 17+
//DEPS org.javamoney.moneta:moneta-core:1.4.2

import java.util.Arrays;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;

public class ImperativeDiscount {

    public static void main(String... args) {

        var currency = Monetary.getCurrency("BRL");

        final var prices = Arrays.asList(
                Money.of(10, currency),
                Money.of(30, currency),
                Money.of(17, currency),
                Money.of(20, currency),
                Money.of(15, currency),
                Money.of(18, currency),
                Money.of(45, currency),
                Money.of(12, currency));

        var totalOfDiscountedPrices = Money.of(0, currency);
        var totalOfPrices = Money.of(0, currency);

        for (MonetaryAmount price : prices) {
            if (price.compareTo(Money.of(18, currency)) > 0) {
                totalOfPrices = totalOfPrices.add(price);
                totalOfDiscountedPrices = totalOfDiscountedPrices.add(price.multiply(0.85));
            }
        }

        System.out.println("Total of prices:            " + totalOfPrices);
        System.out.println("Total of discounted prices: " + totalOfDiscountedPrices);
    }
}
