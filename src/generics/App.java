package generics;

public class App {

    public static void main(String[] args) {

        // SOCCER

        Team<SoccerPlayer, BasicCoach> soccerTeam = new Team<>(SoccerPlayer.class);

        soccerTeam.addPlayer(new SoccerPlayer("Mark"));
        soccerTeam.addPlayer(new SoccerPlayer("Peter"));
        soccerTeam.addPlayer(new SoccerPlayer("Attila"));

        soccerTeam.findPlayer("Mark").shootGoal();

        showTeamMembers(soccerTeam);
        sendPayment(500, soccerTeam);
        showTeamMembers(soccerTeam);

        // FOOTBALL

        Team<FootballPlayer, BasicCoach> footballTeam = new Team<>(FootballPlayer.class);

        footballTeam.addPlayer(new FootballPlayer("Jack"));
        footballTeam.addPlayer(new FootballPlayer("Joe"));
        footballTeam.addPlayer(new FootballPlayer("John"));

        footballTeam.findPlayer("Jack").tackle();

        showTeamMembers(footballTeam);
        sendPayment(1000, footballTeam);
        showTeamMembers(footballTeam);

    }

    public static void showTeamMembers(Team<? extends Player, ? extends Coaching> team) {
        System.out.println(team.getTeamType().getSimpleName().replace("Player", "") + " team:");
        for (Player player : team) {
            System.out.println(player);
        }
    }

    public static void sendPayment(int amount, Team<? extends Player, ? extends Coaching> team) {
        System.out.println(team.getTeamType().getSimpleName().replace("Player", "") + " team was paid.");
        for (Player player : team) {
            player.getPaid(amount);
        }
    }

}
