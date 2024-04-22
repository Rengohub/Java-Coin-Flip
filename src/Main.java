import client.ClientController;
import client.ClientModel;
import client.ClientView;
import server.controller.ServerController;
import server.model.ServerModel;

public class Main {
    public static void main(String[] args) {
        // Start the server in a new thread
        Thread serverThread = new Thread(() -> {
            ServerModel serverModel = new ServerModel();
            ServerController serverController = new ServerController(12345, serverModel);
            serverController.start(); // This will block and wait for clients
        });
        serverThread.start();

        // Allow the server some time to start up
        try {
            Thread.sleep(1000); // 1-second delay for the server to initialize
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Server start was interrupted");
        }

        // Start the client
        ClientView view = new ClientView();  // This launches the GUI
        ClientModel model = new ClientModel();
        ClientController controller = new ClientController(view, model);

        // Execute a test interaction with the server
        try {
            // Initialize the connection
            model.connectToServer("localhost", 12345); // Assuming server is on localhost and port 5000
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
