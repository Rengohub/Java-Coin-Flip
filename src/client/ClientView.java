package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ClientView extends JFrame {
    private JLabel imgLabel;
    JButton headsButton;
    JButton tailsButton;
    private JTextField betField;
    private int betAmount = 0;
    private String[] imagesPath = {"src/Assets/Java-Coin-Flip.png", "src/Assets/Java-Coin-Tails-Flip.png", "src/Assets/Java-Coin-Flip.png", "src/Assets/Java-Coin-Heads-Flip.png"};
    // private String headsImg = imagesPath[3];
    // private String tailsImg = imagesPath[1];
    private Timer timer;
    JButton decreaseBetButton;
    JButton increaseBetButton;
    private int currentImageIndex = 0;
    private int imageFlag = 1;

    // public void stopImageRotator() {
    //     imageFlag = 0;
    // }

    public ClientView() {
        ImageRotator(imagesPath);
    }

    // public void refreshImage() {
    //     setVisible(true);
    // }

    public void ImageRotator(String[] images) { 
        this.imagesPath = images;
        createUI();
        startTimer();
        setVisible(true);
    }

    private void startTimer() {
        int delay = 1000;

        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateImage();
            }
        };

        timer = new Timer(delay, taskPerformer);
        timer.setInitialDelay(0);
        timer.start();
    }

       // Image Rotator Pointer Method
       private void updateImage() {
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

    // Force Heads image
    // public void updateHeadsImage() {
    //     try {
    //         Image img = ImageIO.read(new File(headsImg));
    //         ImageIcon icon = new ImageIcon(img.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_SMOOTH));
    //         imgLabel.setIcon(icon);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    // Force Tails image
    // public void updateTailsImage() {
    //     try {
    //         Image img = ImageIO.read(new File(tailsImg));
    //         ImageIcon icon = new ImageIcon(img.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_SMOOTH));
    //         imgLabel.setIcon(icon);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    // Main View Method
    public void createUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.black);
        setSize(1080, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(JLabel.CENTER);
        imgLabel.setVerticalAlignment(JLabel.CENTER);
        imgLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        imgLabel.setPreferredSize(new Dimension(350, 150));


      
        add(imgLabel, BorderLayout.CENTER);

        JPanel bettingPanel = new JPanel();

        betField = new JTextField(String.valueOf(betAmount), 5);
        betField.setEditable(true);

        decreaseBetButton = new JButton("▼");
        increaseBetButton = new JButton("▲");

        bettingPanel.add(decreaseBetButton);
        bettingPanel.add(betField);
        bettingPanel.add(increaseBetButton);

        JPanel choicesPanel = new JPanel();

        headsButton = new JButton("H");
        tailsButton = new JButton("T");

        choicesPanel.add(headsButton);
        choicesPanel.add(bettingPanel);
        choicesPanel.add(tailsButton);

        add(choicesPanel, BorderLayout.SOUTH);
        setVisible(true);
    }


    public void adjustBet(int amount) {
        betAmount += amount;
        if (betAmount < 0) betAmount = 0; // Prevent negative bets
        betField.setText(String.valueOf(betAmount));
    }

    // Get Bet
    public int getBet() {
        return betAmount;
    }

    // Set Bet
    public void setBet() {
        betAmount = 0;
    }
}
