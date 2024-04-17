import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientView extends JFrame {
    private JButton sendButton = new JButton("Send Hello World");
    private JTextArea textArea = new JTextArea(10, 20);

    public ClientView() {
        super("Client Interface");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 200);
        this.setLayout(new FlowLayout());
        this.add(sendButton);
        this.add(new JScrollPane(textArea));
        this.setVisible(true);
    }

    public void setSendButtonListener(ActionListener actionListener) {
        sendButton.addActionListener(actionListener);
    }

    public void updateTextArea(String message) {
        textArea.append(message + "\n");
    }
}
