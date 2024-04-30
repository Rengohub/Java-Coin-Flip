package client.View;

import client.Controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class ClientHeader {
    private static JLabel Username = new JLabel("Username: ");
    private static JTextField streak = new JTextField("Streak: 0", 10);
    private static JTextField balance = new JTextField("Balance: 0", 10);
    private static JButton MainMenu = new JButton("Main Menu");
    private static JButton leaderBoards = new JButton("Leaderboards");
    private static ClientController controller;

    public static void setController(ClientController cont) {
        controller = cont;
        updateHeader();
    }

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

        header.add(new JLabel("Welcome to the Dice Game!"));
        header.add(Username);
        header.add(streak);
        header.add(balance);
        header.add(MainMenu);
        header.add(leaderBoards);

        return header;
    }

    public static void updateHeader() {
        if (controller != null) {
            Username.setText("Username: " + controller.getCurrentUser());
            balance.setText("Balance: " + controller.getBalance());
        }
    }

    public static JButton getMainMenu() {
        return MainMenu;
    }
}
