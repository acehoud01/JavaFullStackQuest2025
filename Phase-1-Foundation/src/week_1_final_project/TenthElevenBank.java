package week_1_final_project;

import java.util.Scanner;

/**
 * Note: This is a build-up project for learning Java Full-Stack development.
 * It represents progress as of Day 5 (March 7th, 2025) of Week 1: Java Fundamentals.
 * Features will expand daily as new concepts (e.g., Collections, APIs) are learned.
 * <p>
 * Current state: Encapsulation (private fields, getters/setters), Scanner for user input,
 * login system with PIN validation (inspired by real banking), and menu-driven CLI using
 * switch, OOP (classes, objects), syntax, variables, data types, operators, and control flow
 * (if-else, while). Handles edge cases like negative deposits and PIN retries.
 */
public class TenthElevenBank {   // Inner class representing a Current Account
    static class CurrentAccount {
        // Private fields for account details
        private String name;
        private String surname;
        private String title;
        private long accountNumber;
        private double balance;
        private int pin;

        // Constructor to initialize account details
        public CurrentAccount(String name, String surname, String title,
                              long accountNumber, double balance, int pin) {
            this.name = name;
            this.surname = surname;
            this.title = title;
            setAccountNumber(accountNumber); // Validate and set account number
            this.balance = balance;
            setPin(pin); // Validate and set PIN
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

        // Placeholder methods for future features
        public void openAccount() {
        }
        public void closeAccount() {}
        public void addSavingsAccount() {}
        public void applyForCredit() {}

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
                System.out.printf("Withdrawal successful! Available balance: R%.2f\n", balance);
            } else if (amount <= 0) {
                System.out.println("Invalid amount! Must be positive.");
            } else {
                System.out.println("Insufficient funds!");
            }
        }
    }

    // Main method to run the program
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // Create a test account with sample data
        CurrentAccount test = new CurrentAccount("Anele", "Billy", "Mr", 1234567890L, 500.00, 12345);
        System.out.println("TenthElevenBank");
        System.out.println("1. Manage your Account\n2. Open an Account\n3. Deposit money to someone");
        System.out.print("\nEnter your option: ");
        int testOptions = input.nextInt();

        switch (testOptions) {
            case 1 -> System.out.println("just a test");
            case 2 -> test.openAccount();
            case 3 -> System.out.println("another test");
        }

        boolean isLoggedIn = false; // Track login status
        int pinTries = 0; // Track number of login attempts

//        // Login Loop
//        while (pinTries < 3 && !isLoggedIn) {
//            System.out.print("Please enter your Account Number: ");
//            long userAcc = input.nextLong();
//            System.out.print("Please enter your PIN: ");
//            int userPin = input.nextInt();
//
//            // Validate account number and PIN
//            if (userAcc == test.getAccountNumber() && userPin == test.getPin()) {
//                isLoggedIn = true; // Successful login
//                System.out.println("Welcome " + test.getTitle() + " " + test.getSurname());
//            } else {
//                pinTries++; // Increment failed attempts
//                System.out.println("Incorrect account number or PIN. " + (3 - pinTries) + " tries left.");
//            }
//        }
//
//        // Block account if login fails after 3 attempts
//        if (!isLoggedIn) {
//            System.out.println("Account blocked after 3 failed attempts.");
//            input.close(); // Close Scanner
//            return; // Exit program
//        }
//
//        // Menu Loop
//        while (true) {
//            System.out.println("\nChoose one of the following options:");
//            System.out.println("1. View Account Details\n2. Deposit\n3. Withdraw\n4. Exit");
//            System.out.print("Enter option: ");
//            int option = input.nextInt();
//
//            // Handle user menu selection
//            switch (option) {
//                case 1:
//                    test.viewAccount(); // Display account details
//                    break;
//                case 2:
//                    test.deposit(input); // Deposit money
//                    break;
//                case 3:
//                    test.withdraw(input); // Withdraw money
//                    break;
//                case 4:
//                    // Exit program with final balance
//                    System.out.println("Thank you for using TenthEleven. Final balance: R" +
//                            String.format("%.2f", test.getBalance()));
//                    input.close(); // Close Scanner
//                    return; // Exit program
//                default:
//                    System.out.println("Invalid option. Please try again.");
//            }
//        }
    }
}