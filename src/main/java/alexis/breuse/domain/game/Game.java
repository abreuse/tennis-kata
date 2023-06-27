package alexis.breuse.domain.game;

import alexis.breuse.domain.Score;
import alexis.breuse.domain.player.Player;

public interface Game {

    void playerScores(Player playerWhoScores);

    Score getScore();

    boolean hasAWinner();
}
