package concurrency.other;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

import static concurrency.ConcurrentUtils.sleep;
import static concurrency.ConcurrentUtils.stop;

public class StampedLockTest {

    public static int count = 0;

    public static void main(String[] args) {

        // StampedLock can do the same thing as ReadWriteLock but does not have reentrant characteristic
        // StampedLock may use tryOptimisticRead(), which is a non-blocking way of trying to acquire a lock
        // validate() can be used to check if lock is acquired and being held
        // Optimistic read lock does not prevent write locks to be acquired and may become invalid at any moment
        // tryConvertToWriteLock() can be used to turn a read lock into a write lock without unlocking even for a moment

        // Create executor, lock and map
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();
        StampedLock lock = new StampedLock();

        // Example #1 - Normal read and write locks with StampedLock
        executor.submit(() -> {
            long stamp = lock.writeLock();
            try {
                sleep(1);
                map.put("foo", "bar");
            } finally {
                lock.unlockWrite(stamp);
            }
        });

        Runnable readTask = () -> {
            long stamp = lock.readLock();
            try {
                System.out.println(map.get("foo"));
                sleep(1);
            } finally {
                lock.unlockRead(stamp);
            }
        };

        executor.submit(readTask);
        executor.submit(readTask);

        // Sleep just between different examples
        sleep(5);

        // Example #2 -  Optimistic lock example
        executor.submit(() -> {
            long stamp = lock.tryOptimisticRead();
            try {
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                sleep(1);
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                sleep(2);
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
            } finally {
                lock.unlock(stamp);
            }
        });

        executor.submit(() -> {
            long stamp = lock.writeLock();
            try {
                System.out.println("Write Lock acquired");
                sleep(2);
            } finally {
                lock.unlock(stamp);
                System.out.println("Write done");
            }
        });

        // Sleep just between different examples
        sleep(5);

        // Example #3 - Convert read lock into write lock without unlocking, or try to get write lock if occupied
        executor.submit(() -> {
            long stamp = lock.readLock();
            try {
                if (count == 0) {
                    stamp = lock.tryConvertToWriteLock(stamp);
                    if (stamp == 0L) {
                        System.out.println("Could not convert to write lock");
                        stamp = lock.writeLock();
                    }
                    count = 23;
                }
                System.out.println(count);
            } finally {
                lock.unlock(stamp);
            }
        });

        stop(executor);
    }

}
