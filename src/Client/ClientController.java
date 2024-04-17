package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientController {
    private ClientView view;
    private ClientModel model;

    public ClientController(ClientView view, ClientModel model) {
        this.view = view;
        this.model = model;

        view.setSendButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.connectToServer("localhost", 5000); // Server IP and port
                    model.sendMessage("Hello World");
                    String response = model.receiveMessage();
                    view.updateTextArea("Server: " + response);
                    model.closeConnection();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    view.updateTextArea("Error connecting to server.");
                }
            }
        });
    }
}
