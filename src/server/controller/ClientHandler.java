package server.controller;

import server.model.ServerModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
            while (true) {  // Change to a condition that can be controlled, e.g., until a shutdown command is received
                inputLine = in.readLine();
                if (inputLine == null || "disconnect".equals(inputLine.trim())) {
                    break;  // Exit loop on disconnect command or if client closes connection
                }
                String response = model.processMessage(inputLine);
                out.println(response);
            }
        } catch (IOException e) {
            System.err.println("Error handling client communication: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}
