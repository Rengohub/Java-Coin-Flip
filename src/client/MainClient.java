package client;

import client.Controller.ClientController;

import java.io.IOException;

public class MainClient {
    public static void main(String[] args) throws IOException {
        try {
            ClientController client = new ClientController("localhost", 12345);
        } catch (Exception e) {
            System.err.println("Failed to connect to the server: " + e.getMessage());
        }
    }
}
