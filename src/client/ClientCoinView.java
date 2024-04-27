package client;

import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ClientCoinView extends JFrame {
    private JFrame jframe;
    private JLabel imgLabel;
    private JButton headsButton;
    private JButton tailsButton;
    private JTextField betField;
    private int betAmount = 0;
    String[] imagesPath = {"src/Assets/Java-Coin-Flip.png", "src/Assets/Java-Coin-Tails-Flip.png", "src/Assets/Java-Coin-Flip.png", "src/Assets/Java-Coin-Heads-Flip.png"};
    private String headsImg = imagesPath[3];
    private String tailsImg = imagesPath[1];
    private Timer timer;
    private JButton decreaseBetButton;
    private JButton increaseBetButton;
    private int currentImageIndex = 0;

    public ClientCoinView() {
        ImageRotator(imagesPath);
    }

    private void ImageRotator(String[] images) { 
        this.imagesPath = images;
        CoinGame();
        startTimer();
    }

    public void cycleFrame() {
        jframe.setVisible(false);
    }

    public void CoinGame() {
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

        decreaseBetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adjustBet(-10);
            }
        });

        increaseBetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adjustBet(10);
            }
        });

        bettingPanel.add(decreaseBetButton);
        bettingPanel.add(betField);
        bettingPanel.add(increaseBetButton);

        JPanel choicesPanel = new JPanel();

        headsButton = new JButton("H");
        tailsButton = new JButton("T");

        choicesPanel.add(headsButton);
        choicesPanel.add(bettingPanel);
        choicesPanel.add(tailsButton);

        // startImageRotator();
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
        // while (imageFlag == 1) {
        if (currentImageIndex < imagesPath.length) {
            try {
                Image img = ImageIO.read(new File(imagesPath[currentImageIndex]));
                ImageIcon icon = new ImageIcon(img.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_SMOOTH));
                imgLabel.setIcon(icon);
                currentImageIndex++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            currentImageIndex = 0;
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


}
