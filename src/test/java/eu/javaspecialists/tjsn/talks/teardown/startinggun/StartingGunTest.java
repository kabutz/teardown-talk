package eu.javaspecialists.tjsn.talks.teardown.startinggun;

import org.junit.jupiter.api.*;

import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class StartingGunTest {
    protected abstract StartingGun create();

    @Test
    void testStartup() throws InterruptedException {
        var startingGun = create();
        long timeoutInMillis = 500;
        Runnable task = () -> {
            long time = System.nanoTime();
            try {
                startingGun.awaitUninterruptibly();
                System.out.println("Started: " + Thread.currentThread());
            } finally {
                time = (System.nanoTime() - time) / 1_000_000;
                System.out.printf("time = %dms%n", time);
                assertTrue(time >= timeoutInMillis);
            }
        };
        try (var pool = Executors.newCachedThreadPool();
             var vpool = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<?>> futures = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                futures.add(pool.submit(task));
            }
            for (int i = 0; i < 3; i++) {
                futures.add(vpool.submit(task));
            }
            Thread.sleep(timeoutInMillis);
            System.out.println("Shooting gun");
            startingGun.ready();
        }
    }

    @Test
    void testNoWait() {
        var startingGun = create();
        startingGun.ready();
        long time = System.nanoTime();
        try {
            startingGun.awaitUninterruptibly();
        } finally {
            time = (System.nanoTime() - time) / 1_000_000;
            assertTrue(time < 50);
        }
    }
}