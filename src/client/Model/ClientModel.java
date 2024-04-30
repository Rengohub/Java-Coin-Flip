package client.Model;

import java.io.*;
import java.net.Socket;

public class ClientModel {
    private Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;

    public void connectToServer(String ip, int port) throws IOException {
        if (socket == null || socket.isClosed()) {
            socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
    }

    public static String sendRequest(String request) {
        try {
            out.println(request);
            out.flush();

            StringBuilder responseBuilder = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null && !line.equals("END")) { responseBuilder.append(line + "\n"); }
            if (line == null) { throw new IOException("Connection closed by server."); }

            String response = responseBuilder.toString().trim();

            return response;
        } catch (IOException ex) {
            System.err.println("Network error: " + ex.getMessage());
            return "Network error: " + ex.getMessage();
        } catch (Exception ex) {
            System.err.println("Error during request: " + ex.getMessage());
            return "Failed to send request: " + ex.getMessage();
        }
    }

    public void closeConnection() throws IOException {
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
        if (socket != null) {
            socket.close();
        }
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }
}
