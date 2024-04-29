package client.Controller;

import client.Model.AuthenticationManager;
import client.Model.ClientModel;
import client.View.*;
import server.test.CoinFlipGameDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ClientController {
    private ClientModel clientModel;
    private AuthenticationManager authManager;
    private static JFrame frame;
    private JButton loginButton,
            registerButton,
            logoutButton,
            playCoinGameButton,
            playDiceGameButton,
            viewAccountButton,
            adminPanelButton;
    private JLabel userStatusLabel;
    private String currentUser;
    private int currentUserId;
    private JTable leaderboardTable;
    private DefaultTableModel leaderboardModel;

    private ClientLoginView loginView;
    private ClientGameStart gameStartView;
    private ClientCoinView coinGameView;
    private ClientDiceView diceGameView;
    private ClientLeaderboardView leaderboardView;

    // Start // 
    public ClientController(String serverAddress, int serverPort) {
        clientModel = new ClientModel();
        try {
            clientModel.connectToServer(serverAddress, serverPort);
            authManager = new AuthenticationManager(this);
            System.out.println("Connected to server successfully!");
            createUI();
            // showLeaderboard();
        } catch (Exception e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }

    public String sendRequest(String request) {
        String response = clientModel.sendRequest(request);
        // Handle login specific response
        if (request.startsWith("LOGIN:") && response.startsWith("Login successful")) {
            String uidPart = response.split("UID=")[1].trim();
            currentUserId = Integer.parseInt(uidPart);
            currentUser = request.substring(6).split(",")[0];
            updateUIBasedOnUser();
            return "Logged in successfully as " + currentUser + " with UID " + currentUserId;
        }
        return response;
    }


    // Start UI with this
    private void createUI() {
        frame = new JFrame("Client Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 970);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());

        userStatusLabel = new JLabel("No user logged in");
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        logoutButton = new JButton("Logout");
        playCoinGameButton = new JButton("Play Coin Game");
        playDiceGameButton = new JButton("Play Dice Game");
        viewAccountButton = new JButton("View Account");
        adminPanelButton = new JButton("Admin Panel");

        // leaderboardModel = new DefaultTableModel();
        // leaderboardModel.setColumnIdentifiers(new Object[]{"Username", "Credits", "Streak"});
        // leaderboardTable = new JTable(leaderboardModel);
        // leaderboardTable.setFillsViewportHeight(true);

        frame.add(userStatusLabel);
        frame.add(loginButton);
        frame.add(registerButton);
        frame.add(logoutButton);
        frame.add(playCoinGameButton);
        frame.add(playDiceGameButton);
        frame.add(viewAccountButton);
        frame.add(adminPanelButton);

        // logoutButton.addActionListener(this::handleLogout);
        loginButton.addActionListener(e -> authManager.showLoginDialog());
        registerButton.addActionListener(e -> authManager.showRegistrationDialog());
        playCoinGameButton.addActionListener(e -> new ClientCoinView());
        playDiceGameButton.addActionListener(e -> new ClientDiceView());

        updateUIBasedOnUser();
        frame.setVisible(true);
    }

    

    // public String getCurrUser() {
    //     updateUIBasedOnUser();
    //     return currentUser;
    // }

    // public int getCurrentUserID() {
    //     updateUIBasedOnUser();
    //     return currentUserId;
    // }

    public static JFrame getFrame() {
        return frame;
    }

    private void updateUIBasedOnUser() {
        boolean isLoggedIn = currentUser != null;
        userStatusLabel.setText(isLoggedIn ? "Logged in as: " + currentUser : "No user logged in");
        playCoinGameButton.setVisible(isLoggedIn);
        playDiceGameButton.setVisible(isLoggedIn);
        viewAccountButton.setVisible(isLoggedIn);
        adminPanelButton.setVisible(isLoggedIn && currentUserId == 1);
        logoutButton.setVisible(isLoggedIn);
        loginButton.setVisible(!isLoggedIn);
        registerButton.setVisible(!isLoggedIn);
    }

    // private void handleLogout(ActionEvent e) {
    //     currentUser = null;
    //     currentUserId = -1;
    //     updateUIBasedOnUser();
    //     JOptionPane.showMessageDialog(null, "Logged out successfully.");
    // }

    

    // public void showLeaderboard() {
    //     String response = ClientModel.sendRequest("LEADERBOARD");
    //     if (response != null && !response.isEmpty()) {
    //         String[] rows = response.split("\n");
    //         leaderboardModel.setRowCount(0);
    //         for (int i = 1; i < rows.length; i++) {
    //             String[] data = rows[i].split(" \\| ");
    //             leaderboardModel.addRow(data);
    //         }
    //         // new ClientLeaderboardView(leaderboardModel);
    //     }
    // }
}
