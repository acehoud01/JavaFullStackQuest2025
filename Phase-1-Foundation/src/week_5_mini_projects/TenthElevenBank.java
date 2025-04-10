package week_5_mini_projects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class TenthElevenBank {
    static class Account {
        protected String accountHolder;
        protected double balance;
        protected ArrayList<String> transactions;
        protected int pin; // New!

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
    }

    static class SavingsAccount extends Account {
        private double interestRate;

        public SavingsAccount(String accountHolder, double balance, double interestRate, int pin) {
            super(accountHolder, balance, pin);
            this.interestRate = interestRate;
        }

        public double getInterestRate() { return interestRate; }
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

    public void loadAccounts() {
        File file = new File("accounts.txt");
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length < 5) { // Updated for PIN
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                try {
                    int id = Integer.parseInt(parts[0]);
                    String holder = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    String type = parts[3];
                    int pin = Integer.parseInt(parts[4]); // New!

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
        } finally {
            if (fileScanner != null) fileScanner.close(); // Cleanup!
        }
    }

    private void setupTestAccounts() {
        accounts.put(nextAccountId++, new Account("Dollar Bill", 500.00, 1234));
        accounts.put(nextAccountId++, new SavingsAccount("Siya Kolisi", 1000.00, 0.05, 4321));
    }

    public void saveAccounts() {
        try (PrintWriter writer = new PrintWriter(new File("accounts.txt"))) { // Try-with-resources
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

    public void login() throws Exception { // Throws custom error
        System.out.print("Enter Account ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter PIN: ");
        int pin = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        if (!accounts.containsKey(id)) {
            throw new Exception("Account ID not found!");
        }
        Account acc = accounts.get(id);
        if (acc.getPin() != pin) {
            throw new Exception("Invalid PIN—try again, champ!");
        }
        System.out.println("Login successful! Welcome, " + acc.getAccountHolder());
    }

    public static void main(String[] args) {
        TenthElevenBank bank = new TenthElevenBank();
        System.out.println("Welcome to TenthElevenBank!");

        try {
            bank.login();
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
        }

        System.out.println("\nCurrent Accounts:");
        int index = 0;
        for (Integer id : bank.accounts.keySet()) {
            Account acc = bank.accounts.get(id);
            System.out.printf("%d. ID: %d, Holder: %s, Balance: R%.2f",
                    ++index, id, acc.getAccountHolder(), acc.getBalance());
            if (acc instanceof SavingsAccount) {
                System.out.printf(", Interest Rate: %.2f%%\n",
                        ((SavingsAccount) acc).getInterestRate() * 100);
            } else {
                System.out.println();
            }
        }

        bank.saveAccounts();
    }
}