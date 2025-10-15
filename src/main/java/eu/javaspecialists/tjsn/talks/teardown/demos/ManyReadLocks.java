package eu.javaspecialists.tjsn.talks.teardown.demos;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.stream.*;

public class ManyReadLocks {
    public static void main(String[] args) {
        var rwlock = new ReentrantReadWriteLock();
        var maxReaders = new LongAccumulator(Long::max, 0L);
        var done = new AtomicBoolean(false);
        var error = new AtomicReference<Throwable>();
        try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
            var readers = 100_000;
            var count = new AtomicInteger();
            for (int i = 0; i < readers && error.get() == null; i++) {
                pool.submit(() -> {
                    try {
                        rwlock.readLock().lock();
                        try {
                            maxReaders.accumulate(rwlock.getReadLockCount());
                            count.incrementAndGet();
                            while (error.get() == null && count.get() < readers)
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
        System.out.println("maxReaders = " + maxReaders);
        if (error.get() != null) error.get().printStackTrace();
    }
}
