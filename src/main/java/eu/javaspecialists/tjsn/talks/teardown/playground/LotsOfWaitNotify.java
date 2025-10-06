package eu.javaspecialists.tjsn.talks.teardown.playground;


import java.util.concurrent.*;

public class LotsOfWaitNotify {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        for (int i = 0; i < 10_000; i++) {
            Thread.startVirtualThread(() -> {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new CancellationException("interrupted");
                    }
                }
            });
        }

        Thread thread = Thread.startVirtualThread(
                () -> System.out.println("we are done - all good"));
        thread.join();
    }
}
