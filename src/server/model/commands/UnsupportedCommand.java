package server.model.commands;

public class UnsupportedCommand implements Command {
    private final String message;

    public UnsupportedCommand(String message) {
        this.message = message;
    }

    @Override
    public String execute(String data) {
        return message;
    }
}