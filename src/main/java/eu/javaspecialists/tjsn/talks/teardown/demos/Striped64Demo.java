package eu.javaspecialists.tjsn.talks.teardown.demos;

import java.util.concurrent.atomic.*;
import java.util.stream.*;

public class Striped64Demo {
    private static class Counter {
        private volatile long value;
        public void increment() {
            value++;
        }
    }
    public static void main(String... args) {
        // TODO: Compare time taken to increment 100m times in parallel
        //  using AtomicLong and LongAdder

        // TODO: Quick look at how Striped64 works

        for (int i = 0; i < 10; i++) {
            testSequential();
            // testParallel();
        }
    }

    private static void testParallel() {
        long adderTime = System.nanoTime();
        try {
            var adder = new LongAdder();
            IntStream.range(0, 100_000_000).parallel()
                    .forEach(_ -> adder.increment());
        } finally {
            adderTime = System.nanoTime() - adderTime;
            System.out.printf("adderTime = %dms%n", (adderTime / 1_000_000));
        }

        long atomicTime = System.nanoTime();
        try {
            var atomic = new AtomicLong();
            IntStream.range(0, 100_000_000).parallel()
                    .forEach(_ -> atomic.incrementAndGet());
        } finally {
            atomicTime = System.nanoTime() - atomicTime;
            System.out.printf("atomicTime = %dms%n", (atomicTime / 1_000_000));
        }
    }
    private static void testSequential() {
        long adderTime = System.nanoTime();
        try {
            var adder = new LongAdder();
            for (int i = 0; i < 100_000_000; i++) {
                adder.increment();
            }
        } finally {
            adderTime = System.nanoTime() - adderTime;
            System.out.printf("adderTime = %dms%n", (adderTime / 1_000_000));
        }

        long atomicTime = System.nanoTime();
        try {
            var atomic = new AtomicLong();
            for (int i = 0; i < 100_000_000; i++) {
                atomic.incrementAndGet();
            }
        } finally {
            atomicTime = System.nanoTime() - atomicTime;
            System.out.printf("atomicTime = %dms%n", (atomicTime / 1_000_000));
        }
        long counterTime = System.nanoTime();
        try {
            var counter = new Counter();
            for (int i = 0; i < 100_000_000; i++) {
                counter.increment();
            }
        } finally {
            counterTime = System.nanoTime() - counterTime;
            System.out.printf("counterTime = %dms%n", (counterTime / 1_000_000));
        }

    }
}