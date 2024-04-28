package server.model.commands;

import server.model.DatabaseUtils;

import java.sql.SQLException;
import java.util.Random;

public class DiceRoll implements Command {
    @Override
    public String execute(String data) {
        System.out.println("DiceRoll S : " + data);
        String[] parts = data.split(",");
        if (parts.length != 3) {
            return "Invalid data format. Expected format: userId,betChoice,betAmount";
        }

        int userId = Integer.parseInt(parts[0]);
        int userBet = Integer.parseInt(parts[1]); // The user bets on a number 1-6
        int betAmount = Integer.parseInt(parts[2]);

        if (userBet < 1 || userBet > 6) {
            return "Invalid bet choice. Please choose a number between 1 and 6.";
        }

        try {
            String fetchSql = "SELECT credits FROM users WHERE id = ?";

            int diceResult = new Random().nextInt(6) + 1; // Generates a number between 1 and 6
            int creditChange = (userBet == diceResult) ? betAmount * 5 : -betAmount; // Pays 5:1 if they win

            String sql = "UPDATE users SET credits = credits + ? WHERE id = ?";
            DatabaseUtils.executeUpdate(sql, new String[]{String.valueOf(creditChange), String.valueOf(userId)});

            String newCredits = DatabaseUtils.executeQuery(fetchSql, new String[]{String.valueOf(userId)});
            return String.format("Your bet: %d, Dice roll: %d, Outcome: %s, New Credits: %s",
                    userBet, diceResult, (userBet == diceResult ? "Win" : "Loss"), newCredits);
        } catch (SQLException e) {
            return "Error updating credits: " + e.getMessage();
        } catch (NumberFormatException e) {
            return "Invalid data: " + data;
        }
    }
}
