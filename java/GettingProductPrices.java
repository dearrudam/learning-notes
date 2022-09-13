///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 17+
//DEPS org.javamoney.moneta:moneta-core:1.4.2

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;

public class GettingProductPrices {

    record Product(String description, MonetaryAmount price) { }

    public static void main(String... args) {

        var currency = Monetary.getCurrency("BRL");
        var products = List.of(
                new Product("bean", Money.of(5.99, currency)),
                new Product("rice", Money.of(12.49, currency)),
                new Product("coffee", Money.of(18.99, currency)),
                new Product("bread", Money.of(6.59, currency)),
                new Product("chocolate", Money.of(6.80, currency)));

        gettingProdutPricesByUsingImperativeWay(products);

        gettingProdutPricesByUsingForEach(products);

        gettingProdutPricesByUsingStreamsWithLambdaExpression(products);

        gettingProdutPricesByUsingStreamsWithMethodReference(products);

    }

    private static void gettingProdutPricesByUsingImperativeWay(List<Product> products) {
        System.out.println("getting the product prices by using an imperative way:");

        List<MonetaryAmount> prices = new ArrayList<>();
        for (Product product : products) {
            prices.add(product.price());
        }
        System.out.println(prices);

        System.out.println();
    }

    private static void gettingProdutPricesByUsingForEach(List<Product> products) {
        System.out.println("getting the product prices by using Iterator.forEach method:");

        List<MonetaryAmount> prices = new ArrayList<>();
        products.forEach(product -> prices.add(product.price()));

        System.out.println(prices);

        System.out.println("");

    }

    private static void gettingProdutPricesByUsingStreamsWithLambdaExpression(List<Product> products) {
        System.out.println("getting the product prices by using Streams API with lambda expression:");

        List<MonetaryAmount> prices = products
                .stream()
                .map(product -> product.price)
                .collect(Collectors.toList());

        System.out.println(prices);

        System.out.println("");

    }

    private static void gettingProdutPricesByUsingStreamsWithMethodReference(List<Product> products) {
        System.out.println("getting the product prices by using Streams API with Method Reference:");

        List<MonetaryAmount> prices = products
                .stream()
                .map(Product::price)
                .collect(Collectors.toList());

        System.out.println(prices);

        System.out.println("");

    }

}
