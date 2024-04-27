package server.model.commands;

import server.model.DatabaseUtils;
import java.sql.SQLException;
import java.util.HashMap;

public class Login implements Command {
    @Override
    public String execute(String loginData) {
        String[] data = loginData.split(",");
        if (data.length != 2) {
            return "Invalid login data format. Expected format: username,password";
        }
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            HashMap<String, String> userData = DatabaseUtils.executeQueryWithResult(sql, new String[]{data[0]});
            if (userData != null && userData.get("password").trim().equals(data[1])) {
                // Assuming userData contains keys corresponding to the database fields like id, username, credits, and streak
                return String.format("Login successful,id=%s,credits=%s,streak=%s",
                        userData.get("id"), userData.get("credits"), userData.get("streak"));
            } else {
                return "Invalid password or username does not exist";
            }
        } catch (SQLException e) {
            return "Error during login: " + e.getMessage();
        }
    }
}