package org.set.game;

import java.util.List;

import org.set.player.Player;
import org.set.template.Template;
import org.set.marketplace.MarketPlace;

public class GameController {
    private Template board;
    private List<Player> players;
    public boolean isGameOver = false;
    public int turnCounter;
    public MarketPlace market = new MarketPlace();

    public GameController(Template board2) {
        this.board = board2;
        Player_move.caveMap = Before_game.allocateTokens(board);
        players = Before_game.addPlayer(board);
        Before_game.placePlayersOnBoard(board);
        Before_game.displayMarketInfo(board, market);
    }

    public void GameSession() {
        int turnNumber = 0;
        int FirstWinningIndex = -1;
        while (!isGameOver) {
            for (int currentPlayerIndex = 0; currentPlayerIndex < players.size(); currentPlayerIndex++) {
                Player currentPlayer = players.get(currentPlayerIndex);
                System.out.println("Turn " + turnNumber + ": Player " + currentPlayer.getName() + "'s turn.");
                Player_draw_discard.PlayerDrawCards(board, currentPlayer);
                Player_draw_discard.PlayActionCard(board, currentPlayer);
                Player_move.PlayerMove2(board, currentPlayer);
                Player_draw_discard.PlayActionCard(board, currentPlayer);

                if (Final_round.isThereAwinnier(board, currentPlayer)) {
                    System.out.println("Final Round!");
                    if (currentPlayerIndex != players.size() - 1) {
                        FirstWinningIndex = currentPlayerIndex;
                    }
                    isGameOver = true;
                    continue;
                }


              Player_buy.PlayerBuy(board,currentPlayer,market);
              Player_draw_discard.PlayerDiscard(board,currentPlayer);
              boolean TurnOVER=InputHelper.getYesNoInput("Your turn will finish now.");
              

            }

            turnNumber++;
        }

        if (FirstWinningIndex != -1) {
            Final_round.FinalRound(board, FirstWinningIndex, players);
        }
        Final_round.EndGame(players);
    }

}
