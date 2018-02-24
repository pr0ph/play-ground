package concurrency.other;

public class ThreadLocalTest {

    public static void main(String[] args) {

        Runnable runnable = new Runnable() {
            ThreadLocal<Double> threadLocal = new ThreadLocal<>();
            @Override
            public void run() {
                System.out.println("BEFORE || " + threadLocal.get() + " || " + Thread.currentThread().getName());
                threadLocal.set(Math.random() * 100.0);
                System.out.println("just set || " + Thread.currentThread().getName());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("AFTER || " + threadLocal.get() + " || " + Thread.currentThread().getName());
            }
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);

        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("finished");

    }

}
