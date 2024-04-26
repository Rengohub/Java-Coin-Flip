package server.commands;

import server.model.DatabaseUtils;

import java.sql.SQLException;

public class DeleteUser implements Command {
    @Override
    public String execute(String username) {
        if (username == null || username.trim().isEmpty()) {
            return "Invalid username provided.";
        }
        String sql = "DELETE FROM users WHERE username = ?";
        try {
            DatabaseUtils.executeUpdate(sql, new String[]{username});
            return "User deleted successfully.";
        } catch (SQLException e) {
            return "Error deleting user: " + e.getMessage();
        }
    }
}
