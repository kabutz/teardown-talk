package eu.javaspecialists.tjsn.talks.teardown.demos;


import java.math.BigInteger;
import java.util.concurrent.*;
import java.util.stream.*;

public class FactorialPuzzle {
    public static void main(String... args) {
        for (int i = 0; i <= 10; i++) {
            System.out.println(factorial(i));
        }
        long fastTime = System.nanoTime();
        try {
            System.out.println(factorialFast(2_000_000).bitLength());
        } finally {
            fastTime = System.nanoTime() - fastTime;
            System.out.printf("fastTime = %dms%n", (fastTime / 1_000_000));
        }
        long time = System.nanoTime();
        try {
            System.out.println(factorial(2_000_000).bitLength());
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
    }

    private static BigInteger factorialFast(int n) {
        return factorialFast(1, n);
    }

    private static BigInteger factorialFast(int from, int to) {
        if (from == to) return BigInteger.valueOf(from);
        int mid = (from + to) >>> 1;
        var left = new RecursiveTask<BigInteger>() {
            @Override
            protected BigInteger compute() {
                return factorialFast(from, mid);
            }
        };
        left.fork();
        var right = factorialFast(mid + 1, to);
        return left.join().multiply(right);
    }

    private static BigInteger factorial(int n) {
        if (n <= 1) return BigInteger.ONE;
        return IntStream.range(2, n)
                .mapToObj(BigInteger::valueOf)
                .reduce(BigInteger.ONE, BigInteger::multiply);
    }
}
