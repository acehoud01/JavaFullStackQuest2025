package week_3_mini_projects;

import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> tasks = new ArrayList<>(); // Dynamic list

        while (true) {
            System.out.println("\nCurrent Tasks:");
            if (tasks.size() == 0) {
                System.out.println("No tasks yet!");
            } else {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
            }

                System.out.print("\nEnter a task (or 'exit' to quit): ");
                String task = scanner.nextLine();
                if (task.equalsIgnoreCase("exit")) break;

                tasks.add(task);
                System.out.println("Task added");

        }
    }
}
