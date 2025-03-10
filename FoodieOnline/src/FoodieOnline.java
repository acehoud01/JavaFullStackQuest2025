import java.util.Scanner;

/**
 * Note: This is a build-up project for learning Java Full-Stack development.
 * It represents progress as of Day 6 (March 10th, 2025) of Week 2: Java Fundamentals.
 * Features will expand daily as new concepts (e.g., Methods, APIs) are learned.
 * <p>
 * Current state: Arrays for multiple stores added to encapsulation, Scanner input,
 * switch selection, OOP (classes, objects), syntax, variables, data types, operators,
 * and control flow (if-else, while).
 */

public class FoodieOnline{
    static class FoodItem {
        private String name;
        private double price;
        private boolean isAvailable;

        FoodItem(String name, double price, boolean isAvailable) {
            this.name = name;
            this.price = price;
            this.isAvailable = isAvailable;
        }


        public String getName() { return name; }
        public double getPrice() { return price; }


        void display() {
            System.out.println(name + " - R" + price + (isAvailable ? " (Available)" : " (Out of Stock)"));
        }

        public boolean canOrder() { return isAvailable; }
    }

    static class Store {
        private String name;
        private FoodItem[] menu;

        Store(String name, FoodItem[] menu) {
            this.name = name;
            this.menu = menu;
        }

        public String getName() { return name; }
        public FoodItem[] getMenu() { return menu; }

        void displayMenu() {
            System.out.println("______ " + name + " Menu ______");

            for (FoodItem item : menu) {
                item.display();
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Foodie online!");

        // Define Menu
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
        Store[] stores = {
                new Store("KFC", kfcMenu),
                new Store("Nandos", nandosMenu),
                new Store("Chicken Licken", chickenLickenMenu)
        };

        // Show store options
        System.out.println("Available stores:");
        for (int i = 0; i < stores.length; i++) {
            System.out.println((i + 1) + ". " + stores[i].getName());
        }

        // User picks a store
        System.out.print("Enter store number (1-" + stores.length + "): ");
        int storeChoice = scanner.nextInt() - 1; // Adjust for 0-based index
        Store selectedStore = null;

        if (storeChoice >= 0 && storeChoice < stores.length) {
            selectedStore = stores[storeChoice];
        } else {
            System.out.println("Invalid store choice!");
            scanner.close();
            return;
        }

        // Order logic
        System.out.println("You chose: " + selectedStore.getName());
        selectedStore.displayMenu();

        System.out.print("Enter food item to order: ");
        scanner.nextLine(); // Clear buffer
        String foodChoice = scanner.nextLine();

        double total = 0.0;
        for (int i = 0; i < selectedStore.getMenu().length; i++) {
            if (foodChoice.equals(selectedStore.getMenu()[i].getName()) &&
                    selectedStore.getMenu()[i].canOrder()) {
                total += selectedStore.getMenu()[i].getPrice();
                System.out.println("Ordered " + foodChoice + " for R" +
                        selectedStore.getMenu()[i].getPrice());
                break;
            }
        }
        if (total == 0.0) {
            System.out.println("Sorry, " + foodChoice + " is unavailable or invalid!");
        }
        System.out.println("Total: R" + total);
        scanner.close();
    }
}