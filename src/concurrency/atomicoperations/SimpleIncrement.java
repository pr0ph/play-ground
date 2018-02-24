package concurrency.atomicoperations;

public class SimpleIncrement {

    // Class created only to decompile: javap -c SimpleIncrement.class
    public static void main(String[] args) {
    }

    public static int counter = 123;

    public void incrementCounter(int n) {
        counter += n;
    }

}
