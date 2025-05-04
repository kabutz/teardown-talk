package eu.javaspecialists.tjsn.talks.teardown.history;


import java.io.*;

public class Vector1_7 implements Serializable {
    protected int elementCount;
    protected int capacityIncrement;
    protected Object[] elementData;

    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {
        final java.io.ObjectOutputStream.PutField fields = s.putFields();
        final Object[] data;
        synchronized (this) {
            fields.put("capacityIncrement", capacityIncrement);
            fields.put("elementCount", elementCount);
            data = elementData.clone();
        }
        fields.put("elementData", data);
        s.writeFields();
    }
}
