import java.util.Scanner;

/**
 * Note: This is a build-up project for learning Java Full-Stack development.
 * It represents progress as of Day 8 (March 12th, 2025) of Week 2: Java Fundamentals.
 * Features will expand daily as new concepts (e.g., Debugging, APIs) are learned.
 *
 * Current state: String manipulation (case-insensitive matching) added to methods,
 * arrays, encapsulation, Scanner input, switch selection, OOP, syntax, variables,
 * data types, operators, and control flow.
 */
public class FoodieOnline {
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

        // Method to calculate total for multiple items
        public double calculateOrderTotal(String[] orderItems) {
            double total = 0.0;
            for (String itemName : orderItems) {
                for (int i = 0; i < menu.length; i++) {
                    if (itemName.equals(menu[i].getName()) && menu[i].canOrder()) {
                        total += menu[i].getPrice();
                        break;
                    }
                }
            }
            return total;
        }

        // Method to check item availability
        public boolean isItemAvailable(String itemName) {
            for (int i = 0; i < menu.length; i++) {
                if (itemName.equals(menu[i].getName())) {
                    return menu[i].canOrder();
                }
            }
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to FoodieOnline!");

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
        int storeChoice = scanner.nextInt() - 1;
        Store selectedStore = null;

        if (storeChoice >= 0 && storeChoice < stores.length) {
            selectedStore = stores[storeChoice];
        } else {
            System.out.println("Invalid store choice!");
            scanner.close();
            return;
        }

        // Order logic with methods
        System.out.println("You chose: " + selectedStore.getName());
        selectedStore.displayMenu();

        System.out.print("How many items to order? ");
        int numItems = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        String[] orderItems = new String[numItems];

        for (int i = 0; i < numItems; i++) {
            System.out.print("Enter item " + (i + 1) + " to order: ");
            orderItems[i] = scanner.nextLine();
        }

        // Use both methods
        double total = 0.0;
        for (String item : orderItems) {
            if (selectedStore.isItemAvailable(item)) {
                total = selectedStore.calculateOrderTotal(orderItems);
                System.out.println("Order placed for: " + String.join(", ", orderItems));
                break; // Exit after valid order
            } else {
                System.out.println("Sorry, " + item + " is unavailable or invalid!");
            }
        }

        if (total > 0) {
            System.out.printf("Total: R%.2f\n", total);
        } else {
            System.out.println("No valid items ordered!");
        }
        scanner.close();
    }
}