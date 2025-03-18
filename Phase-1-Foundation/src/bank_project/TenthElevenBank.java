package bank_project;
import java.util.Scanner;
/**
 * Note: This is a build-up project for learning Java Full-Stack development.
 * It represents progress as of Day 10 (March 14th, 2025) of Week 2: Java Fundamentals.
 * Features will expand as new concepts (e.g., Collections, APIs) are learned.
 * <p>
 * Current state: Transaction history (array-based) added to encapsulation, Scanner,
 * login system, menu-driven CLI, OOP, syntax, variables, data types, operators,
 * control flow, methods, and string manipulation.
 */

public class TenthElevenBank {    // Inner class representing a Current Account
    static class CurrentAccount {
        // Private fields for account details
        private String name;
        private String surname;
        private String title;
        private long accountNumber;
        private double balance;
        private int pin;
        private String[] transactionHistory; // New array for history
        private int transactionCount; // Track number of transactions;

        // Constructor to initialize account details
        public CurrentAccount(String name, String surname, String title,
                              long accountNumber, double balance, int pin) {
            this.name = name;
            this.surname = surname;
            this.title = title;
            setAccountNumber(accountNumber); // Validate and set account number
            this.balance = balance;
            setPin(pin); // Validate and set PIN
            this.transactionHistory = new String[10]; // Fixed size for now
            this.transactionCount = 0; // Start at 0
        }

        // Getters & Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getSurname() { return surname; }
        public void setSurname(String surname) { this.surname = surname; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        // Getter and setter for account number with validation
        public long getAccountNumber() { return accountNumber; }
        public void setAccountNumber(long accountNumber) {
            String acc = Long.toString(accountNumber);
            if (acc.length() == 10) { // Ensure account number is exactly 10 digits
                this.accountNumber = accountNumber;
            } else {
                System.out.println("Account number must be 10 digits!");
                this.accountNumber = 0; // Set to 0 if invalid
            }
        }

        // Getter and setter for balance with validation
        public double getBalance() { return balance; }
        public void setBalance(double balance) {
            if (balance >= 0) this.balance = balance; // Ensure balance is non-negative
        }

        // Getter and setter for PIN with validation
        public int getPin() { return pin; }
        public void setPin(int pin) {
            String pinNum = Integer.toString(pin);
            if (pinNum.length() == 5) { // Ensure PIN is exactly 5 digits
                this.pin = pin;
            } else {
                System.out.println("PIN must be 5 digits!");
                this.pin = 0; // Set to 0 if invalid
            }
        }

        public void logTransaction(String description, double amount, double newBalance) {
            if (transactionCount <= transactionHistory.length) {
                String entry = String.format("2025-03-14   %-20s R%.2f     R%.2f", description, amount, newBalance); // date fixed for now
                transactionHistory[transactionCount] = entry;
                transactionCount++;
            } else {
                System.out.println("Transaction history full!");
            }
        }

        // Placeholder methods for future features
//        public void openAccount() {}
//        public void closeAccount() {}
//        public void addSavingsAccount() {}
//        public void applyForCredit() {}



        // Method to display account details
        public void viewAccount() {
            System.out.println("Account Number: " + accountNumber + "\n" +
                    "Available Balance: R" + String.format("%.2f", balance));
        }

        // Method to deposit money into the account
        public void deposit(Scanner input) { // Reuse Scanner for input
            System.out.print("Enter the amount to deposit: ");
            double amount = input.nextDouble();
            if (amount > 0) { // Validate deposit amount
                balance += amount;
                logTransaction("ATM Deposit", amount, balance); // Log it!
                System.out.printf("Deposit successful. New balance: R%.2f\n", balance);
            } else {
                System.out.println("Invalid amount. Deposit failed.");
            }
        }

        // Method to withdraw money from the account
        public void withdraw(Scanner input) { // Reuse Scanner for input
            System.out.print("Enter amount to withdraw: ");
            double amount = input.nextDouble();
            if (amount > 0 && amount <= balance) { // Validate withdrawal amount
                balance -= amount;
                logTransaction("ATM Withdrawal", amount, balance); // Log it!
                System.out.printf("Withdrawal successful! Available balance: R%.2f\n", balance);
            } else if (amount <= 0) {
                System.out.println("Invalid amount! Must be positive.");
            } else {
                System.out.println("Insufficient funds!");
            }
        }

        // Transaction Method
        public void viewTransactionHistory() {
            System.out.println("Transaction History:");
            System.out.println("Date         Description          Amount     Balance");
            if (transactionCount == 0) {
                System.out.println("All transaction will be displayed here");
                return;
            } else {
                for (int i = 0; i < transactionCount; i++) {
                    System.out.println(transactionHistory[i]);
                }
            }
        }
    }

    public static void pauseExecution(Scanner scanner) {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine(); // Extra nextLine() to consume any leftover newline
        scanner.nextLine(); // This will wait for user input
    }

    public static void clearScreen() {
        try {
            String operatingSystem = System.getProperty("os.name");

            if (operatingSystem.contains("Windows")) {
                // For Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Unix/Linux/MacOS
                System.out.print("\033[H\033[2J");
                System.out.flush();

                // Alternative approach for Unix systems
                // new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Fallback if the above doesn't work
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    // Main method to run the program
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // Create a test account with sample data
        CurrentAccount test = new CurrentAccount("Anele", "Billy", "Mr", 1234567890L, 500.00, 12345);
        System.out.println("Welcome to TenthElevenBank");

        boolean isLoggedIn = false; // Track login status
        int pinTries = 0; // Track number of login attempts

        // Login Loop
        while (pinTries < 3 && !isLoggedIn) {
            System.out.print("Please enter your Account Number: ");
            long userAcc = input.nextLong();
            System.out.print("Please enter your PIN: ");
            int userPin = input.nextInt();

            // Validate account number and PIN
            if (userAcc == test.getAccountNumber() && userPin == test.getPin()) {
                isLoggedIn = true; // Successful login
                System.out.println("Welcome " + test.getTitle() + " " + test.getSurname());
            } else {
                pinTries++; // Increment failed attempts
                System.out.println("Incorrect account number or PIN. " + (3 - pinTries) + " tries left.");
            }
        }

        // Block account if login fails after 3 attempts
        if (!isLoggedIn) {
            System.out.println("Account blocked after 3 failed attempts.");
            input.close(); // Close Scanner
            return; // Exit program
        }

        // Menu Loop
        while (true) {
            System.out.println("\nChoose one of the following options:");
            System.out.println("1. View Account Details\n2. Deposit\n3. Withdraw\n4. View Transaction History\n5. Exit");
            System.out.print("Enter option: ");
            int option = input.nextInt();

            // Handle user menu selection
            switch (option) {
                case 1:
                    clearScreen();
                    test.viewAccount(); // Display account details
                    pauseExecution(input);
                    break;
                case 2:
                    clearScreen();
                    test.deposit(input); // Deposit money
                    pauseExecution(input);
                    break;
                case 3:
                    clearScreen();
                    test.withdraw(input); // Withdraw money
                    pauseExecution(input);
                    break;
                case 4:
                    clearScreen();
                    test.viewTransactionHistory(); // New option!
                    pauseExecution(input);
                    break;
                case 5:
                    // Exit program with final balance
                    System.out.println("Thank you for using TenthEleven.\nAvailable balance: R" +
                            String.format("%.2f", test.getBalance()));
                    input.close(); // Close Scanner
                    return; // Exit program
                default:
                    clearScreen();
                    System.out.println("Invalid option. Please try again.");
                    pauseExecution(input);
            }
        }
    }
}