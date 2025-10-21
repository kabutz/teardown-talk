package eu.javaspecialists.tjsn.talks.teardown.demos;


import eu.javaspecialists.tjsn.talks.teardown.startinggun.StartingGun;

import java.util.concurrent.locks.*;

public class StartingGunAQS implements StartingGun {
    // TODO: Copy implementation from CountDownLatch
    private final Sync sync = new Sync();

    public void awaitUninterruptibly() {
        sync.acquireShared(1);
    }

    public void ready() {
        sync.releaseShared(1);
    }

    private static final class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 4982264981922014374L;

        Sync() {
            setState(1);
        }

        protected int tryAcquireShared(int acquires) {
            return (getState() == 0) ? 1 : -1;
        }

        protected boolean tryReleaseShared(int releases) {
            // Decrement count; signal when transition to zero
            for (;;) {
                int c = getState();
                if (c == 0)
                    return false;
                int nextc = c - 1;
                if (compareAndSetState(c, nextc))
                    return nextc == 0;
            }
        }
    }
}