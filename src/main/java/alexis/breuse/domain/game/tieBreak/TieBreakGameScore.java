package alexis.breuse.domain.game.tieBreak;

import alexis.breuse.domain.Score;
import alexis.breuse.domain.player.Player;

public class TieBreakGameScore implements Score<Integer> {
    private final Integer playerOneScore;
    private final Integer playerTwoScore;

    public TieBreakGameScore() {
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
    }

    private TieBreakGameScore(Integer playerOneScore, Integer playerTwoScore) {
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
    }

    @Override
    public Score<Integer> nextScore(Player winner) {
        Integer currentWinnerPoints = winner.isPlayerOne() ? playerOneScore : playerTwoScore;
        Integer nextPoints = nextPoints(currentWinnerPoints);

        return new TieBreakGameScore(winner.isPlayerOne() ? nextPoints : playerOneScore, winner.isPlayerTwo() ? nextPoints : playerTwoScore);
    }

    private Integer nextPoints(Integer currentWinnerPoints) {
        return currentWinnerPoints + 1;
    }

    @Override
    public Integer getPlayerOneScore() {
        return this.playerOneScore;
    }

    @Override
    public Integer getPlayerTwoScore() {
        return this.playerTwoScore;
    }
}
