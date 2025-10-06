package eu.javaspecialists.tjsn.talks.teardown.playground;

import java.lang.management.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

public class ForEachDeadlocks {
    private static final Set<Long> knownDeadlockedThreads = new TreeSet<>();
    public static void main(String... args) {
        testForDeadlocksRunnable(() -> {
            System.out.println("Testing Vector.forEach() and nested contains()");
            var c1 = new Vector<String>();
            var c2 = new Vector<String>();
            Collections.addAll(c1, "a", "b", "c");
            Collections.addAll(c2, "d", "e", "f");
            Thread.Builder builder = Thread.ofVirtual();
            Thread t1 = builder
                    .name("c1")
                    .start(() -> c1.forEach(e -> {
                        LockSupport.parkNanos(10_000_000);
                        c2.contains(e);
                    }));
            Thread t2 = builder
                    .name("c2")
                    .start(() -> c2.forEach(e -> {
                        LockSupport.parkNanos(10_000_000);
                        c1.contains(e);
                    }));
            LockSupport.parkNanos(30_000_000);

            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                throw new CancellationException();
            }
        });
        // testForDeadlocksRunnable(() -> {
        //     // adapted from https://bugs.openjdk.org/browse/JDK-6205425
        //     System.out.println("Testing Vector.equals() between two vectors");
        //     var v1 = new Vector<String>();
        //     var v2 = new Vector<String>();
        //     Collections.addAll(v1, "a", "b", "c");
        //     Collections.addAll(v2, "d", "e", "f");
        //     Thread.ofPlatform().start(() -> {
        //         for (int i = 0; i < 10_000; i++) {
        //             v1.equals(v2);
        //         }
        //     });
        //     Thread.ofPlatform().start(() -> {
        //         for (int i = 0; i < 10_000; i++) {
        //             v2.equals(v1);
        //         }
        //     });
        //     LockSupport.parkNanos(30_000_000);
        // });

        // System.exit(0);
    }

    private static void testForDeadlocksRunnable(Runnable deadlockableCode) {
        deadlockableCode.run();
        if (printNewDeadlockedThreads())
            System.out.println("Deadlocked threads found");
        System.out.println();
    }

    private static final ThreadMXBean THREAD_MX_BEAN = ManagementFactory.getThreadMXBean();

    private static boolean printNewDeadlockedThreads() {
        var deadlockedThreads = THREAD_MX_BEAN.findDeadlockedThreads();
        var newDeadlocksFound = false;
        if (deadlockedThreads != null) {
            for (long threadId : deadlockedThreads) {
                if (knownDeadlockedThreads.add(threadId)) {
                    newDeadlocksFound = true;
                    System.out.println(threadId + ": " + findThread(threadId));
                }
            }
        }
        return newDeadlocksFound;
    }

    private static Thread findThread(long threadId) {
        return Thread.getAllStackTraces().keySet().stream()
                .filter(t -> t.threadId() == threadId)
                .findFirst()
                .get(); // has to work - it's deadlocked
    }
}