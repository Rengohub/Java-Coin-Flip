package server.commands;

import server.model.UserServerModel;

public class RegisterUser implements Command{
    private UserServerModel userServerModel;

    public RegisterUser(UserServerModel userServerModel) {
        this.userServerModel = userServerModel;
    }

    @Override
    public String execute(String data) {
        try {
            return userServerModel.registerUser(data);
        } catch (Exception e) {
            return "Error during registration: " + e.getMessage();
        }
    }
}