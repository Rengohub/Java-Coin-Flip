package server.model.commands;

import server.model.DatabaseUtils;
import java.sql.SQLException;

public class DeleteUser implements Command {
    @Override
    public String execute(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return "Invalid user ID provided.";
        }
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            DatabaseUtils.executeUpdate(sql, new String[]{userId.trim()});
            return "User deleted successfully.";
        } catch (SQLException e) {
            return "Error deleting user: " + e.getMessage();
        }
    }
}