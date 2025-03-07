import java.util.Scanner;


/**
 * Note: This is a build-up project for learning Java Full-Stack development.
 * It represents progress as of Day 4 (March 6th, 2025) of Week 1: Java Fundamentals.
 * Features will expand daily as new concepts (e.g., Collections, APIs) are learned.
 * <p>
 * Current state: Encapsulation (private fields, getters/setters) and Scanner for user input
 * added to store selection (switch), OOP (classes, objects), syntax, variables, data types,
 * operators, and control flow (if-else, while).
 */


public class FoodieOnline{
    static class FoodItem {
        String name;
        double price;
        boolean isAvailable;

        FoodItem(String name, double price, boolean isAvailable) {
            this.name = name;
            this.price = price;
            this.isAvailable = isAvailable;
        }

        void display() {
            System.out.println(name + " - R" + price + (isAvailable ? " (Available)" : " (Out of Stock)"));
        }

        boolean canOrder() { return isAvailable; }
    }

    static class Store {
        String name;
        FoodItem[] menu;

        Store(String name, FoodItem[] menu) {
            this.name = name;
            this.menu = menu;
        }

        void displayMenu() {
            System.out.println("______ " + name + " Menu ______");
            int i = 0;
            while (i < menu.length) {
                menu[i].display();
                i++;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Foodie online!");
        System.out.println("Choose a store:\n1. KFC\n2. Nandos\n3. Chicken Licken");

        // create stores
        FoodItem[] kfcMenu = {
                new FoodItem("Streetwise Three with pap", 49.90, true),
                new FoodItem("Streetwise Bucket For 1", 49.90, false),
                new FoodItem("Streetwise Three with Chips", 55.99, true)
        };

        FoodItem[] nandosMenu = {
               new FoodItem("Full Chicken + any 3 sharing sides", 374.00, true),
               new FoodItem("Roasted Veg", 43.00, false),
               new FoodItem("Chicken wrap", 99.00, true)
        };

        FoodItem[] chickenLickenMenu = {
                new FoodItem("Hotwings 12", 109.00, true),
                new FoodItem("Soul Mates Classic Party", 195, true),
                new FoodItem("Hotwings Meal 8 Max", 120, true)
        };

        // Create stores
        Store kfc = new Store("KFC", kfcMenu);
        Store nandos = new Store("Nandos", nandosMenu);
        Store chickenLicken = new Store("Chicken Licken", chickenLickenMenu);

        // Store options
        Store[] stores = {kfc, nandos, chickenLicken};

        // User picks a store with switch
        int storeChoice = scanner.nextInt();
        Store selectedStore = null;

        switch (storeChoice) {
            case 1 -> selectedStore = stores[0];
            case 2 -> selectedStore = stores[1];
            case 3 -> selectedStore = stores[2];
            default -> System.out.println("Invalid store");
        }

        // Order logic
        if (selectedStore != null) {
            System.out.println("You chose: " + selectedStore.name);
            selectedStore.displayMenu();

            System.out.print("Enter food item to order: ");
            scanner.nextLine(); // Clear buffer
            String foodChoice = scanner.nextLine();


            double total = 0.0;
            int i = 0;
            
            while (i < selectedStore.menu.length) {
                if (foodChoice.equals(selectedStore.menu[i].name) && selectedStore.menu[i].canOrder()) {
                    total += selectedStore.menu[i].price;
                    System.out.println("Ordered " + foodChoice + " for R" + selectedStore.menu[i].price);
                    break;
                }
                i++;
            }
            if (total == 0.0) {
                System.out.println("Sorry, " + foodChoice + " is unavailable or invalid!");
            }
            System.out.println("Total: R" + total);
        }
        scanner.close();
    }
}