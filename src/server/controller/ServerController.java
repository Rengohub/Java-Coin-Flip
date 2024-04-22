package server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import server.model.ServerModel;

/**
 * ServerController is responsible for handling incoming client connections and directing
 * requests to the appropriate model components based on the request type.
 */
public class ServerController {
    private int port;
    private ServerModel model;
    private ExecutorService pool;

    /**
     * Constructs a ServerController with a specified port for the server and a model for handling logic.
     * @param port The port number on which the server should listen.
     * @param model The ServerModel for handling the business logic.
     */
    public ServerController(int port, ServerModel model) {
        this.port = port;
        this.model = model;
        this.pool = Executors.newFixedThreadPool(10);  // Pool size can be adjusted based on expected concurrency.
    }

    /**
     * Starts the server, listens for client connections, and handles them using a thread pool.
     */
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