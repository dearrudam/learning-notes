///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 17+
//DEPS org.javamoney.moneta:moneta-core:1.4.2

import java.util.Arrays;

import javax.money.Monetary;

import org.javamoney.moneta.Money;

public class DeclarativeDiscount {

    public static void main(String... args) {

        var currency = Monetary.getCurrency("BRL");

        var prices = Arrays.asList(
                Money.of(10, currency),
                Money.of(30, currency),
                Money.of(17, currency),
                Money.of(20, currency),
                Money.of(15, currency),
                Money.of(18, currency),
                Money.of(45, currency),
                Money.of(12, currency));

        var totalOfDiscountedPrices = prices
                .stream()
                .filter(price -> price.compareTo(Money.of(18, currency)) > 0)
                .map(price -> price.multiply(0.85))
                .reduce(Money.of(0, currency), Money::add);

        System.out.println("Total of discounted prices: " + totalOfDiscountedPrices);
    }
}
