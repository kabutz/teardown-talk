package eu.javaspecialists.tjsn.talks.teardown.demos;


import java.util.concurrent.*;

public class WeaklyConsistentViaWeakReferences {
    public static void main(String... args) {
        var queue = new ArrayBlockingQueue<String>(10);
        var iterator = queue.iterator();
        // TODO: Look at how the iterator is implemented
    }
}
