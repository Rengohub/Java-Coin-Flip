package server.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseUtils {

    private static final String DATABASE_URL = "jdbc:sqlite:serverDB.db";

    public static synchronized void executeUpdate(String sql, String[] params) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Update executed successfully, affected rows: " + affectedRows);
            } else {
                System.out.println("No rows affected.");
            }
        }
    }

    public static synchronized String executeQuery(String sql, String[] params) throws SQLException {
        StringBuilder result = new StringBuilder();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.append(rs.getString(1)).append("\n");
            }
            return result.toString();
        }
    }

    public static synchronized HashMap<String, String> executeQueryWithResult(String sql, String[] params) throws SQLException {
        HashMap<String, String> userData = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                if (rs.next()) {
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        userData.put(rsmd.getColumnName(i), rs.getString(i));
                    }
                }
            }
        }
        return userData;
    }

    public static ArrayList<HashMap<String, String>> executeQueryWithResults(String sql, String[] params) throws SQLException {
        ArrayList<HashMap<String, String>> resultsList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                while (rs.next()) {
                    HashMap<String, String> rowData = new HashMap<>();
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        rowData.put(rsmd.getColumnName(i), rs.getString(i));
                    }
                    resultsList.add(rowData);
                }
            }
        }
        return resultsList;
    }

    public static synchronized int executeQueryForCount(String sql, String[] params) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public static void initializeDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "users", null);
            if (!tables.next()) {
                createUsersTable(connection);
                createAdminAcc(connection);
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

    private static void createAdminAcc(Connection connection) throws SQLException {
        String sql = "INSERT INTO users (id, username, password, credits, streak) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, 1);
            pstmt.setString(2, "admin");
            pstmt.setString(3, "admin");
            pstmt.setInt(4, 0);
            pstmt.setInt(5, 0);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Admin account created successfully.");
            } else {
                System.out.println("Failed to create admin account.");
            }
        }
    }
}