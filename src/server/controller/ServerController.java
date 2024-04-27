package server.controller;

import server.model.ServerModel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ServerController {
    private int port;
    private ServerModel model;
    private ExecutorService pool;
    private ServerSocket serverSocket;
    private volatile boolean running = true;

    public ServerController(int port, ServerModel model) {
        this.port = port;
        this.model = model;
        this.pool = Executors.newFixedThreadPool(10);
    }

    public static void main(String[] args) {
        ServerModel model = new ServerModel();
        ServerController server = new ServerController(12345, model);
        server.start();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);

            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New client connected on thread " + Thread.currentThread().getId());
                    pool.execute(new ClientHandler(clientSocket, model));
                } catch (IOException e) {
                    if (running) {
                        System.out.println("Error accepting client connection: " + e.getMessage());
                    } else {
                        System.out.println("Server has stopped accepting new clients.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port " + port + ": " + e.getMessage());
        } finally {
            stop();
        }
    }

    public void stop() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            System.out.println("Server socket closed.");
        } catch (IOException e) {
            System.out.println("Error closing server socket: " + e.getMessage());
        }

        pool.shutdown();
        try {
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow();
                if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.out.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("Server shutdown completed.");
    }
}