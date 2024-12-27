import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbHelper {
    // Path to your Access database
    private static final String DB_URL = "jdbc:ucanaccess://db/BC210200948.accdb";

    // Method to establish a connection to the database
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Method to add a new inventor to the database
    public static void addInventor(String name, int score) throws SQLException {
        String query = "INSERT INTO Inventors (PersonName, Score) VALUES (?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, score);
            stmt.executeUpdate();
        }
    }

    // Method to retrieve all inventors from the database
    public static List<Inventor> getInventors() throws SQLException {
        String query = "SELECT * FROM Inventors";
        List<Inventor> inventors = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                inventors.add(new Inventor(rs.getInt("ID"), rs.getString("PersonName"), rs.getInt("Score")));
            }
        }
        return inventors;
    }
}
