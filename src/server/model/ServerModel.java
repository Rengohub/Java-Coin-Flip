package server.model;

import server.model.commands.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ServerModel extends BaseServerModel {
    private Map<String, Command> commands;

    public ServerModel() {
        try {
            DatabaseUtils.initializeDatabase();
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
        initializeCommands();
    }

    private void initializeCommands() {
        commands = new HashMap<>();
        commands.put("CREATE_USER", new CreateUser());
        commands.put("DELETE_USER", new DeleteUser());
        commands.put("READ_USER", new ReadUser());
        commands.put("READ_ALL_USERS", new ReadAllUsers());
        commands.put("UPDATE_USER", new UpdateUser());
        commands.put("LOGIN", new Login());
        commands.put("REGISTER_USER", new RegisterUser());
        commands.put("FLIP_COIN", new CoinFlip());
        commands.put("ROLL_DICE", new DiceRoll());
        // Initialize other commands similarly
    }

    public String processMessage(String message) {
        String[] parts = message.split(":");
        String commandKey = parts[0];
        String requestData = parts.length > 1 ? parts[1] : "";

        Command command = commands.getOrDefault(commandKey, new UnsupportedCommand("Unsupported request type"));
        try {
            String response = command.execute(requestData);
            return response + "\nEND";  // Append "END" to signify the end of the response
        } catch (Exception e) {
            return "Error processing request: " + e.getMessage() + "\nEND";  // Ensure "END" is also sent in case of an error
        }
    }
}