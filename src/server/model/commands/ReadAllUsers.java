package server.model.commands;

import server.model.DatabaseUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadAllUsers implements Command {
    @Override
    public String execute(String data) {
        String sql = "SELECT * FROM users";
        try {
            ArrayList<HashMap<String, String>> allUsersData = DatabaseUtils.executeQueryWithResults(sql, new String[]{});
            if (allUsersData.isEmpty()) {
                return "No users found in the database.";
            }
            return formatUsersData(allUsersData);
        } catch (SQLException e) {
            return "Error reading users: " + e.getMessage();
        }
    }

    private String formatUsersData(ArrayList<HashMap<String, String>> allUsersData) {
        StringBuilder result = new StringBuilder();
        for (HashMap<String, String> userData : allUsersData) {
            result.append(userData.get("id"))
                    .append(",").append(userData.get("username"))
                    .append(",").append(userData.get("email"))
                    .append(",").append(userData.get("credits"))
                    .append(",").append(userData.get("streak"))
                    .append("\n");
        }
        return result.toString().trim();
    }
}
