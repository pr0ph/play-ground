package concurrency.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Processor implements Runnable {

    private CountDownLatch latch;
    private List<String> content;

    public Processor(CountDownLatch latch, List<String> content) {
        this.latch = latch;
        this.content = content;
    }

    @Override
    public void run() {
        System.out.println("Started at " + latch.getCount() + " on " + Thread.currentThread().getName());

        try {
            Thread.sleep(1); // simply to simulate long running work
            content.add(UUID.randomUUID().toString()); // imitate processed data written to content
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        latch.countDown();
        System.out.println("Finished at " + latch.getCount() + " on " + Thread.currentThread().getName());
    }
}

public class CountDownLatchTest {

    // CountDownLatch is a thread safe class, can be used by multiple threads in a thread safe way,
    // no explicit synchronized has to be used, syncing implemented internally.

    // Main usage: using await() makes sure that a thread (main in this case) is blocked
    // until worker threads finish parallel processing and prepare some data for boss thread.

    // Can be used to force exact parallel execution of processes on shared resource,
    // which is great to debug occasionally occurring concurrency-related bugs.
    // How? Create 3 CountDownLatches: main thread awaits for all workers to be ready for work,
    // then workers wait for main thread to give green flag (unleash the hounds effect),
    // then main is blocked until all workers are down with work.

    // Source: http://www.baeldung.com/java-countdown-latch

    public static void main(String[] args) {

        final int countDownFrom = 5000;

        // A synchronized (thread safe) ArrayList, to
        List<String> processedContent = Collections.synchronizedList(new ArrayList<>());

        // Shared data structure that has a thread safe implementation
        CountDownLatch latch = new CountDownLatch(countDownFrom);

        // High-level class that manages thread pools (recycles them)
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Throw work at worker threads until latch is not completely decremented
        for (int i = 0; i < countDownFrom; i++) {
            executor.submit(new Processor(latch, processedContent));
        }

        // Control flow halts until latch count down finished (until work is not being done)
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Shutting down ExecutorService gracefully
        executor.shutdown();
        try {
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (!executor.isTerminated()) {
                System.out.println(Thread.currentThread().getName() + " could not finish.");
            }
            executor.shutdownNow();
        }

        System.out.println("Processed content:");
        processedContent.forEach(System.out::println);
        System.out.println(processedContent.size());

    }

}
