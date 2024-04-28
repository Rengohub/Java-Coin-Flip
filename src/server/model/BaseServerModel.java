package server.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseServerModel {
    protected Connection connection;

    public BaseServerModel() {
        connectToDatabase("jdbc:sqlite:serverDB.db");
    }

    private void connectToDatabase(String url) {
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Database connected successfully.");
            DatabaseUtils.initializeDatabase();
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
