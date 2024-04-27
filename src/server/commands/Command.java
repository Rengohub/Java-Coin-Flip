package server.commands;

import server.model.UserOperationException;

public interface Command {
    String execute(String data) throws UserOperationException;
}
