package alexis.breuse;

import alexis.breuse.domain.match.Match;
import alexis.breuse.domain.player.PlayerTag;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter player 1 name:");
        String playerOneName = scanner.nextLine();
        System.out.print("Enter player 2 name:");
        String playerTwoName = scanner.nextLine();

        Match match = new Match(playerOneName, playerTwoName);

        System.out.print("Do you wish to play in auto-mode (y/n): ");
        boolean autoMode = scanner.nextLine().equals("y");
        Random random = new Random();

        while (!match.hasWinner()) {
            displayMatchInformation(match);
            int scoringPlayer = autoMode ? autoPickScoringPlayer(random) : askWhichPlayerWillScore(scanner);

            if (scoringPlayer == 1 || scoringPlayer == 2) {
                match.playerScores(scoringPlayer == 1 ? PlayerTag.PLAYER_ONE : PlayerTag.PLAYER_TWO);
            }
        }
        displayMatchInformation(match);
    }

    private static int askWhichPlayerWillScore(Scanner scanner) {
        System.out.print("Which player wins the point (1 or 2) ? ");
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.next();
            return 0;
        }
    }

    private static int autoPickScoringPlayer(Random random) {
        return random.nextInt((2 - 1) + 1) + 1;
    }

    private static void displayMatchInformation(Match match) {
        System.out.println("Player 1: " + match.getPlayerOne().getName());
        System.out.println("Player 2: " + match.getPlayerTwo().getName());
        System.out.println("Score: " + match.getScore());
        System.out.println("Current game status: " + match.getCurrentGameScore().format());
        System.out.println("Match status: " + (match.hasWinner() ?
                match.getWinner().isPlayerOne() ? "Player 1 wins" : "Player 2 wins"
                : "In progress"));
    }
}