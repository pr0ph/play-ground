package concurrency.executorservices;

import concurrency.ConcurrentUtils;

import java.util.concurrent.*;

public class CallableAndFuture {

    public static void main(String[] args) {

        // Callable - same as Runnable with return type and value
        Callable<String> task = () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new IllegalStateException("oops", e);
            }
            return "String from Callable!";
        };

        // Executor service with thread pool - takes Callable, not just Runnable
        ExecutorService executor2 = Executors.newFixedThreadPool(1);
        Future<String> future = executor2.submit(task);

        String result = null;
        int counter = 0;

        // Giving 5 seconds of time to finish task (since we use 1sec sleep in each loop).
        while (counter < 5) {

            // Periodically checks if task has been done
            if (future.isDone()) {
                System.out.println("future is done after " + counter + " seconds");
                try {
                    // get() is blocking the thread and waits for execution to be complete
                    // since we asked isDone() before, we can be sure we are not blocking our main thread
                    // get() can be set with timeout
                    result = future.get();
                    break;
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("future is still not done after " + counter + " seconds");
            }

            // Other tasks could be done here on this main thread in the meanwhile
            System.out.println("doing some other processing work");
            ConcurrentUtils.sleep(1);

            counter++;
        }

        if (result == null) {
            result = "Could not finish task in time (on other thread).";
        }

        System.out.print("result: " + result);
    }

}
