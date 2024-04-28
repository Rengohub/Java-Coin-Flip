package server.model.commands;

import server.model.DatabaseUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadAllUsers implements Command {
    @Override
    public String execute(String data) {
        String sql = "SELECT * FROM users";  // Query to select all users
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
        StringBuilder result = new StringBuilder("<html><table>");  // Start with HTML for better formatting in JOptionPane or similar
        result.append("<tr><th>ID</th><th>Username</th><th>Email</th><th>Credits</th><th>Streak</th></tr>");
        for (HashMap<String, String> userData : allUsersData) {
            result.append("<tr>");
            userData.forEach((key, value) -> {
                result.append("<td>").append(value).append("</td>");
            });
            result.append("</tr>");
        }
        result.append("</table></html>");
        return result.toString();
    }
}
