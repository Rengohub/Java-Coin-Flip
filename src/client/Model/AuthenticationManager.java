package client.Model;

import client.Controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class AuthenticationManager {
    private final ClientController clientController;

    public AuthenticationManager(ClientController clientController) {
        this.clientController = clientController;
    }

    public void showLoginDialog() {
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
            String response = ClientController.sendRequest("LOGIN:" + username + "," + password);
            JOptionPane.showMessageDialog(null, response);
        }
    }

    public void showRegistrationDialog() {
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
            String response = ClientController.sendRequest("REGISTER_USER:" + username + "," + password);
            JOptionPane.showMessageDialog(null, response);
        }
    }
}
