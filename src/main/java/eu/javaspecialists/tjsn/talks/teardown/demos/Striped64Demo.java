package eu.javaspecialists.tjsn.talks.teardown.demos;

import java.util.concurrent.atomic.*;
import java.util.stream.*;

public class Striped64Demo {
    public static void main(String... args) {
        // TODO: Compare time taken to increment 100m times in parallel
        //  using AtomicLong and LongAdder
        // var atomictime = System.nanoTime();
        // try {
        //     var atomicLong = new AtomicLong();
        //     IntStream.range(0, 100_000_000)
        //             .parallel()
        //             .forEach(_ -> atomicLong.getAndIncrement());
        // } finally {
        //     atomictime = System.nanoTime() - atomictime;
        //     System.out.printf("atomictime = %dms%n", (atomictime / 1_000_000));
        // }

        // // TODO: Quick look at how Striped64 works
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