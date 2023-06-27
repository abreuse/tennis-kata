package alexis.breuse.domain.game.standard;

import alexis.breuse.domain.Score;
import alexis.breuse.domain.player.Player;

public class DeuceGameScore implements Score<String> {
    private final String playerOneScore;
    private final String playerTwoScore;

    public DeuceGameScore() {
        this.playerOneScore = "40";
        this.playerTwoScore = "40";
    }

    private DeuceGameScore(String playerOneScore, String playerTwoScore) {
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
    }

    @Override
    public Score nextScore(Player winner) {
        String currentWinnerPoints = winner.isPlayerOne() ? playerOneScore : playerTwoScore;
        String currentLoserPoints = winner.isPlayerOne() ? playerTwoScore : playerOneScore;
        String nextPoints = nextPoints(currentWinnerPoints, currentLoserPoints);
        return new DeuceGameScore(winner.isPlayerOne() ? nextPoints : "40", winner.isPlayerTwo() ? nextPoints : "40");
    }

    private String nextPoints(String currentWinnerPoints, String currentLoserPoints) {
        if (currentLoserPoints.equals("Advantage")) {
            return "40";
        }
        if (currentWinnerPoints.equals("40") && currentLoserPoints.equals("40")) {
            return "Advantage";
        }
        if (currentWinnerPoints.equals("Advantage")) {
            return "WINS";
        }
        throw new IllegalStateException("Unexpected value: " + currentWinnerPoints);
    }

    @Override
    public String getPlayerOneScore() {
        return this.playerOneScore;
    }

    @Override
    public String getPlayerTwoScore() {
        return this.playerTwoScore;
    }
}
