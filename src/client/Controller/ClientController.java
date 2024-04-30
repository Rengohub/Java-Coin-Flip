package client.Controller;

import client.Model.AuthenticationManager;
import client.Model.ClientModel;
import client.View.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ClientController {
    private static JFrame frame;
    private static int currentUserId;
    private ClientModel clientModel;
    private AuthenticationManager authManager;
    private JButton loginButton,
            registerButton,
            logoutButton,
            playCoinGameButton,
            playDiceGameButton,
            viewAccountButton,
            adminPanelButton,
            reloadLeaderboardButton;
    private JLabel userStatusLabel;
    private String currentUser;
    private JTable leaderboardTable;
    private DefaultTableModel leaderboardModel;

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

    public static JFrame getFrame() {
        return frame;
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
        frame.setSize(700, 220);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Top panel for buttons and labels
        JPanel topPanel = new JPanel(new FlowLayout());
        userStatusLabel = new JLabel("No user logged in");
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        logoutButton = new JButton("Logout");
        playCoinGameButton = new JButton("Play Coin Game");
        playDiceGameButton = new JButton("Play Dice Game");
        viewAccountButton = new JButton("View Account");
        adminPanelButton = new JButton("Admin Panel");
        reloadLeaderboardButton = new JButton("Reload Leaderboard");

        topPanel.add(userStatusLabel);
        topPanel.add(loginButton);
        topPanel.add(registerButton);
        topPanel.add(logoutButton);
        topPanel.add(playCoinGameButton);
        topPanel.add(playDiceGameButton);
        topPanel.add(viewAccountButton);
        topPanel.add(adminPanelButton);

        frame.add(topPanel, BorderLayout.NORTH);

        // Leaderboard setup
        leaderboardModel = new DefaultTableModel(new Object[]{"Username", "Credits", "Streak"}, 0);
        leaderboardTable = new JTable(leaderboardModel);
        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Bottom pnael for reload button
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(reloadLeaderboardButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Event handlers
        logoutButton.addActionListener(this::handleLogout);
        loginButton.addActionListener(e -> authManager.showLoginDialog());
        registerButton.addActionListener(e -> authManager.showRegistrationDialog());
        playCoinGameButton.addActionListener(e -> openCoinGame());
        playDiceGameButton.addActionListener(e -> new ClientDiceView(this));
        adminPanelButton.addActionListener(e -> openAdminPanel());
        reloadLeaderboardButton.addActionListener(e -> showLeaderboard());

        showLeaderboard();
        updateUIBasedOnUser();

        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    clientModel.closeConnection();
                } catch (IOException ex) {
                    System.err.println("Error closing connection: " + ex.getMessage());
                }
                System.exit(0);
            }
        });
    }

    private void openCoinGame() {
        if (currentUserId != -1) {
            new ClientCoinView(this);
        } else {
            System.out.println("User must be logged in to play the game.");
        }
    }

    private void openAdminPanel() {
        if (currentUser != null && currentUserId == 1) {
            AdminPanelDialog adminPanel = new AdminPanelDialog(frame, this);
            adminPanel.show();
        } else {
            JOptionPane.showMessageDialog(frame, "Only admin can access this panel.", "Access Denied", JOptionPane.ERROR_MESSAGE);
        }
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

    public int getCurrentUserId() {
        return currentUserId;
    }

    private void handleLogout(ActionEvent e) {
        currentUser = null;
        currentUserId = -1;
        updateUIBasedOnUser();
        JOptionPane.showMessageDialog(null, "Logged out successfully.");
    }

    public void showLeaderboard() {
        String response = ClientModel.sendRequest("LEADERBOARD");
        if (response != null && !response.isEmpty()) {
            String[] rows = response.split("\n");
            leaderboardModel.setRowCount(0);
            for (int i = 1; i < rows.length; i++) {
                String[] data = rows[i].split(" \\| ");
                leaderboardModel.addRow(data);
            }
            // new ClientLeaderboardView(leaderboardModel);
        }
    }
}
