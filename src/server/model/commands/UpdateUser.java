package server.model.commands;

import server.model.DatabaseUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateUser implements Command {
    @Override
    public String execute(String userData) {
        String[] data = userData.split(",");
        if (data.length != 5) {
            return "Invalid user data format. Expected format: username,newPassword,credits,streak,userId";
        }

        List<String> queryParts = new ArrayList<>();
        List<String> parameters = new ArrayList<>();

        if (!data[0].isEmpty()) {  // username
            queryParts.add("username = ?");
            parameters.add(data[0]);
        }
        if (!data[1].isEmpty()) {  // newPassword
            queryParts.add("password = ?");
            parameters.add(data[1]);
        }
        if (!data[2].isEmpty()) {  // credits
            queryParts.add("credits = ?");
            parameters.add(data[2]);
        }
        if (!data[3].isEmpty()) {  // streak
            queryParts.add("streak = ?");
            parameters.add(data[3]);
        }

        if (queryParts.isEmpty()) {
            return "No updates provided.";
        }

        parameters.add(data[4]);  // Add userId last for WHERE clause
        String sql = "UPDATE users SET " + String.join(", ", queryParts) + " WHERE id = ?";

        try {
            DatabaseUtils.executeUpdate(sql, parameters.toArray(new String[0]));
            return "User updated successfully.";
        } catch (SQLException e) {
            return "Error updating user: " + e.getMessage();
        }
    }
}