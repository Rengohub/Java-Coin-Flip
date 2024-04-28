package server.model.commands;

import server.model.DatabaseUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class LeaderBoard implements Command {
    @Override
    public String execute(String data) {
        String sql = "SELECT username, credits, streak FROM users ORDER BY credits DESC LIMIT 3";  // Query to select top 3 users based on credits
        try {
            ArrayList<HashMap<String, String>> leaderboardData = DatabaseUtils.executeQueryWithResults(sql, new String[]{});
            if (leaderboardData.isEmpty()) {
                return "No users found in the leaderboard.";
            }
            return formatLeaderboardData(leaderboardData);
        } catch (SQLException e) {
            return "Error retrieving leaderboard: " + e.getMessage();
        }
    }

    private String formatLeaderboardData(ArrayList<HashMap<String, String>> leaderboardData) {
        StringBuilder result = new StringBuilder();
        result.append("USERNAME | CREDITS | STREAK\n"); // Header row
        for (HashMap<String, String> userData : leaderboardData) {
            result.append(userData.get("username"))
                    .append(" | ").append(userData.get("credits"))
                    .append(" | ").append(userData.get("streak"))
                    .append("\n");
        }
        return result.toString().trim();
    }
}