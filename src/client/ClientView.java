package client;

import javax.swing.*;
//import javax.xml.ws.Action;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ClientView extends JFrame {
    private static JButton MainMenu;

                                        //////////     START GAME       //////////
    public ClientView() {
        new ClientGameStart();
    }

    static public JPanel header() {
        JPanel header = new JPanel();
        header.setLayout(new GridLayout(1, 1));
        JLabel label = new JLabel("Welcome to the Dice Game!");
        JLabel Username = new JLabel("Username: " + "username");
        JTextField streak = new JTextField("Streak: 0", 10);
        streak.setEditable(false);
        JTextField balance = new JTextField("Balance: 0", 10);
        balance.setEditable(false);
        MainMenu = new JButton("Main Menu");

        MainMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ClientGameStart();
            }
        });




        JButton leaderBoards = new JButton("Leaderboards");
        JButton logoutButton = new JButton("Logout");
        
        header.add(label);
        header.add(Username);
        header.add(streak);
        header.add(balance);
        header.add(MainMenu);
        header.add(leaderBoards);
        header.add(logoutButton);

        return header;
    }

    public JButton getMainMenuButton() {
        return MainMenu;
    }


    // Force Heads image
    // public void updateHeadsImage() {
    //     try {
    //         Image img = ImageIO.read(new File(headsImg));
    //         ImageIcon icon = new ImageIcon(img.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_SMOOTH));
    //         imgLabel.setIcon(icon);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    // Force Tails image
    // public void updateTailsImage() {
    //     try {
    //         Image img = ImageIO.read(new File(tailsImg));
    //         ImageIcon icon = new ImageIcon(img.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_SMOOTH));
    //         imgLabel.setIcon(icon);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    // Main View Method
    public void createUI() {
 
    }

}
