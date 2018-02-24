package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ConcurrentUtils {

    public static void stop(ExecutorService executor) {
        try {
            executor.shutdown();
            executor.awaitTermination(60, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            System.err.println("termination interrupted");
        }
        finally {
            if (!executor.isTerminated()) {
                System.err.println("killing non-finished tasks");
            }
            executor.shutdownNow();
        }
    }

    public static void sleep(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void wait(Object resource, int ms) {
        try {
            resource.wait(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
