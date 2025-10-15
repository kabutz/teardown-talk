package eu.javaspecialists.tjsn.talks.teardown.demos;

import java.util.concurrent.atomic.*;
import java.util.stream.*;

public class Striped64Demo {
    public static void main(String... args) {
        // TODO: Compare time taken to increment 100m times in parallel
        //  using AtomicLong and LongAdder
        long atomicTime = System.nanoTime();
        try {
            var atomicLong = new AtomicLong();
            IntStream.range(0, 100_000_000).parallel()
                    .forEach(i -> atomicLong.incrementAndGet());
        } finally {
            atomicTime = System.nanoTime() - atomicTime;
            System.out.printf("atomicTime = %dms%n", (atomicTime / 1_000_000));
        }

        long synchronizedTime = System.nanoTime();
        try {
            class SynchronizedCounter {
                private long count;

                public synchronized void increment() {
                    count++;
                }

                public synchronized long getCount() {
                    return count;
                }
            }
            var counter = new SynchronizedCounter();
            IntStream.range(0, 100_000_000).parallel()
                    .forEach(i -> counter.increment());
        } finally {
            synchronizedTime = System.nanoTime() - synchronizedTime;
            System.out.printf("synchronizedTime = %dms%n", (synchronizedTime / 1_000_000));
        }


        // TODO: Quick look at how Striped64 works
        long longAdderTime = System.nanoTime();
        try {
            var longAdder = new LongAdder();
            IntStream.range(0, 100_000_000).parallel()
                    .forEach(i -> longAdder.increment());
        } finally {
            longAdderTime = System.nanoTime() - longAdderTime;
            System.out.printf("longAdderTime = %dms%n", (longAdderTime / 1_000_000));
        }
    }
}