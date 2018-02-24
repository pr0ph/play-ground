package streamsandfuncint;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamsTest {

    public static void main(String[] args) {

        // forEach - taking Consumer as input parameter, works on Iterable(Collection), Map and Stream objects
        Arrays.asList("A", "B", "C").forEach(s -> System.out.println(s.toLowerCase())); // array wrapped as ArrayList
        Stream.of("D", "E", "F").forEach(s -> System.out.println(s.toLowerCase())); // Stream of separate elements

        // Sample cars collection (models: BMW, Ferrari, Mercedes, Opel, Tesla)
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Ferrari", 200, "Mark"));
        cars.add(new Car("Mercedes", 300, "Peter"));
        cars.add(new Car("Ferrari", 350, "Mark"));
        cars.add(new Car("Tesla", 150, "Mark"));
        cars.add(new Car("BMW", 250, "Peter"));
        cars.add(new Car("Opel", 450, "Attila"));
        cars.add(new Car("Ferrari", 330, "Attila"));
        cars.add(new Car("Opel", 550, "Peter"));

        // Add 3 items to trunk of each car
        cars.forEach(car -> IntStream.range(1, 4).forEach(i -> car.addTrunkItem("Item" + i)));

        // Sort by owner first, then map into (horsePower)
        cars.stream().sorted(Car.Comparators.carOwnerComparator).map(Car::getHorsePower).limit(3).forEach(System.out::println);

        // Sort by model first, then map into model strings, remove all string not Ferrari/BMW, return first
        String aModel = cars.stream()
                .sorted()
                .map(Car::getModel)
                .filter(s -> s.equals("Ferrari") || s.equals("BMW")).findFirst().map(s -> s.substring(0, 1)).orElse("NONE");
        System.out.println(aModel);

        // HP average
        Double avgOfHp = cars.stream().mapToInt(Car::getHorsePower).average().orElse(0.0);
        Double avgOfHp2 = cars.stream().collect(Collectors.averagingDouble(Car::getHorsePower));
        System.out.println("Average: " + avgOfHp);
        System.out.println("Average2: " + avgOfHp2);

        // IntStream creations, array to IntStream, Stream to IntStream to array
        IntStream.range(2, 6).filter(num -> num % 2 == 0).forEach(System.out::print);
        IntStream.of(4, 2, 6).forEach(System.out::print);
        IntStream.rangeClosed(8, 10).forEach(System.out::print);
        int[] arr = {4, 8, 9};
        double avg = Arrays.stream(arr).average().orElse(0.0);
        int[] arr2 = cars.stream().filter(car -> car.getModel().length() <= 5).mapToInt(Car::getHorsePower).sorted().toArray();
        System.out.println("\narr2: " + Arrays.toString(arr2));

        // Stream reuse via Supplier
        Supplier<Stream<String>> streamSupplier =
                () -> Stream.of("d2", "a2", "b1", "b3", "c")
                        .filter(s -> s.startsWith("a"));
        boolean anyMatch = streamSupplier.get().anyMatch(s -> s.endsWith("2"));

        // Sum of HP for each owner
        Map<String, Integer> map = cars
                .stream()
                .collect(Collectors.toMap(Car::getOwner, Car::getHorsePower, (hp1, hp2) -> hp1 + hp2));

        System.out.println("MAP: " + map);

        // Group by single field
        Map<Integer, List<Car>> map1 = cars
                .stream()
                .collect(Collectors.groupingBy(Car::getHorsePower));
        System.out.println("MAP1: " + map1);

        // Group by multiple fields
        Map<OwnerAndModelTuple, List<Car>> map2 = cars
                .stream()
                .collect(Collectors.groupingBy(car -> new OwnerAndModelTuple(car.getOwner(), car.getModel())));
        System.out.println("MAP2: " + map2);

        // Downstream collector - groupingBy second parameter - nested groupingBy or any Collector can be specified
        Map<String, Map<String, List<Car>>> map3 = cars
                .stream()
                .collect(Collectors.groupingBy(Car::getOwner, Collectors.groupingBy(Car::getModel)));
        System.out.println("MAP3: " + map3);

        // Average / Sum from groupingBy results
        Map<String, Double> avgsByModel = cars
                .stream()
                .collect(Collectors.groupingBy(Car::getModel, Collectors.averagingDouble(Car::getHorsePower)));
        System.out.println("avgsByModel " + avgsByModel);

        // Min / Max from groupingBy results
        Map<String, Optional<Car>> maxHpPerModel = cars
                .stream()
                .collect(Collectors.groupingBy(Car::getModel, Collectors.maxBy(Comparator.comparingInt(Car::getHorsePower))));
        System.out.println("maxHpPerModel " + maxHpPerModel);

        // Grouping and mapping and distinct filtering by a field
        Map<String, String> modelsOwnedByStrings = cars
                .stream()
                .filter(distinctByKey(Car::getModel))
                .collect(Collectors.groupingBy(Car::getOwner, Collectors.mapping(Car::getModel, Collectors.joining(", "))));
        modelsOwnedByStrings.forEach((owner, modelsStr) -> System.out.printf("%s owns models: %s%n", owner, modelsStr));

        // Summary
        IntSummaryStatistics stats = cars.stream().collect(Collectors.summarizingInt(Car::getHorsePower));
        System.out.println(stats);

        // Joining into string
        String phrase = cars.stream().map(Car::getModel).collect(Collectors.joining(" || ", "BEGIN || ", " || END"));
        System.out.println(phrase);

        // Custom collector (supplier, accumulator, combiner, finisher)
        Collector<Car, StringJoiner, String> myCollector = Collector.of(
                () -> new StringJoiner(" || "),
                (j, p) -> j.add(p.getModel()),
                StringJoiner::merge,
                StringJoiner::toString
        );
        String carNamesString = cars.stream().collect(myCollector);
        System.out.println(carNamesString);

        // Stream nested list elements with flatMap (flatMap: transforms into 0, 1 or more objects, streams)
        String allTrunkItemsStr = cars
                .stream()
                .flatMap(car -> car.getTrunkItems().stream())
                .collect(Collectors.joining(", "));
        System.out.println(allTrunkItemsStr);

        // Reduce into single result, takes a BinaryOperator
        Car highestHpCar = cars.stream().reduce((c1, c2) -> c1.getHorsePower() > c2.getHorsePower() ? c1 : c2).orElseGet(Car::new);
        System.out.println(highestHpCar);

        // Reduce car HPs into one car, reduce takes identity object as well
        Car allHpMergedInOneCar = cars.stream().reduce(new Car(), (c1, c2) -> {
            c1.setHorsePower(c1.getHorsePower() + c2.getHorsePower());
            return c1;
        });
        System.out.println(allHpMergedInOneCar);

        // Reduce car HPs into an Integer directly as accumulator does not need Car.
        // Combiner not being used in sequential streams but needs to be added as argument.
        Integer hpSum = cars
                .stream()
                .reduce(0, (sum, c) -> sum += c.getHorsePower(), (sum1, sum2) -> sum1 + sum2);
        System.out.println("hpSum: " + hpSum);

        // ForkJoinPool - thread pool for parallelism
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        System.out.println(forkJoinPool.getParallelism());

        // Parallel collect
        ConcurrentMap<String, List<Car>> carsByOwner = cars
                .parallelStream()
                .collect(Collectors.groupingByConcurrent(Car::getOwner, Collectors.toList()));
        System.out.println(carsByOwner);

    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
