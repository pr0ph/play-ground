package generics;

public class SoccerPlayer extends Player {
    public SoccerPlayer(String name) {
        super(name);
    }

    public void shootGoal() {
        System.out.println(this.getName() + " SHOOTS GOAL!");
    }
}
