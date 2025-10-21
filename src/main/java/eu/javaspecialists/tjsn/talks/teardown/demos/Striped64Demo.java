package eu.javaspecialists.tjsn.talks.teardown.demos;

import java.util.concurrent.atomic.*;
import java.util.stream.*;

public class Striped64Demo {
    public static void main(String... args) {
        for (int i = 0; i < 10; i++) {
            test();
        }
    }

    private static void test() {
        // // TODO: Compare time taken to increment 100m times in parallel
        // //  using AtomicLong and LongAdder
        // long atomicTime = System.nanoTime();
        // try {
        //     var atomicLong = new AtomicLong();
        //     IntStream.range(0, 100_000_000)
        //             .parallel()
        //             .forEach(_ -> atomicLong.getAndIncrement());
        // } finally {
        //     atomicTime = System.nanoTime() - atomicTime;
        //     System.out.printf("atomicTime = %dms%n", (atomicTime / 1_000_000));
        // }

        // TODO: Quick look at how Striped64 works
        long adderTime = System.nanoTime();
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