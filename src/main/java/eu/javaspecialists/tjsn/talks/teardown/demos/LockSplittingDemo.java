package eu.javaspecialists.tjsn.talks.teardown.demos;

import eu.javaspecialists.tjsn.talks.teardown.util.*;

import java.util.concurrent.*;

public class LockSplittingDemo {

    protected static final int ELEMENTS = 10_000_000;

    public static void main(String... args) throws InterruptedException {
        // TODO: submit 10m Integers to a BlockingQueue, with either SPSC or SPMC
        //  Use LinkedBlockingQueue, LinkedBlockingDeque and ArrayBlockingQueue

        // TODO: Show how the two locks work in enqueue and dequeue

        for (int i = 0; i < 10; i++) {
            // test(new ArrayBlockingQueue<>(100_000));
            // test(new LinkedBlockingDeque<>());
            test(new LinkedBlockingQueue<>());
        }
    }

    private static void test(BlockingQueue<Integer> q) {
        long time = System.nanoTime();
        try {
            System.out.println(q.getClass().getSimpleName());
            try (var executor = Executors.newCachedThreadPool()) {
                executor.submit(() -> {
                    var bm = new Benchmark();
                    bm.start();
                    for (int i = 0; i < ELEMENTS; i++) {
                        try {
                            q.put(42);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    bm.stop();
                    System.out.println("producer: " + bm);
                });
                int numberOfConsumers = 2;
                for (int j = 0; j < numberOfConsumers; j++) {
                    executor.submit(() -> {
                        var bm = new Benchmark();
                        bm.start();
                        for (int i = 0; i < ELEMENTS/numberOfConsumers; i++) {
                            try {
                                q.take();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        bm.stop();
                        System.out.println("consumer: " + bm);
                    });
                }
            }
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
        System.gc();
    }
}