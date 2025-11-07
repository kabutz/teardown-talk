package eu.javaspecialists.tjsn.talks.teardown.demos;


import java.util.concurrent.locks.*;

public class DeadlockVirtualThreads {
    static void main() throws InterruptedException {
        var lock1 = new Object();
        var lock2 = new Object();
        var builder = Thread.ofVirtual();

        var t1 = builder.start(() -> {
            synchronized (lock1) {
                LockSupport.parkNanos(10_000_000);
                synchronized (lock2) {
                    IO.println("Won't see this");
                }
            }
        });
        builder.start(() -> {
            synchronized (lock2) {
                LockSupport.parkNanos(10_000_000);
                synchronized (lock1) {
                    IO.println("Won't see this either");
                }
            }
        });

        t1.join();
        System.out.println("Done");
    }
}
