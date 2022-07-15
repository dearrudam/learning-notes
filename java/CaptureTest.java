
///usr/bin/env jbang "$0" "$@" ; exit $?
//REFs: 
// https://bugs.openjdk.org/browse/JDK-8023524
// https://stackoverflow.com/questions/41570839/finding-a-java-lambda-from-its-mangled-name-in-a-heap-dump

import java.util.function.*;
import java.util.*;

public class CaptureTest {

    static List<IntSupplier> list;

    static IntSupplier foo(boolean b, Object o) {
        if (b) {
            return () -> 0;
        }
        int h = o.hashCode();
        return () -> h;
    }

    static IntSupplier bar(boolean b, Object o) {
        if (b) {
            return () -> o.hashCode();
        }
        int len = o.toString().length();
        return () -> len;
    }

    static void run() {
        Object big = new byte[10_000_000];
        list = Arrays.asList(
                bar(false, big),
                bar(true, big),
                foo(false, big),
                foo(true, big));
        System.out.println("Done!");
    }

    public static void main(String... args) throws InterruptedException {
        run();
        Thread.sleep(Long.MAX_VALUE); // stay alive so a heap dump can be taken
    }
}
