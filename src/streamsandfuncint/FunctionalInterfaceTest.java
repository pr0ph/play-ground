package streamsandfuncint;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

public class FunctionalInterfaceTest {

    public static int hello = 1;

    public static void main(String[] args) {

        // Local variables must be effectively final to be used in FunctionalInterface method implementations
        // Static variables can be reassigned in FunctionalInterfaces, although not recommended to have global states
        // Important: FunctionalInterfaces may contain only ONE abstract method but any number of default methods
        int localVar = 3;
        Function<Integer, String> testFunction = a -> {
            hello = localVar; // it is ok only to use local variable values
            // localVar = 5; // would cause compiler error, effectively final
            return String.valueOf(a);
        };
        // localVar = 4; // would cause compile time error because localVar was used in lambda expression already

        // Custom FunctionalInterface example #1
        BiNumOperatorAndConverter<Integer, String> intAdderToString = (x, y) -> String.valueOf(x + y);
        System.out.println("intAdderToString: " + intAdderToString.convert(123, 7));

        // Custom FunctionalInterface example #2
        BiNumOperatorAndConverter<Integer, Long> safeIntAdderToLong = (n, m) -> (long) n + m;
        System.out.println("safeIntAdderToLong" + safeIntAdderToLong.convert(1_500_000_000, 1_500_000_000));

        // Custom FunctionalInterface example #3
        BiNumOperatorAndConverter<Integer, Integer> complexOperator = (a, b) -> {
            int c = a * b;
            return c + a + b;
        };
        System.out.println("complexOperator: " + complexOperator.convert(2, 3));

        // Built-in functional interfaces:
        // PREDICATE - One input parameter evaluated to boolean
        Predicate<String> myPredicate = s -> s.length() > 3;
        System.out.println("myPredicate: " + myPredicate.and(s -> s.equals("1234")).negate().test("1234")); // negate used
        Predicate<Boolean> isNull = Objects::isNull; // Helper method used from Objects to be more descriptive
        System.out.println("isNull: " + isNull.test(null));

        // FUNCTION - One input parameter and a return type, used for transformation, mapping
        Function<String, Integer> toInteger = Integer::valueOf; // method reference, same as: (s) -> Integer.valueOf(s);
        Function<String, String> backToString = toInteger.andThen(String::valueOf); // function chaining
        System.out.println("From String to Integer back to String: " + backToString.apply("123"));

        // UNARY OPERATOR: Function having same parameter and return type
        UnaryOperator<String> stringOperator = s -> s.toUpperCase().concat("!");
        System.out.println("stringOperator: " + stringOperator.apply("codecool"));

        // BI-FUNCTION - Two input parameters
        BiFunction<Integer, Integer, String> addAndToString = (x, y) -> String.valueOf(x + y);
        System.out.println("addAndToString: " + addAndToString.apply(5, 10));

        // BINARY OPERATOR: BiFunction with same types
        BinaryOperator<Integer> addTwoNumsIntoInteger = (x, y) -> x + y;
        System.out.println("addTwoNumsIntoInteger: " + addTwoNumsIntoInteger.apply(20, 35));

        // SUPPLIER - No input and supplies a new instance
        Supplier<Car> carSupplier = Car::new; // constructor reference, contains a print
        Car ferrari = carSupplier.get().setModel("Ferrari");

        // CONSUMER - Single input parameter
        Consumer<Car> carModelPrinter = car -> System.out.println("PRINTING: " + car.getModel());
        carModelPrinter.accept(ferrari);
        Consumer<Car> fakeAudiCreator = car -> car.setModel("Audi");
        fakeAudiCreator.accept(ferrari);

        // COMPARATOR - compares two objects of the same type
        Comparator<Car> carComparator = Comparator.comparing(Car::getModel);
        Comparator<Car> carComparator2 = (c1, c2) -> c1.getModel().compareTo(c2.getModel()); // same as above
        Comparator<Car> carComparator3 = Car.Comparators.carModelComparator; // same as above
        System.out.println(carComparator.compare(new Car("Audi"), new Car("Opel"))); // -14, difference of letters as int

        // OPTIONAL - not a FunctionalInterface
        // Helps to prevent NullPointerException and convenient, returned by some stream terminal operations
        String nothing = null; // could be null
        Optional<String> optional = Optional.ofNullable(nothing);
        optional.ifPresent(s -> System.out.println(s.length())); // will not print because optional is not present, nothing is null
        String nothingOrSomething = optional.orElse("default string");
        System.out.println(nothingOrSomething);

    }

}
