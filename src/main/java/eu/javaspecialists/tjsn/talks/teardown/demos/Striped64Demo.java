package eu.javaspecialists.tjsn.talks.teardown.demos;

import java.util.concurrent.atomic.*;
import java.util.stream.*;

public class Striped64Demo {
    public static void main(String... args) {
        // TODO: Compare time taken to increment 100m times in parallel
        //  using AtomicLong and LongAdder
        var atomicLong = new AtomicLong();
        IntStream.range(0, 100_000_000)
                .parallel()
                .forEach(_ -> atomicLong.getAndIncrement());

        // TODO: Quick look at how Striped64 works
        var longAdder = new LongAdder();
        IntStream.range(0, 100_000_000)
                .parallel()
                .forEach(_ -> longAdder.increment());
    }
}