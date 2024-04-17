package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientView extends JFrame {
    private JButton sendButton = new JButton("Send Hello World");
    private JTextArea textArea = new JTextArea(10, 20);

    public ClientView() {
        super("Client Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new FlowLayout());
        add(sendButton);
        add(new JScrollPane(textArea));
        setVisible(true);
    }

    public void setSendButtonListener(ActionListener actionListener) {
        sendButton.addActionListener(actionListener);
    }

    public void updateTextArea(String message) {
        textArea.append(message + "\n");
    }
}
