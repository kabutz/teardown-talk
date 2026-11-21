package eu.javaspecialists.tjsn.talks.teardown.demos;


import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class COWDemo {
    static void main() {
        var list = new CopyOnWriteArrayList<Integer>();
        var time = System.nanoTime();
        try {
            var temp = new ArrayList<Integer>();
            for (int i = 0; i < 200_000; i++) {
                temp.add(i);
            }
            list.addAll(temp);
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
    }
}
