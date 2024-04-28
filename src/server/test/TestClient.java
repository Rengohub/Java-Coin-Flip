package server.test;

import javax.swing.*;
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
    private JButton loginButton, logoutButton, registerButton, playGameButton, viewAccountButton;
    private JLabel userStatusLabel;
    private String currentUser = null;
    private int currentUserId = -1;
    private AuthenticationManager authManager;

    public TestClient(String serverAddress, int serverPort) throws Exception {
        socket = new Socket(serverAddress, serverPort);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        authManager = new AuthenticationManager(this);
        createUI();
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
        loginButton = new JButton("Login");
        logoutButton = new JButton("Logout");
        registerButton = new JButton("Register User");
        playGameButton = new JButton("Play Game");
        viewAccountButton = new JButton("View Account");


        loginButton.addActionListener(e -> authManager.showLoginDialog());
        logoutButton.addActionListener(this::handleLogout);
        registerButton.addActionListener(e -> authManager.showRegistrationDialog());
        playGameButton.addActionListener(e -> openCoinFlipGame());
        viewAccountButton.addActionListener(e -> viewAccountDetails());

        userStatusLabel = new JLabel("No user logged in");

        JPanel panel = new JPanel();
        panel.add(loginButton);
        panel.add(logoutButton);
        panel.add(registerButton);
        panel.add(playGameButton);
        panel.add(viewAccountButton);
        panel.add(userStatusLabel);

        updateUIBasedOnUser();

        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void updateUIBasedOnUser() {
        if (currentUser != null) {
            userStatusLabel.setText("Logged in as: " + currentUser);
            playGameButton.setEnabled(true);
            viewAccountButton.setEnabled(true);
        } else {
            userStatusLabel.setText("No user logged in");
            playGameButton.setEnabled(false);
            viewAccountButton.setEnabled(false);
        }

        logoutButton.setVisible(currentUser != null);
        loginButton.setVisible(currentUser == null);
        registerButton.setVisible(currentUser == null);
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

            String response = in.readLine();
            if (response == null) {
                throw new IOException("Connection closed by server.");
            }

            System.out.println("Request sent: " + request);
            System.out.println("Initial response received: " + response);

            // Handle specific responses
            if (request.startsWith("LOGIN:")) {
                if (response.startsWith("Login successful")) {
                    // Extracting user details from the response
                    String uidPart = response.split("=")[1].trim();
                    currentUserId = Integer.parseInt(uidPart);
                    currentUser = request.substring(6).split(",")[0];
                    updateUIBasedOnUser();
                    return "Logged in successfully as " + currentUser + " with UID " + currentUserId;
                }
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

    public int getCurrentUserId() {
        return currentUserId;
    }
}