package server.commands;

import server.model.DatabaseUtils;

import java.sql.SQLException;

public class UpdateUser implements Command {
    @Override
    public String execute(String userData) {
        String[] data = userData.split(",");
        if (data.length != 5) {
            return "Invalid user data format. Expected format: username,newPassword,credits,streak,userId";
        }
        String sql = "UPDATE users SET password = ?, credits = ?, streak = ? WHERE id = ?";
        try {
            DatabaseUtils.executeUpdate(sql, data);
            return "User updated successfully.";
        } catch (SQLException e) {
            return "Error updating user: " + e.getMessage();
        }
    }
}