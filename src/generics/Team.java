package generics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Team<E extends Player & Payable, S extends Coaching> implements Iterable<E> {
    private List<E> players;
    private Class<E> teamType;
    private S coach;

    public Team(Class<E> teamType) {
        this.players = new ArrayList<>();
        this.teamType = teamType;
    }

    public S getCoach() {
        return coach;
    }

    public void setCoach(S coach) {
        this.coach = coach;
    }

    public Class<E> getTeamType() {
        return teamType;
    }

    public void addPlayer(E e) {
        players.add(e);
    }

    public E findPlayer(String name) {
        for (E player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Team{" +
                "players=" + players +
                '}';
    }

    @Override
    public Iterator<E> iterator() {
        return this.players.iterator();
    }
}
