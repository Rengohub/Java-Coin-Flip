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
        commands.put("UPDATE_USER", new UpdateUser());
        commands.put("LOGIN", new Login());
        commands.put("REGISTER_USER", new RegisterUser());
        // Initialize other commands similarly
    }

    public String processMessage(String message) {
        String[] parts = message.split(":");
        String commandKey = parts[0];
        String requestData = parts.length > 1 ? parts[1] : "";

        Command command = commands.getOrDefault(commandKey, new UnsupportedCommand("Unsupported request type"));
        try {
            return command.execute(requestData);
        } catch (Exception e) {
            return "Error processing request: " + e.getMessage(); // General error handling, could be refined further
        }
    }
}