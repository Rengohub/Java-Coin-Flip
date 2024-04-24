package server.model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {

    private static final String DATABASE_URL = "jdbc:sqlite:serverDB.db";

    // Synchronize the whole method to avoid concurrent access issues
    public static synchronized void executeUpdate(String sql, String[] params) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]); // Correctly setting parameters
            }
            pstmt.executeUpdate();
            System.out.println("Update executed successfully.");
        }
    }

    public static synchronized String executeQuery(String sql, String[] params) throws SQLException {
        StringBuilder result = new StringBuilder();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]); // Correctly setting parameters
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.append(rs.getString(1)).append("\n");
            }
            return result.toString();
        }
    }

    public static void initializeDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "users", null);
            if (!tables.next()) {
                createUsersTable(connection);
            }
        }
    }

    private static void createUsersTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "credits INTEGER NOT NULL," +
                "streak INTEGER NOT NULL)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table created successfully.");
        }
    }
}