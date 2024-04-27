package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientLoginView extends JFrame {
    private JFrame jframe;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public ClientLoginView() {
        jframe = new JFrame();
        jframe.setTitle("Login");
        setLayout(new GridLayout(3, 2));
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(1920, 970);
        jframe.setLocationRelativeTo(null);
        jframe.setLayout(new BorderLayout());

        JLabel label = new JLabel("Welcome to Casino of Chance!");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.setPreferredSize(new Dimension(350, 150));

        JPanel choicePanel = new JPanel();
        
        choicePanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        usernameField.setColumns(10);
        usernameField.setEditable(true);
        choicePanel.add(usernameField);

        choicePanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        passwordField.setColumns(10);
        choicePanel.add(passwordField);

        loginButton = new JButton("Login");
        choicePanel.add(loginButton);

        registerButton = new JButton("Register");
        choicePanel.add(registerButton);

        jframe.add(label, BorderLayout.CENTER);
        jframe.add(choicePanel, BorderLayout.SOUTH);

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
    }

    public JButton getloginbutton() {
        return loginButton;
    }

    public JButton getregisterButton() {
        return registerButton;
    }

    public void cycleFrame() {
        jframe.setVisible(false);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
}
