package week_6_mini_projects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Custom exception for invalid PIN or account ID during login.
 */
class InvalidPinException extends Exception {
    public InvalidPinException(String message) {
        super(message);
    }
}

/**
 * TenthElevenBank - A CLI banking app with account management, transactions, and summaries.
 * Supports file-based persistence, PIN login, and sorted/searchable transactions.
 */
public class TenthElevenBank {
    /**
     * Base Account class for regular accounts.
     */
    static class Account {
        protected String accountHolder;
        protected double balance;
        protected ArrayList<String> transactions;
        protected int pin;

        public Account(String accountHolder, double balance, int pin) {
            this.accountHolder = accountHolder;
            this.balance = balance;
            this.pin = pin;
            this.transactions = new ArrayList<>();
            transactions.add("Initial deposit: R" + String.format("%.2f", balance));
        }

        public String getAccountHolder() { return accountHolder; }
        public double getBalance() { return balance; }
        public int getPin() { return pin; }
        public ArrayList<String> getTransactions() { return transactions; }

        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount;
                transactions.add("Deposit: R" + String.format("%.2f", amount));
                System.out.printf("Deposited R%.2f. New balance: R%.2f\n", amount, balance);
            } else {
                System.out.println("Invalid deposit amount!");
            }
        }

        public void withdraw(double amount) {
            if (amount > 0 && amount <= balance) {
                balance -= amount;
                transactions.add("Withdrawal: R" + String.format("%.2f", amount));
                System.out.printf("Withdrew R%.2f. New balance: R%.2f\n", amount, balance);
            } else {
                System.out.println("Invalid withdrawal amount or insufficient funds!");
            }
        }
    }

    /**
     * SavingsAccount extends Account with interest on deposits.
     */
    static class SavingsAccount extends Account {
        private double interestRate;

        public SavingsAccount(String accountHolder, double balance, double interestRate, int pin) {
            super(accountHolder, balance, pin);
            this.interestRate = interestRate;
        }

        public double getInterestRate() { return interestRate; }

        @Override
        public void deposit(double amount) {
            if (amount > 0) {
                double interest = amount * interestRate;
                balance += amount + interest;
                transactions.add("Deposit: R" + String.format("%.2f", amount) +
                        " + Interest: R" + String.format("%.2f", interest));
                System.out.printf("Deposited R%.2f + R%.2f interest. New balance: R%.2f\n",
                        amount, interest, balance);
            } else {
                System.out.println("Invalid deposit amount!");
            }
        }

        @Override
        public void withdraw(double amount) {
            super.withdraw(amount);
        }
    }

    private Scanner scanner;
    private HashMap<Integer, Account> accounts;
    private int nextAccountId;

    public TenthElevenBank() {
        this.scanner = new Scanner(System.in);
        this.accounts = new HashMap<>();
        this.nextAccountId = 1001;
        loadAccounts();
    }

    /**
     * Loads accounts from accounts.txt, handles missing or malformed data.
     */
    public void loadAccounts() {
        File file = new File("accounts.txt");
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length < 5) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                try {
                    int id = Integer.parseInt(parts[0]);
                    String holder = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    String type = parts[3];
                    int pin = Integer.parseInt(parts[4]);

                    if (type.equals("A")) {
                        accounts.put(id, new Account(holder, balance, pin));
                    } else if (type.equals("S") && parts.length == 6) {
                        double interestRate = Double.parseDouble(parts[5]);
                        accounts.put(id, new SavingsAccount(holder, balance, interestRate, pin));
                    } else {
                        System.out.println("Skipping malformed line: " + line);
                        continue;
                    }
                    if (id >= nextAccountId) nextAccountId = id + 1;
                } catch (NumberFormatException e) {
                    System.out.println("Bad number in line: " + line + " - skipping!");
                }
            }
            System.out.println("Accounts loaded from accounts.txt!");
        } catch (FileNotFoundException e) {
            System.out.println("No accounts.txt—starting fresh!");
            setupTestAccounts();
        }
    }

    /**
     * Sets up test accounts if file loading fails.
     */
    private void setupTestAccounts() {
       accounts.put(nextAccountId++, new Account("Anele Billy", 500.00, 1234));
        accounts.put(nextAccountId++, new SavingsAccount("Ace Man", 1000.00, 0.05, 4321));
    }

    /**
     * Saves accounts to accounts.txt.
     */
    public void saveAccounts() {
        try (PrintWriter writer = new PrintWriter(new File("accounts.txt"))) {
            for (Integer id : accounts.keySet()) {
                Account acc = accounts.get(id);
                String type = (acc instanceof SavingsAccount) ? "S" : "A";
                String line = String.format("%d,%s,%.2f,%s,%d",
                        id, acc.getAccountHolder(), acc.getBalance(), type, acc.getPin());
                if (type.equals("S")) {
                    line += "," + String.format("%.2f", ((SavingsAccount) acc).getInterestRate());
                }
                writer.println(line);
            }
            System.out.println("Accounts saved to accounts.txt!");
        } catch (FileNotFoundException e) {
            System.out.println("Oops! Couldn’t save accounts.txt!");
        }
    }

    /**
     * Handles login with PIN, allows 3 attempts.
     * @return Logged-in Account
     * @throws InvalidPinException if login fails
     */
    public Account login() throws InvalidPinException {
        Account acc = null;
        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Enter Account ID: ");
            int id = scanner.nextInt();
            System.out.print("Enter PIN: ");
            int pin = scanner.nextInt();
            scanner.nextLine();

            if (!accounts.containsKey(id)) {
                attempts--;
                throw new InvalidPinException("Account ID not found! " + attempts + " attempts left.");
            }
            acc = accounts.get(id);
            if (acc.getPin() != pin) {
                attempts--;
                throw new InvalidPinException("Invalid PIN—try again! " + attempts + " attempts left.");
            }
            break;
        }
        if (attempts == 0) {
            throw new InvalidPinException("Too many failed attempts—locked out!");
        }
        System.out.println("Login successful! Welcome, " + acc.getAccountHolder());
        return acc;
    }

    /**
     * Searches transactions for amounts >= minAmount.
     * @return List of matching transactions
     */
    public ArrayList<String> searchTransactions(Account acc, double minAmount) {
        ArrayList<String> matches = new ArrayList<>();
        for (String transaction : acc.getTransactions()) {
            if (transaction.contains("R")) {
                String[] parts = transaction.split("R");
                try {
                    double amount = Double.parseDouble(parts[1].split("[^0-9.]")[0]);
                    if (amount >= minAmount) {
                        matches.add(transaction);
                    }
                } catch (NumberFormatException e) {
                    // Skip malformed transactions
                }
            }
        }
        return matches;
    }

    /**
     * Sorts accounts by balance (descending).
     * @return Sorted list of accounts
     */
    public ArrayList<Account> sortAccountsByBalance() {
        ArrayList<Account> sorted = new ArrayList<>(accounts.values());
        Collections.sort(sorted, new Comparator<Account>() {
            @Override
            public int compare(Account a1, Account a2) {
                return Double.compare(a2.getBalance(), a1.getBalance());
            }
        });
        return sorted;
    }

    /**
     * Summarizes transactions by type and total.
     */
    public void summarizeTransactions(Account acc) {
        double totalDeposits = 0.0;
        double totalWithdrawals = 0.0;
        int depositCount = 0;
        int withdrawalCount = 0;

        for (String transaction : acc.getTransactions()) {
            if (transaction.contains("Deposit")) {
                String[] parts = transaction.split("R");
                try {
                    double amount = Double.parseDouble(parts[1].split("[^0-9.]")[0]);
                    totalDeposits += amount;
                    depositCount++;
                } catch (NumberFormatException e) {
                    // Skip malformed
                }
            } else if (transaction.contains("Withdrawal")) {
                String[] parts = transaction.split("R");
                try {
                    double amount = Double.parseDouble(parts[1].split("[^0-9.]")[0]);
                    totalWithdrawals += amount;
                    withdrawalCount++;
                } catch (NumberFormatException e) {
                    // Skip malformed
                }
            }
        }

        System.out.println("Transaction Summary for " + acc.getAccountHolder() + ":");
        System.out.printf("Deposits: %d, Total: R%.2f\n", depositCount, totalDeposits);
        System.out.printf("Withdrawals: %d, Total: R%.2f\n", withdrawalCount, totalWithdrawals);
    }

    // Refactored methods for run()
    private void handleDeposit(Account acc) {
        System.out.print("Enter amount to deposit: R");
        double amount = scanner.nextDouble();
        acc.deposit(amount);
    }

    private void handleWithdraw(Account acc) {
        System.out.print("Enter amount to withdraw: R");
        double amount = scanner.nextDouble();
        acc.withdraw(amount);
    }

    private void handleViewBalance(Account acc) {
        System.out.printf("Current balance: R%.2f\n", acc.getBalance());
    }

    private void handleSearchTransactions(Account acc) {
        System.out.print("Enter minimum transaction amount: R");
        double minAmount = scanner.nextDouble();
        ArrayList<String> matches = searchTransactions(acc, minAmount);
        if (matches.isEmpty()) {
            System.out.println("No transactions found above R" + minAmount);
        } else {
            System.out.println("Matching transactions:");
            for (String t : matches) System.out.println(t);
        }
    }

    private void handleViewSortedAccounts() {
        ArrayList<Account> sorted = sortAccountsByBalance();
        System.out.println("Accounts sorted by balance:");
        int index = 0;
        for (Account acc : sorted) {
            System.out.printf("%d. %s: R%.2f\n", ++index, acc.getAccountHolder(), acc.getBalance());
        }
    }

    private void handleSummary(Account acc) {
        summarizeTransactions(acc);
    }

    /**
     * Runs the banking menu for the logged-in account.
     */
    public void run(Account loggedIn) {
        while (true) {
            System.out.println("\nTenthElevenBank Menu:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. View Balance");
            System.out.println("4. Search Transactions");
            System.out.println("5. View All Accounts (Sorted)");
            System.out.println("6. Transaction Summary");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        handleDeposit(loggedIn);
                        break;
                    case 2:
                        handleWithdraw(loggedIn);
                        break;
                    case 3:
                        handleViewBalance(loggedIn);
                        break;
                    case 4:
                        handleSearchTransactions(loggedIn);
                        break;
                    case 5:
                        handleViewSortedAccounts();
                        break;
                    case 6:
                        handleSummary(loggedIn);
                        break;
                    case 7:
                        System.out.println("Logging out—see you!");
                        saveAccounts();
                        return;
                    default:
                        System.out.println("Invalid option—try again!");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input—enter a number!");
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    public static void main(String[] args) {
        TenthElevenBank bank = new TenthElevenBank();
        System.out.println("Welcome to TenthElevenBank!");

        Account loggedIn = null;
        try {
            loggedIn = bank.login();
            bank.run(loggedIn);
        } catch (InvalidPinException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }
}