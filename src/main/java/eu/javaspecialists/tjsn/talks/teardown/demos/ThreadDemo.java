package eu.javaspecialists.tjsn.talks.teardown.demos;


import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class ThreadDemo {
    static void main() {
        var count = new AtomicLong();
        while(true) {
            Thread.ofVirtual().start(() -> {
                var t = count.incrementAndGet();
                if (t % 1000 == 0) System.out.println(t);
                LockSupport.park();
            });
        }
    }
}
