package eu.javaspecialists.tjsn.talks.teardown.demos;

import java.util.concurrent.atomic.*;
import java.util.stream.*;

public class Demo1_Striped64 {
    public static void main(String... args) {
        // link: https://javaspecialists.teachable.com/p/data-structures?coupon_code=COMMERZBANK-2026

        // TODO: Compare time taken to increment 100m times in parallel
        //  using AtomicLong and LongAdder
        // for (int i = 0; i < 10; i++) testAtomic(); // 3.2s

        // TODO: Quick look at how Striped64 works
        for (int i = 0; i < 30; i++) testAdder();}

    private static void testAdder() {
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

    private static void testAtomic() {
        var atomicTime = System.nanoTime();
        try {
            var atomicLong = new AtomicLong();
            IntStream.range(0, 100_000_000)
                    .parallel()
                    .forEach(_ -> atomicLong.getAndIncrement());
        } finally {
            atomicTime = System.nanoTime() - atomicTime;
            System.out.printf("atomicTime = %dms%n", (atomicTime / 1_000_000));
        }
    }
}