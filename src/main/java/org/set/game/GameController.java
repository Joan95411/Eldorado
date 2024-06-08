package org.set.game;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.set.player.Player;
import org.set.boardPieces.HexagonGameBoard;
import org.set.boardPieces.Tile;
import org.set.boardPieces.Util;
import org.set.cards.Card;
import org.set.cards.expedition.ExpeditionCard;
import org.set.marketplace.MarketPlace;

public class GameController {
    private HexagonGameBoard board;
    private List<Player> players;
    private String GameState;
    public int turnCounter;
    public MarketPlace market;

    public GameController(HexagonGameBoard board) {
        this.board = board;
        GameState = "Game Starts";
        During_game.removeblock(board);
        market=new MarketPlace();
        players = Before_game.addPlayer(board);
        Before_game.placePlayersOnBoard(board);
        GameSession();
    }
    
    private String displayGameState() {
        // Display the current state of the game, including the board and player
        // positions
        return GameState;
    }
    private void displayMarketInfo() {
    	HashMap<String, Integer> marketoption = market.getMarketBoardOption();

        // Iterate over the entries of the HashMap and print each key-value pair
        for (Map.Entry<String, Integer> entry : marketoption.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("marketoption: " + key + ", Value: " + value);
        }
        
        HashMap<String, Integer> currentMarket = market.getCurrentMarketBoard();

        // Iterate over the entries of the HashMap and print each key-value pair
        for (Map.Entry<String, Integer> entry : currentMarket.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("currentMarket: " + key + ", Value: " + value);
        }
    }

    private void PlayerDrawCards(int turn, int currentPlayerIndex, Player player) {
        // Phase 3
        // draw cards from your draw pile until you have 4 cards in your hand.
        // If your draw pile doesn't contain enough cards to draw for your next turn,
        // draw as many as possible.
        // Then, shuffle your discard pile to form your new draw pile, then draw the
        // rest of the cards you need.
        System.out.println("Player " + (currentPlayerIndex + 1) + " drawing cards");
        Player currentPlayer = players.get(currentPlayerIndex);
        player.myDeck.draw(4 - player.myDeck.getCardsInHand().size());
        board.PlayerCards = currentPlayer.myDeck.getCardsInHand();
        board.repaint();
    }

    


    public void PlayerBuy(Player player) {
        // Phase 1
        // Use the rest of your cards to buy up to 1 new card per turn.
    }

    public void PlayerDiscard(Player player) {
        // Phase 2
        // Discard Played Cards, keep card in your hand for your next turn
        // decide for each card individually.
        while(true){
        	boolean discardCard=InputHelper.getYesNoInput("Do you want to discard any cards?");
        	if(!discardCard) {break;}
        	int cardIndex = InputHelper.getIntInput("Choose 1 card to discard, input index (e.g. 0), or '-1' to stop discarding");
	        if (cardIndex == -1) {
	            break;
	        }
	        if (cardIndex >= player.myDeck.getCardsInHand().size()) {
	            System.out.println("Please enter a number between 0 to " + (player.myDeck.getCardsInHand().size() - 1));
	            continue;
	        }player.myDeck.discardFromHand(cardIndex);
	        board.repaint();
        }
        
    }

    public void GameSession() {
    	displayMarketInfo();
        int turnNumber = 0;

        while (true) {
            for (int currentPlayerIndex = 0; currentPlayerIndex < players.size(); currentPlayerIndex++) {
                Player currentPlayer = players.get(currentPlayerIndex);
                System.out.println("Turn " + turnNumber + ": Player " + currentPlayer.getName() + "'s turn.");
                PlayerDrawCards(turnNumber, currentPlayerIndex, currentPlayer);
                During_game.PlayerMove(board,currentPlayer);
//                int[] position = InputHelper.getPositionInput(board);
//
//                currentPlayer.setPlayerPosition(position[0], position[1]);
                board.repaint();
                PlayerDiscard(currentPlayer);
            }

            turnNumber++;
            // Check for end conditions or other game logic
            if (isGameOver()) {
                break;
            }
        }

        // Game over, perform any cleanup or display final results
        endGame();
    }

    public void FinalRound() {
        // 1 player reaches WinningPiece, triggers final round
        // Each player left in that round will now play their final turn
        GameState = "Final Round";
    }

    private boolean isGameOver() {
        // when the final round completes, the game is over
        return false;
    }

    private void endGame() {
        InputHelper.closeScanner();
        System.out.println("Game Over!");
    }
}
