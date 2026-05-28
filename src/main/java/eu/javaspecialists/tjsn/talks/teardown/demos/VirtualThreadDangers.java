package eu.javaspecialists.tjsn.talks.teardown.demos;


public class VirtualThreadDangers {
    static void main() throws InterruptedException {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            Thread.startVirtualThread(() -> {
                while (true) Thread.yield();
            });
        }
        Thread.startVirtualThread(() ->
                System.out.println("Lisbon is beautiful"))
                .join();
        Thread.sleep(20000);
    }
}
