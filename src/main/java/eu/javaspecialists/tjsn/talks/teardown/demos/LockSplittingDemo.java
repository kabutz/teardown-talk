package eu.javaspecialists.tjsn.talks.teardown.demos;

import java.util.concurrent.*;

public class LockSplittingDemo {
    public static void main(String... args) throws InterruptedException {
        // TODO: submit 10m Integers to a BlockingQueue, with either SPSC or SPMC
        //  Use LinkedBlockingQueue, LinkedBlockingDeque and ArrayBlockingQueue

        // TODO: Show how the two locks work in enqueue and dequeue
        // var queue = new ArrayBlockingQueue<Integer>(10_000);
        // var queue = new LinkedBlockingDeque<Integer>();
        var queue = new LinkedBlockingQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            test(queue);
        }
    }

    private static void test(BlockingQueue<Integer> queue) {
        System.out.println(queue.getClass().getName());
        long time = System.nanoTime();
        try {
            try (var pool = Executors.newCachedThreadPool()) {
                pool.submit(() -> {
                    for (int i = 0; i < 10_000_000; i++) {
                        queue.put(42);
                    }
                    return "done";
                });
                int numberOfConsumers = 2;
                for (int c = 0; c < numberOfConsumers; c++) {
                    pool.submit(() -> {
                        for (int i = 0; i < 10_000_000 / numberOfConsumers; i++) {
                            queue.take();
                        }
                        return "done";
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