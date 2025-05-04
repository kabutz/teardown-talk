package eu.javaspecialists.tjsn.talks.teardown.startinggun;


public class StartingGunCountDownLatchTest extends StartingGunTest {
    protected StartingGun create() {
        return new StartingGunCountDownLatch();
    }
}
