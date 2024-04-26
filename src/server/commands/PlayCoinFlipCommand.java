package server.commands;

import server.model.CoinFlipGame;

public class PlayCoinFlipCommand implements Command{
    private CoinFlipGame game = new CoinFlipGame();

    @Override
    public String execute(String userData){
        String[] parts = userData.split(",");
        int bet = Integer.parseInt(parts[0].trim());
        boolean isHeads = "heads".equals(parts[1].trim());
        return game.playGame(bet, isHeads);
    }
}
