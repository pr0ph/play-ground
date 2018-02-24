package concurrency.executorservices;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import static concurrency.ConcurrentUtils.sleep;
import static concurrency.ConcurrentUtils.stop;

public class ReentrantLockExplicit {

    public static void main(String[] args) {

        // lock() pauses the thread until lock is released by other thread
        // tryLock() does not block thread

        ExecutorService executor = Executors.newFixedThreadPool(2);
        ReentrantLock lock = new ReentrantLock();

        executor.submit(() -> {
            lock.lock();
            try {
                sleep(1);
            } finally {
                lock.unlock();
            }
        });

        executor.submit(() -> {
            System.out.println("Locked: " + lock.isLocked());
            System.out.println("Held by me: " + lock.isHeldByCurrentThread());
            boolean locked = lock.tryLock();
            System.out.println("Lock acquired: " + locked);
            sleep(2);
            System.out.println("Locked: " + lock.isLocked());
        });

        stop(executor);

    }

}
