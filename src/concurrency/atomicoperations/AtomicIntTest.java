package concurrency.atomicoperations;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static concurrency.ConcurrentUtils.sleep;
import static concurrency.ConcurrentUtils.stop;

public class AtomicIntTest {

    public static void main(String[] args) {

        // Atomic classes make heavy use of compare-and-swap (CAS), an atomic instruction supported by modern CPUs
        // Read old value, use it and calculate updated value, then update only if the value did not change since first read

        /* Pseudo code for CAS-type of incrementing:
        int current;
        do {
            current = get();
        } while(!compareAndSet(current, current + 1));
        */

        AtomicInteger atomicInt = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // incrementAndGet - simple incrementing
        IntStream.range(0, 1000)
                .forEach(i -> executor.submit(atomicInt::incrementAndGet));

        // updateAndGet - arbitrary processing as IntUnaryOperator (function)
        IntUnaryOperator o = n -> n + 2;
        Runnable task = () -> atomicInt.updateAndGet(o);
        IntStream.range(0, 1000).forEach(i -> executor.submit(task));

        // accumulateAndGet - takes a value to update and an accumulator function (side-effects free IntBinaryOperator)
        IntStream.range(1, 4)
                .forEach(i -> {
                    Runnable task2 = () ->
                            atomicInt.accumulateAndGet(i, (n, m) -> n + m);
                    executor.submit(task2);
                });

        stop(executor);

        System.out.println(atomicInt.get());

        // LongAdder - adders are useful when there is a lot of thread contention
        // Allows a growth dynamically, does not sum cells until sum() is called
        ExecutorService executor2 = Executors.newFixedThreadPool(3);
        LongAdder longAdder = new LongAdder();
        LongStream.range(0L, 6L).forEach(i -> executor2.submit(() -> longAdder.add(i)));

        System.out.println(longAdder.sum());
        sleep(1);
        System.out.println(longAdder.sum());

        stop(executor2);

        // Thread contention: when threads require a resource but are slowed down because another thread
        // is accessing or accessed given resource. In programming languages that allow objects stored in
        // stack memory, using processor's L2 cache (which is not shared across CPU cores), each thread
        // may have a local copy of object. When one thread updates it, then other thread has to invalidate
        // the object and reload from main memory, which slows it down, even if didn't have to wait for lock.
    }

}
