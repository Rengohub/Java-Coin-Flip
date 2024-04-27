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
        String sql = "SELECT id, username, password FROM users WHERE username = ?";
        try {
            HashMap<String, String> userData = DatabaseUtils.executeQueryWithResult(sql, new String[]{data[0]});
            if (userData != null && userData.get("password").equals(data[1])) {
                // Send back login successful message with the UID
                return "Login successful: UID=" + userData.get("id");
            } else {
                return "Invalid password or username does not exist";
            }
        } catch (SQLException e) {
            return "Error during login: " + e.getMessage();
        }
    }
}