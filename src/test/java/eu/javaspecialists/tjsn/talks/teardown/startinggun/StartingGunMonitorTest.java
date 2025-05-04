package eu.javaspecialists.tjsn.talks.teardown.startinggun;


public class StartingGunMonitorTest extends StartingGunTest {
    protected StartingGun create() {
        return new StartingGunMonitor();
    }
}
