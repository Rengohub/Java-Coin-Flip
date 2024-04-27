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
    private JButton loginButton, logoutButton, registerButton, createUserButton;
    private JLabel userStatusLabel; // Label to display the login status
    private String currentUser = null; // To keep track of the logged-in user
    private int currentUserId = -1;

    public TestClient(String serverAddress, int serverPort) throws Exception {
        // Establish connection to the server
        socket = new Socket(serverAddress, serverPort);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        createUI();
    }

    private void createUI() {
        frame = new JFrame("Test Client");
        loginButton = new JButton("Login");
        logoutButton = new JButton("Logout");
        registerButton = new JButton("Register User");

        loginButton.addActionListener(this::handleLogin);
        logoutButton.addActionListener(this::handleLogout);
        registerButton.addActionListener(this::handleRegister);

        userStatusLabel = new JLabel("No user logged in"); // Initialize the user status label

        JPanel panel = new JPanel();
        panel.add(loginButton);
        panel.add(logoutButton);
        panel.add(registerButton);
        panel.add(userStatusLabel); // Add the status label to the panel

        updateUIBasedOnUser(); // Initial UI setup based on login status

        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void updateUIBasedOnUser() {
        if (currentUser != null) {
            userStatusLabel.setText("Logged in as: " + currentUser); // Update label to show logged-in user
        } else {
            userStatusLabel.setText("No user logged in"); // Reset label when no user is logged in
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
            String username = usernameField.getText().trim(); // Trim to remove any leading or trailing spaces
            String password = new String(passwordField.getPassword()).trim(); // Convert char[] to String and trim

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
        currentUser = null; // Clear the current user
        updateUIBasedOnUser(); // Update UI to reflect the login status
        JOptionPane.showMessageDialog(null, "Logged out successfully.");
    }

    private void sendRequest(String request) {
        try {
            out.println(request);
            String response = in.readLine();
            if (request.startsWith("LOGIN:") && response.startsWith("Login successful")) {
                // Extract UID from response
                String uidPart = response.split("=")[1].trim();
                currentUserId = Integer.parseInt(uidPart);
                currentUser = request.substring(6).split(",")[0];
                updateUIBasedOnUser();
                JOptionPane.showMessageDialog(null, "Logged in successfully as " + currentUser + " with UID " + currentUserId);
            } else {
                JOptionPane.showMessageDialog(null, response);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to send request: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            new TestClient("localhost", 12345);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Could not connect to server: " + e.getMessage());
        }
    }
}
