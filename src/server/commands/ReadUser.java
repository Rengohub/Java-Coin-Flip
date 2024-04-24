package server.commands;

import server.model.UserOperationException;
import server.model.UserServerModel;

public class ReadUser implements Command {
    private UserServerModel userServerModel;

    public ReadUser(UserServerModel userServerModel) {
        this.userServerModel = userServerModel;
    }

    @Override
    public String execute(String username) throws UserOperationException {
        return userServerModel.readUser(username);
    }
}