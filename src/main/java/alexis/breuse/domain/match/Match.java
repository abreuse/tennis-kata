package alexis.breuse.domain.match;


import alexis.breuse.domain.Score;
import alexis.breuse.domain.game.Game;
import alexis.breuse.domain.game.standard.StandardGame;
import alexis.breuse.domain.game.tieBreak.TieBreakGame;
import alexis.breuse.domain.player.Player;
import alexis.breuse.domain.player.PlayerTag;
import alexis.breuse.domain.set.Set;

import java.util.ArrayList;
import java.util.List;

public class Match {
    private final Player playerOne;
    private final Player playerTwo;
    private Player winner;
    private Game currentGame;
    private List<Set> sets;

    public Match(String playerOneName, String playerTwoName) {
        this.playerOne = new Player(playerOneName, PlayerTag.PLAYER_ONE);
        this.playerTwo = new Player(playerTwoName, PlayerTag.PLAYER_TWO);
        this.currentGame = new StandardGame();
        this.sets = List.of(new Set());
    }

    public void playerScores(PlayerTag playerTag) {
        if (hasAWinner()) {
            return;
        }

        Player playerWhoScores = playerTag == PlayerTag.PLAYER_ONE ? playerOne : playerTwo;
        this.currentGame.playerScores(playerWhoScores);

        if (this.currentGame.hasAWinner()) {
            getCurrentSet().playerWonTheCurrentGame(playerWhoScores);

            if(getCurrentSet().reachesTieBreakState()) {
                this.currentGame = new TieBreakGame();
            } else {
                this.currentGame = new StandardGame();
            }

            if (playerWinsTheMatch(playerWhoScores)) {
                this.winner = playerWhoScores;
            } else if (getCurrentSet().hasAWinner()) {
                startsNewSet();
            }
        }
    }

    private boolean hasAWinner() {
        return this.winner != null;
    }

    private boolean playerWinsTheMatch(Player player) {
        List<Set> setWon = this.sets.stream()
                .filter(Set::hasAWinner)
                .filter(set -> set.getWinner().equals(player)).toList();
        return setWon.size() == 3;
    }

    private void startsNewSet() {
        ArrayList<Set> sets = new ArrayList<>(this.sets);
        sets.add(new Set());
        this.sets = List.copyOf(sets);
    }

    public Score getCurrentGameScore() {
        return this.currentGame.getScore();
    }

    public Score getCurrentSetScore() {
        return getCurrentSet().getScore();
    }

    private Set getCurrentSet() {
        return this.sets.get(this.sets.size() - 1);
    }

    public String getScore() {
        return sets.stream().map(Set::getScore).map(Score::format).reduce("", (one, second) -> one + second);
    }

    public Player getWinner() {
        return winner;
    }

    public boolean hasWinner() {
        return winner != null;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }
}
