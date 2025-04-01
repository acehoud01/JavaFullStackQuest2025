package bank_project;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Note: Progress as of Day 16 (March 22nd, 2025) of Week 3: Collections.
 * Current state: Completed TenthElevenBank with HashMap for accounts (ID -> Account object),
 * ArrayList for transaction history per account, full banking flow (create, deposit, withdraw,
 * view history, check balance, delete account). Features: Encapsulation with public/private
 * methods, Scanner input, switch-based CLI menu, OOP (Account class), string manipulation,
 * variables, data types, operators, and control flow. Week 3 complete: TriviaGame, UberEats Clone,
 * TenthElevenBank—all done!
 */
public class TenthElevenBank {
    static class Account {
        private String accountHolder;
        private double balance;
        private ArrayList<String> transactions;

        public Account(String accountHolder, double balance) {
            this.accountHolder = accountHolder;
            this.balance = balance;
            this.transactions = new ArrayList<>();
            transactions.add("Initial deposit: R" + String.format("%.2f", balance));
        }

        public String getAccountHolder() { return accountHolder; }
        public double getBalance() { return balance; }
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
                System.out.println("Invalid withdrawal—check amount or balance!");
            }
        }
    }

    private Scanner scanner;
    private HashMap<Integer, Account> accounts;
    private int nextAccountId;

    public TenthElevenBank() {
        this.scanner = new Scanner(System.in);
        this.accounts = new HashMap<>();
        this.nextAccountId = 1001;
        setupTestAccounts();
    }

    private void setupTestAccounts() {
        accounts.put(1001, new Account("Dollar Bill", 500.00));
        accounts.put(1002, new Account("Siya Kolisi", 1000.00));
    }

    public void createAccount() {
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();
        System.out.print("Enter initial deposit (R): ");
        double deposit = scanner.nextDouble();
        scanner.nextLine();
        accounts.put(nextAccountId, new Account(name, deposit));
        System.out.println("Account created! ID: " + nextAccountId);
        nextAccountId++;
    }

    public void performDeposit() {
        System.out.print("Enter account ID: ");
        int id = scanner.nextInt();
        Account acc = accounts.get(id);
        if (acc != null) {
            System.out.print("Enter deposit amount (R): ");
            double amount = scanner.nextDouble();
            acc.deposit(amount);
        } else {
            System.out.println("Account not found!");
        }
        scanner.nextLine();
    }

    public void performWithdraw() {
        System.out.print("Enter account ID: ");
        int id = scanner.nextInt();
        Account acc = accounts.get(id);
        if (acc != null) {
            System.out.print("Enter withdrawal amount (R): ");
            double amount = scanner.nextDouble();
            acc.withdraw(amount);
        } else {
            System.out.println("Account not found!");
        }
        scanner.nextLine();
    }

    public void viewHistory() {
        System.out.print("Enter account ID: ");
        int id = scanner.nextInt();
        Account acc = accounts.get(id);
        if (acc != null) {
            System.out.println("\nTransaction History for " + acc.getAccountHolder() + " (ID: " + id + "):");
            int index = 0;
            for (String trans : acc.getTransactions()) {
                System.out.println(++index + ". " + trans);
            }
        } else {
            System.out.println("Account not found!");
        }
        scanner.nextLine();
    }

    public void checkBalance() {
        System.out.print("Enter account ID: ");
        int id = scanner.nextInt();
        Account acc = accounts.get(id);
        if (acc != null) {
            System.out.printf("\nBalance for %s (ID: %d): R%.2f\n",
                    acc.getAccountHolder(), id, acc.getBalance());
        } else {
            System.out.println("Account not found!");
        }
        scanner.nextLine();
    }

    public void deleteAccount() {
        System.out.print("Enter account ID to delete: ");
        int id = scanner.nextInt();
        Account acc = accounts.remove(id);
        if (acc != null) {
            System.out.println("Account for " + acc.getAccountHolder() + " (ID: " + id + ") deleted!");
        } else {
            System.out.println("Account not found!");
        }
        scanner.nextLine();
    }

    public static void main(String[] args) {
        TenthElevenBank bank = new TenthElevenBank();
        System.out.println("Welcome to TenthElevenBank!");

        while (true) {
            System.out.println("\n1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View History");
            System.out.println("5. Check Balance");
            System.out.println("6. Delete Account");
            System.out.println("7. Quit");
            System.out.print("Choose: ");
            int choice = bank.scanner.nextInt();
            bank.scanner.nextLine();

            switch (choice) {
                case 1:
                    bank.createAccount();
                    break;
                case 2:
                    bank.performDeposit();
                    break;
                case 3:
                    bank.performWithdraw();
                    break;
                case 4:
                    bank.viewHistory();
                    break;
                case 5:
                    bank.checkBalance();
                    break;
                case 6:
                    bank.deleteAccount();
                    break;
                case 7:
                    System.out.println("Thanks for banking with us!");
                    bank.scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }

            System.out.println("\nCurrent Accounts:");
            int index = 0;
            for (Integer id : bank.accounts.keySet()) {
                Account acc = bank.accounts.get(id);
                System.out.printf("%d. ID: %d, Holder: %s, Balance: R%.2f\n",
                        ++index, id, acc.getAccountHolder(), acc.getBalance());
            }
        }
    }
}