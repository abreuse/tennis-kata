package alexis.breuse.domain;

import alexis.breuse.domain.match.Match;
import alexis.breuse.domain.player.PlayerTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatchTest {

    private Match match;

    @BeforeEach
    public void beforeEach() {
        match = new Match("Nadal", "Federer");
    }

    @ParameterizedTest(name = "Game score should be {2}-{3} when players score {0} and {1} times respectively")
    @CsvSource({
            "0,0,0,0",
            "1,0,15,0",
            "0,1,0,15",
            "1,1,15,15",
            "2,1,30,15",
            "1,2,15,30",
            "2,2,30,30",
            "3,2,40,30",
            "2,3,30,40",
            "3,3,40,40",
    })
    public void game_score_should_be_x_y_when_players_score_n_n2_times(int playerOneScoreTimes,
                                                                       int playerTwoScoreTimes,
                                                                       String expectedPlayerOneScore,
                                                                       String expectedPlayerTwoScore) {
        playerScores(PlayerTag.PLAYER_ONE, playerOneScoreTimes);
        playerScores(PlayerTag.PLAYER_TWO, playerTwoScoreTimes);

        assertEquals(expectedPlayerOneScore, match.getCurrentGameScore().getPlayerOneScore());
        assertEquals(expectedPlayerTwoScore, match.getCurrentGameScore().getPlayerTwoScore());
    }

    @Test
    public void player_one_should_have_advantage_when_both_players_have_40_score_and_he_scores() {
        playerScores(PlayerTag.PLAYER_ONE, 3);
        playerScores(PlayerTag.PLAYER_TWO, 3);

        playerScores(PlayerTag.PLAYER_ONE, 1);

        assertEquals("Advantage", match.getCurrentGameScore().getPlayerOneScore());
        assertEquals("40", match.getCurrentGameScore().getPlayerTwoScore());
    }

    @Test
    void player_two_should_have_the_advantage_when_both_players_have_40_score_and_he_scores() {
        playerScores(PlayerTag.PLAYER_ONE, 3);
        playerScores(PlayerTag.PLAYER_TWO, 3);

        playerScores(PlayerTag.PLAYER_TWO, 1);

        assertEquals("Advantage", match.getCurrentGameScore().getPlayerTwoScore());
        assertEquals("40", match.getCurrentGameScore().getPlayerOneScore());
    }

    @Test
    void player_one_should_lose_the_advantage_when_player_two_scores() {
        playerScores(PlayerTag.PLAYER_ONE, 3);
        playerScores(PlayerTag.PLAYER_TWO, 3);
        playerScores(PlayerTag.PLAYER_ONE, 1);

        playerScores(PlayerTag.PLAYER_TWO, 1);

        assertEquals("40", this.match.getCurrentGameScore().getPlayerOneScore());
        assertEquals("40", this.match.getCurrentGameScore().getPlayerTwoScore());
    }

    @Test
    void player_two_should_lose_the_advantage_when_player_one_scores() {
        playerScores(PlayerTag.PLAYER_ONE, 3);
        playerScores(PlayerTag.PLAYER_TWO, 4);

        playerScores(PlayerTag.PLAYER_ONE, 1);

        assertEquals("40", this.match.getCurrentGameScore().getPlayerOneScore());
        assertEquals("40", this.match.getCurrentGameScore().getPlayerTwoScore());
    }

    @Test
    void player_two_should_wins_the_game_when_he_has_a_score_of_40_and_scores_again_while_the_other_player_has_a_score_below_40() {
        playerScores(PlayerTag.PLAYER_ONE, 2);
        playerScores(PlayerTag.PLAYER_TWO, 3);

        playerScores(PlayerTag.PLAYER_TWO, 1);

        assertEquals(0, match.getCurrentSetScore().getPlayerOneScore());
        assertEquals(1, match.getCurrentSetScore().getPlayerTwoScore());
    }

    @Test
    void player_one_should_wins_the_game_when_he_has_a_score_of_40_and_scores_again_while_the_other_player_has_a_score_below_40() {
        playerScores(PlayerTag.PLAYER_ONE, 3);
        playerScores(PlayerTag.PLAYER_TWO, 2);

        playerScores(PlayerTag.PLAYER_ONE, 1);

        assertEquals(1, match.getCurrentSetScore().getPlayerOneScore());
        assertEquals(0, match.getCurrentSetScore().getPlayerTwoScore());
    }

    @Test
    void player_two_should_wins_the_game_when_he_has_the_advantage_and_scores_again() {
        playerScores(PlayerTag.PLAYER_ONE, 3);
        playerScores(PlayerTag.PLAYER_TWO, 4);

        playerScores(PlayerTag.PLAYER_TWO, 1);

        assertEquals(0, match.getCurrentSetScore().getPlayerOneScore());
        assertEquals(1, match.getCurrentSetScore().getPlayerTwoScore());
    }

    @ParameterizedTest(name = "Match score should be {2} when players win {0} and {1} games respectively")
    @CsvSource({
            "1,0,(1-0)",
            "0,1,(0-1)",
            "2,1,(2-1)",
            "3,4,(3-4)",
            "5,6,(5-6)",
            "6,0,(6-0)(0-0)",
            "7,6,(6-0)(1-6)(0-0)",
            "8,10,(6-0)(2-6)(0-4)",
            "10,15,(6-0)(4-6)(0-6)(0-3)",
            "10,18,(6-0)(4-6)(0-6)(0-6)",
            "18,0,(6-0)(6-0)(6-0)",
    })
    void set_score_should_be_x_y_when_players_win_n_and_n2_games_respectively(int playerOneWinningTimes,
                                                                              int playerTwoWinningTimes,
                                                                              String setsScores) {
        playerWinGames(PlayerTag.PLAYER_ONE, playerOneWinningTimes);
        playerWinGames(PlayerTag.PLAYER_TWO, playerTwoWinningTimes);

        assertEquals(setsScores, match.getScore());
    }

    @Test
    void a_player_can_win_a_set_7_to_5() {
        playerWinGames(PlayerTag.PLAYER_ONE, 5);
        playerWinGames(PlayerTag.PLAYER_TWO, 5);

        playerWinGames(PlayerTag.PLAYER_ONE, 2);

        assertEquals("(7-5)(0-0)", match.getScore());
    }

    @Test
    void player_one_should_wins_the_set_when_he_wins_6_games() {
        playerWinGames(PlayerTag.PLAYER_ONE, 6);

        assertEquals("(6-0)(0-0)", match.getScore());
    }

    @Test
    void player_one_should_wins_the_set_when_he_wins_6_games_and_the_other_player_wins_less_than_5_games() {
        playerWinGames(PlayerTag.PLAYER_ONE, 5);
        playerWinGames(PlayerTag.PLAYER_TWO, 4);

        playerWinGames(PlayerTag.PLAYER_ONE, 1);

        assertEquals("(6-4)(0-0)", match.getScore());
    }

    @Test
    void player_two_should_wins_the_set_when_he_wins_6_games_and_the_other_player_wins_less_than_5_games() {
        playerWinGames(PlayerTag.PLAYER_TWO, 5);
        playerWinGames(PlayerTag.PLAYER_ONE, 4);

        playerWinGames(PlayerTag.PLAYER_TWO, 1);

        assertEquals("(4-6)(0-0)", match.getScore());
    }

    @Test
    void player_one_should_not_win_when_he_has_less_than_two_winning_games_difference_with_his_opponent() {
        playerWinGames(PlayerTag.PLAYER_TWO, 5);
        playerWinGames(PlayerTag.PLAYER_ONE, 5);

        playerWinGames(PlayerTag.PLAYER_ONE, 1);

        assertEquals("(6-5)", match.getScore());
    }

    @Test
    void player_two_should_not_win_when_he_has_less_than_two_winning_games_difference_with_his_opponent() {
        playerWinGames(PlayerTag.PLAYER_TWO, 5);
        playerWinGames(PlayerTag.PLAYER_ONE, 5);

        playerWinGames(PlayerTag.PLAYER_TWO, 1);

        assertEquals("(5-6)", match.getScore());
    }

    @Test
    void when_both_players_have_won_6_games_in_a_set_the_next_game_is_a_tie_break() {
        currentSetScoreIs6To6();

        playerScores(PlayerTag.PLAYER_TWO, 2);
        playerScores(PlayerTag.PLAYER_ONE, 3);

        assertEquals("(3-2)", match.getCurrentGameScore().format());
    }

    @Test
    void player_one_wins_the_tie_break_and_set_when_he_has_at_least_7_points_and_2_more_than_the_opponent() {
        currentSetScoreIs6To6();

        playerScores(PlayerTag.PLAYER_ONE, 6);
        playerScores(PlayerTag.PLAYER_TWO, 6);

        playerScores(PlayerTag.PLAYER_ONE, 2);

        assertEquals("(7-6)(0-0)", match.getScore());
    }

    @Test
    void player_two_wins_the_tie_break_and_set_when_he_has_at_least_7_points_and_2_more_than_the_opponent() {
        currentSetScoreIs6To6();

        playerScores(PlayerTag.PLAYER_TWO, 6);
        playerScores(PlayerTag.PLAYER_ONE, 6);

        playerScores(PlayerTag.PLAYER_TWO, 2);

        assertEquals("(6-7)(0-0)", match.getScore());
    }

    @Test
    void player_one_wins_the_match_when_he_wins_3_sets() {
        playerWinSets(PlayerTag.PLAYER_TWO, 2);
        playerWinSets(PlayerTag.PLAYER_ONE, 3);

        assertTrue(match.getWinner().isPlayerOne());
    }

    @Test
    void player_two_wins_the_match_when_he_wins_3_sets() {
        playerWinSets(PlayerTag.PLAYER_ONE, 2);
        playerWinSets(PlayerTag.PLAYER_TWO, 3);

        assertTrue(match.getWinner().isPlayerTwo());
    }

    private void playerWinSets(PlayerTag playerTag, int times) {
        for (int i = 0; i < times; i++) {
            playerWinGames(playerTag, 6);
        }
    }

    private void currentSetScoreIs6To6() {
        playerWinGames(PlayerTag.PLAYER_ONE, 5);
        playerWinGames(PlayerTag.PLAYER_TWO, 5);

        playerWinGames(PlayerTag.PLAYER_ONE, 1);
        playerWinGames(PlayerTag.PLAYER_TWO, 1);
    }

    private void playerScores(PlayerTag playerTag, int times) {
        for (int i = 0; i < times; i++) {
            match.playerScores(playerTag);
        }
    }

    private void playerWinGames(PlayerTag playerTag, int times) {
        for (int i = 0; i < times; i++) {
            playerWinOneGame(playerTag);
        }
    }

    private void playerWinOneGame(PlayerTag playerTag) {
        for (int i = 0; i < 4; i++) {
            match.playerScores(playerTag);
        }
    }
}
