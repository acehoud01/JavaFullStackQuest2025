package week_3_mini_projects;
import java.util.HashMap;
import java.util.Scanner;

public class GuessTheNumber {
    private int secreteNumber;
    private Scanner scanner;
    private int attempts;
    private HashMap<String, Integer> highScores;

    public GuessTheNumber() {
        this.scanner = new Scanner(System.in);
        this.highScores = new HashMap<>();
        resetGame();
    }

    private void resetGame() {
        this.secreteNumber = (int)(Math.random() * 100) + 1;
        this.attempts = 0;
    }

    public void playRound(String playerName) {
        while (true) {
            System.out.println("Enter your guess (1 - 100): ");
            int guess = scanner.nextInt();
            attempts++;

            if (guess == secreteNumber) {
                System.out.println("Congrats! the guess was " + secreteNumber + " You got it in " + attempts + " attempts!");
                break;
            } else if (guess < secreteNumber) {
                System.out.println("Too low! Try again.");
            } else {
                System.out.println("Too high! Try again.");
            }
        }
    }

    private void updateHighScore(String playerName, int attempts) {
        if (!highScores.containsKey(playerName) || highScores.get(playerName) > attempts) {
            highScores.put(playerName, attempts);
            System.out.println("New high score for " + playerName + ": " + attempts + " attempts!");
        }
    }

    public void viewHighScores() {
        if (highScores.isEmpty()) {
            System.out.println("No high scores yet!");
        } else {
            System.out.println("High Scores:");
            for (String name : highScores.keySet()) {
                System.out.println(name + ": " + highScores.get(name) + " attempts");
            }
        }
    }


    public static void main(String[] args) {
        GuessTheNumber game = new GuessTheNumber();
        System.out.println("Welcome to guess the number (1 - 100)!");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Play Game");
            System.out.println("2. View High Scores");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            int choice = game.scanner.nextInt();
            game.scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter your name: ");
                    String playerName = game.scanner.nextLine();
                    game.playRound(playerName);
                    break;
                case 2:
                    game.viewHighScores();
                    break;
                case 3:
                    System.out.println("Thanks for playing!");
                    game.scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
            game.resetGame();
        }
    }

}
