package server.model.commands;

import server.model.DatabaseUtils;

import java.sql.SQLException;

public class CreateUser implements Command {
    @Override
    public String execute(String userData) {
        String[] data = userData.split(",");
        if (data.length != 4) {
            return "Invalid user data format. Expected format: username,password,credits,streak";
        }

        for (int i = 0; i < data.length; i++) {
            data[i] = data[i].trim();
        }

        String sql = "INSERT INTO users (username, password, credits, streak) VALUES (?, ?, ?, ?)";

        try {
            DatabaseUtils.executeUpdate(sql, data);
            return "User created successfully.";
        } catch (SQLException e) {
            return "Error creating user: " + e.getMessage();
        }
    }
}
