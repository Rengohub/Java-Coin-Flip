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

        // view.setSendButtonListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         try {
        //             if (!model.isConnected()) { // Check if model is already connected
        //                 model.connectToServer("localhost", 12345);
        //             }
        //             model.sendMessage("Hello World");
        //             String response = model.receiveMessage();
        //             view.updateTextArea("Server: " + response);
        //         } catch (IOException ex) {
        //             ex.printStackTrace();
        //             view.updateTextArea("Error connecting to server.");
        //         }
        //     }
        // });

        view.decreaseBetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.adjustBet(-10);
            }
        });
    
        view.increaseBetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.adjustBet(10);
            }
        });

        view.startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.frame.dispose();
                view.ImageRotator(view.imagesPath);
            }
        });

        // view.headsButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         view.stopImageRotator();
        //         view.updateTailsImage();
        //         // view.updateTextArea("Flipping coin...");
        //         // view.updateTextArea("Coin landed on: " + model.flipCoin());
        //         // view.updateTextArea("You " + model.checkWin("heads"));
        //     }
        // });
    }
}
