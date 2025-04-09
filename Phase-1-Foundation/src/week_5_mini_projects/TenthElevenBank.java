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

        public Account(String accountHolder, double balance) {
            this.accountHolder = accountHolder;
            this.balance = balance;
            this.transactions = new ArrayList<>();
            transactions.add("Initial deposit: R" + String.format("%.2f", balance));
        }

        public String getAccountHolder() { return accountHolder; }
        public double getBalance() { return balance; }
    }

    static class SavingsAccount extends Account {
        private double interestRate;

        public SavingsAccount(String accountHolder, double balance, double interestRate) {
            super(accountHolder, balance);
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
        try {
            File file = new File("accounts.txt");
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length < 4) {
                    System.out.println("Skipping bad line: " + line);
                    continue;
                }

                try {
                    int id = Integer.parseInt(parts[0]);
                    String holder = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    String type = parts[3];

                    if (type.equals("A")) {
                        accounts.put(id, new Account(holder, balance));
                    } else if (type.equals("S") && parts.length == 5) {
                        double interestRate = Double.parseDouble(parts[4]);
                        accounts.put(id, new SavingsAccount(holder, balance, interestRate));
                    } else {
                        System.out.println("Invalid type or data: " + line);
                        continue;
                    }
                    if (id >= nextAccountId) nextAccountId = id + 1;
                } catch (NumberFormatException e) {
                    System.out.println("Bad number format in line: " + line);
                }
            }
            fileScanner.close();
            System.out.println("Accounts loaded from accounts.txt!");
        } catch (FileNotFoundException e) {
            System.out.println("No accounts.txt—starting fresh!");
            setupTestAccounts();
        }
    }

    private void setupTestAccounts() {
        accounts.put(nextAccountId++, new Account("Dollar Bill", 500.00));
        accounts.put(nextAccountId++, new SavingsAccount("Siya Kolisi", 1000.00, 0.05));
    }

    public void saveAccounts() {
        try {
            PrintWriter writer = new PrintWriter(new File("accounts.txt"));
            for (Integer id : accounts.keySet()) {
                Account acc = accounts.get(id);
                String type = (acc instanceof SavingsAccount) ? "S" : "A";
                String line = String.format("%d,%s,%.2f,%s",
                        id, acc.getAccountHolder(), acc.getBalance(), type);
                if (type.equals("S")) {
                    line += "," + String.format("%.2f", ((SavingsAccount) acc).getInterestRate());
                }
                writer.println(line);
            }
            writer.close();
            System.out.println("Accounts saved to accounts.txt!");
        } catch (FileNotFoundException e) {
            System.out.println("Oops! Couldn’t save accounts.txt!");
        }
    }

    public static void main(String[] args) {
        TenthElevenBank bank = new TenthElevenBank();
        System.out.println("Welcome to TenthElevenBank!");

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
