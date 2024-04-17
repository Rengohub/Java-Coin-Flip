package client;

public class MainClient {
    public static void main(String[] args) {
        ClientView view = new ClientView();
        ClientModel model = new ClientModel();
        new ClientController(view, model);
    }
}
