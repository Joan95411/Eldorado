package org.set.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.set.player.Player;
import org.set.tokens.Cave;
import org.set.tokens.Token;
import org.set.boardPieces.HexagonGameBoard;
import org.set.boardPieces.Tile;
import org.set.boardPieces.Util;
import org.set.cards.Card;
import org.set.cards.expedition.ExpeditionCard;
import org.set.marketplace.MarketPlace;

public class GameController {
    private HexagonGameBoard board;
    private List<Player> players;
    public static Map<String,Cave> caveMap;
    private String GameState;
    public int turnCounter;
    public MarketPlace market=new MarketPlace();
    public GameController(HexagonGameBoard board) {
        this.board = board;
        GameState = "Game Starts";
        caveMap=Before_game.allocateTokens(board);
        //During_game.removeblock(board);
        
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
    	HashMap<Card, Integer> marketoption = market.getMarketBoardOptions();
    	for (Map.Entry<Card, Integer> entry : marketoption.entrySet()) {
    	    entry.setValue(3); // Set every value to 3, because it doesn't make sense when it's not on market, the value is 1
    	    //there are always 3 cards, doesn't matter if you put the pile on market or not
    	}
        board.market=market;
    }
    
    
    private void PlayerDrawCards(int turn, int currentPlayerIndex, Player player) {

    	board.currentPlayer=player;
        System.out.println("Player " + (currentPlayerIndex + 1) + " drawing cards");
        player.myDeck.draw(4 - player.myDeck.getCardsInHand().size());
        board.repaint();
    }

    public void fillEmptyMarketSpace() {
    	HashMap<Card, Integer> marketoption = market.getMarketBoardOptions();
    	HashMap<Card, Integer> currentMarket = market.getCurrentMarketBoard();
    	List<Card> cardList = new ArrayList<>(marketoption.keySet());
    	if(currentMarket.size()<6) {
    		boolean fillEmptyMarket=InputHelper.getYesNoInput("There's empty space on market, do you fill in with any pile?");
    		if(fillEmptyMarket) {
    			int marketOptionIndex = InputHelper.getIntInput("Choose 1 card pile to fill, input index (e.g. 0), or '-1' to stop",cardList.size()-1);
    			if (marketOptionIndex == -1) {
    	            return;
    	        }
    			Card thePile=cardList.get(marketOptionIndex);
    			market.moveOptionToCurrentMarket(thePile);
    			board.repaint();
    		}
    	}
    	}

    public void PlayerBuy(Player player) {
    	List<Card> mydeck=player.myDeck.getCardsInHand();
    	double totalGold=0;
    	for(int i = 0; i < mydeck.size(); i++) {
    		totalGold+=mydeck.get(i).cost;
    	}
    	System.out.println("You have "+totalGold+" gold now.");
    	while(totalGold>0){
        	boolean toBuyCard=InputHelper.getYesNoInput("Do you want to buy any cards?");
        	if(!toBuyCard) {break;}
        	fillEmptyMarketSpace();
        	Map<Card, Integer> currentMarketBoard = market.getCurrentMarketBoard();
        	List<Card> cardList = new ArrayList<>(currentMarketBoard.keySet());
        	int cardIndex = InputHelper.getIntInput("Choose 1 card to buy, input index (e.g. 0), or '-1' to stop buying",cardList.size()-1);
	        if (cardIndex == -1) {
	            break;
	        } Card theCard=cardList.get(cardIndex);
	        if(currentMarketBoard.get(theCard)==0) {
	        	System.out.println("There's no card of this type in the market, choose another card.");
	        }
	        else if(totalGold<theCard.cost){
	        	System.out.println("You do not have enough gold for this card, choose another card.");
	        	continue;
	        }

        	double buyingGold=0;
        	List<Integer> indices=new ArrayList<>();
	        while(buyingGold<theCard.cost){
	        	indices.clear();
	        	indices = InputHelper.getIntListInput("Please enter indices for your buying cards, separated by commas, or 'stop' to choose another card ", cardList.size()-1);
	        	 if (indices != null && indices.size() == 1 && indices.get(0) == -1) {
	        		 indices.clear();
	                 break;
	             }
	        	for(int i = 0; i < mydeck.size(); i++) {
	        		if (indices.contains(i)) {
	        	        Card card = mydeck.get(i);
	        	        buyingGold += card.cost; 
	        		}
	        	}
	        	if(buyingGold<cardList.get(cardIndex).cost) {
	        	System.out.println("This is "+buyingGold+" gold, that's not enough.");
	        	chooseToken(player);
	        	}
	        }
	        if(indices.size()>0) {
	        player.myDeck.discard(theCard);	
	        System.out.println("This card has been placed on your discard pile to be reshuffled into the draw pile.");
	        currentMarketBoard.put(theCard, currentMarketBoard.get(theCard)-1);
	        if(currentMarketBoard.get(theCard)==0) {
	        	currentMarketBoard.remove(theCard);}
        	List<Card> toDiscard=new ArrayList<>();
	        for(int i=0; i<indices.size();i++) {
	        Card toBeDiscard=player.myDeck.getCardsInHand().get(indices.get(i));
	        toDiscard.add(toBeDiscard);
	        }player.myDeck.discard(toDiscard);
        	break;}
    	}
    	board.discardPhase=true;
    	board.currentPlayer=player;
    	board.market=market;
        board.repaint();
        
    }
    
    public void chooseToken(Player player) {
    	//use coin token for gold
    }
    public void PlayerDiscard(Player player) {
    	board.currentPlayer=player;
        while(player.myDeck.getCardsInHand().size()>0){
        	boolean discardCard=InputHelper.getYesNoInput("Do you want to discard any cards?");
        	if(!discardCard) {break;}
        	int cardIndex = InputHelper.getIntInput("Choose 1 card to discard, input index (e.g. 0), or '-1' to stop discarding",player.myDeck.getCardsInHand().size()-1);
	        if (cardIndex == -1) {
	            break;
	        }
	        player.myDeck.discardFromHand(cardIndex);
	        board.repaint();
        }
        board.discardPhase=false;
    }

    public void GameSession() {
        int turnNumber = 0;

        while (true) {
            for (int currentPlayerIndex = 0; currentPlayerIndex < players.size(); currentPlayerIndex++) {
                Player currentPlayer = players.get(currentPlayerIndex);
                System.out.println("Turn " + turnNumber + ": Player " + currentPlayer.getName() + "'s turn.");
                PlayerDrawCards(turnNumber, currentPlayerIndex, currentPlayer);
//                During_game.PlayerMove(board,currentPlayer);

              int[] position = InputHelper.getPositionInput(board);

              currentPlayer.setPlayerPosition(position[0], position[1]);

              board.repaint();
              During_game.caveExplore(board, currentPlayer);
              displayMarketInfo();
              PlayerBuy(currentPlayer);
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
