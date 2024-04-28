package server.test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private JFrame frame;
    private JButton loginButton,
            logoutButton,
            registerButton,
            playCoinGameButton,
            playDiceGameButton,
            adminPanelButton,
            viewAccountButton;
    private JLabel userStatusLabel;
    private String currentUser = null;
    private int currentUserId = -1;
    private AuthenticationManager authManager;
    private JTable leaderboardTable;
    private DefaultTableModel leaderboardModel;

    public TestClient(String serverAddress, int serverPort) throws Exception {
        socket = new Socket(serverAddress, serverPort);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        authManager = new AuthenticationManager(this);
        createUI();
        showLeaderboard();
    }

    public static void main(String[] args) {
        try {
            new TestClient("localhost", 12345);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Could not connect to server: " + e.getMessage());
        }
    }

    private void createUI() {
        frame = new JFrame("Test Client");
        frame.setMinimumSize(new Dimension(600, 400));  // Adjusted for better space

        loginButton = new JButton("Login");
        logoutButton = new JButton("Logout");
        registerButton = new JButton("Register User");
        playCoinGameButton = new JButton("Play CoinFlip");
        playDiceGameButton = new JButton("Play Dice");
        viewAccountButton = new JButton("View Account");
        adminPanelButton = new JButton("Admin Panel");

        loginButton.addActionListener(e -> authManager.showLoginDialog());
        logoutButton.addActionListener(this::handleLogout);
        registerButton.addActionListener(e -> authManager.showRegistrationDialog());
        playCoinGameButton.addActionListener(e -> openCoinFlipGame());
        playDiceGameButton.addActionListener(e -> openDiceGame());
        adminPanelButton.addActionListener(e -> openAdminPanel());
        viewAccountButton.addActionListener(e -> viewAccountDetails());

        userStatusLabel = new JLabel("No user logged in");

        // Initialize the table with columns
        leaderboardModel = new DefaultTableModel();
        leaderboardModel.setColumnIdentifiers(new Object[]{"Username", "Credits", "Streak"});
        leaderboardTable = new JTable(leaderboardModel);
        leaderboardTable.setFillsViewportHeight(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(userStatusLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(playCoinGameButton);
        buttonPanel.add(playDiceGameButton);
        buttonPanel.add(viewAccountButton);
        buttonPanel.add(adminPanelButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        updateUIBasedOnUser();

        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
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

    private void viewAccountDetails() {
        if (currentUserId != -1) {
            String response = sendRequest("READ_USER:" + currentUserId);
            // Ensure the dialog is refreshed every time
            JOptionPane.showMessageDialog(frame, response, "Account Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Please log in to view account details.", "Not Logged In", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleLogout(ActionEvent e) {
        currentUser = null;
        currentUserId = -1;
        updateUIBasedOnUser();
        JOptionPane.showMessageDialog(null, "Logged out successfully.");
    }

    protected String sendRequest(String request) {
        try {
            out.println(request);
            out.flush();

            StringBuilder responseBuilder = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null && !line.equals("END")) {
                responseBuilder.append(line + "\n");
            }

            if (line == null) {
                // If line is null, it means the server has closed the connection unexpectedly
                throw new IOException("Connection closed by server.");
            }

            String response = responseBuilder.toString().trim(); // Trim to remove the last newline
            System.out.println("Request sent: " + request);
            System.out.println("Full response received: " + response);


            // Handle specific responses
            if (request.startsWith("LOGIN:") && response.startsWith("Login successful")) {
                String uidPart = response.split("UID=")[1].trim(); // Assuming the server sends UID in this format
                currentUserId = Integer.parseInt(uidPart);
                currentUser = request.substring(6).split(",")[0];
                updateUIBasedOnUser();
                return "Logged in successfully as " + currentUser + " with UID " + currentUserId;
            }

            // Return the full response for further processing or display
            return response;

        } catch (IOException ex) {
            System.err.println("Network error: " + ex.getMessage());
            return "Network error: " + ex.getMessage();
        } catch (Exception ex) {
            System.err.println("Error during request: " + ex.getMessage());
            return "Failed to send request: " + ex.getMessage();
        }
    }

    private void openCoinFlipGame() {
        if (currentUser != null) {
            CoinFlipGameDialog gameDialog = new CoinFlipGameDialog(frame, this);
            gameDialog.show();
        } else {
            JOptionPane.showMessageDialog(null, "Please log in to play the game.");
        }
    }

    private void openDiceGame() {
        if (currentUser != null) {
            DiceRollGameDialog gameDialog = new DiceRollGameDialog(frame, this);
            gameDialog.show();
        } else {
            JOptionPane.showMessageDialog(null, "Please log in to play the game.");
        }
    }

    private void openAdminPanel() {
        if (currentUser != null) {
            AdminPanelDialog adminPanelDialog = new AdminPanelDialog(frame, this);
            adminPanelDialog.show();
        } else {
            JOptionPane.showMessageDialog(null, "Please have admin to access.");
        }
    }

    private void showLeaderboard() {
        String response = sendRequest("LEADERBOARD");
        if (response != null && !response.isEmpty()) {
            String[] rows = response.split("\n");
            leaderboardModel.setRowCount(0);
            for (int i = 1; i < rows.length; i++) {
                String[] data = rows[i].split(" \\| ");
                leaderboardModel.addRow(data);
            }
        }
    }

    public int getCurrentUserId() {
        return currentUserId;
    }
}