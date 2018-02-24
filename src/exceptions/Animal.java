package exceptions;

public abstract class Animal {
    private String name;

    public Animal() {
        this.name = getClass().getSimpleName();
    }

    public String getName() {
        return name;
    }

    public void walk() {
        System.out.println("Animal walks");
    }
}
