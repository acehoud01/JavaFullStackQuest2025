package week_1_mini_projects;
import java.util.Scanner;

public class Player {
    int number; // The player's guessed number
    private Scanner scanner = new Scanner(System.in); // Scanner for user input

    // Method for the player to make a guess
    public void guess(int playerNumber) {
        System.out.print("Player " + playerNumber + ", enter your guess (0-9): ");
        while (!scanner.hasNextInt()) { // Validate input
            System.out.print("Invalid input. Enter a number between 0 and 9: ");
            scanner.next(); // Discard invalid input
        }
        number = scanner.nextInt();

        // Ensure the number is within the range
        while (number < 0 || number > 9) {
            System.out.print("Out of range! Enter a number between 0 and 9: ");
            number = scanner.nextInt();
        }
    }
}
