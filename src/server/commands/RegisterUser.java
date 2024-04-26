package server.commands;

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
}
