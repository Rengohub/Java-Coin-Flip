package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestClient {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public TestClient(String serverAddress, int serverPort) throws Exception {
        // Establish a connection to the server
        socket = new Socket(serverAddress, serverPort);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    // Send a request to the server
    public void sendRequest(String request) {
        writer.println(request);
    }

    // Receive a response from the server
    public String getResponse() throws Exception {
        return reader.readLine();
    }

    // Close the connection
    public void close() throws Exception {
        reader.close();
        writer.close();
        socket.close();
    }
}
