package client.View;

import client.Controller.ClientController;
import client.Model.ClientModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ClientCoinView extends JFrame {
    private final ClientController controller;
    String[] imagesPath = {"src/Assets/Java-Coin-Flip.png", "src/Assets/Java-Coin-Tails-Flip.png", "src/Assets/Java-Coin-Flip.png", "src/Assets/Java-Coin-Heads-Flip.png"};
    private JFrame jframe;
    private JLabel imgLabel;
    private JButton headsButton;
    private JButton tailsButton;
    private JTextField betField;
    private int betAmount = 0;
    private String headsImg = imagesPath[3];
    private String tailsImg = imagesPath[1];
    private Timer timer;
    private JButton decreaseBetButton;
    private JButton increaseBetButton;
    private int currentImageIndex = 0;
    private boolean isRotating = true;

    public ClientCoinView(ClientController controller) {
        this.controller = controller;
        ImageRotator(imagesPath);
    }

    private void ImageRotator(String[] images) {
        this.imagesPath = images;
        initializeUI();
        startTimer();
    }

    public void cycleFrame() {
        jframe.setVisible(false);
    }

    public void initializeUI() {
        jframe = new JFrame();
        jframe.setTitle("Coin Game");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setBackground(Color.black);
        jframe.setSize(1920, 970);
        jframe.setLocationRelativeTo(null);
        jframe.setLayout(new BorderLayout());

        imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(JLabel.CENTER);
        imgLabel.setVerticalAlignment(JLabel.CENTER);
        imgLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        imgLabel.setPreferredSize(new Dimension(350, 150));

        JPanel bettingPanel = new JPanel();

        betField = new JTextField(String.valueOf(betAmount), 5);
        betField.setEditable(true);

        decreaseBetButton = new JButton("▼");
        increaseBetButton = new JButton("▲");

        decreaseBetButton.addActionListener(e -> adjustBet(-10));
        increaseBetButton.addActionListener(e -> adjustBet(10));

        bettingPanel.add(decreaseBetButton);
        bettingPanel.add(betField);
        bettingPanel.add(increaseBetButton);

        JPanel choicesPanel = new JPanel();

        headsButton = new JButton("H");
        tailsButton = new JButton("T");

        choicesPanel.add(headsButton);
        choicesPanel.add(bettingPanel);
        choicesPanel.add(tailsButton);

        headsButton.addActionListener(e -> playGame("HEADS"));
        tailsButton.addActionListener(e -> playGame("TAILS"));

        // startImageRotator();
        jframe.add(ClientHeader.header(jframe), BorderLayout.NORTH);
        jframe.add(imgLabel, BorderLayout.CENTER);
        jframe.add(choicesPanel, BorderLayout.SOUTH);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);

    }


    private void startTimer() {
        int delay = 100;

        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateImage();
            }
        };

        timer = new Timer(delay, taskPerformer);
        timer.setInitialDelay(0);
        timer.start();
    }

    private void stopTimer() {
        timer.stop();
    }

    // Image Rotator Pointer Method
    private void updateImage() {
        if (isRotating && currentImageIndex < imagesPath.length) {
            try {
                Image img = ImageIO.read(new File(imagesPath[currentImageIndex]));
                ImageIcon icon = new ImageIcon(img.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_SMOOTH));
                imgLabel.setIcon(icon);
                currentImageIndex++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            currentImageIndex = 0;  // Reset to loop the rotation
        }
    }

    public void getBet() {
        betAmount = Integer.parseInt(betField.getText());
    }

    public void adjustBet(int amount) {
        betAmount += amount;
        if (betAmount < 0) betAmount = 0;
        betField.setText(String.valueOf(betAmount));
    }

    private void disableButtons() {
        headsButton.setEnabled(false);
        tailsButton.setEnabled(false);
        decreaseBetButton.setEnabled(false);
        increaseBetButton.setEnabled(false);
    }

    private void enableButtons() {
        headsButton.setEnabled(true);
        tailsButton.setEnabled(true);
        decreaseBetButton.setEnabled(true);
        increaseBetButton.setEnabled(true);
    }

    private void playGame(String choice) {
        if (controller.getCurrentUserId() != -1) {
            int bet = Integer.parseInt(betField.getText());
            if (bet > 0) {
                disableButtons();
                String requestData = String.format("%d,%s,%d", controller.getCurrentUserId(), choice, bet);
                try {
                    System.out.println(requestData);
                    String response = controller.sendRequest("FLIP_COIN:" + requestData);
                    processResponse(response);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Failed to communicate with server: " + e.getMessage());
                }
                enableButtons();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid bet amount. Please enter a positive number.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please log in to play the game.");
        }
    }

    private void processResponse(String response) {
        try {
            // Parse the response assuming format "Bet: ..., Amount: ..., Result: ..., Outcome: ..., New Credits: ..."
            String[] parts = response.split(", ");
            String userBet = parts[0].split(": ")[1];
            String amount = parts[1].split(": ")[1];
            String result = parts[2].split(": ")[1];
            String outcome = parts[3].split(": ")[1];
            String newCredits = parts[4].split(": ")[1];

            // Update UI elements with the response
            showGameResult(outcome, userBet, amount, result, newCredits);
            restartRotation();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error processing server response: " + e.getMessage());
        }
    }

    private void showGameResult(String outcome, String userBet, String amount, String result, String newCredits) {
        isRotating = false;  // Stop rotating images
        ImageIcon resultIcon = result.equals("HEADS") ? new ImageIcon(headsImg) : new ImageIcon(tailsImg);
        imgLabel.setIcon(resultIcon);
        JOptionPane.showMessageDialog(this, String.format("Bet: %s\nAmount: %s\nResult: %s\nOutcome: %s\nNew Credits: %s",
                userBet, amount, result, outcome, newCredits));
    }

    private void restartRotation() {
        Timer restartTimer = new Timer(1500, e -> {
            isRotating = true;  // Resume rotation after 1.5 seconds
        });
        restartTimer.setRepeats(false);
        restartTimer.start();
    }


}
