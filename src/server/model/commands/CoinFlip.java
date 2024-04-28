package server.model.commands;

import server.model.DatabaseUtils;
import java.sql.SQLException;
import java.util.Random;

public class CoinFlip implements Command {
    @Override
    public String execute(String data) {
        int userId = Integer.parseInt(data);
        boolean win = new Random().nextBoolean();
        int creditChange = win ? 50 : -50;
        try {
            String sql = "UPDATE users SET credits = credits + ? WHERE id = ?";
            DatabaseUtils.executeUpdate(sql, new String[]{String.valueOf(creditChange), String.valueOf(userId)});

            String fetchSql = "SELECT credits FROM users WHERE id = ?";
            String newCredits = DatabaseUtils.executeQuery(fetchSql, new String[]{String.valueOf(userId)});

            return "Result: " + (win ? "Win" : "Loss") + ", New Credits: " + newCredits;
        } catch (SQLException e) {
            return "Error updating credits: " + e.getMessage();
        } catch (NumberFormatException e) {
            return "Invalid user ID: " + data;
        }
    }
}