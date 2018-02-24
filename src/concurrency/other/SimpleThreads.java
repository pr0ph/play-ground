package concurrency.other;

public class SimpleThreads {
    static void threadMessage(String message) {
        System.out.println(Thread.currentThread().getName() + " " + message);
    }
    private static class MessageLoop implements Runnable {
        public void run() {
            String importantInfo[] = {"one", "two", "three", "four"};
            try {
                for (int i = 0; i < importantInfo.length; i++) {
                    Thread.sleep(2000); // sleep for 2 seconds
                    threadMessage(importantInfo[i]);
                }
            } catch (InterruptedException e) {
                threadMessage("I wasn't done!");
            }
        }
    }
    public static void main(String args[]) throws InterruptedException {
        threadMessage("Starting MessageLoop thread");
        long startTime = System.currentTimeMillis();
        Thread t = new Thread(new MessageLoop());
        t.start();
        threadMessage("Waiting for MessageLoop thread to finish");

        while (t.isAlive()) {
            threadMessage("Still waiting...");
            // Wait maximum of 1 second for MessageLoop thread to finish.
            t.join(1000);
            if (((System.currentTimeMillis() - startTime) > 1000 * 5) && t.isAlive()) {
                threadMessage("Tired of waiting!");
                t.interrupt();
                t.join();
            }
        }
        threadMessage("The end!");
    }
}
