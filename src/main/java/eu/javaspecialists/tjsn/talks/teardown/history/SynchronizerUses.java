package eu.javaspecialists.tjsn.talks.teardown.history;


public class SynchronizerUses {
    public static void main(String... args) {
        // Usage new Instance creation excluding self
        // 4   java.util.concurrent.ArrayBlockingQueue a;
        // 213  java.util.concurrent.ConcurrentHashMap a;
        // 13   java.util.concurrent.ConcurrentLinkedDeque a;
        // 16   java.util.concurrent.ConcurrentLinkedQueue a;
        // 1   java.util.concurrent.ConcurrentSkipListMap a;
        // 2   java.util.concurrent.ConcurrentSkipListSet a;
        // 24  java.util.concurrent.CopyOnWriteArrayList a;
        // 0    java.util.concurrent.CopyOnWriteArraySet a;
        // 6    java.util.concurrent.CountDownLatch a;
        // 0    java.util.concurrent.CyclicBarrier a;
        // 0    java.util.concurrent.Exchanger a;
        // 12   java.util.concurrent.FutureTask a;
        // 11   java.util.concurrent.LinkedBlockingQueue a;
        // 1    java.util.concurrent.LinkedBlockingDeque a;
        // 1    java.util.concurrent.LinkedTransferQueue a;
        // 0    java.util.concurrent.PriorityBlockingQueue a;
        // 0    java.util.concurrent.Phaser a;
        // 1      java.util.concurrent.Semaphore a;
        // 3     java.util.concurrent.SynchronousQueue a;
    }
}
