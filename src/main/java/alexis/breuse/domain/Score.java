package alexis.breuse.domain;

import alexis.breuse.domain.player.Player;

public interface Score<T> {
    Score<T> nextScore(Player winner);

    T getPlayerOneScore();

    T getPlayerTwoScore();

    default String format() {
        return "(" + getPlayerOneScore() + "-" + getPlayerTwoScore() + ")";
    }
}
