package eu.javaspecialists.tjsn.talks.teardown.demos;


import java.util.*;
import java.util.concurrent.*;

public class DCLOnSteroidsCOWDemo {
    public static void main(String... args) {
        var cowList = new CopyOnWriteArrayList<String>();
        Collections.addAll(cowList, "a", "b", "c", "d", "e", "f");
        cowList.remove("b");
        cowList.remove("b");

        // TODO: Look at how remove(Object) works
    }
}
