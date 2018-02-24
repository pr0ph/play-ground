package exceptions;

public class Dog extends Animal {
    public String go(int ok, String oks) {
        return null;
    }
    public String go(String oks ,int ok) {
        return null;
    }

    @Override
    public void walk() {
        System.out.println("Dog walks");
    }
}
