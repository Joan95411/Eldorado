package org.set.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.set.player.Asset;
import org.set.player.Player;
import org.set.template.Template;
import org.set.tokens.Cave;
import org.set.tokens.Token;
import org.set.boardPieces.Tile;
import org.set.boardPieces.TileType;
import org.set.boardPieces.Util;
import org.set.boardPieces.WinningPiece;
import org.set.cards.Card;
import org.set.cards.CardActionHandler;
import org.set.cards.CardType;
import org.set.cards.action.ActionCard;
import org.set.cards.expedition.ExpeditionCard;
import org.set.marketplace.MarketPlace;

public class GameController {
    private Template board;
    private List<Player> players;
    private boolean isGameOver = false;
    public int turnCounter;
    public MarketPlace market=new MarketPlace();
    public GameController(Template board2) {
        this.board = board2;
        Player_move.caveMap=Before_game.allocateTokens(board);
        players = Before_game.addPlayer(board);
        Before_game.placePlayersOnBoard(board);
        Before_game.displayMarketInfo(board,market);
    }
    
    public void GameSession() {
        int turnNumber = 0;
        int FirstWinningIndex = -1;
        while (!isGameOver) {
            for (int currentPlayerIndex = 0; currentPlayerIndex < players.size(); currentPlayerIndex++) {
                Player currentPlayer = players.get(currentPlayerIndex);
                System.out.println("Turn " + turnNumber + ": Player " + currentPlayer.getName() + "'s turn.");
                Player_draw_discard.PlayerDrawCards(board,currentPlayer);
                Player_draw_discard.PlayActionCard(board, currentPlayer);
                Player_move.PlayerMove2(board,currentPlayer);
                Player_draw_discard.PlayActionCard(board, currentPlayer);
      //the next 3 lines are only for testing purpose, to be deleted
//      int[] position = InputHelper.getPositionInput(board);
//      currentPlayer.setPlayerPosition(position[0], position[1]);
//      Player_move.caveExplore(board, currentPlayer);

                if(Final_round.isThereAwinnier(board, currentPlayer)) {
                	System.out.println("Final Round!");	
                	if(currentPlayerIndex!=players.size()-1) {
                	FirstWinningIndex=currentPlayerIndex;}
                	isGameOver = true;
                	continue;
                }

              Player_buy.PlayerBuy(board,currentPlayer,market);
              Player_draw_discard.PlayerDiscard(board,currentPlayer);
              boolean discardCard=InputHelper.getYesNoInput("Your turn will finish now.");
              board.discardPhase=false;
              
            }

            turnNumber++;  
        }
        
        if (FirstWinningIndex != -1) {
        	Final_round.FinalRound(board,FirstWinningIndex,players);
        }
        Final_round.EndGame(players,FirstWinningIndex);
    }

    
    
}
