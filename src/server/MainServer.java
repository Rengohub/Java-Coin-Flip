package server;

import server.controller.ServerController;
import server.model.ServerModel;

public class MainServer {
    public static void main(String[] args) {
        int port = 12345; // Set the port for the server
        ServerController serverController = new ServerController(port, new ServerModel());
        serverController.start();
        System.out.println("Server is running on port " + port);
    }
}
