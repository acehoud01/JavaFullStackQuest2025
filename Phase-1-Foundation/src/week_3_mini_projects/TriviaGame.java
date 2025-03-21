package week_3_mini_projects;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class TriviaGame {
    private Scanner scanner;
    private int score;
    private int streak;
    private int maxStreak;
    private HashMap<String, HashMap<String, String>> categories;
    private HashMap<String, HashMap<String, String>> originalCategories;

    public TriviaGame() {
        this.scanner = new Scanner(System.in);
        this.score = 0;
        this.streak = 0;
        this.maxStreak = 0;
        this.categories = new HashMap<>();
        this.originalCategories = new HashMap<>();
        setupCategories();
        resetGame();
    }

    private void setupCategories() {
        HashMap<String, String> sports = new HashMap<>();
        sports.put("Which South African football club has won the most PSL (Premier Soccer League) titles?", "Mamelodi Sundowns");
        sports.put("What sport is known as 'The Beautiful Game'?", "Football");
        sports.put("In which year did South Africa host the FIFA World Cup?", "2010");
        sports.put("Which country hosts the Tour de France?", "France");
        sports.put("How many times has the South African national rugby team, the Springboks, won the Rugby World Cup?", "4");
        sports.put("Who was the captain of the Springboks when they won the 2019 Rugby World Cup?", "Siya Kolisi");
        sports.put("Which South African cricketer is known for scoring the fastest century in One Day Internationals (ODIs)?", "AB de Villiers");
        sports.put("What’s the distance of a marathon in miles?", "26.2");
        sports.put("What is the nickname of South Africa’s national cricket team?","The Proteas");
        sports.put("Which South African sprinter set a world record in the 400m at the 2016 Olympics?", "Wayde van Niekerk");
        sports.put("Caster Semenya, a South African middle-distance runner, specializes in which event?", "800 meters");
        sports.put("Which South African golfer has won the most major championships?", "Gary Player");
        sports.put("Which South African Formula 1 driver won the 1979 World Championship?", "Jody Scheckter");
        sports.put("Who was the top scorer in the inaugural PSL (Premier Soccer League) season in 1996/97?", "Wilfred Mugeyi");
        sports.put("Which South African club was the first to win the CAF Champions League?", "Orlando Pirates");
        sports.put("Bafana Bafana won their first and only Africa Cup of Nations (AFCON) title in 1996. Who was their captain?", "Neil Tovey");
        sports.put("Who was South Africa's coach when they won the 1995 Rugby World Cup?", "Kitch Christie" );
        sports.put("Which South African golfer won The Open Championship in 2010?", "Louis Oosthuizen");
        sports.put("Which South African female athlete won gold in the long jump at the 2000 Sydney Olympics?", "Hestrie Cloete");
        sports.put("Which South African bowler took a hat-trick in the 1999 Cricket World Cup against the West Indies?", "Allan Donald");
        categories.put("Sport", sports);
        originalCategories.put("Sports", new HashMap<>(sports));

        HashMap<String, String> geography = new HashMap<>();
        geography.put("What is the capital of Brazil?", "Brasilia");
        geography.put("Which continent is the largest by land area?", "Asia");
        geography.put("What’s the longest river in the world?", "Nile");
        geography.put("Which country has the most deserts?", "Australia");
        geography.put("What’s the smallest country by land area?", "Vatican City");
        geography.put("Which ocean is the largest?", "Pacific");
        geography.put("What mountain range separates Europe and Asia?", "Ural");
        geography.put("What’s the capital of Japan?", "Tokyo");
        geography.put("Which country has the most islands?", "Sweden");
        geography.put("What’s the highest mountain in the world?", "Everest");
        originalCategories.put("Geography", new HashMap<>(geography));
    }

    private void resetGame() {
        this.score = 0;
        this.streak = 0;
        this.maxStreak = 0;
        this.categories.clear();
        for (String cat : originalCategories.keySet()) {
            categories.put(cat, new HashMap<>(originalCategories.get(cat)));
        }
    }

    public String pickCategory() {
        System.out.println("Choose a category:");
        int index = 1;
        for (String category : categories.keySet()) {
            System.out.println(index++ + ". " + category);
        }
        System.out.print("Pick (1-" + categories.size() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice > 0 && choice <= categories.size() ?
                (String) categories.keySet().toArray()[choice - 1] : "Sports";
    }

    public String[] getRandomQuestion(String category) {
        HashMap<String, String> qa = categories.get(category);
        Object[] questions = qa.keySet().toArray();
        String question = (String) questions[new Random().nextInt(questions.length)];
        return new String[] { question, qa.get(question) };
    }

    public void playGame(String category) {
        HashMap<String, String> qa = categories.get(category);
        int totalQuestions = qa.size(); // Store initial size
        System.out.println("Starting " + category + " Trivia (" + totalQuestions + " questions)!");

        // Copy to avoid modifying during iteration
        HashMap<String, String> tempQa = new HashMap<>(qa);
        for (int i = 0; i < totalQuestions; i++) {
            String[] questionPair = getRandomQuestion(category);
            System.out.println("\nQuestion " + (i + 1) + ": " + questionPair[0]);
            System.out.print("Answer: ");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase(questionPair[1])) {
                score++;
                streak++;
                maxStreak = Math.max(maxStreak, streak);
                System.out.println("Correct! Score: " + score + ", Streak: " + streak);
                if (streak == 3) {
                    score += 5;
                    System.out.println("3 in a row! +5 Bonus! New Score: " + score);
                }
            } else {
                streak = 0;
                System.out.println("Wrong! Correct answer: " + questionPair[1]);
                System.out.println("Score: " + score + ", Streak: " + streak);
            }
            tempQa.remove(questionPair[0]); // Remove from temp copy
            categories.get(category).remove(questionPair[0]); // Remove from original
        }
        System.out.println("\nGame Over! Final Stats:");
        System.out.println("Score: " + score);
        System.out.println("Max Streak: " + maxStreak);
    }

    public static void main(String[] args) {
        TriviaGame game = new TriviaGame();
        System.out.println("Welcome to Trivia Challenge!");

        while (true) {
            System.out.println("\n1. Play Trivia");
            System.out.println("2. Reset Game");
            System.out.println("3. Quit");
            System.out.print("Choose: ");
            int choice = game.scanner.nextInt();
            game.scanner.nextLine();

            switch (choice) {
                case 1:
                    game.resetGame(); // Reset before each game
                    String category = game.pickCategory();
                    game.playGame(category);
                    break;
                case 2:
                    game.resetGame();
                    System.out.println("Game reset! Ready for a new round.");
                    break;
                case 3:
                    System.out.println("Thanks for playing! Final Score: " + game.score);
                    game.scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}