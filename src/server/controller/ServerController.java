package server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import server.model.ServerModel;
public class ServerController {
    private int port;
    private ServerModel model;
    private ExecutorService pool;

    public ServerController(int port, ServerModel model) {
        this.port = port;
        this.model = model;
        this.pool = Executors.newFixedThreadPool(10);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");
                pool.execute(new ClientHandler(clientSocket, model));
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}