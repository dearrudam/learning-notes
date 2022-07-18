///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 17+

import java.util.List;
import java.util.function.Consumer;

public class CreatingLambdaExpression {

    public static void main(String... args) {

        Consumer<String> printer = (text) -> System.out.println(text);

        List.of("Wellington", "João Vitor", "Maximillian")
                .forEach(printer);

    }
}
