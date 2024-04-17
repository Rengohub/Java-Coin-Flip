import client.*;
import server.*;

public class Main {
    public static void main(String[] args) {
        ClientView view = new ClientView();  // This launches the GUI
        ClientModel model = new ClientModel();
        ClientController controller = new ClientController(view, model);

        // Perform a manual test to connect to the server and send a message
        try {
            // Initialize the connection
            model.connectToServer("localhost", 12345); // Make sure your server is running on this port
            System.out.println("Connected to server.");

            // Send a test message
            String testMessage = "Hello World";
            model.sendMessage(testMessage);
            System.out.println("Sent to server: " + testMessage);

            // Wait for a response from the server
            String response = model.receiveMessage();
            System.out.println("Received from server: " + response);

            // Clean up by closing the connection
            model.closeConnection();
            System.out.println("Connection closed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
