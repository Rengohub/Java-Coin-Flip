package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseServerModel {
    protected Connection connection;

    public BaseServerModel() {
        connectedToDatabase("jdbc:sqlite:mainDB.db");
    }

    private void connectedToDatabase(String url) {
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connected to database, sqlite");
        } catch (SQLException e) {
            System.out.println("Error Connecting..." + e.getMessage());
        }
    }

    public String processRequest(String requestType, String requestData) { return null; }

    protected void closeConnection() {
        try {
            if (connection != null) { connection.close(); }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
