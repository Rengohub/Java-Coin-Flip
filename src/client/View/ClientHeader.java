package client.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import client.Controller.ClientController;

import java.awt.*;
import java.awt.event.*;

public class ClientHeader extends JPanel { 
    private static JButton MainMenu;
    

    public ClientHeader() {
        header(null);
        }
    
    static public JPanel header(JFrame frame) {
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
                frame.setVisible(false);
                ClientController.getFrame();
            }
        });

        JButton leaderBoards = new JButton("Leaderboards");
       
        leaderBoards.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // ClientController.showLeaderboard();
            }
        });

        JButton logoutButton = new JButton("Logout");
        
        header.add(label);
        header.add(Username);
        header.add(streak);
        header.add(balance);
        header.add(MainMenu);
        header.add(leaderBoards);
        header.add(logoutButton);

        // logoutButton.addActionListener(handleLogout(this));

        return header;
    }

    public JButton getMainMenu() {
        return MainMenu;
    }
}
