package com.dollar.tenthelevenbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * TenthElevenBank - CLI banking App (MySQL version).
 */
public class TenthElevenBank {
    private Connection conn;

    public TenthElevenBank() {
        connectToDataBase();
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

    public static void main(String[] args) {
        TenthElevenBank bank = new TenthElevenBank();
    }
}