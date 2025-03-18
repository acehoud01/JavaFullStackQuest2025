package week_3_mini_projects;
import java.util.ArrayList;
import java.util.Scanner;

public class ShoppingCart {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> cart = new ArrayList<>();
        boolean running = true;

        while (running) {
            System.out.println("\nYour Cart:");
            if (cart.isEmpty()) {
                System.out.println("Empty");
            } else {
                for (int i = 0; i < cart.size(); i++) {
                    System.out.println((i + 1) + ". " + cart.get(i));
                }
            }

            System.out.print("\n1. Add Item\n2. Remove Item\n3. Check Item\n4. Exit\nChoose: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter item to add: ");
                    String item = scanner.nextLine();
                    cart.add(item);
                    System.out.println(item + " added!");
                }
                case 2 -> {
                    System.out.print("Enter item to remove: ");
                    String item = scanner.nextLine();
                    if (cart.remove(item)) {
                        System.out.println(item + " removed!");
                    } else {
                        System.out.println(item + " not in the cart");
                    }
                }
                case 3 -> {
                    System.out.print("Enter item to check: ");
                    String item = scanner.nextLine();
                    if (cart.contains(item)) {
                        System.out.println(item + " is in the cart!");
                    } else {
                        System.out.println(item + " not found!");
                    }
                }
                case 4 -> {
                    System.out.println("Exiting...");
                    running = false; // Exit the loop
                }
                default -> {
                    System.out.println("Invalid choice! Please choose a number between 1 and 4.");
                }
            }
        }

        scanner.close();
    }
}
