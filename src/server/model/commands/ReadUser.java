package server.model.commands;

import server.model.DatabaseUtils;

import java.sql.SQLException;
import java.util.HashMap;

public class ReadUser implements Command {
    @Override
    public String execute(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return "Invalid user ID provided.";
        }
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            HashMap<String, String> userData = DatabaseUtils.executeQueryWithResult(sql, new String[]{userId.trim()});
            if (userData.isEmpty()) {
                return "No user found with ID: " + userId;
            }
            return formatUserData(userData);
        } catch (SQLException e) {
            return "Error reading user: " + e.getMessage();
        }
    }

    private String formatUserData(HashMap<String, String> userData) {
        StringBuilder result = new StringBuilder("<html>");
        userData.forEach((key, value) -> result.append("<b>").append(key).append("</b>: ").append(value).append("<br>"));
        result.append("</html>");
        return result.toString();
    }
}