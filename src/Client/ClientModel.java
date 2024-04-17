package client;

import java.io.*;
import java.net.Socket;

public class ClientModel {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void connectToServer(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage() throws IOException {
        return in.readLine();
    }

    public void closeConnection() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}