package generics;

public abstract class Player implements Payable {
    private String name;
    private int balance;

    public Player(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void getPaid(int amount) {
        this.balance = this.balance + amount;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
