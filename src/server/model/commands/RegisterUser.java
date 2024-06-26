package server.model.commands;

import server.model.DatabaseUtils;

import java.sql.SQLException;

public class RegisterUser implements Command {
    @Override
    public String execute(String userData) {
        String[] inputs = userData.split(",");
        if (inputs.length != 2) {
            return "Invalid user data format. Expected format: username,password";
        }
        String username = inputs[0].trim();
        String password = inputs[1].trim();

        if (usernameExists(username)) {
            return "Error: Username already exists.";
        }

        int credits = 100;
        int streak = 0;

        String sql = "INSERT INTO users (username, password, credits, streak) VALUES (?, ?, ?, ?)";
        String[] data = new String[]{username, password, String.valueOf(credits), String.valueOf(streak)};

        try {
            DatabaseUtils.executeUpdate(sql, data);
            return "User registered successfully.";
        } catch (SQLException e) {
            return "Error registering user: " + e.getMessage();
        }
    }

    private boolean usernameExists(String username) {
        String checkSql = "SELECT COUNT(*) FROM users WHERE username = ?";
        String[] params = new String[]{username};

        try {
            int count = DatabaseUtils.executeQueryForCount(checkSql, params);
            return count > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            return false;
        }
    }
}
