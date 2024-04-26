package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TestUserView extends JFrame {
    private TestClient client;

    public TestUserView() {
        try {
            client = new TestClient("localhost", 12345); // Connect to the server
            initializeUI();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to connect to the server: " + e.getMessage());
            System.exit(1);
        }
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton logoutButton = new JButton("Logout");
        JButton testUserButton = new JButton("Test User");

        loginButton.addActionListener(this::handleLogin);
        registerButton.addActionListener(this::handleRegister);
        logoutButton.addActionListener(e -> updateVisibility(false));
        testUserButton.addActionListener(this::handleTestUser);

        add(loginButton);
        add(registerButton);
        add(logoutButton);
        add(testUserButton);

        updateVisibility(false);  // Assume user is not logged in initially

        pack();
        setVisible(true);
    }

    private void handleLogin(ActionEvent e) {
        JTextField usernameField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(Box.createHorizontalStrut(15)); // a spacer
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Login", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            client.sendRequest("LOGIN:" + username + "," + password);
            try {
                String response = client.getResponse();
                JOptionPane.showMessageDialog(this, response);
                if (response.equals("Login successful")) {
                    updateVisibility(true);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to process login: " + ex.getMessage());
            }
        }
    }

    private void handleRegister(ActionEvent e) {
        JTextField usernameField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);
        JPanel panel = new JPanel(new GridLayout(0, 2, 2, 2));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Register", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim(); // Trim to remove any leading or trailing spaces
            String password = new String(passwordField.getPassword()).trim(); // Convert char[] to String and trim

            // Send the registration request with just the username and password
            client.sendRequest("REGISTER_USER:" + username + "," + password);
            try {
                String response = client.getResponse();
                JOptionPane.showMessageDialog(null, response); // Show the response message in a dialog
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Failed to process registration: " + ex.getMessage()); // Handle any exceptions
            }
        }
    }

    private void handleTestUser(ActionEvent e) {
        client.sendRequest("READ_USER:testuser");
        try {
            String response = client.getResponse();
            JOptionPane.showMessageDialog(this, response);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to retrieve user data: " + ex.getMessage());
        }
    }

    private void updateVisibility(boolean loggedIn) {
        getContentPane().getComponents()[0].setVisible(!loggedIn); // Login button
        getContentPane().getComponents()[1].setVisible(!loggedIn); // Register button
        getContentPane().getComponents()[2].setVisible(loggedIn);  // Logout button
        getContentPane().getComponents()[3].setVisible(loggedIn);  // Test User button
    }

    public static void main(String[] args) {
        new TestUserView();
    }
}