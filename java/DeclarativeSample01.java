///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 17+

import java.util.*;

public class DeclarativeSample01 {

    public static void main(String... args) {

        var cities = List.of("Campinas", "Jundiaí", "Santos", "Santo André", "São Caetano do Sul", "São Paulo");

        System.out.println("Found São Paulo? " + cities.contains("São Paulo"));
    }
}
