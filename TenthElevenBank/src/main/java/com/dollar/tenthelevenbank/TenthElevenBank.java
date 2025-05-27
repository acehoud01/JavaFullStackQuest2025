package com.dollar.tenthelevenbank;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * TenthElevenBank - CLI banking app with MySQL backend.
 */
public class TenthElevenBank {
    private Scanner scanner;
    private Connection conn;
    private List<Account> accounts;

    public TenthElevenBank() {
        scanner = new Scanner(System.in);
        accounts = new ArrayList<>();
        connectToDatabase();
        loadAccounts();
    }

    private void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/TenthElevenDB?useSSL=false&serverTimezone=UTC";
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        if (user == null || password == null) {
            System.out.println("DB_USER or DB_PASSWORD not set in environment!");
            System.exit(1);
        }
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to TenthElevenDB!");
            String createTableSql = "CREATE TABLE IF NOT EXISTS transactions (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "accountId INT NOT NULL, " +
                    "type VARCHAR(10) NOT NULL, " +
                    "amount DOUBLE NOT NULL, " +
                    "transactionDate DATETIME NOT NULL, " +
                    "FOREIGN KEY (accountId) REFERENCES accounts(id))";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(createTableSql);
                System.out.println("Transactions table ready!");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            System.exit(1);
        }
    }

    private void loadAccounts() {
        String sql = "SELECT id, holder, balance, type, pin, interestRate FROM accounts";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            accounts.clear();
            while (rs.next()) {
                int id = rs.getInt("id");
                String holder = rs.getString("holder");
                double balance = rs.getDouble("balance");
                String type = rs.getString("type");
                int pin = rs.getInt("pin");
                Double interestRate = rs.getObject("interestRate", Double.class);
                if (type.equals("S")) {
                    accounts.add(new SavingsAccount(id, holder, balance, pin, interestRate != null ? interestRate : 0.0));
                } else {
                    accounts.add(new Account(id, holder, balance, pin));
                }
            }
            System.out.println("Loaded " + accounts.size() + " accounts.");
        } catch (SQLException e) {
            System.out.println("Load accounts failed: " + e.getMessage());
        }
    }

    private void saveAccountsAndLogTransaction(Account acc, String type, double amount) {
        String updateSql = "UPDATE accounts SET holder = ?, balance = ?, type = ?, pin = ?, interestRate = ? WHERE id = ?";
        String insertSql = "INSERT INTO accounts (id, holder, balance, type, pin, interestRate) VALUES (?, ?, ?, ?, ?, ?)";
        String transactionSql = "INSERT INTO transactions (accountId, type, amount, transactionDate) VALUES (?, ?, ?, ?)";

        try {
            conn.setAutoCommit(false);
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                 PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                 PreparedStatement transactionStmt = conn.prepareStatement(transactionSql)) {
                updateStmt.setString(1, acc.getHolder());
                updateStmt.setDouble(2, acc.getBalance());
                updateStmt.setString(3, acc instanceof SavingsAccount ? "S" : "A");
                updateStmt.setInt(4, acc.getPin());
                if (acc instanceof SavingsAccount) {
                    updateStmt.setDouble(5, ((SavingsAccount) acc).getInterestRate());
                } else {
                    updateStmt.setNull(5, Types.DOUBLE);
                }
                updateStmt.setInt(6, acc.getId());
                int rows = updateStmt.executeUpdate();

                if (rows == 0) {
                    insertStmt.setInt(1, acc.getId());
                    insertStmt.setString(2, acc.getHolder());
                    insertStmt.setDouble(3, acc.getBalance());
                    insertStmt.setString(4, acc instanceof SavingsAccount ? "S" : "A");
                    insertStmt.setInt(5, acc.getPin());
                    if (acc instanceof SavingsAccount) {
                        insertStmt.setDouble(6, ((SavingsAccount) acc).getInterestRate());
                    } else {
                        insertStmt.setNull(6, Types.DOUBLE);
                    }
                    insertStmt.executeUpdate();
                }

                transactionStmt.setInt(1, acc.getId());
                transactionStmt.setString(2, type);
                transactionStmt.setDouble(3, amount);
                transactionStmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                transactionStmt.executeUpdate();

                conn.commit();
                System.out.println("Saved account and logged transaction: " + type + " of R" + amount + " for account " + acc.getId());
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Transaction failed, rolled back: " + e.getMessage());
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Transaction setup failed: " + e.getMessage());
        }
    }

    private void viewTransactions(int accountId) {
        String sql = "SELECT id, type, amount, transactionDate FROM transactions WHERE accountId = ? ORDER BY transactionDate DESC";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\nTransaction History for Account " + accountId + ":");
                boolean hasTransactions = false;
                while (rs.next()) {
                    hasTransactions = true;
                    int id = rs.getInt("id");
                    String type = rs.getString("type");
                    double amount = rs.getDouble("amount");
                    Timestamp date = rs.getTimestamp("transactionDate");
                    System.out.printf("ID: %d, Type: %s, Amount: R%.2f, Date: %s%n", id, type, amount, date);
                }
                if (!hasTransactions) {
                    System.out.println("No transactions found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to view transactions: " + e.getMessage());
        }
    }

    private Account getAccountById(int id) {
        String sql = "SELECT id, holder, balance, type, pin, interestRate FROM accounts WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String holder = rs.getString("holder");
                    double balance = rs.getDouble("balance");
                    String type = rs.getString("type");
                    int pin = rs.getInt("pin");
                    Double interestRate = rs.getObject("interestRate", Double.class);
                    if (type.equals("S")) {
                        return new SavingsAccount(id, holder, balance, pin, interestRate != null ? interestRate : 0.0);
                    } else {
                        return new Account(id, holder, balance, pin);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch account: " + e.getMessage());
        }
        return null;
    }

    private Account login() {
        System.out.print("Enter account ID: ");
        int id;
        try {
            id = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Invalid ID!");
            return null;
        }
        System.out.print("Enter PIN: ");
        int pin;
        try {
            pin = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Invalid PIN!");
            return null;
        }
        scanner.nextLine();

        String sql = "SELECT id, holder, balance, type, pin, interestRate FROM accounts WHERE id = ? AND pin = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, pin);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String holder = rs.getString("holder");
                    double balance = rs.getDouble("balance");
                    String type = rs.getString("type");
                    Double interestRate = rs.getObject("interestRate", Double.class);
                    if (type.equals("S")) {
                        return new SavingsAccount(id, holder, balance, pin, interestRate != null ? interestRate : 0.0);
                    } else {
                        return new Account(id, holder, balance, pin);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        System.out.println("Invalid ID or PIN!");
        return null;
    }

    public void run() {
        while (true) {
            System.out.println("\nTenthElevenBank Menu:");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (java.util.InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Invalid input!");
                continue;
            }

            if (choice == 1) {
                Account acc = login();
                if (acc != null) {
                    while (true) {
                        System.out.println("\nAccount Menu:");
                        System.out.println("1. Deposit");
                        System.out.println("2. Withdraw");
                        System.out.println("3. View Balance");
                        System.out.println("4. View Transactions");
                        System.out.println("5. Logout");
                        System.out.print("Choose an option: ");

                        int subChoice;
                        try {
                            subChoice = scanner.nextInt();
                            scanner.nextLine();
                        } catch (java.util.InputMismatchException e) {
                            scanner.nextLine();
                            System.out.println("Invalid input!");
                            continue;
                        }

                        if (subChoice == 1) {
                            System.out.print("Enter amount to deposit: R");
                            double amount;
                            try {
                                amount = scanner.nextDouble();
                            } catch (java.util.InputMismatchException e) {
                                scanner.nextLine();
                                System.out.println("Invalid amount!");
                                continue;
                            }
                            if (acc.deposit(amount)) {
                                saveAccountsAndLogTransaction(acc, "DEPOSIT", amount);
                                System.out.println("Deposited R" + amount);
                            } else {
                                System.out.println("Invalid deposit amount!");
                            }
                        } else if (subChoice == 2) {
                            System.out.print("Enter amount to withdraw: R");
                            double amount;
                            try {
                                amount = scanner.nextDouble();
                            } catch (java.util.InputMismatchException e) {
                                scanner.nextLine();
                                System.out.println("Invalid amount!");
                                continue;
                            }
                            if (acc.withdraw(amount)) {
                                saveAccountsAndLogTransaction(acc, "WITHDRAW", amount);
                                System.out.println("Withdrew R" + amount);
                            } else {
                                System.out.println("Invalid withdrawal amount or insufficient funds!");
                            }
                        } else if (subChoice == 3) {
                            System.out.println(acc);
                        } else if (subChoice == 4) {
                            viewTransactions(acc.getId());
                        } else if (subChoice == 5) {
                            break;
                        } else {
                            System.out.println("Invalid option!");
                        }
                    }
                }
            } else if (choice == 2) {
                System.out.println("Thank you for banking with TenthElevenBank!");
                try {
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
                scanner.close();
                return;
            } else {
                System.out.println("Invalid option!");
            }
        }
    }

    public static void main(String[] args) {
        TenthElevenBank bank = new TenthElevenBank();
        bank.run();
    }
}