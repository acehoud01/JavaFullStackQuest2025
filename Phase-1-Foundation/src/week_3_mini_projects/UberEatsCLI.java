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
    static class Order {
        private final ArrayList<String> items;
        private final double total;
        private String status;

        public Order(ArrayList<String> items, double total) {
            this.items = new ArrayList<>(items); // Copy cart
            this.total = total;
            this.status = "Preparing";
        }

        public void updateStatus() {
            if (status.equals("Preparing")) status = "Out for Delivery";
            else if (status.equals("Out for Delivery")) status = "Delivered";
        }

        public void display() {
            System.out.println("Status Order " + status);
            System.out.println("Items:");
            int index = 0;
            for (String item : items) {
                System.out.println(++index + ". " + item);
            }
            System.out.printf("Total: R%.2f\n", total);
        }
    }
    private final Scanner scanner;
    private final HashMap<String, HashMap<String, Double>> restaurants; // Name -> Menu (Item -> Price)
    private final ArrayList<String> cart;
    private double total;
    private final HashMap<Integer, Order> orderHistory;
    private int orderId;

    public UberEatsCLI() {
        this.scanner = new Scanner(System.in);
        this.restaurants = new HashMap<>();
        this.cart = new ArrayList<>();
        this.orderHistory = new HashMap<>();
        this.orderId = 1;
        this.total = 0.0;
        setUpRestaurants();
    }

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

    public void displayMenu(String restaurant) {
        HashMap<String, Double> menu = restaurants.get(restaurant);
        System.out.println(restaurant + " Menu: ");
        int index = 0;
        for (String item : menu.keySet()) { // Get all items in menu
            System.out.printf("%d. %s - R%.2f\n", ++index, item, menu.get(item));
        }
    }

    public void addToCart(String restaurant, int itemChoice) {
        HashMap<String, Double> menu = restaurants.get(restaurant);
        String item = (String) menu.keySet().toArray()[itemChoice - 1];
        cart.add(item);
        total += menu.get(item);
        System.out.println(item + " added to cart!");
    }

    public void showCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart's empty!");
        } else {
            System.out.println("\nYour Cart:");
            int index = 0;
            for (String item : cart) {
                System.out.println(++index + ". " + item);
            }
            System.out.printf("Total: R%.2f\n", total);
        }
    }

    public void placeOrder() {
        if (cart.isEmpty()) {
            System.out.println("Add items to cart!");
        } else {
            Order order = new Order(cart, total);
            orderHistory.put(orderId++, order);
            cart.clear();
            total = 0.0;
            System.out.println("Order Placed! ID: " + (orderId - 1));
        }
    }

    public void trackOrder(int id) {
        Order order = orderHistory.get(id);
        if (order != null) {
            order.updateStatus();
            order.display();
        } else {
            System.out.println("Order not found!");
        }
    }

    public void resetCart() {
        cart.clear();
        total = 0.0;
        System.out.println("Cart cleared!");
    }

    public void viewOrderHistory() {
        if (orderHistory.isEmpty()) {
            System.out.println("No orders yet!");
        } else {
            System.out.println("\nOrder History:");
            for (Integer id : orderHistory.keySet()) {
                System.out.println("Order ID: " + id);
                orderHistory.get(id).display();
            }
        }
    }

    public static void main(String[] args) {
        UberEatsCLI app = new UberEatsCLI();
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
                        System.out.println(++index + ". " + name);
                    }

                    System.out.print("Pick a restaurant (1-" + app.restaurants.size() + "): ");
                    int resChoice = app.scanner.nextInt();
                    app.scanner.nextLine();

                    String selected = resChoice > 0 && resChoice <= app.restaurants.size() ?
                            (String) app.restaurants.keySet().toArray()[resChoice - 1] : "KFC";
                    app.displayMenu(selected);

                    System.out.print("Add item to cart (1-" + app.restaurants.get(selected).size() + "): ");
                    int itemChoice = app.scanner.nextInt();

                    if (itemChoice > 0 && itemChoice <= app.restaurants.get(selected).size()) {
                        app.addToCart(selected, itemChoice);
                    } else  {
                        System.out.println("Invalid item!");
                    }
                }
                case 2 -> app.showCart();
                case 3 -> app.placeOrder();
                case 4 -> {
                    System.out.print("Enter Oder ID: ");
                    int id = app.scanner.nextInt();
                    app.trackOrder(id);
                }
                case 5 -> app.viewOrderHistory();
                case 6 -> app.resetCart();
                case 7 -> {
                    System.out.println("See ya!");
                    app.scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
