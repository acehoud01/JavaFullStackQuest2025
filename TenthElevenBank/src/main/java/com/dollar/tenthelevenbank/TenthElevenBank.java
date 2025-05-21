package com.dollar.tenthelevenbank;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * TenthElevenBank - CLI banking App (MySQL version).
 */
public class TenthElevenBank {
    private Scanner scanner;
    private Connection conn;
    private List<Account> accounts;

    public TenthElevenBank() {
        scanner = new Scanner(System.in);
        accounts  = new ArrayList<>();
        connectToDataBase();
        loadAccounts();
    }

    private void connectToDataBase() {
        String url = "jdbc:mysql://localhost:3306/TenthElevenDB?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to TenthElevenDB!");
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
                Double interestRate = rs.getObject("interestRate", Double.class); // handles null
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

    private void saveAccounts() {
        String updateSql = "UPDATE accounts SET holder = ?, balance = ?, type = ?, pin = ?, interestRate = ? WHERE id = ?";
        String insertSql = "INSERT INTO accounts (id, holder, balance, type, pin, interestRate) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            for (Account acc : accounts) {
                // Try to update first
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

                // If update fails (account not in DB), insert
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
            }
            System.out.println("Saved " + accounts.size() + " accounts.");
        } catch (SQLException e) {
            System.out.println("Save accounts failed: " + e.getMessage());
        }
    }

    private Account login() {
        System.out.print("Enter account ID: ");
        int id;
        try {
            id = scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Invalid ID!");
            return null;
        }
        System.out.print("Enter PIN: ");
        int pin;
        try {
            pin = scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Invalid PIN!");
            return null;
        }
        scanner.nextLine();

        for (Account acc : accounts) {
            if (acc.getId() == id && acc.getPin() == pin) {
                return acc;
            }
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
                        System.out.println("4. Logout");
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
                                saveAccounts();
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
                                saveAccounts();
                                System.out.println("Withdrew R" + amount);
                            } else {
                                System.out.println("Invalid withdrawal amount or insufficient funds!");
                            }
                        } else if (subChoice == 3) {
                            System.out.println(acc);
                        } else if (subChoice == 4) {
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