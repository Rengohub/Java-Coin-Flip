package server.test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private JFrame frame;
    private JButton loginButton, logoutButton, registerButton, playGameButton;
    private JLabel userStatusLabel;
    private String currentUser = null;
    private int currentUserId = -1;

    public TestClient(String serverAddress, int serverPort) throws Exception {
        // Establish connection to the server
        socket = new Socket(serverAddress, serverPort);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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

        loginButton.addActionListener(this::handleLogin);
        logoutButton.addActionListener(this::handleLogout);
        registerButton.addActionListener(this::handleRegister);
        playGameButton.addActionListener(e -> playCoinFlipGame());

        userStatusLabel = new JLabel("No user logged in");

        JPanel panel = new JPanel();
        panel.add(loginButton);
        panel.add(logoutButton);
        panel.add(registerButton);
        panel.add(playGameButton);
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
        } else {
            userStatusLabel.setText("No user logged in");
            playGameButton.setEnabled(false);
        }

        logoutButton.setVisible(currentUser != null);
        loginButton.setVisible(currentUser == null);
        registerButton.setVisible(currentUser == null);
    }

    private void handleRegister(ActionEvent e) {
        JTextField usernameField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Register", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            sendRequest("REGISTER_USER:" + username + "," + password);
        }
    }

    private void handleLogin(ActionEvent e) {
        JTextField usernameField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            sendRequest("LOGIN:" + username + "," + password);
        }
    }

    private void handleLogout(ActionEvent e) {
        currentUser = null;
        updateUIBasedOnUser();
        JOptionPane.showMessageDialog(null, "Logged out successfully.");
    }

    private String sendRequest(String request) {
        try {
            out.println(request);
            String response = in.readLine();
            if (request.startsWith("LOGIN:") && response.startsWith("Login successful")) {
                String uidPart = response.split("=")[1].trim();
                currentUserId = Integer.parseInt(uidPart);
                currentUser = request.substring(6).split(",")[0];
                updateUIBasedOnUser();
                return "Logged in successfully as " + currentUser + " with UID " + currentUserId;
            } else {
                return response;
            }
        } catch (Exception ex) {
            return "Failed to send request: " + ex.getMessage();
        }
    }

    public void playCoinFlipGame() {
        if (currentUserId != -1) {
            String response = sendRequest("PLAY_COIN_FLIP:" + currentUserId);
            JOptionPane.showMessageDialog(null, response);
        } else {
            JOptionPane.showMessageDialog(null, "Please log in to play the game.");
        }
    }
}
