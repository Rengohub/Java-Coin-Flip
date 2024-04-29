package server.model.commands;

import server.model.DatabaseUtils;

import java.sql.SQLException;
import java.util.Random;

public class CoinFlip implements Command {
    @Override
    public String execute(String data) {
        System.out.println("CoinFlip S : " + data);
        String[] parts = data.split(",");
        if (parts.length != 3) {
            return "Invalid data format. Expected format: userId,betChoice,betAmount";
        }

        System.out.println("UserID: " + parts[0]);
        System.out.println("User Bet: " + parts[1]);
        System.out.println("Bet Amount: " + parts[2]);

        int userId = Integer.parseInt(parts[0]);
        String userBet = parts[1].trim();
        int betAmount = Integer.parseInt(parts[2]);

        try {
            String fetchSql = "SELECT credits FROM users WHERE id = ?";

            boolean win = new Random().nextBoolean();
            String result = win ? "HEADS" : "TAILS";
            int creditChange = userBet.equals(result) ? betAmount : -betAmount;

            String sql = "UPDATE users SET credits = credits + ? WHERE id = ?";
            DatabaseUtils.executeUpdate(sql, new String[]{String.valueOf(creditChange), String.valueOf(userId)});

            String newCredits = DatabaseUtils.executeQuery(fetchSql, new String[]{String.valueOf(userId)});
            System.out.println("newCredits : " + newCredits);
            return "Bet: " + userBet + ", Amount: " + betAmount + ", Result: " + result + ", Outcome: " + (userBet.equals(result) ? "Win" : "Loss") + ", New Credits: " + newCredits;
        } catch (SQLException e) {
            return "Error updating credits: " + e.getMessage();
        } catch (NumberFormatException e) {
            return "Invalid data: " + data;
        }
    }
}