///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 17+

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class CheckingPerformance {

    public static void main(String... args) {

        List<Long> numbers = LongStream
                .rangeClosed(0, 1_000_000)
                .boxed()
                .collect(Collectors.toList());

        execute(numbers, "counting primes by using for loop", CheckingPerformance::countingPrimesForLoop);
        System.out.println();
        execute(numbers, "counting primes by using serial stream",CheckingPerformance::countingPrimesSerialStream);
        System.out.println();
        execute(numbers, "counting primes by using parallel stream" , CheckingPerformance::countingPrimesParallelStream);
    }

    private static void execute(List<Long> numbers, String approach, Consumer<List<Long>> consumer) {
        Instant start = Instant.now();
        consumer.accept(numbers);
        Instant end = Instant.now();
        System.out.println("%s:\n%s".formatted(approach, Duration.between(start, end)));
    }

    private static void countingPrimesForLoop(List<Long> numbers) {
        long count = 0;
        for (long number : numbers) {
            if (isPrime(number)) {
                count++;
            }
        }
    }

    private static void countingPrimesSerialStream(List<Long> numbers) {
        numbers.stream()
                .filter(i -> isPrime(i))
                .count();
    }

    private static void countingPrimesParallelStream(List<Long> numbers) {
        numbers.parallelStream()
                .filter(i -> isPrime(i))
                .count();
    }

    private static boolean isPrime(long number) {
        if (number < 1) {
            return false;
        }

        for (int i = 2; i < Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
