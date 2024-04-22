package client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientView extends JFrame {
    private JButton sendButton;
    private JTextArea textArea;

    public ClientView() {
        super("Client Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new FlowLayout());

        sendButton = new JButton("Send");
        textArea = new JTextArea(10, 20);
        textArea.setEditable(false);

        add(sendButton);
        add(new JScrollPane(textArea));
        setVisible(true);
    }

    public void setSendButtonListener(ActionListener listener) {
        sendButton.addActionListener(listener);
    }

    public void updateTextArea(String message) {
        textArea.append(message + "\n");
    }
}
