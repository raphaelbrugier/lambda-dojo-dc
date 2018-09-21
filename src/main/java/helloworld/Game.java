package helloworld;

public class Game {
    String team1Name;
    String team2Name;
    int team1Score;
    int team2Score;

    @Override
    public String toString() {
        return "Game{" +
                "team1Name='" + team1Name + '\'' +
                ", team2Name='" + team2Name + '\'' +
                ", team1Score=" + team1Score +
                ", team2Score=" + team2Score +
                '}';
    }
}
