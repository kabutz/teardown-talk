package eu.javaspecialists.tjsn.talks.teardown.history;


import java.util.concurrent.locks.*;

public class Bug8074773 {
    static {
        // Prevent rare disastrous classloading in first call to LockSupport.park.
        // See: https://bugs.openjdk.java.net/browse/JDK-8074773
        Class<?> ensureLoaded = LockSupport.class;
    }
}
