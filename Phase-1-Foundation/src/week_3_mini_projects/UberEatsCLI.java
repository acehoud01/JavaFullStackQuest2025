package week_3_mini_projects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Note: Progress as of Day 15 (March 21st, 2025) of Week 3: Collections.
 * Current state: Completed UberEats Clone with HashMap for restaurant menus (nested: name -> item/price),
 * ArrayList for cart and order items, full order flow (browse, add to cart, place, track, history).
 * Features: Encapsulation with public/private methods, Scanner input, switch-based CLI menu,
 * OOP (Order class), reset cart, order history view, string manipulation, variables, data types,
 * operators, and control flow. Next: TenthElevenBank.
 */

public class UberEatsCLI {
    // Inner class representing an order
    static class Order {
        private final ArrayList<String> items; // List of items in the order
        private final double total; // Total cost of the order
        private String status; // Current status of the order

        // Constructor to initialize an order with items and total cost
        public Order(ArrayList<String> items, double total) {
            this.items = new ArrayList<>(items); // Copy cart items to the order
            this.total = total;
            this.status = "Preparing"; // Initial status is "Preparing"
        }

        // Method to update the status of the order
        public void updateStatus() {
            if (status.equals("Preparing")) status = "Out for Delivery";
            else if (status.equals("Out for Delivery")) status = "Delivered";
        }

        // Method to display the order details
        public void display() {
            System.out.println("Status Order " + status);
            System.out.println("Items:");
            int index = 0;
            for (String item : items) {
                System.out.println(++index + ". " + item); // Display each item with an index
            }
            System.out.printf("Total: R%.2f\n", total); // Display the total cost
        }
    }

    // Instance variables for the UberEatsCLI class
    private final Scanner scanner; // Scanner for user input
    private final HashMap<String, HashMap<String, Double>> restaurants; // Map of restaurants and their menus
    private final ArrayList<String> cart; // List of items in the cart
    private double total; // Total cost of items in the cart
    private final HashMap<Integer, Order> orderHistory; // Map of order IDs to orders
    private int orderId; // Counter for generating unique order IDs

    // Constructor to initialize the UberEatsCLI application
    public UberEatsCLI() {
        this.scanner = new Scanner(System.in);
        this.restaurants = new HashMap<>();
        this.cart = new ArrayList<>();
        this.orderHistory = new HashMap<>();
        this.orderId = 1; // Start order IDs from 1
        this.total = 0.0; // Initialize total cost to 0
        setUpRestaurants(); // Populate the restaurants with menus
    }

    // Method to set up the restaurants and their menus
    private void setUpRestaurants() {
        HashMap<String, Double> kfc = new HashMap<>();
        kfc.put("10 Dunked Wings", 109.90);
        kfc.put("Streetwise Five with Chips", 123.90);
        kfc.put("Crunch Burger", 36.90);
        kfc.put("15 Piece Bucket", 289.90);
        kfc.put("Family Treat 10pc", 327.90);
        restaurants.put("KFC", kfc);

        HashMap<String, Double> burgerKing = new HashMap<>();
        burgerKing.put("Big King Meal", 80.90);
        burgerKing.put("Whopper with Cheese Meal", 107.90);
        burgerKing.put("Bacon King Meal", 161.90);
        burgerKing.put("Bacon Loaded Fries", 60.90);
        burgerKing.put("Triple Whopper Meal", 155.90);
        restaurants.put("Burger King", burgerKing);
    }

    // Method to display the menu of a selected restaurant
    public void displayMenu(String restaurant) {
        HashMap<String, Double> menu = restaurants.get(restaurant);
        System.out.println(restaurant + " Menu: ");
        int index = 0;
        for (String item : menu.keySet()) { // Iterate over the menu items
            System.out.printf("%d. %s - R%.2f\n", ++index, item, menu.get(item)); // Display each item with its price
        }
    }

