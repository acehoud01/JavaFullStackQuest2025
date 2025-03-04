/**
Note: This is a build-up project for learning Java Full-Stack development.
It represents progress as of Day 2 (March 4th, 2025) of Week 1: Java Fundamentals.
Features will expand daily as new concepts (e.g., Switch, OOP, APIs) are learned.

Current state: Basic store selection and ordering using syntax, variables, data types,
operators, and control flow (if-else, while).
 */

public class FoodieOnline{
    // FoodItem class (blueprint for menu items)
    static class FoodItem {
        String name;
        double price;
        boolean available;

        FoodItem(String name, double price, boolean available) {
            this.name = name;
            this.price = price;
            this.available = available;
        }

        void display() {
            System.out.println(name + " - R" + price + (available ? " (Available)" : " (Out of Stock)"));
        }

        boolean canOrder() {
            return available;
        }
    }

    // Store class (blueprint for stores)
    static class Store {
        String name;
        FoodItem[] menu; // Each store has its own menu

        Store(String name, FoodItem[] menu) {
            this.name = name;
            this.menu = menu;
        }

        void displayMenu() {
            System.out.println("=== " + name + " Menu ===");
            int i = 0;
            while (i < menu.length) { // Loop through menu
                menu[i].display();
                i++;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to FoodieOnline");

        // Create food items for Store 1
        FoodItem pizza = new FoodItem("Pap and Steak", 49.99, true);
        FoodItem burger = new FoodItem("Chicken and Rice", 38.99, false);
        FoodItem[] store1Menu = {pizza, burger};

        // Create food items for Store 2
        FoodItem sushi = new FoodItem("Kota King", 15.49, true);
        FoodItem ramen = new FoodItem("Biltong", 10.99, true);
        FoodItem[] store2Menu = {sushi, ramen};

        // Create stores
        Store store1 = new Store("Africa Taste", store1Menu);
        Store store2 = new Store("SA Fav", store2Menu);

        // Hardcoded store choice (for Day 2)
        String chosenStore = "Africa Taste"; // User picks this

        // Find and display chosen store's menu
        Store[] stores = {store1, store2};
        int i = 0;
        Store selectedStore = null;
        while (i < stores.length) {
            if (chosenStore.equals(stores[i].name)) { // String comparison (== for now)
                selectedStore = stores[i];
                break;
            }
            i++;
        }

        if (selectedStore != null) {
            selectedStore.displayMenu();

            // Order logic (hardcoded item for now)
            String choice = "Pap and Steak";
            double total = 0.0;
            i = 0;
            while (i < selectedStore.menu.length) {
                if (choice.equals(selectedStore.menu[i].name) && selectedStore.menu[i].canOrder()) {
                    total += selectedStore.menu[i].price;
                    System.out.println("Ordered " + selectedStore.menu[i].name + " for R" + selectedStore.menu[i].price);
                    break;
                }
                i++;
            }
            if (total == 0.0) {
                System.out.println("Sorry, " + choice + " is unavailable or invalid!");
            }

            // Confirmation countdown
            int count = 3;
            System.out.print("Confirming order in: ");
            while (count > 0) {
                System.out.print(count + " ");
                count--;
            }
            System.out.println("\nTotal: R" + total);
        } else {
            System.out.println("Store '" + chosenStore + "' not found!");
        }
    }
}