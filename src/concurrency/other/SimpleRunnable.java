package concurrency.other;

import java.util.concurrent.TimeUnit;

public class SimpleRunnable {

    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            private int counter;
            @Override
            public void run() {
                System.out.println("Starting thread: " + Thread.currentThread().getName());
                while (counter < 9) {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                        counter += 3;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread slept for " + counter + " seconds overall.");
                }
                System.out.println("Exiting thread: " + Thread.currentThread().getName());
            }
        });

        System.out.println("Hello!");
        thread.start();

    }

}
