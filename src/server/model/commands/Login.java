package server.model.commands;

import server.model.DatabaseUtils;

import java.sql.SQLException;

public class Login implements Command {
    @Override
    public String execute(String loginData) {
        String[] data = loginData.split(",");
        if (data.length != 2) {
            return "Invalid login data format. Expected format: username,password";
        }
        String sql = "SELECT password FROM users WHERE username = ?";
        try {
            String result = DatabaseUtils.executeQuery(sql, new String[]{data[0]});
            if (result.trim().equals(data[1])) {
                return "Login successful";
            } else {
                return "Invalid password";
            }
        } catch (SQLException e) {
            return "Error during login: " + e.getMessage();
        }
    }
}
