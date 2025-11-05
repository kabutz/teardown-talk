package eu.javaspecialists.tjsn.talks.teardown.demos;

import java.util.concurrent.atomic.*;
import java.util.stream.*;

public class Striped64Demo {
    public static void main(String... args) {
        // TODO: Compare time taken to increment 100m times in parallel
        //  using AtomicLong and LongAdder
/*        var adderTime = System.nanoTime();
        try {
            // 5.2s
            var atomicLong = new AtomicLong();
            IntStream.range(0, 100_000_000)
                    .parallel()
                    .forEach(_ -> atomicLong.getAndIncrement());
        } finally {
            adderTime = System.nanoTime() - adderTime;
            System.out.printf("adderTime = %dms%n", (adderTime / 1_000_000));
        }

        class LongCounter {
            private volatile long value;

            public synchronized long getAndIncrement() {
                return value++;
            }

            public long getValue() {
                return value;
            }
        }
        for (int i = 0; i < 5; i++) {
            var counterTime = System.nanoTime();
            try {
                var counter = new LongCounter();
                IntStream.range(0, 100_000_000)
                        .parallel()
                        .forEach(_ -> counter.getAndIncrement());
            } finally {
                counterTime = System.nanoTime() - counterTime;
                System.out.printf("counterTime = %dms%n", (counterTime / 1_000_000));
            }
        }
 */
        // TODO: Quick look at how Striped64 works
        for (int i=0; i<5; i++) {
            // 60m
            var adderTime = System.nanoTime();
            try {
                var longAdder = new LongAdder();
                IntStream.range(0, 100_000_000)
                        .parallel()
                        .forEach(_ -> longAdder.increment());
            } finally {
                adderTime = System.nanoTime() - adderTime;
                System.out.printf("adderTime = %dms%n", (adderTime / 1_000_000));
            }
        }
    }
}