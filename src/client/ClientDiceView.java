package client;

import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ClientDiceView extends JFrame {
    private JFrame jframe;
    private JLabel imgLabel;
    private JTextField guessInput;
    private JTextField betField;
    private int betAmount = 0;
    String[] imagesPath = {"src/Assets/Java-Dice-1.png", "src/Assets/Java-Dice-2.png", "src/Assets/Java-Dice-3.png", "src/Assets/Java-Dice-4.png", "src/Assets/Java-Dice-5.png", "src/Assets/Java-Dice-6.png"};
    private String dice1 = "src/Assets/Sprites/Sprite-1.png";
    private String dice2 = "src/Assets/Sprites/Sprite-2.png";
    private String dice3 = "src/Assets/Sprites/Sprite-3.png";
    private String dice4 = "src/Assets/Sprites/Sprite-4.png";
    private String dice5 = "src/Assets/Sprites/Sprite-5.png";
    private String dice6 = "src/Assets/Sprites/Sprite-6.png";
    private ImageIcon icon1;
    private ImageIcon icon2;
    private ImageIcon icon3;
    private ImageIcon icon4;
    private ImageIcon icon5;
    private ImageIcon icon6;
    private JButton guessButton1;
    private JButton guessButton2;
    private JButton guessButton3;
    private JButton guessButton4;
    private JButton guessButton5;
    private JButton guessButton6;
    private Timer timer;
    private JButton decreaseBetButton;
    private JButton increaseBetButton;
    private int currentImageIndex = 0;

    public ClientDiceView() {
        ImageRotator(imagesPath);
    }

    private void ImageRotator(String[] images) { 
        this.imagesPath = images;
        DiceGame();
        startTimer();
    }

    public void cycleFrame() {
        jframe.setVisible(false);
    }


    public void DiceGame() {
        jframe = new JFrame();
        jframe.setTitle("Dice Game");
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
        betField.setColumns(2);
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

        icon1 = new ImageIcon(dice1);
        icon2 = new ImageIcon(dice2);
        icon3 = new ImageIcon(dice3);
        icon4 = new ImageIcon(dice4);
        icon5 = new ImageIcon(dice5);
        icon6 = new ImageIcon(dice6);

        JPanel GuessPanel = new JPanel();
        guessButton1 = new JButton();
        guessButton2 = new JButton();
        guessButton3 = new JButton();
        guessButton4 = new JButton();
        guessButton5 = new JButton();
        guessButton6 = new JButton();

        try {
            Image img1 = ImageIO.read(new File(dice1));
            Image img2 = ImageIO.read(new File(dice2));
            Image img3 = ImageIO.read(new File(dice3));
            Image img4 = ImageIO.read(new File(dice4));
            Image img5 = ImageIO.read(new File(dice5));
            Image img6 = ImageIO.read(new File(dice6));

            icon1 = new ImageIcon(img1.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            icon2 = new ImageIcon(img2.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            icon3 = new ImageIcon(img3.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            icon4 = new ImageIcon(img4.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            icon5 = new ImageIcon(img5.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            icon6 = new ImageIcon(img6.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        guessButton1.setIcon(icon1);
        guessButton2.setIcon(icon2);
        guessButton3.setIcon(icon3);
        guessButton4.setIcon(icon4);
        guessButton5.setIcon(icon5);
        guessButton6.setIcon(icon6);

        GuessPanel.add(guessButton1);
        GuessPanel.add(guessButton2);
        GuessPanel.add(guessButton3);
        GuessPanel.add(guessButton4);
        GuessPanel.add(guessButton5);
        GuessPanel.add(guessButton6);

        choicesPanel.add(GuessPanel);

        // guessInput = new JTextField("Enter Guess", 10);

        // choicesPanel.add(guessInput);
        choicesPanel.add(bettingPanel);

        // startImageRotator();
        jframe.add(ClientView.header(), BorderLayout.NORTH);
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

    // public void addRollListener(ActionListener listenForRollButton) {
    //     rollButton.addActionListener(listenForRollButton);
    // }

    // public void setResult(int result) {
    //     resultLabel.setText("Result: " + result);
    // }
}
