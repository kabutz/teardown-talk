package eu.javaspecialists.tjsn.talks.teardown.demos;

import eu.javaspecialists.tjsn.talks.teardown.util.Benchmark;

import java.util.*;
import java.util.concurrent.*;

public class LockSplittingDemo {
    protected static final int ELEMENTS = 10_000_000;
    protected static final int NUMBER_OF_CONSUMERS = 2;

    public static void main(String... args) throws InterruptedException {
        // TODO: submit 10m Integers to a BlockingQueue, with either SPSC or SPMC
        //  Use LinkedBlockingQueue, LinkedBlockingDeque and ArrayBlockingQueue

        // TODO: Show how the two locks work in enqueue and dequeue

        for (int i = 0; i < 10; i++) {
            // test(new LinkedBlockingQueue<>());
            // test(new LinkedBlockingDeque<>());
            test(new ArrayBlockingQueue<>(100_000));
        }
    }

    private static void test(BlockingQueue<Integer> q) {
        long time = System.nanoTime();
        List<Future<String>> futures = new ArrayList<>();
        try {
            System.out.println(q.getClass().getSimpleName());
            try (var executor = Executors.newCachedThreadPool()) {
                futures.add(executor.submit(() -> {
                    var bm = new Benchmark();
                    bm.start();
                    for (int i = 0; i < ELEMENTS; i++) {
                        q.put(42);
                    }
                    bm.stop();
                    return "producer: " + bm;
                }));
                for (int j = 0; j < NUMBER_OF_CONSUMERS; j++) {
                    futures.add(executor.submit(() -> {
                        var bm = new Benchmark();
                        bm.start();
                        for (int i = 0; i < ELEMENTS / NUMBER_OF_CONSUMERS; i++) {
                            q.take();
                        }
                        bm.stop();
                        return "consumer: " + bm;
                    }));
                }
            }
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
        futures.stream().map(Future::resultNow).forEach(System.out::println);
        System.out.println();
        System.gc();
    }
}