package alexis.breuse.domain.game.standard;

import alexis.breuse.domain.game.Game;
import alexis.breuse.domain.player.Player;
import alexis.breuse.domain.Score;

public class StandardGame implements Game {
    private Score<String> score;
    private GameState gameState;

    public StandardGame() {
        this.score = new StandardGameScore();
        this.gameState = GameState.STANDARD;
    }

    @Override
    public Score<String> getScore() {
        return this.score;
    }

    @Override
    public void playerScores(Player playerWhoScores) {
        this.score = this.score.nextScore(playerWhoScores);
        if (gameState != GameState.DEUCE && gameReachesDeuceState()) {
            this.score = new DeuceGameScore();
            this.gameState = GameState.DEUCE;
        }
    }

    @Override
    public boolean hasAWinner() {
        return this.score.getPlayerOneScore().equals("WINS") || this.score.getPlayerTwoScore().equals("WINS");
    }

    private boolean gameReachesDeuceState() {
        return (this.score.getPlayerOneScore().equals("40") && this.score.getPlayerTwoScore().equals("40"))
                || this.score.getPlayerOneScore().equals("Advantage")
                || this.score.getPlayerTwoScore().equals("Advantage");
    }
}
