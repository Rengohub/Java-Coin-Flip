package client;

import javax.swing.*;
import javax.xml.ws.Action;

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
    String[] imagesPath = {"src/Assets/Java-Coin-Flip.png", "src/Assets/Java-Coin-Tails-Flip.png", "src/Assets/Java-Coin-Flip.png", "src/Assets/Java-Coin-Heads-Flip.png"};
    private String headsImg = imagesPath[3];
    private String tailsImg = imagesPath[1];
    private Timer timer;
    JButton decreaseBetButton;
    JButton increaseBetButton;
    private int currentImageIndex = 0;
    // private int imageFlag = 1;

    // public void stopImageRotator() {
    //     imageFlag = 0;
    // }

    // public void startImageRotator() {
    //     imageFlag = 1;
    // }

                                        //////////     START GAME       //////////
    public ClientView() {
        new GameStart();
    }

    public class GameStart extends JFrame {
        JButton startButton = new JButton("Start Game");
        JFrame frame;

        public GameStart() {
            LandingPage();
        }

        public void LandingPage() {
            frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1080, 720);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());

            JLabel label = new JLabel("Welcome to Coin Flip!");
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.black));
            label.setPreferredSize(new Dimension(350, 150));

            
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    ImageRotator(imagesPath);
                }
            });
 
            frame.add(label, BorderLayout.CENTER);
            frame.add(startButton, BorderLayout.SOUTH);
            frame.setVisible(true);
        }
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
        // }

    // Force Heads image
    public void updateHeadsImage() {
        try {
            Image img = ImageIO.read(new File(headsImg));
            ImageIcon icon = new ImageIcon(img.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_SMOOTH));
            imgLabel.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Force Tails image
    public void updateTailsImage() {
        try {
            Image img = ImageIO.read(new File(tailsImg));
            ImageIcon icon = new ImageIcon(img.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_SMOOTH));
            imgLabel.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        setVisible(true);

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

        tailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopTimer();
                updateTailsImage();
                // Thread.sleep(7000);
                // startTimer();
                // updateTextArea("Flipping coin...");
                // updateTextArea("Coin landed on: " + model.flipCoin());
                // updateTextArea("You " + model.checkWin("heads"));
            }
        });
        
        headsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopTimer();
                updateHeadsImage();
                // Thread.sleep(7000);
                // startTimer();
                // updateTextArea("Flipping coin...");
                // updateTextArea("Coin landed on: " + model.flipCoin());
                // updateTextArea("You " + model.checkWin("heads"));
            }
        });

        choicesPanel.add(headsButton);
        choicesPanel.add(bettingPanel);
        choicesPanel.add(tailsButton);

        // startImageRotator();

        add(choicesPanel, BorderLayout.SOUTH);
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
