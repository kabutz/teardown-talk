package eu.javaspecialists.tjsn.talks.teardown.demos;

import eu.javaspecialists.tjsn.talks.teardown.startinggun.*;

public class StartingGunAQSTest extends StartingGunTest {
    protected StartingGun create() {
        return new StartingGunAQS();
    }
}