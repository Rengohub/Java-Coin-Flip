package client.Controller;

import client.Model.AuthenticationManager;
import client.Model.ClientModel;
import client.View.*;
import server.test.CoinFlipGameDialog;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientController {
    private ClientModel clientModel;
    private AuthenticationManager authManager;
    private JFrame frame;
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
        frame.setSize(600, 400);
        frame.setLayout(new FlowLayout());

        userStatusLabel = new JLabel("No user logged in");
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        logoutButton = new JButton("Logout");
        playCoinGameButton = new JButton("Play Coin Game");
        playDiceGameButton = new JButton("Play Dice Game");
        viewAccountButton = new JButton("View Account");
        adminPanelButton = new JButton("Admin Panel");

        frame.add(userStatusLabel);
        frame.add(loginButton);
        frame.add(registerButton);
        frame.add(logoutButton);
        frame.add(playCoinGameButton);
        frame.add(playDiceGameButton);
        frame.add(viewAccountButton);
        frame.add(adminPanelButton);

        loginButton.addActionListener(e -> authManager.showLoginDialog());
        registerButton.addActionListener(e -> authManager.showRegistrationDialog());
        playCoinGameButton.addActionListener(e -> new ClientCoinView());
        playDiceGameButton.addActionListener(e -> new ClientDiceView());

        updateUIBasedOnUser();
        frame.setVisible(true);
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
}
