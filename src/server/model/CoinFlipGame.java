package server.model;

public class CoinFlipGame {
    public String playGame(int bet, boolean isHeads) {
        boolean outcome = Math.random() < 0.5;
        boolean isWin = (outcome == isHeads);
        int resultCredits = isWin ? bet : -bet;

        return isWin ? "Win: +" + resultCredits : "Lose: " + resultCredits;
    }
}