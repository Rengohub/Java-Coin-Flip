package server;

public class UserServerModel extends BaseServerModel {
    @Override
    public String processRequest(String requestType, String requestData) {
        switch (requestType) {
            case "CREATE":
                return createUser(requestData);
            case "UPDATE":
                return updateUser(requestData);
            case "DELETE":
                return deleteUser(requestData);
            case "READ":
                return readUser(requestData);
            default:
                return "Invalid request type";
        }
    }

    private String createUser(String userData) {
        
    }
}
