package alexis.breuse.domain.game.standard;

import alexis.breuse.domain.Score;
import alexis.breuse.domain.player.Player;

public class StandardGameScore implements Score<String> {
    private final String playerOneScore;
    private final String playerTwoScore;

    private StandardGameScore(String playerOneScore, String playerTwoScore) {
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
    }

    public StandardGameScore() {
        this.playerOneScore = "0";
        this.playerTwoScore = "0";
    }


    @Override
    public Score<String> nextScore(Player winner) {
        String currentWinnerPoints = winner.isPlayerOne() ? playerOneScore : playerTwoScore;
        String nextPoints = nextPoints(currentWinnerPoints);

        return new StandardGameScore(winner.isPlayerOne() ? nextPoints : playerOneScore, winner.isPlayerTwo() ? nextPoints : playerTwoScore);
    }

    private String nextPoints(String currentWinnerPoints) {
        return switch (currentWinnerPoints) {
            case "0" -> "15";
            case "15" -> "30";
            case "30" -> "40";
            case "40", "Advantage" -> "WINS";
            default -> throw new IllegalStateException("Unexpected value: " + currentWinnerPoints);
        };
    }

    public String getPlayerOneScore() {
        return this.playerOneScore;
    }

    public String getPlayerTwoScore() {
        return this.playerTwoScore;
    }
}
