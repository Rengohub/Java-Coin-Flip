package server.commands;

import server.model.UserServerModel;

public class Login implements Command {
    private UserServerModel userServerModel;

    public Login(UserServerModel userServerModel) {
        this.userServerModel = userServerModel;
    }

    @Override
    public String execute(String data) {
        try {
            return userServerModel.handleLogin(data);
        } catch (Exception e) {
            return "Error logging in: " + e.getMessage();
        }
    }
}
