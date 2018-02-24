package concurrency.conchashmap;

import java.util.concurrent.ConcurrentHashMap;

import static concurrency.ConcurrentUtils.sleep;

public class ConcurrentHashMapTest {

    public static void main(String[] args) {

        // ConcurrentHashMap: all methods available as in Map interface
        // Parallelized methods: forEach (overloaded in many different ways), search, reduce

        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("foo", "bar");
        map.put("han", "solo");
        map.put("r2", "d2");
        map.put("c3", "p0");
        map.put("c4", "p0");
        map.put("c35", "p0");
        map.put("c36", "p0");

        // Print all key-value pairs in a parallel fashion
        map.forEach(1, (k, v) -> System.out.println(Thread.currentThread().getName() + ": " + k + " " + v));

        sleep(3);
        System.out.println("=======================");

        // Search in key-value pairs, using multiple threads
        String result = map.search(1, (key, value) -> {
            System.out.println(Thread.currentThread().getName());
            if ("foo".equals(key)) {
                return value;
            }
            return null;
        });
        System.out.println("Result: " + result);


        sleep(3);
        System.out.println("=======================");

        // Search in values, using multiple threads
        result = map.searchValues(1, value -> {
            System.out.println(Thread.currentThread().getName());
            if (value.length() > 3) {
                return value;
            }
            return null;
        });

        System.out.println("Result: " + result);

        sleep(3);
        System.out.println("=======================");

        // Reducing the whole map into a String, but could be used to reduce it into any type of object,
        // taking all the information that the map contains and transform into something else
        // Define a transformer function for key-value pairs and a combiner/reducer function
        result = map.reduce(1,
                (key, value) -> {
                    System.out.println("Transform: " + Thread.currentThread().getName());
                    return key + "=" + value;
                },
                (s1, s2) -> {
                    System.out.println("Reduce: " + Thread.currentThread().getName());
                    return s1 + ", " + s2;
                });

        System.out.println("Result: " + result);
    }

}
