package server.commands;

import server.model.UserOperationException;
import server.model.UserServerModel;

public class UpdateUser implements Command {
    private UserServerModel userServerModel;

    public UpdateUser(UserServerModel userServerModel) {
        this.userServerModel = userServerModel;
    }

    @Override
    public String execute(String userData) throws UserOperationException {
        return userServerModel.updateUser(userData);
    }
}
