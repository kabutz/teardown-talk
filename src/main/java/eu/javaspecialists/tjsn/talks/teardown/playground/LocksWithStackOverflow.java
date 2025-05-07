package eu.javaspecialists.tjsn.talks.teardown.playground;


import java.util.concurrent.locks.*;

public class LocksWithStackOverflow {
    public static void main(String... args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
            Thread.ofPlatform().start(() -> {
                deep(lock);
            }).join();
        }
    }

    private static void deep(Lock lock) {
        lock.lock();
        try {
            deep(lock);
        } finally {
            lock.unlock();
        }
    }
}
