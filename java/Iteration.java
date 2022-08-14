
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 17+
import java.util.List;
import java.util.function.Consumer;

/**
 * Iteration
 */
public class Iteration {

    public static void main(String[] args) {

        final List<String> developers = List.of(
                "Maximillian",
                "Otavio Santana",
                "Bruno Souza",
                "Elder Moraes",
                "Sérgio Lopes",
                "Fernando Boaglio");

        for (int i = 0; i < developers.size(); i++) {
            System.out.println(developers.get(i));
        }
        
        System.out.println();

        // another way to iterate using imperative style
        for (String developer : developers) {
            System.out.println(developer);
        }

        System.out.println();

        // using forEach
        developers.forEach(new Consumer<String>() {
            @Override
            public void accept(final String developer) {
                System.out.println(developer);
            }
        });

        System.out.println();
        
        // using forEach with Lambda Expression
        developers.forEach((final String developer) -> System.out.println(developer));


        System.out.println();
        
        // using forEach with Lambda Expression + type inference
        developers.forEach((developer) -> System.out.println(developer));


        System.out.println();
        
        // using forEach with Lambda Expression + type inference +  without parentheses
        developers.forEach((developer) -> System.out.println(developer));

        
        System.out.println();
        
        // using forEach with Method References
        developers.forEach(System.out::println);
    }

}