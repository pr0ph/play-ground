package streamsandfuncint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Car implements Comparable<Car> {

    private String model;
    private int horsePower;
    private String owner;
    private List<String> trunkItems = new ArrayList<>();

    public Car(String model, int horsePower) {
        this.model = model;
        this.horsePower = horsePower;
    }

    public Car() {
        System.out.println("Car constructor: default car created...");
    }

    public Car(String model) {
        this.model = model;
    }

    public Car(String model, int horsePower, String owner) {
        this.model = model;
        this.horsePower = horsePower;
        this.owner = owner;
    }

    public void addTrunkItem(String item) {
        trunkItems.add(item);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public String getModel() {
        return model;
    }

    public Car setModel(String model) {
        System.out.println("Old model: " + this.model + " New model: " + model);
        this.model = model;
        return this;
    }

    public List<String> getTrunkItems() {
        return trunkItems;
    }

    @Override
    public int compareTo(Car o) {
        return this.model.compareTo(o.model);
    }


    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", HP=" + horsePower +
                ", owner='" + owner + '\'' +
                '}';
    }

    public static class Comparators {
        // If one natural order is not enough, create multiple comparators for your class.
        public static Comparator<Car> carModelComparator = Comparator.comparing(a -> a.model);
        public static Comparator<Car> carOwnerComparator = Comparator.comparing(a -> a.owner);
        public static Comparator<Car> carHorsepowerComparator = Comparator.comparing(a -> a.horsePower);
    }

}
