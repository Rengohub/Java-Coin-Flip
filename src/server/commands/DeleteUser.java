package server.commands;

import server.model.UserOperationException;
import server.model.UserServerModel;

public class DeleteUser implements Command {
    private UserServerModel userServerModel;

    public DeleteUser(UserServerModel userServerModel) {
        this.userServerModel = userServerModel;
    }

    @Override
    public String execute(String username) throws UserOperationException {
        return userServerModel.deleteUser(username);
    }
}
