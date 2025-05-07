package eu.javaspecialists.tjsn.talks.teardown.playground;


import java.util.concurrent.locks.*;

public class SynchronizedWithStackOverflow {
    public static void main(String... args) throws InterruptedException {
        Object monitor = new Object();
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
            Thread.ofPlatform().start(() -> {
                deep(monitor);
            }).join();
        }
    }

    private static void deep(Object monitor) {
        synchronized (monitor) {
            deep(monitor);
        }
    }
}
