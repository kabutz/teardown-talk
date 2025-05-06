package eu.javaspecialists.tjsn.talks.teardown.weakly;


import java.util.*;
import java.util.concurrent.*;

public class ArrayBlockingQueueDemo {
    public static void main(String... args) {
        var queue = new ArrayBlockingQueue<Integer>(10);
        Collections.addAll(queue, 1, 2, 3, 4, 5);
        var iterator = queue.iterator();
        for (int i = 0; i < 3; i++) System.out.println(iterator.next()); // 1, 2, 3
        Collections.addAll(queue, 6, 7, 8, 9, 10);
        iterator.forEachRemaining(System.out::println); // 4, 5, 6, 7, 8, 9, 10
    }
}
