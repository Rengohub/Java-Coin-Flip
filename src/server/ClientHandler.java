package server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ServerModel model;

    public ClientHandler(Socket clientSocket, ServerModel model) {
        this.clientSocket = clientSocket;
        this.model = model;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String response = model.processMessage(inputLine);
                out.println(response);
            }
        } catch (IOException e) {
            System.out.println("Error handling client #" + this.clientSocket.getInetAddress().getHostAddress());
            e.printStackTrace();
        }
    }
}
