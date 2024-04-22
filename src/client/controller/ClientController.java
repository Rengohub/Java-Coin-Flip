package client.controller;

import client.model.ClientModel;
import client.view.ClientView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientController {
    private ClientView view;
    private ClientModel model;

    public ClientController(ClientView view, ClientModel model) {
        this.view = view;
        this.model = model;
        initController();
    }

    private void initController() {
        view.setSendButtonListener(new SendButtonListener());
    }

    private class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String messageToSend = "Hello from Client";
                model.connectToServer("localhost", 12345);
                model.sendMessage(messageToSend);
                String response = model.receiveMessage();
                view.updateTextArea("Server: " + response);
            } catch (IOException ex) {
                view.updateTextArea("Error: " + ex.getMessage());
            }
        }
    }
}
