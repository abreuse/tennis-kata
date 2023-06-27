package alexis.breuse.domain.set;

import alexis.breuse.domain.Score;
import alexis.breuse.domain.player.Player;

public class SetScore implements Score<Integer> {
    private final Integer playerOneScore;
    private final Integer playerTwoScore;

    public SetScore(Integer playerOneScore, Integer playerTwoScore) {
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
    }

    @Override
    public Score nextScore(Player winner) {
        Integer currentWinnerPoints = winner.isPlayerOne() ? playerOneScore : playerTwoScore;
        Integer nextPoints = nextPoints(currentWinnerPoints);
        return new SetScore(winner.isPlayerOne() ? nextPoints : playerOneScore, winner.isPlayerTwo() ? nextPoints : playerTwoScore);
    }

    private Integer nextPoints(Integer currentWinnerPoints) {
        return currentWinnerPoints + 1;
    }

    public Integer getPlayerOneScore() {
        return playerOneScore;
    }

    public Integer getPlayerTwoScore() {
        return playerTwoScore;
    }
}
