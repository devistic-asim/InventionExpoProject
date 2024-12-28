import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbHelper {
    private static final String DB_URL = "jdbc:ucanaccess://db/BC210200948.accdb";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Method to add a new inventor to the database
    public static boolean addInventor(String name, int score) throws SQLException {
        String query = "INSERT INTO Inventors (PersonName, Score) VALUES (?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, score);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                throw new SQLException("Failed to add the inventor. No rows affected.");
            }
        }
    }

    // Method to retrieve all inventors from the database
    public static List<Inventor> getInventors() throws SQLException {
        String query = "SELECT ID, PersonName, Score FROM Inventors ORDER BY ID ASC";
        List<Inventor> inventors = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                inventors.add(new Inventor(rs.getInt("ID"), rs.getString("PersonName"), rs.getInt("Score")));
            }
        }
        return inventors;
    }

    // Method to delete all inventors from the database
    public static void deleteAllInventors() throws SQLException {
        String query = "DELETE FROM Inventors";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No records found to delete.");
            }
        }
    }

    // Method to get the top 3 inventors by score
    public static List<Inventor> getTopWinners() throws SQLException {
        String query = "SELECT ID, PersonName, Score FROM Inventors ORDER BY Score DESC LIMIT 3";
        List<Inventor> winners = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                winners.add(new Inventor(rs.getInt("ID"), rs.getString("PersonName"), rs.getInt("Score")));
            }
            if (winners.isEmpty()) {
                throw new SQLException("No top winners found in the database.");
            }
        }
        return winners;
    }
}
