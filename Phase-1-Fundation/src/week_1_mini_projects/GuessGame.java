package week_1_mini_projects;

import java.util.Scanner;

public class GuessGame {
    private Player p1;
    private Player p2;
    private Player p3;
    private Scanner scanner = new Scanner(System.in);

    public void startGame() {
        // Initialize players
        p1 = new Player();
        p2 = new Player();
        p3 = new Player();

        int targetNumber = (int) (Math.random() * 10); // Generate a target number
        System.out.println("\nI'm thinking of a number between 0 and 9...");

        while (true) {
            // Players make their guesses
            p1.guess(1);
            p2.guess(2);
            p3.guess(3);

            // Get players' guesses
            int guess1 = p1.number;
            int guess2 = p2.number;
            int guess3 = p3.number;

            // Check if any player guessed correctly
            boolean p1IsRight = (guess1 == targetNumber);
            boolean p2IsRight = (guess2 == targetNumber);
            boolean p3IsRight = (guess3 == targetNumber);

            if (p1IsRight || p2IsRight || p3IsRight) {
                System.out.println("\nWe have a winner!");
                System.out.println("Player 1 got it right? " + p1IsRight);
                System.out.println("Player 2 got it right? " + p2IsRight);
                System.out.println("Player 3 got it right? " + p3IsRight);
                System.out.println("Game is over!");
                break; // End the game if someone wins
            } else {
                System.out.println("No one guessed correctly. Try again!");
            }
        }
    }
}

