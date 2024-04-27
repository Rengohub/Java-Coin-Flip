package client;

import java.awt.*;
import javax.swing.*;

public class ClientLeaderboardView extends JFrame {
    private JTextArea leaderboardArea;

    public ClientLeaderboardView() {
        setTitle("Leaderboard");
        setSize(300, 400);
        setLayout(new BorderLayout());

        leaderboardArea = new JTextArea();
        add(new JScrollPane(leaderboardArea), BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void setLeaderboard(String leaderboard) {
        leaderboardArea.setText(leaderboard);
    }
}
