package eu.javaspecialists.tjsn.talks.teardown.demos;


import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class ManyReadLocks {
    public static void main(String[] args) {
        var rwlock = new ReentrantReadWriteLock();
        AtomicInteger readerCount = new AtomicInteger();
        AtomicReference<Throwable> error = new AtomicReference<>();
        try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
            int readers = 100_000;
            for (int i = 0; i < readers && error.get() == null; i++) {
                pool.submit(() -> {
                    try {
                        rwlock.readLock().lock();
                        try {
                            readerCount.incrementAndGet();
                            while(readerCount.get() < readers && error.get() == null)
                                Thread.sleep(100);
                        } finally {
                            rwlock.readLock().unlock();
                        }
                    } catch (Throwable e) {
                        error.set(e);
                    }
                });
            }
        }
        System.out.println("readerCount = " + readerCount);
        if (error.get() != null) error.get().printStackTrace();
    }
}
