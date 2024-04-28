package server.model.commands;

import server.model.DatabaseUtils;

import java.sql.SQLException;

public class ReadUser implements Command {
    @Override
    public String execute(String username) {
        if (username == null || username.trim().isEmpty()) {
            return "Invalid username provided.";
        }
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            return DatabaseUtils.executeQuery(sql, new String[]{username});
        } catch (SQLException e) {
            return "Error reading user: " + e.getMessage();
        }
    }
}
