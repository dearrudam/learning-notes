///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 17+

import java.util.*;

public class ImperativeSample01 {

    public static void main(String... args) {

        var cities = List.of("Campinas", "Jundiaí", "Santos", "Santo André", "São Caetano do Sul", "São Paulo");

        boolean found = false;

        for (String city : cities) {
            if ("São Paulo".equals(city)) {
                found = true;
                break;
            }
        }

        System.out.println("Found São Paulo? " + found);
    }
}
