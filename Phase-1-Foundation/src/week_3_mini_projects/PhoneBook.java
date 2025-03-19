package week_3_mini_projects;
import java.util.HashMap;
import java.util.Scanner;

public class PhoneBook {
    // Encapsulated fields
    private String name;
    private Long number;

    // Constructor
    public PhoneBook(String name, Long number) {
        this.name = name;
        this.number = number;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for number
    public Long getNumber() {
        return number;
    }

    // Setter for number
    public void setNumber(Long number) {
        this.number = number;
    }

    // Override toString() for better readability
    @Override
    public String toString() {
        return "Name: " + name + ", Number: " + number;
    }

    // Static method to view all contacts
    static void viewAll(HashMap<String, PhoneBook> contacts) {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found!");
            return;
        }
        for (String name : contacts.keySet()) {
            System.out.println(contacts.get(name));
        }
    }

    // Static method to search for a contact by name or number
    static void search(HashMap<String, PhoneBook> contacts, String input) {
        boolean found = false;

        // Check if input is a number
        if (isNumeric(input)) {
            Long number = Long.parseLong(input);
            for (PhoneBook contact : contacts.values()) {
                if (contact.getNumber().equals(number)) {
                    System.out.println("Contact found: " + contact);
                    found = true;
                    break;
                }
            }
        } else {
            // If input is not a number, treat it as a name
            if (contacts.containsKey(input)) {
                System.out.println("Contact found: " + contacts.get(input));
                found = true;
            }
        }

        if (!found) {
            System.out.println("Contact not found!");
        }
    }

    // Static method to delete a contact by name or number
    static void delete(HashMap<String, PhoneBook> contacts, String input) {
        boolean found = false;

        // Check if input is a number
        if (isNumeric(input)) {
            Long number = Long.parseLong(input);
            for (String name : contacts.keySet()) {
                if (contacts.get(name).getNumber().equals(number)) {
                    System.out.println("Contact deleted: " + contacts.remove(name));
                    found = true;
                    break;
                }
            }
        } else {
            // If input is not a number, treat it as a name
            if (contacts.containsKey(input)) {
                System.out.println("Contact deleted: " + contacts.remove(input));
                found = true;
            }
        }

        if (!found) {
            System.out.println("Contact not found!");
        }
    }

    // Static method to delete all contacts
    static void deleteAll(HashMap<String, PhoneBook> contacts) {
        contacts.clear();
        System.out.println("All contacts deleted!");
    }

    // Helper method to check if a string is numeric
    private static boolean isNumeric(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    // Main method to test the functionality
    public static void main(String[] args) {
        HashMap<String, PhoneBook> contacts = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        // Adding some sample contacts
        contacts.put("Alice", new PhoneBook("Alice", 1234567890L));
        contacts.put("Bob", new PhoneBook("Bob", 9876543210L));
        contacts.put("Charlie", new PhoneBook("Charlie", 5555555555L));

        while (true) {
            System.out.println("\nPhone Book Menu:");
            System.out.println("1. View All Contacts");
            System.out.println("2. Search Contact");
            System.out.println("3. Delete Contact");
            System.out.println("4. Delete All Contacts");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    PhoneBook.viewAll(contacts);
                    break;
                case 2:
                    System.out.print("Enter name or number to search: ");
                    String searchInput = scanner.nextLine();
                    PhoneBook.search(contacts, searchInput);
                    break;
                case 3:
                    System.out.print("Enter name or number to delete: ");
                    String deleteInput = scanner.nextLine();
                    PhoneBook.delete(contacts, deleteInput);
                    break;
                case 4:
                    PhoneBook.deleteAll(contacts);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}