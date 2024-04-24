package server.model;

import server.commands.*;

import java.util.HashMap;
import java.util.Map;

public class ServerModel extends BaseServerModel implements MessageProcessor {
    private Map<String, Command> commands;

    public ServerModel() {
        initializeCommands();
    }

    private void initializeCommands() {
        commands = new HashMap<>();
        commands.put("CREATE_USER", new CreateUser(new UserServerModel()));
        commands.put("DELETE_USER", new DeleteUser(new UserServerModel()));
        commands.put("READ_USER", new ReadUser(new UserServerModel()));
        commands.put("UPDATE_USER", new UpdateUser(new UserServerModel()));
        commands.put("LOGIN", new Login(new UserServerModel()));
        commands.put("REGISTER_USER", new RegisterUser(new UserServerModel()));
        // Initialize other commands similarly
    }

    @Override
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