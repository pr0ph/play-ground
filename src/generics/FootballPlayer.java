package generics;

public class FootballPlayer extends Player {
    public FootballPlayer(String name) {
        super(name);
    }

    public void tackle() {
        System.out.println(this.getName() + " TACKLES!");
    }
}
