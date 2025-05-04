package eu.javaspecialists.tjsn.talks.teardown.history;


import java.util.*;

public class VectorDeadlock {
    public static void main(String... args) {
        Vector v1 = new Vector();
        Vector v2 = new Vector();
        v1.addElement(v2);
        v2.addElement(v1);
        // serialize v1 and v2 from two different threads
    }
}
