import java.util.ArrayList;
import java.util.Scanner;

/**
 * Note: Progress as of Day 12 (March 18th, 2025) of Week 3: Collections.
 * Current state: Multiple stores with ArrayList, operations (remove unavailable items)
 * added to ArrayList menus, methods, arrays, encapsulation, Scanner input, switch
 * selection, OOP, syntax, variables, data types, operators, string manipulation,
 * and control flow.
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
        public boolean canOrder() { return isAvailable; }

        void display() {
            System.out.println(name + " - R" + price + (isAvailable ? " (Available)" : " (Out of Stock)"));
        }
    }

    static class Store {
        private String name;
        private ArrayList<FoodItem> menu;

        Store(String name) {
            this.name = name;
            this.menu = new ArrayList<>();
        }

        public String getName() { return name; }
        public ArrayList<FoodItem> getMenu() { return menu; }

        void addItem(FoodItem item) {
            menu.add(item);
        }

        void removeUnavailableItems() {
            for (int i = menu.size() - 1; i >= 0; i--) {
                if (!menu.get(i).canOrder()) {
                    menu.remove(i);
                }
            }
        }

        void displayMenu() {
            System.out.println("______ " + name + " Menu ______");
            for (FoodItem item : menu) {
                item.display();
            }
        }

        public double calculateOrderTotal(String[] orderItems) {
            double total = 0.0;
            for (String itemName : orderItems) {
                for (FoodItem item : menu) {
                    if (itemName.equalsIgnoreCase(item.getName()) && item.canOrder()) {
                        total += item.getPrice();
                        break;
                    }
                }
            }
            return total;
        }

        public boolean isItemAvailable(String itemName) {
            for (FoodItem item : menu) {
                if (itemName.equalsIgnoreCase(item.getName())) {
                    return item.canOrder();
                }
            }
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to FoodieOnline!");

        // Multiple stores with ArrayList
        ArrayList<Store> stores = new ArrayList<>();
        Store kfc = new Store("KFC");
        kfc.addItem(new FoodItem("Streetwise Three with pap", 49.90, true));
        kfc.addItem(new FoodItem("Streetwise Bucket For 1", 49.90, false));
        kfc.addItem(new FoodItem("Streetwise Three with Chips", 55.99, true));

        Store nandos = new Store("Nandos");
        nandos.addItem(new FoodItem("Full Chicken + any 3 sharing sides", 374.00, true));
        nandos.addItem(new FoodItem("Roasted Veg", 43.00, false));
        nandos.addItem(new FoodItem("Chicken wrap", 99.00, true));

        Store chickenLicken = new Store("Chicken Licken");
        chickenLicken.addItem(new FoodItem("Hotwings 12", 109.00, true));
        chickenLicken.addItem(new FoodItem("Soul Mates Classic Party", 195.00, true));
        chickenLicken.addItem(new FoodItem("Hotwings Meal 8 Max", 120.00, false));

        stores.add(kfc);
        stores.add(nandos);
        stores.add(chickenLicken);

        // Show stores
        System.out.println("Available Stores:");
        for (int i = 0; i < stores.size(); i++) {
            System.out.println((i + 1) + ". " + stores.get(i).getName());
        }

        // Pick a store
        System.out.print("Choose a store (1-" + stores.size() + "): ");
        int storeChoice = scanner.nextInt() - 1;
        Store selectedStore = null;

        if (storeChoice >= 0 && storeChoice < stores.size()) {
            selectedStore = stores.get(storeChoice);
        } else {
            System.out.println("Invalid store choice!");
            scanner.close();
            return;
        }

        System.out.println("\nYou chose: " + selectedStore.getName());
        System.out.println("Before cleanup:");
        selectedStore.displayMenu();

        selectedStore.removeUnavailableItems();
        System.out.println("\nAfter cleanup:");
        selectedStore.displayMenu();

        System.out.print("How many items to order? ");
        int numItems = scanner.nextInt();
        scanner.nextLine();
        String[] orderItems = new String[numItems];

        for (int i = 0; i < numItems; i++) {
            System.out.print("Enter item " + (i + 1) + " to order: ");
            orderItems[i] = scanner.nextLine();
        }

        double total = 0.0;
        for (String item : orderItems) {
            if (selectedStore.isItemAvailable(item)) {
                total = selectedStore.calculateOrderTotal(orderItems);
                System.out.println("Order placed for: " + String.join(", ", orderItems));
                break;
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