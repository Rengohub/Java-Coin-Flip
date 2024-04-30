package client.View;

import client.Controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class ClientHeader {
    private static final JLabel Username = new JLabel("Username: " + ClientController.getCurrentUser());
    private static final JLabel balance = new JLabel("Balance: " + ClientController.getBalance());
    private static final JButton MainMenu = new JButton("Main Menu");
    private static ClientController controller;


    public static JPanel header(JFrame frame) {
        JPanel header = new JPanel();
        header.setLayout(new GridLayout(1, 7));

        MainMenu.addActionListener(e -> {
            frame.setVisible(false);
            JFrame mainFrame = ClientController.getFrame();
            if (mainFrame != null) {
                mainFrame.setVisible(true);
            }
        });

        header.add(new JLabel("Welcome to the Casino of Chance!"));
        header.add(Username);
        header.add(balance);
        header.add(MainMenu);

        return header;
    }

    public static void updateHeader() {
        Username.setText("Username: " + ClientController.getCurrentUser());
        balance.setText("Balance: " + ClientController.getBalance());
    }
}
