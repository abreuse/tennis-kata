package alexis.breuse.domain.set;

import alexis.breuse.domain.Score;
import alexis.breuse.domain.player.Player;

public class Set {
    private Score<Integer> score;
    private Player winner;

    public Set() {
        this.score = new SetScore(0, 0);
    }

    public Score<Integer> getScore() {
        return this.score;
    }

    public void playerWonTheCurrentGame(Player gameWinner) {
        this.score = this.score.nextScore(gameWinner);
        if (playerWinsTheSet(gameWinner)) {
            this.winner = gameWinner;
        }
    }

    private boolean playerWinsTheSet(Player gameWinner) {
        if (gameWinner.isPlayerOne()) {
            return (this.score.getPlayerOneScore() >= 6
                    && this.score.getPlayerOneScore() >= this.score.getPlayerTwoScore() + 2)
                    || this.score.getPlayerOneScore() == 7;
        } else {
            return (this.score.getPlayerTwoScore() >= 6
                    && this.score.getPlayerTwoScore() >= this.score.getPlayerOneScore() + 2)
                    || this.score.getPlayerTwoScore() == 7;
        }
    }

    public Player getWinner() {
        return winner;
    }

    public boolean hasAWinner() {
        return winner != null;
    }

    public boolean reachesTieBreakState() {
        return this.score.getPlayerOneScore() == 6 && this.score.getPlayerTwoScore() == 6;
    }
}
