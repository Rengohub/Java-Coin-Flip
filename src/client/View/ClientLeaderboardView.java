package client.View;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ClientLeaderboardView extends JFrame {
    private JTextArea leaderboardArea;
    private JTable leaderboardTable;
    private DefaultTableModel leaderboardModel;

    public ClientLeaderboardView(DefaultTableModel leaderboard) {
        setTitle("Leaderboard");
        setSize(1920, 970);
        setLayout(new BorderLayout());

        leaderboardModel = leaderboard;
        // add(ClientHeader.header(this));
        // add(new DefaultTableModel(leaderboardModel), BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void setLeaderboard(String leaderboard) {
        leaderboardArea.setText(leaderboard);
    }
}
