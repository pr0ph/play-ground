package concurrency.waitandsleep;

/**
 * Wait and notify can be used on Object instances, a form of low-level thread synchronization techniques.
 * Both can be called only from synchronized blocks or methods.
 *
 * notifyAll() can be used if not just one waiting thread has to be woken up.
 *
 * Thread.sleep(n) can be called from any context, halting thread for n unless interrupted
 * Sleep does NOT release lock!
 */
public class WaitAndSleep {

    public static void main(String[] args) {

        MyTimer myTimer = new MyTimer(3);

        Thread countDownThread = new Thread(() -> {
            try {
                myTimer.countDownToZero();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread windingUpThread = new Thread(() -> {
            try {
                myTimer.windingUp();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Both threads are executing a synchronized method with same resource locked,
        // both running in a while cycle, using wait() to release lock of resource when needed,
        // and using notify() when done with operation and send this signal back to other thread.
        countDownThread.start();
        windingUpThread.start();

    }

}
