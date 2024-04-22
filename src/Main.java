import client.ClientController;
import client.ClientModel;
import client.ClientView;
import server.controller.ServerController;
import server.model.ServerModel;

public class Main {
    public static void main(String[] args) {
        Thread serverThread = new Thread(() -> {
            ServerModel serverModel = new ServerModel();
            ServerController serverController = new ServerController(12345, serverModel);
            serverController.start();
        }, "ServerThread");
        serverThread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Server start was interrupted");
        }

        // Start the client
        javax.swing.SwingUtilities.invokeLater(() -> {
            ClientView view = new ClientView();
            ClientModel model = new ClientModel();
            ClientController controller = new ClientController(view, model);

            // Optionally, execute a test interaction or leave the handling to the controller
        });
    }
}
