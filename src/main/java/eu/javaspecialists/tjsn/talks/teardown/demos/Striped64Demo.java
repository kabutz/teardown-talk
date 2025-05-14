package eu.javaspecialists.tjsn.talks.teardown.demos;

import java.util.concurrent.atomic.*;
import java.util.stream.*;

public class Striped64Demo {
    public static void main(String... args) {
        // TODO: Compare time taken to increment 100m times in parallel
        //  using AtomicLong and LongAdder

        long atomicTime = System.nanoTime();
        try {
            var atomic = new AtomicLong();
            IntStream.range(0, 100_000_000).parallel()
                    .forEach(_ -> atomic.incrementAndGet());
        } finally {
            atomicTime = System.nanoTime() - atomicTime;
            System.out.printf("atomicTime = %dms%n", (atomicTime / 1_000_000));
        }

        long adderTime = System.nanoTime();
        try {
            var adder = new LongAdder();
            IntStream.range(0, 100_000_000).parallel()
                    .forEach(_ -> adder.increment());
        } finally {
            adderTime = System.nanoTime() - adderTime;
            System.out.printf("adderTime = %dms%n", (adderTime / 1_000_000));
        }

        // TODO: Quick look at how Striped64 works
    }
}