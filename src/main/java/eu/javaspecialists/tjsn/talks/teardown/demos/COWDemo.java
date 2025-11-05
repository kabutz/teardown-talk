package eu.javaspecialists.tjsn.talks.teardown.demos;


import java.util.*;
import java.util.concurrent.*;

public class COWDemo {
    static void main() {
        var time = System.nanoTime();
        try {
            var list = new CopyOnWriteArrayList<Integer>();
            var temp = new ArrayList<Integer>();
            for (int i = 0; i < 400_000; i++) {
                temp.add(i);
            }
            list.addAll(temp);
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
    }
}
