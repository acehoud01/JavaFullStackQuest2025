package week_2_mini_projects;
import java.util.Scanner;

public class Calculator {
    public int add(int a, int b) { return a + b; }

    public int minus(int a, int b) { return a - b; }

    public int multiply(int a, int b) {
        return a * b;
    }
    public double divide(double a, double b) {
        if (b <= 0) {
            System.out.println("Cannot divide by 0");
            return 0.0;
        } return a / b;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Calculator calculate = new Calculator();


        while (true) {
            System.out.println("Perform your calculations (or type 'exit' to quit):");
            String firstInput = input.next();

            // Check if the user wants to exit
            if (firstInput.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the calculator. Goodbye!");
                break;
            }

            int a = Integer.parseInt(firstInput);
            char operator = input.next().charAt(0);
            int b = input.nextInt();


            switch (operator) {
                case '+' -> System.out.println(a + " + " + b + " = " + (calculate.add(a, b)));
                case '-' -> System.out.println(a + " -  " + b + " = " + (calculate.minus(a, b)));
                case '/' -> System.out.println(a + " / " + b + " = " + (calculate.divide(a, b)));
                case '*' -> System.out.println(a + " * " + b + " = " + (calculate.multiply(a, b)));
                default -> System.out.println("Invalid operator");
            }
        }
    }
}
