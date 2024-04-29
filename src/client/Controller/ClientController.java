package client.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import client.View.ClientCoinView;
import client.View.ClientDiceView;
import client.View.ClientGameStart;
import client.View.ClientLeaderboardView;
import client.View.ClientLoginView;

public class ClientController {
    private ClientLoginView loginView;
    private ClientGameStart gameStartView;
    private ClientCoinView coinGameView;
    private ClientDiceView diceGameView;
    private ClientLeaderboardView leaderboardView;

    // Start // 
    public ClientController() {
        loginView = new ClientLoginView();

        loginView.getloginbutton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginView.cycleFrame();
                login();
            }
        });
    }

    private void login() {
        // Implementation for logging in
        gameStartView = new ClientGameStart();

        gameStartView.getCoinButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameStartView.exitGameV();
                showCoinGame();
            }
        });

        gameStartView.getDiceButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameStartView.exitGameV();
                showDiceGame();
            }
        });
    }

    private void showCoinGame() {
        coinGameView = new ClientCoinView();
    }

    private void showDiceGame() {
        diceGameView = new ClientDiceView();
    }



    private void flipCoin() {
        // Implementation for flipping a coin
    }

    private void rollDice() {
        // Implementation for rolling dice
    }
}
