package server.test;

import javax.swing.*;
import java.awt.*;

public class DiceRollGameDialog {
    private JDialog dialog;
    private JLabel resultLabel;
    private TestClient client;
    private JTextField betAmountField;
    private JButton[] numberButtons = new JButton[6]; // Array to hold buttons for numbers 1-6

    public DiceRollGameDialog(Frame owner, TestClient client) {
        this.client = client;
        dialog = new JDialog(owner, "Dice Roll Game", false);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());

        resultLabel = new JLabel("Enter your bet amount and choose a number (1-6).", SwingConstants.CENTER);
        dialog.add(resultLabel, BorderLayout.NORTH);

        JPanel betPanel = new JPanel();
        betAmountField = new JTextField(5); // Input for betting amount
        betPanel.add(new JLabel("Bet Amount:"));
        betPanel.add(betAmountField);
        dialog.add(betPanel, BorderLayout.CENTER);

        // Grid layout for 6 buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 6));
        for (int i = 0; i < 6; i++) {
            int number = i + 1;
            numberButtons[i] = new JButton(String.valueOf(number));
            numberButtons[i].addActionListener(e -> playDiceGame(number));
            buttonPanel.add(numberButtons[i]);
        }
        dialog.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void show() {
        dialog.setVisible(true);
    }

    private void playDiceGame(int chosenNumber) {
        if (client.getCurrentUserId() != -1) {
            String betAmount = betAmountField.getText().trim();
            if (betAmount.matches("\\d+") && Integer.parseInt(betAmount) > 0) {
                disableButtons();
                String requestData = String.format("%d,%s,%s", client.getCurrentUserId(), chosenNumber, betAmount);
                String response = client.sendRequest("ROLL_DICE:" + requestData);
                resultLabel.setText("<html><center>" + response.replace(", ", "<br>") + "</center></html>");
                Timer timer = new Timer(8000, e -> {
                    resultLabel.setText("Enter your bet amount and choose a number (1-6).");
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
        for (JButton button : numberButtons) {
            button.setEnabled(false);
        }
        betAmountField.setEnabled(false);
    }

    private void enableButtons() {
        for (JButton button : numberButtons) {
            button.setEnabled(true);
        }
        betAmountField.setEnabled(true);
    }
}
