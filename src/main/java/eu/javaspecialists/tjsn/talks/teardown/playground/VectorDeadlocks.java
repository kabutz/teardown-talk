package eu.javaspecialists.tjsn.talks.teardown.playground;


import java.util.*;

public class VectorDeadlocks {
    // adapted from https://bugs.openjdk.org/browse/JDK-6205425
    public static void main(String... args) {
        var v1 = new Vector<Long>();
        var v2 = new Vector<Long>();
        v1.add(10L);
        v2.add(20L);

        Thread.ofPlatform().start(compare(v1, v2));
        Thread.ofPlatform().start(compare(v2, v1));
    }

    private static Runnable compare(Vector<Long> a, Vector<Long> b) {
        return () -> {
            while (true) System.out.println("a.equals(b) = " + a.equals(b));
        };
    }
}
