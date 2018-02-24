package concurrency.conchashmap;

import streamsandfuncint.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelStream {

    public static void main(String[] args) {

        // Sample cars collection (models: BMW, Ferrari, Mercedes, Opel, Tesla)
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Ferrari", 200, "Sly"));
        cars.add(new Car("Mercedes", 300, "Matyi"));
        cars.add(new Car("Ferrari", 350, "Sly"));
        cars.add(new Car("Tesla", 150, "Sly"));
        cars.add(new Car("BMW", 250, "Matyi"));
        cars.add(new Car("Opel", 450, "Zozi"));
        cars.add(new Car("Ferrari", 330, "Zozi"));
        cars.add(new Car("Opel", 550, "Matyi"));

        // Add 3 items to trunk of each car
        cars.forEach(car -> IntStream.range(1, 4).forEach(i -> car.addTrunkItem("Item" + i)));
        // ForkJoinPool - thread pool for parallelism
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        System.out.println("Number of worker threads: " + forkJoinPool.getParallelism());

        // Parallel collect
        ConcurrentMap<String, List<Car>> carsByOwner = cars
                .parallelStream()
                .collect(Collectors.groupingByConcurrent(Car::getOwner, Collectors.toList()));
        System.out.println(carsByOwner);

        // Parallel sorting by horse power, descending order
        List<Car> carsSortedByHorsepowerDesc = cars
                .parallelStream()
                .sorted(Car.Comparators.carHorsepowerComparator.reversed())
                .collect(Collectors.toList());
        System.out.println(carsSortedByHorsepowerDesc);
    }
}
