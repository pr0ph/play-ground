package concurrency.other;

import java.util.HashMap;
import java.util.Map;

public class Java8Map {

    public static void main(String[] args) {

        // Map - Java8
        Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("three", 3);
        map.put("two", 2);

        // putIfAbsent
        map.putIfAbsent("one", 4);
        map.putIfAbsent("four", 4);

        // forEach
        map.forEach((key, val) -> System.out.println(key + " - " + val));
        System.out.println(map.getOrDefault("zero", 666));

        // replaceAll - compute and replace on all entries
        map.replaceAll((key, value) -> "one".equals(key) ? 1234 : value);
        System.out.println(map.get("one"));

        // compute, computeIfPresent, computeIfAbsent - compute may thrown NullPointerException if key does not exist
        map.computeIfPresent("four", (s, integer) -> integer + 3);
        System.out.println(map.get("four"));
        map.computeIfAbsent("five", s -> {
            int a = 55 * 3;
            return 555 + a;
        });
        System.out.println(map.get("five"));
        map.computeIfPresent("hell", (s, integer) -> integer + 3);

        // merge - merges new value as specified if key exists, otherwise puts value
        map.merge("six", 5, (val, newVal) -> val + newVal);
        System.out.println(map.get("six"));
        map.merge("six", 6, (val, newVal) -> val + newVal);
        System.out.println(map.get("six"));

    }
}
