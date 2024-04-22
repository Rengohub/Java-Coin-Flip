package client;

import client.controller.ClientController;
import client.model.ClientModel;
import client.view.ClientView;

public class MainClient {
    public static void main(String[] args) {
        ClientView view = new ClientView();
        ClientModel model = new ClientModel();
        new ClientController(view, model);
    }
}
