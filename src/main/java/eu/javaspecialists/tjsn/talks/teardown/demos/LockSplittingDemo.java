package eu.javaspecialists.tjsn.talks.teardown.demos;

import java.util.concurrent.*;

public class LockSplittingDemo {
    public static void main(String... args) throws InterruptedException {
        // TODO: submit 10m Integers to a BlockingQueue, with either SPSC or SPMC
        //  Use LinkedBlockingQueue, LinkedBlockingDeque and ArrayBlockingQueue

        for (int i = 0; i < 10; i++) {
            // test(new ArrayBlockingQueue<>(10_000), 2);
            test(new LinkedBlockingDeque<>(), 2);
            // test(new LinkedBlockingQueue<>(), 1);
        }
        // TODO: Show how the two locks work in enqueue and dequeue
    }

    private static void test(BlockingQueue<Integer> queue, int consumerCount) {
        System.out.println(queue.getClass().getSimpleName());
        long time = System.nanoTime();
        try {
            try (var pool = Executors.newCachedThreadPool()) {
                pool.submit(() -> {
                    for (int i = 0; i < 10_000_000; i++) {
                        try {
                            queue.put(42);
                        } catch (InterruptedException e) {
                            throw new CancellationException("interrupted");
                        }
                    }
                });
                for (int c = 0; c < consumerCount; c++) {
                    pool.submit(() -> {
                        for (int i = 0; i < 10_000_000 / consumerCount; i++) {
                            try {
                                queue.take();
                            } catch (InterruptedException e) {
                                throw new CancellationException("interrupted");
                            }
                        }
                    });
                }
            }
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }

        for (int i = 0; i < 3; i++) {
            System.gc();
        }
    }
}