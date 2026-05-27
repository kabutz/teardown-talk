package eu.javaspecialists.tjsn.talks.teardown.demos;


import java.util.*;
import java.util.concurrent.*;

public class COWDemo {
    static void main() {
        var time = System.nanoTime();
        try {
            var list = new CopyOnWriteArrayList<>();
            var temp = new ArrayList<>();
            for (int i = 0; i < 320_000; i++) {
                temp.add(i);
            }
            list.addAll(temp);
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
    }
}