    // Method to add an item to the cart
    public void addToCart(String restaurant, int itemChoice) {
        HashMap<String, Double> menu = restaurants.get(restaurant);
        String item = (String) menu.keySet().toArray()[itemChoice - 1]; // Get the selected item
        cart.add(item); // Add the item to the cart
        total += menu.get(item); // Update the total cost
        System.out.println(item + " added to cart!");
    }

    // Method to display the contents of the cart
    public void showCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart's empty!");
        } else {
            System.out.println("\nYour Cart:");
            int index = 0;
            for (String item : cart) {
                System.out.println(++index + ". " + item); // Display each item in the cart
            }
            System.out.printf("Total: R%.2f\n", total); // Display the total cost
        }
    }

    // Method to place an order
    public void placeOrder() {
        if (cart.isEmpty()) {
            System.out.println("Add items to cart!");
        } else {
            Order order = new Order(cart, total); // Create a new order
            orderHistory.put(orderId++, order); // Add the order to the order history
            cart.clear(); // Clear the cart
            total = 0.0; // Reset the total cost
            System.out.println("Order Placed! ID: " + (orderId - 1)); // Display the order ID
        }
    }

    // Method to track the status of an order
    public void trackOrder(int id) {
        Order order = orderHistory.get(id); // Retrieve the order by ID
        if (order != null) {
            order.updateStatus(); // Update the order status
            order.display(); // Display the order details
        } else {
            System.out.println("Order not found!"); // Handle invalid order ID
        }
    }

    // Method to reset the cart
    public void resetCart() {
        cart.clear(); // Clear the cart
        total = 0.0; // Reset the total cost
        System.out.println("Cart cleared!");
    }

    // Method to view the order history
    public void viewOrderHistory() {
        if (orderHistory.isEmpty()) {
            System.out.println("No orders yet!");
        } else {
            System.out.println("\nOrder History:");
            for (Integer id : orderHistory.keySet()) {
                System.out.println("Order ID: " + id); // Display the order ID
                orderHistory.get(id).display(); // Display the order details
            }
        }
    }

    // Main method to run the UberEatsCLI application
    public static void main(String[] args) {
        UberEatsCLI app = new UberEatsCLI(); // Create an instance of the application
        System.out.println("Welcome to UberEats Clone - Snack Time");

        while (true) {
            System.out.println("""
                    
                    1. Browse & Order
                    2. View Cart
                    3. Place Order
                    4. Track Order
                    5. View Order History\
                    
                    6. Clear Cart
                    7. Quit""");
            System.out.print("Choose: ");
            int choice = app.scanner.nextInt();
            app.scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("\nRestaurants:");
                    int index = 0;
                    for (String name : app.restaurants.keySet()) {
                        System.out.println(++index + ". " + name); // Display the list of restaurants
                    }

                    System.out.print("Pick a restaurant (1-" + app.restaurants.size() + "): ");
                    int resChoice = app.scanner.nextInt();
                    app.scanner.nextLine();

                    String selected = resChoice > 0 && resChoice <= app.restaurants.size() ?
                            (String) app.restaurants.keySet().toArray()[resChoice - 1] : "KFC"; // Select the restaurant
                    app.displayMenu(selected); // Display the menu of the selected restaurant

                    System.out.print("Add item to cart (1-" + app.restaurants.get(selected).size() + "): ");
                    int itemChoice = app.scanner.nextInt();

                    if (itemChoice > 0 && itemChoice <= app.restaurants.get(selected).size()) {
                        app.addToCart(selected, itemChoice); // Add the selected item to the cart
                    } else  {
                        System.out.println("Invalid item!"); // Handle invalid item selection
                    }
                }
                case 2 -> app.showCart(); // Display the cart
                case 3 -> app.placeOrder(); // Place an order
                case 4 -> {
                    System.out.print("Enter Order ID: ");
                    int id = app.scanner.nextInt();
                    app.trackOrder(id); // Track the status of an order
                }
                case 5 -> app.viewOrderHistory(); // View the order history
                case 6 -> app.resetCart(); // Reset the cart
                case 7 -> {
                    System.out.println("See ya!"); // Exit the application
                    app.scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice!"); // Handle invalid menu choice
            }
        }
    }
}