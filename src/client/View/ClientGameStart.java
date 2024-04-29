package client.View;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;


public class ClientGameStart extends JFrame {
    private static JFrame jframe;
    private JButton coinGameButton;
    private JButton diceGameButton;

    public ClientGameStart() {
        jframe = new JFrame();
        jframe.setTitle("Game Start");
        setLayout(new GridLayout(3, 2));
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(1920, 970);
        jframe.setLocationRelativeTo(null);
        jframe.setLayout(new BorderLayout());

        JLabel label = new JLabel("Please select a game to play!");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.setPreferredSize(new Dimension(350, 150));

        JPanel choicePanel = new JPanel();
        
        coinGameButton = new JButton("Coin Game");
        choicePanel.add(coinGameButton);

        diceGameButton = new JButton("Dice Game");
        choicePanel.add(diceGameButton);

        jframe.add(ClientHeader.header(jframe), BorderLayout.NORTH); 
        jframe.add(label, BorderLayout.CENTER);
        jframe.add(choicePanel, BorderLayout.SOUTH);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
    }

    public void exitGameV() {
        jframe.setVisible(false);
    }

    public static void enterGameV() {
        jframe.setVisible(true);
    }

    public JButton getCoinButton() {
        return coinGameButton;
    }

    public JButton getDiceButton() {
        return diceGameButton;
    }
}
