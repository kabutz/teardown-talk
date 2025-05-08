package eu.javaspecialists.tjsn.talks.teardown.demos;

import java.util.concurrent.atomic.*;
import java.util.stream.*;

public class Striped64Demo {
    public static void main(String... args) {
        // TODO: Compare time taken to increment 100m times in parallel
        //  using AtomicLong and LongAdder

        long noContTime = System.nanoTime();
        try {
            // var myadder = new AtomicLong();
            var myadder = new LongAdder();
            for (int i = 0; i < 100_000_000; i++) {
                myadder.increment();
                // myadder.incrementAndGet();
            }
            System.out.println(myadder.longValue());
        } finally {
            noContTime = System.nanoTime() - noContTime;
            System.out.printf("noContTime = %dms%n", (noContTime / 1_000_000));
        }

        long time = System.nanoTime();
        try {
            var atomic = new AtomicLong();
            IntStream.range(0, 100_000_000).parallel()
                    .forEach(i -> atomic.incrementAndGet());
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }

        long adderTime = System.nanoTime();
        try {
            var adder = new LongAdder();
            IntStream.range(0, 100_000_000).parallel()
                    .forEach(i -> adder.increment());
            System.out.println(adder.longValue());
        } finally {
            adderTime = System.nanoTime() - adderTime;
            System.out.printf("adderTime = %dms%n", (adderTime / 1_000_000));
        }
        // TODO: Quick look at how Striped64 works
    }
}