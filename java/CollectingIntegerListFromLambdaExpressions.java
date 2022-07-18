///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 17+
import java.util.Arrays;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;

public class CollectingIntegerListFromLambdaExpressions {

    static List<IntSupplier> list;

    static IntSupplier suppliersA(Object o) {
        return () -> 0;
    }

    static IntSupplier suppliersB(Object o) {
        int h = o.hashCode();
        return () -> h;
    }

    static IntSupplier suppliersC(Object o) {
        return () -> o.hashCode();
    }

    static IntSupplier suppliersD(Object o) {
        int len = o.toString().length();
        return () -> len;
    }

    static void run() {
        Object big = new byte[10_000_000];
        list = Arrays.asList(
                suppliersA(big),
                suppliersB(big),
                suppliersC(big),
                suppliersD(big));
        System.out.println("It's done! A list of integers has been created!");
        System.out.println("the generated list of integers is : %s"
                .formatted(list.stream().map(IntSupplier::getAsInt).collect(Collectors.toList())));
    }

    public static void main(String... args) throws InterruptedException {
        run();
        System.out.println("""
            Keeping the application alive in order to let aheap dump be taken.
            Press CTRL+C to close the application.
            """);
        Thread.sleep(Long.MAX_VALUE);
    }
}