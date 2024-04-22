import client.controller.ClientController;
import client.model.ClientModel;
import client.view.ClientView;
import server.controller.ServerController;
import server.model.ServerModel;

public class Main {
    public static void main(String[] args) {
        // Start the server in a new thread to avoid blocking the main thread
        Thread serverThread = new Thread(() -> {
            ServerModel serverModel = new ServerModel();
            ServerController serverController = new ServerController(12345, serverModel);
            serverController.start(); // This will block and wait for clients
        }, "ServerThread");
        serverThread.start();

        // Allow the server some time to start up
        try {
            Thread.sleep(1000); // 1-second delay for the server to initialize
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Server start was interrupted");
        }

        // Initialize and run the client in the event dispatch thread for proper Swing operation
        javax.swing.SwingUtilities.invokeLater(() -> {
            ClientView view = new ClientView(); // This launches the GUI
            ClientModel model = new ClientModel();
            ClientController controller = new ClientController(view, model);

            // Optionally, execute a test interaction or leave the handling to the controller
        });
    }
}
