package eu.javaspecialists.tjsn.talks.teardown.history;


public class Vector1_0 {
    protected int elementCount;

    public final int size() {
        return elementCount;
    }

    public final synchronized void addElement(Object obj) {
        // ...
    }
}
