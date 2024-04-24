package server.commands;


import server.model.UserOperationException;
import server.model.UserServerModel;

public class CreateUser implements Command {
    private UserServerModel userServerModel;

    public CreateUser(UserServerModel userServerModel) {
        this.userServerModel = userServerModel;
    }

    @Override
    public String execute(String userData) throws UserOperationException {
        return userServerModel.createUser(userData);
    }
}