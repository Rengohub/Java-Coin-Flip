package server.controller;

import server.model.ServerModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final ServerModel model;

    public ClientHandler(Socket clientSocket, ServerModel model) {
        this.clientSocket = clientSocket;
        this.model = model;
    }

    @Override
    public void run() {
        System.out.println("ClientHandler is running on thread " + Thread.currentThread().getId());
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            while (true) {
                inputLine = in.readLine();
                if (inputLine == null || "disconnect".equals(inputLine.trim())) {
                    System.out.println("Client disconnected");
                    break;
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