package week_7_mini_projects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * SpringbokFanClub - CLI app to manage rugby fans in MySQL.
 * Add, list, search, and remove fans with a favorite Springbok player.
 */
public class SpringbokFanClub {
    private Scanner scanner;
    private Connection conn;

    /**
     * Initializes CLI and MySQL connection.
     */
    public SpringbokFanClub() {
        this.scanner = new Scanner(System.in);
        connectToDatabase();
        createFansTable();
    }

    /**
     * Connects to MySQL FanClubDB.
     */
    private void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/FanClubDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to FanClubDB!");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Creates fans table if it doesn't exist.
     */
    private void createFansTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS fans (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(50) NOT NULL,
                favoritePlayer VARCHAR(50) NOT NULL
            )
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Fans table ready!");
        } catch (SQLException e) {
            System.out.println("Table creation failed: " + e.getMessage());
        }
    }

    /**
     * Adds a fan to the fans table.
     */
    private void addFan() {
        System.out.print("Enter fan name: ");
        String name = scanner.nextLine();
        System.out.print("Enter favorite Springbok player: ");
        String favoritePlayer = scanner.nextLine();

        String sql = "INSERT INTO fans (name, favoritePlayer) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, favoritePlayer);
            pstmt.executeUpdate();
            System.out.println("Added fan: " + name + " (loves " + favoritePlayer + ")");
        } catch (SQLException e) {
            System.out.println("Add fan failed: " + e.getMessage());
        }
    }

    /**
     * Lists all fans.
     */
    private void listAllFans() {
        String sql = "SELECT name, favoritePlayer FROM fans";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("All Fans:");
            boolean hasFans = false;
            while (rs.next()) {
                hasFans = true;
                String name = rs.getString("name");
                String player = rs.getString("favoritePlayer");
                System.out.println("- " + name + " (loves " + player + ")");
            }
            if (!hasFans) {
                System.out.println("No fans yet!");
            }
        } catch (SQLException e) {
            System.out.println("List fans failed: " + e.getMessage());
        }
    }

    /**
     * Lists fans by favorite player.
     */
    private void listFansByPlayer() {
        System.out.print("Enter favorite player to search: ");
        String player = scanner.nextLine();

        String sql = "SELECT name FROM fans WHERE favoritePlayer = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Fans who love " + player + ":");
            boolean hasFans = false;
            while (rs.next()) {
                hasFans = true;
                String name = rs.getString("name");
                System.out.println("- " + name);
            }
            if (!hasFans) {
                System.out.println("No fans for " + player + "!");
            }
        } catch (SQLException e) {
            System.out.println("Search failed: " + e.getMessage());
        }
    }

    /**
     * Removes a fan by name from the fans table.
     */
    private void removeFan() {
        System.out.print("Enter fan name to remove: ");
        String name = scanner.nextLine();

        String sql = "DELETE FROM fans WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Removed fan: " + name);
            } else {
                System.out.println("No fan named " + name + " found!");
            }
        } catch (SQLException e) {
            System.out.println("Remove fan failed: " + e.getMessage());
        }
    }

    /**
     * Runs the CLI menu.
     */
    public void run() {
        while (true) {
            System.out.println("\nSpringbok Fan Club Menu:");
            System.out.println("1. Add Fan");
            System.out.println("2. List All Fans");
            System.out.println("3. List Fans by Favorite Player");
            System.out.println("4. Remove Fan");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        addFan();
                        break;
                    case 2:
                        listAllFans();
                        break;
                    case 3:
                        listFansByPlayer();
                        break;
                    case 4:
                        removeFan();
                        break;
                    case 5:
                        System.out.println("Go Springboks! Exiting...");
                        try {
                            if (conn != null) conn.close();
                        } catch (SQLException e) {
                            System.out.println("Error closing connection: " + e.getMessage());
                        }
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option—try again!");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input—enter a number!");
                scanner.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        SpringbokFanClub club = new SpringbokFanClub();
        club.run();
    }
}

