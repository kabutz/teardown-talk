package eu.javaspecialists.tjsn.talks.teardown.startinggun;


import java.util.concurrent.*;

public class StartingGunCountDownLatch implements StartingGun {
    private final CountDownLatch latch = new CountDownLatch(1);
    public void awaitUninterruptibly() {
        var interrupted = Thread.interrupted();
        while (true) {
            try {
                latch.await();
                break;
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }
        if (interrupted) Thread.currentThread().interrupt();
    }
    public void ready() { latch.countDown(); }
}
