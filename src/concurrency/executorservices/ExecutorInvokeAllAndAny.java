package concurrency.executorservices;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorInvokeAllAndAny {

    public static void main(String[] args) {

        // Batch submitting of Callables to executors with invokeAll, not blocking thread
        // newWorkStealingPool() returns ForkJoinPool - not fixed sized pool but given based on parallelism size (CPU cores)
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                () -> "task1",
                () -> "task2",
                () -> "task3");

        try {
            executor.invokeAll(callables)
                    .stream()
                    .map(f -> {
                        try {
                            return f.get();
                        } catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    })
                    .forEach(System.out::println);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // invokeAny blocks the thread until one of the Callables return with result, calls get implicitly
        List<Callable<String>> callables2 = Arrays.asList(
                getCallable("task1", 2),
                getCallable("task2", 1),
                getCallable("task3", 3));

        String result = null;
        try {
            result = executor.invokeAny(callables2);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(result);

    }

    public static Callable<String> getCallable(String result, long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }

}
