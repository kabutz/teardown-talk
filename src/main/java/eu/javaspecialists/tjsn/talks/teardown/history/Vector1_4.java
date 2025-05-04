package eu.javaspecialists.tjsn.talks.teardown.history;


import java.io.*;

public class Vector1_4 implements java.io.Serializable {
    protected int elementCount;

    public synchronized int size() {
        return elementCount;
    }

    public synchronized void addElement(Object obj) {
        // ...
    }

    private synchronized void writeObject(ObjectOutputStream s)
            throws IOException {
        s.defaultWriteObject();
    }
}
