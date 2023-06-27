package alexis.breuse.domain.game.tieBreak;

import alexis.breuse.domain.Score;
import alexis.breuse.domain.game.Game;
import alexis.breuse.domain.player.Player;

public class TieBreakGame implements Game {

    private Score<Integer> score;

    public TieBreakGame() {
        this.score = new TieBreakGameScore();
    }

    @Override
    public void playerScores(Player playerWhoScores) {
        this.score = this.score.nextScore(playerWhoScores);
    }

    @Override
    public Score<Integer> getScore() {
        return this.score;
    }

    @Override
    public boolean hasAWinner() {
        return playerOneWins() || playerTwoWins();
    }

    private boolean playerOneWins() {
        return this.score.getPlayerOneScore() >= 7
                && this.score.getPlayerOneScore() >= (this.score.getPlayerTwoScore() + 2);
    }

    private boolean playerTwoWins() {
        return this.score.getPlayerTwoScore() >= 7
                && this.score.getPlayerTwoScore() >= (this.score.getPlayerOneScore() + 2);
    }
}
