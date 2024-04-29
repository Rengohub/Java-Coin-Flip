package server.test;

import javax.swing.*;
import java.awt.*;

public class CoinFlipGameDialog {
    private JDialog dialog;
    private JLabel resultLabel;
    private TestClient client;
    private JButton headsButton, tailsButton;
    private JTextField betAmountField;

    public CoinFlipGameDialog(Frame owner, TestClient client) {
        this.client = client;
        dialog = new JDialog(owner, "Coin Flip Game", false);
        dialog.setSize(300, 200);
        dialog.setLayout(new BorderLayout());

        resultLabel = new JLabel("Enter your bet and choose HEADS or TAILS.", SwingConstants.CENTER);
        dialog.add(resultLabel, BorderLayout.NORTH);

        betAmountField = new JTextField(5);
        JPanel betPanel = new JPanel();
        betPanel.add(new JLabel("Bet Amount:"));
        betPanel.add(betAmountField);
        dialog.add(betPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        headsButton = new JButton("HEADS");
        tailsButton = new JButton("TAILS");

        headsButton.addActionListener(e -> playGame("HEADS"));
        tailsButton.addActionListener(e -> playGame("TAILS"));

        buttonPanel.add(headsButton);
        buttonPanel.add(tailsButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void show() {
        dialog.setVisible(true);
    }

    private void playGame(String bet) {
        if (client.getCurrentUserId() != -1) {
            String betAmount = betAmountField.getText().trim();
            if (betAmount.matches("\\d+") && Integer.parseInt(betAmount) > 0) {
                disableButtons();
                String requestData = String.format("%d,%s,%s", client.getCurrentUserId(), bet, betAmount);
                String response = client.sendRequest("FLIP_COIN:" + requestData);
                resultLabel.setText("<html><center>" + response.replace(", ", "<br>") + "</center></html>");
                Timer timer = new Timer(8000, e -> {
                    resultLabel.setText("Enter your bet and choose HEADS or TAILS.");
                    enableButtons();
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                resultLabel.setText("Invalid bet amount entered. Please enter a positive number.");
            }
        } else {
            resultLabel.setText("Please log in to play the game.");
        }
    }

    private void disableButtons() {
        headsButton.setEnabled(false);
        tailsButton.setEnabled(false);
    }

    private void enableButtons() {
        headsButton.setEnabled(true);
        tailsButton.setEnabled(true);
    }

}