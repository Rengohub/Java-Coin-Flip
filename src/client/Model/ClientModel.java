package client.Model;

import java.io.*;
import java.net.Socket;

public class ClientModel {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void connectToServer(String ip, int port) throws IOException {
        if (socket == null || socket.isClosed()) {
            socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
    }

    public String sendRequest(String request) {
        try {
            // Send the request to the server
            out.println(request);
            out.flush();

            StringBuilder responseBuilder = new StringBuilder();
            String line;

            // Read the response until "END" line is received
            while ((line = in.readLine()) != null && !line.equals("END")) {
                responseBuilder.append(line + "\n");
            }

            // Handle the case where the server closes the connection unexpectedly
            if (line == null) {
                throw new IOException("Connection closed by server.");
            }

            String response = responseBuilder.toString().trim();
            System.out.println("Request sent: " + request);
            System.out.println("Full response received: " + response);

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
