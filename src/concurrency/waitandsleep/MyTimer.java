package concurrency.waitandsleep;

import java.util.Scanner;

public class MyTimer {

    private static Scanner scanner = new Scanner(System.in);

    private int counter;

    public MyTimer(int initialCounter) {
        this.counter = initialCounter;
    }

    public void countDownToZero() throws InterruptedException {
        synchronized (this) {
            System.out.println("Counting down to zero started...");
            while (counter >= 0) {
                System.out.println("Counter: " + counter);
                if (counter == 0) {
                    System.out.println("Releasing lock of MyTimer. Waiting for other thread to wind up the clock...");
                    notify();
                    wait();
                    System.out.println("Notified and lock reacquired by counter thread.");
                    if (counter <= 0) {
                        System.out.println("Counter was not wound up properly. Quitting application.");
                        System.exit(0);
                    }
                    System.out.println("Resuming counting down...");
                    continue;
                }
                Thread.sleep(1000);
                counter--;
            }
        }
    }

    public void windingUp() throws InterruptedException {
        synchronized (this) {
            while (true) {
                System.out.println("Winding up operation started...");
                System.out.print("Enter number: ");
                try {
                    counter = scanner.nextInt();
                } catch (NumberFormatException e) {
                    counter = 0;
                } finally {
                    scanner.nextLine();
                    System.out.println("Winding up operation ended. Notifying other thread so it can resume.");
                    notify();
                    wait();
                }
            }
        }
    }

}
