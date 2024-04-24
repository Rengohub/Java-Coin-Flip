package server.model;

import java.sql.*;

public abstract class BaseServerModel {
    protected Connection connection;

    public BaseServerModel() {
//        this.connectToDatabase("jdbc:sqlite:serverDB.db");  // Using SQLite
        this.initializeDatabase();
    }

    public void initializeDatabase() {
        try {
            if (connection == null) {
                connectToDatabase("jdbc:sqlite:serverDB.db");
            }
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "users", null);
            if (!tables.next()) {
                String sql = "CREATE TABLE users (" +
                        "id INTEGER PRIMARY KEY," +
                        "username TEXT NOT NULL," +
                        "password TEXT NOT NULL," +
                        "credits INTEGER NOT NULL," +
                        "streak INTEGER NOT NULL)";
                connection.createStatement().execute(sql);
                System.out.println("Table created successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error during database initialization: " + e.getMessage());
        }
    }

    private void connectToDatabase(String url) {
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing the database connection: " + e.getMessage());
        }
    }
}