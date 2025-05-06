package eu.javaspecialists.tjsn.talks.teardown.util;

import javax.management.*;
import java.lang.management.*;
import java.util.concurrent.atomic.*;

public class ByteWatcherSingleThread {
    private static final String ALLOCATED = " allocated ";
    private static final String GET_THREAD_ALLOCATED_BYTES =
        "getThreadAllocatedBytes";
    private static final String[] SIGNATURE =
        new String[]{long.class.getName()};
    private static final MBeanServer mBeanServer;
    private static final ObjectName name;

    private final String threadName;
    private final Thread thread;

    private final Object[] PARAMS;
    private final AtomicLong allocated = new AtomicLong();
    private final static long TYPICAL_COST_IN_BYTES = 336;
    private final long tid;
    private final boolean checkThreadSafety;

    static {
        try {
            name = new ObjectName(
                ManagementFactory.THREAD_MXBEAN_NAME);
            mBeanServer = ManagementFactory.getPlatformMBeanServer();
        } catch (MalformedObjectNameException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public ByteWatcherSingleThread() {
        this(Thread.currentThread(), true);
    }

    private ByteWatcherSingleThread(
        Thread thread, boolean checkThreadSafety) {
        this.checkThreadSafety = checkThreadSafety;
        this.tid = thread.getId();
        this.thread = thread;
        threadName = thread.getName();
        PARAMS = new Object[]{tid};
        reset();
    }

    public void reset() {
        checkThreadSafety();
        allocated.set(threadAllocatedBytes());
    }

    long threadAllocatedBytes() {
        try {
            return (long) mBeanServer.invoke(
                name,
                GET_THREAD_ALLOCATED_BYTES,
                PARAMS,
                SIGNATURE
            );
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Calculates the number of bytes allocated since the last
     * reset().
     */
    public long calculateAllocations() {
        checkThreadSafety();
        long mark1 = ((threadAllocatedBytes() -
                TYPICAL_COST_IN_BYTES) - allocated.get());
        return mark1;
    }

    private void checkThreadSafety() {
        if (checkThreadSafety &&
            tid != Thread.currentThread().getId())
            throw new IllegalStateException(
                "AllocationMeasure must not be " +
                    "used over more than 1 thread.");
    }

    public Thread getThread() {
        return thread;
    }

    public String toString() {
        return thread.getName() + ALLOCATED + calculateAllocations();
    }
}
