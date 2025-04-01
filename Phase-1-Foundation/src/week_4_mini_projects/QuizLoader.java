package week_4_mini_projects;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class QuizLoader {
    // Scanner for user input
    private Scanner scanner;
    // HashMap to store questions (key) and answers (value)
    private HashMap<String, String> questions;

    // Constructor initializes the scanner and loads questions
    public QuizLoader() {
        this.scanner = new Scanner(System.in);
        this.questions = new HashMap<>();
        loadQuestions(); // Load questions from file
    }

    // Method to load questions from a text file
    private void loadQuestions() {
        try {
            // Create file object for quiz.txt
            File file = new File("quiz.txt");
            // Scanner to read the file
            Scanner fileScanner = new Scanner(file);
            // Read file line by line
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                // Split each line into question and answer using comma delimiter
                String[] parts = line.split(",");
                // Only add if line has both question and answer
                if (parts.length == 2) {
                    questions.put(parts[0], parts[1]);
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            // Handle case where file is not found
            System.out.println("Oops! quiz.txt not found—check your folder!");
        }
    }

    // Method to run the quiz game
    public void playQuiz() {
        // Check if questions were loaded successfully
        if (questions.isEmpty()) {
            System.out.println("No questions loaded—check quiz.txt!");
            return;
        }

        int score = 0; // Track correct answers
        int total = questions.size(); // Total number of questions
        System.out.println("\nStarting the Quiz—let's go!");

        // Iterate through all questions
        for (String question : questions.keySet()) {
            System.out.println(question); // Display question
            System.out.print("Your answer: ");
            String answer = scanner.nextLine().trim(); // Get user answer
            String correct = questions.get(question); // Get correct answer

            // Check if answer is correct (case insensitive)
            if (answer.equalsIgnoreCase(correct)) {
                System.out.println("Correct!");
                score++; // Increment score
            } else {
                System.out.println("Nope! Right answer: " + correct);
            }
        }
        // Display final score
        System.out.printf("Quiz done! Score: %d/%d\n", score, total);
    }

    // Main method to run the application
    public static void main(String[] args) {
        QuizLoader quiz = new QuizLoader(); // Create quiz instance
        System.out.println("Welcome to QuizLoader!");

        // Main menu loop
        while (true) {
            System.out.println("\n1. Play Quiz");
            System.out.println("2. Quit");
            System.out.print("Choose: ");
            int choice = quiz.scanner.nextInt();
            quiz.scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    quiz.playQuiz(); // Start quiz
                    break;
                case 2:
                    System.out.println("See ya, champ!");
                    quiz.scanner.close(); // Close scanner
                    return; // Exit program
                default:
                    System.out.println("Invalid choice—try again!");
            }
        }
    }
}