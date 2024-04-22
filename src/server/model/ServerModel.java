package server.model;

public class ServerModel {
    private UserServerModel userServerModel;

    public ServerModel() {
        this.userServerModel = new UserServerModel();  // Assuming this manages all user-related operations
    }

    public String processMessage(String message) {
        String[] parts = message.split(":");
        String requestType = parts[0];
        String requestData = parts.length > 1 ? parts[1] : "";

        switch (requestType) {
            case "CREATE_USER":
                return userServerModel.createUser(requestData);
            case "UPDATE_USER":
                return userServerModel.updateUser(requestData);
            case "DELETE_USER":
                return userServerModel.deleteUser(requestData);
            case "READ_USER":
                return userServerModel.readUser(requestData);
            default:
                return "Unsupported request type";
        }
    }
}
