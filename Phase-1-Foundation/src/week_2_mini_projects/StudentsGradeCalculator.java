package week_2_mini_projects;
import java.util.Scanner;

public class StudentsGradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many grades to enter: ");
        int size = scanner.nextInt();

        // Array to store grades
        double[] grades = new double[size];

        // Fill array
        for (int i = 0; i < grades.length; i++) {
            System.out.print("Enter grade " + (i + 1) + ": ");
            grades[i] = scanner.nextDouble();
        }

        // Calculate average
        double sum = 0;
        for (Double grade : grades) {
            sum += grade;
        }

        double avg = sum / grades.length;

        System.out.printf("Average garde: %.2f\n", avg);
        scanner.close();
    }
}
