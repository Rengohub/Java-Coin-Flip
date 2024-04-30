package client.View;

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
    private ImageIcon icon[] = new ImageIcon[6];
    private String[] spriteArr= {"src/Assets/Sprites/Sprite-1.png", "src/Assets/Sprites/Sprite-2.png", "src/Assets/Sprites/Sprite-3.png", "src/Assets/Sprites/Sprite-4.png", "src/Assets/Sprites/Sprite-5.png", "src/Assets/Sprites/Sprite-6.png"};
    private Image img[] = new Image[6];
    private JButton guessButton[] = new JButton[6];
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

        JPanel GuessPanel = new JPanel();


        for (int i = 0; i < 6; i++) {
            int number = i + 1;
            icon[i] = new ImageIcon(spriteArr[i]);
            guessButton[i] = new JButton(String.valueOf(number));
//            guessButton[i].addActionListener(e -> playDiceGame(number));

            try {
                img[i] = ImageIO.read(new File(spriteArr[i]));
    
                icon[i] = new ImageIcon(img[i].getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            } catch (IOException e) {
                e.printStackTrace();
            }

            guessButton[i].setIcon(icon[i]);
            GuessPanel.add(guessButton[i]);
        }


        choicesPanel.add(GuessPanel);

        // guessInput = new JTextField("Enter Guess", 10);

        // choicesPanel.add(guessInput);
        choicesPanel.add(bettingPanel);

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

//    private void playDiceGame(int chosenNumber) {
//        if (client.getCurrentUserId() != -1) {
//            String betAmount = betAmountField.getText().trim();
//            if (betAmount.matches("\\d+") && Integer.parseInt(betAmount) > 0) {
//                disableButtons();
//                String requestData = String.format("%d,%s,%s", client.getCurrentUserId(), chosenNumber, betAmount);
//                String response = client.sendRequest("ROLL_DICE:" + requestData);
//                resultLabel.setText("<html><center>" + response.replace(", ", "<br>") + "</center></html>");
//                Timer timer = new Timer(8000, e -> {
//                    resultLabel.setText("Enter your bet amount and choose a number (1-6).");
//                    enableButtons();
//                });
//                timer.setRepeats(false);
//                timer.start();
//            } else {
//                resultLabel.setText("Invalid bet amount entered. Please enter a positive number.");
//            }
//        } else {
//            resultLabel.setText("Please log in to play the game.");
//        }
//    }

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
