package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerController {
    private int port;
    private ServerModel model;
    private ExecutorService pool = Executors.newFixedThreadPool(10);

    public ServerController(int port, ServerModel model) {
        this.port = port;
        this.model = model;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket, model);
                pool.execute(handler);
            }
        } catch (IOException e) {
            System.out.println("Server exception on port " + port);
            e.printStackTrace();
        }
    }
}
