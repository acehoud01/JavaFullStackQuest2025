package week_7_mini_projects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * SpringbokFanClub - CLI app to manage rugby fans and players in MySQL.
 * Add, list, search, remove, and update fans linked to players via foreign key.
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
        createPlayersTable();
        createFansTable();
    }

    /**
     * Connects to MySQL FanClubDB.
     */
    private void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/FanClubDB?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
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
     * Creates players table if it doesn't exist.
     */
    private void createPlayersTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS players (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(50) NOT NULL,
                position VARCHAR(50) NOT NULL
            )
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            // Seed initial players
            String seedSql = "INSERT INTO players (name, position) VALUES (?, ?) ON DUPLICATE KEY UPDATE name=name";
            try (PreparedStatement pstmt = conn.prepareStatement(seedSql)) {
                pstmt.setString(1, "Siya Kolisi");
                pstmt.setString(2, "Flanker");
                pstmt.executeUpdate();
                pstmt.setString(1, "Eben Etzebeth");
                pstmt.setString(2, "Lock");
                pstmt.executeUpdate();
                pstmt.setString(1, "Cheslin Kolbe");
                pstmt.setString(2, "Wing");
                pstmt.executeUpdate();
            }
            System.out.println("Players table ready!");
        } catch (SQLException e) {
            System.out.println("Players table creation failed: " + e.getMessage());
        }
    }

    /**
     * Creates fans table with foreign key to players.
     */
    private void createFansTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS fans (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(50) NOT NULL,
                favoritePlayerId INT NOT NULL,
                FOREIGN KEY (favoritePlayerId) REFERENCES players(id)
            )
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Fans table ready!");
        } catch (SQLException e) {
            System.out.println("Fans table creation failed: " + e.getMessage());
        }
    }

    /**
     * Validates input for non-empty, non-whitespace strings.
     * @param input The string to validate
     * @return true if valid, false otherwise
     */
    private boolean validateInput(String input) {
        return input != null && !input.trim().isEmpty();
    }

    /**
     * Checks if a player exists by name.
     * @param playerName The player's name
     * @return The player's ID, or -1 if not found
     */
    private int getPlayerId(String playerName) {
        String sql = "SELECT id FROM players WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Player search failed: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Adds a fan to the fans table.
     */
    private void addFan() {
        System.out.print("Enter fan name: ");
        String name = scanner.nextLine();
        System.out.print("Enter favorite Springbok player (e.g., Siya Kolisi): ");
        String playerName = scanner.nextLine();

        if (!validateInput(name) || !validateInput(playerName)) {
            System.out.println("Name and player cannot be empty!");
            return;
        }

        int playerId = getPlayerId(playerName);
        if (playerId == -1) {
            System.out.println("Player " + playerName + " not found! Add them to players first.");
            return;
        }

        String sql = "INSERT INTO fans (name, favoritePlayerId) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, playerId);
            pstmt.executeUpdate();
            System.out.println("Added fan: " + name + " (loves " + playerName + ")");
        } catch (SQLException e) {
            System.out.println("Add fan failed: " + e.getMessage());
        }
    }

    /**
     * Lists all fans with player names and positions.
     */
    private void listAllFans() {
        String sql = """
            SELECT f.name, p.name AS playerName, p.position
            FROM fans f
            JOIN players p ON f.favoritePlayerId = p.id
        """;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("All Fans:");
            boolean hasFans = false;
            while (rs.next()) {
                hasFans = true;
                String name = rs.getString("name");
                String player = rs.getString("playerName");
                String position = rs.getString("position");
                System.out.println("- " + name + " (loves " + player + ", " + position + ")");
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
        System.out.print("Enter favorite player to search (e.g., Siya Kolisi): ");
        String playerName = scanner.nextLine();

        if (!validateInput(playerName)) {
            System.out.println("Player name cannot be empty!");
            return;
        }

        int playerId = getPlayerId(playerName);
        if (playerId == -1) {
            System.out.println("Player " + playerName + " not found!");
            return;
        }

        String sql = """
            SELECT f.name
            FROM fans f
            JOIN players p ON f.favoritePlayerId = p.id
            WHERE p.id = ?
        """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playerId);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Fans who love " + playerName + ":");
            boolean hasFans = false;
            while (rs.next()) {
                hasFans = true;
                String name = rs.getString("name");
                System.out.println("- " + name);
            }
            if (!hasFans) {
                System.out.println("No fans for " + playerName + "!");
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

        if (!validateInput(name)) {
            System.out.println("Name cannot be empty!");
            return;
        }

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
     * Updates a fan's favorite player.
     */
    private void updateFanPlayer() {
        System.out.print("Enter fan name to update: ");
        String name = scanner.nextLine();
        System.out.print("Enter new favorite Springbok player (e.g., Cheslin Kolbe): ");
        String newPlayerName = scanner.nextLine();

        if (!validateInput(name) || !validateInput(newPlayerName)) {
            System.out.println("Name and new player cannot be empty!");
            return;
        }

        int playerId = getPlayerId(newPlayerName);
        if (playerId == -1) {
            System.out.println("Player " + newPlayerName + " not found!");
            return;
        }

        String sql = "UPDATE fans SET favoritePlayerId = ? WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playerId);
            pstmt.setString(2, name);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Updated " + name + "'s favorite player to " + newPlayerName);
            } else {
                System.out.println("No fan named " + name + " found!");
            }
        } catch (SQLException e) {
            System.out.println("Update fan failed: " + e.getMessage());
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
            System.out.println("5. Update Fan's Favorite Player");
            System.out.println("6. Exit");
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
                        updateFanPlayer();
                        break;
                    case 6:
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

