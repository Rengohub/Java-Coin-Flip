package client.View;

import client.Controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class ClientHeader {
    private static JLabel Username = new JLabel("Username: " + ClientController.getCurrentUser());
    private static JLabel balance = new JLabel("Balance: " + ClientController.getBalance());
    private static JButton MainMenu = new JButton("Main Menu");
    private static JButton leaderBoards = new JButton("Leaderboards");
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

        leaderBoards.addActionListener(e -> {
            if (controller != null) controller.showLeaderboard();
        });

        header.add(new JLabel("Welcome to the Casino of Chance!"));
        header.add(Username);
        header.add(balance);
        header.add(MainMenu);
        header.add(leaderBoards);

        return header;
    }

    public static JButton getMainMenu() {
        return MainMenu;
    }

    public static void updateHeader() {
        Username.setText("Username: " + ClientController.getCurrentUser());
        balance.setText("Balance: " + ClientController.getBalance());
    }
}
