package concurrency.executorservices;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutors {

    public static void main(String[] args) {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        // Task to be done
        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());

        // After 3 seconds elapsed, the task will be completed concurrently ONCE
        ScheduledFuture<?> future = executor.schedule(task, 3, TimeUnit.SECONDS);
        try {
            TimeUnit.MILLISECONDS.sleep(1337);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
        System.out.printf("Remaining Delay: %sms%n", remainingDelay);

        // Periodic execution of task on separate thread
        // scheduleAtFixedRate(): executes task at a fixed rate, does not take the task execution duration into account!
        Runnable task2 = () -> System.out.println("Scheduling periodically: " + System.nanoTime());
        executor.scheduleAtFixedRate(task2, 0, 1, TimeUnit.SECONDS);

        // scheduleWithFixedDelay(): after initialDelay passed, executes task then waits for time specified as delay and does again
        // Useful if execution duration is uncertain
        Runnable task3 = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Scheduling: " + System.nanoTime());
            }
            catch (InterruptedException e) {
                System.err.println("task interrupted");
            }
        };
        executor.scheduleWithFixedDelay(task3, 0, 1, TimeUnit.SECONDS);


    }

}
