package org.set.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.set.player.Player;
import org.set.template.Template;
import org.set.tokens.Cave;
import org.set.tokens.Token;
import org.set.boardPieces.Tile;
import org.set.boardPieces.Util;
import org.set.cards.Card;
import org.set.cards.expedition.ExpeditionCard;
import org.set.marketplace.MarketPlace;

public class GameController {
    private Template board;
    private List<Player> players;
    public static Map<String,Cave> caveMap;
    private String GameState;
    public int turnCounter;
    public MarketPlace market=new MarketPlace();
    public GameController(Template board2) {
        this.board = board2;
        GameState = "Game Starts";
        caveMap=Before_game.allocateTokens(board2);
//        During_game.removeblock(board);
        
        players = Before_game.addPlayer(board2);
        Before_game.placePlayersOnBoard(board2);
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
    			int marketOptionIndex = InputHelper.getIntInput("Choose 1 card pile to fill, input index (e.g. 0), or '-1' to stop",cardList.size()-1,-1);
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
    	double totalGold=player.myDeck.getValue();
    	System.out.println("You have "+(totalGold+player.getTokenValue())+" gold now.");
    	while(totalGold>0){
        	boolean toBuyCard=InputHelper.getYesNoInput("Do you want to buy any cards?");
        	if(!toBuyCard) {break;}
        	fillEmptyMarketSpace();
        	Map<Card, Integer> currentMarketBoard = market.getCurrentMarketBoard();
        	List<Card> cardList = new ArrayList<>(currentMarketBoard.keySet());
        	int cardIndex = InputHelper.getIntInput("Choose 1 card to buy, input index (e.g. 0), or '-1' to stop buying",cardList.size()-1,-1);
	        if (cardIndex == -1) {
	            break;
	        } Card theCard=cardList.get(cardIndex);
	        if(currentMarketBoard.get(theCard)==0) {
	        	System.out.println("There's no card of this type in the market, choose another card.");
	        }
	        else if(totalGold+player.getTokenValue()<theCard.getCost()){
	        	System.out.println("You do not have enough gold for this card, choose another card.");
	        	continue;
	        }

        	double buyingGold=0;
        	List<Integer> cardIndices=new ArrayList<>();
        	List<Integer> tokenIndices=new ArrayList<>();
	        while(buyingGold<theCard.getCost()){
	        	cardIndices.clear();
	        	cardIndices = InputHelper.getIntListInput("Please enter indices for your buying cards, separated by commas, or 'skip' to use tokens to buy, or 'stop' to stop this transaction ", cardList.size()-1);
	        	 if (cardIndices != null && cardIndices.size() == 1 && cardIndices.get(0) == -1) {
	        		 cardIndices.clear();
	                 break;
	             }else if(cardIndices.get(0) != -2) {
	        	for(int i = 0; i < mydeck.size(); i++) {
	        		if (cardIndices.contains(i)) {
	        	        Card card = mydeck.get(i);
	        	        buyingGold += card.getValue(); 
	        		}
	        	}System.out.println("You gathered " + buyingGold + " gold so far for the buy.");}
	        	 
	        	if (buyingGold < theCard.getCost() && player.getTokenValue() > 0) {
	                
	                tokenIndices = InputHelper.getIntListInput("If you want to use your tokens, please enter indices for them, separated by commas, or 'stop' to stop this transaction", player.getTokens().size() - 1);
	                for (int i : tokenIndices) {
	                    buyingGold += player.getTokens().get(i).getValue();  
	                }
	            }else {
	            	System.out.println("You do not have enough token that can buy this card.");
	            }
	        }

	        if (cardIndices.size() > 0 || tokenIndices.size() > 0) {
	        player.myDeck.discard(theCard);	
	        System.out.println("Deal! This card has been placed on your discard pile to be reshuffled into the draw pile.");
	        currentMarketBoard.put(theCard, currentMarketBoard.get(theCard)-1);
	        if(currentMarketBoard.get(theCard)==0) {
	        	currentMarketBoard.remove(theCard);}
	        List<Card> toDiscard = new ArrayList<>();
            for (int i : cardIndices) {
                toDiscard.add(mydeck.get(i));
            }
            player.myDeck.discard(toDiscard);

            // Handle discarding tokens
            for (int i : tokenIndices) {
                player.discardToken(i);
            }
            break;}
    	}
    	board.discardPhase=true;
    	board.currentPlayer=player;
    	board.market=market;
        board.repaint();
        
    }
    
    
    public void PlayerDiscard(Player player) {
    	board.currentPlayer=player;
        while(player.myDeck.getCardsInHand().size()>0){
        	boolean discardCard=InputHelper.getYesNoInput("Do you want to discard any cards?");
        	if(!discardCard) {break;}
        	int cardIndex = InputHelper.getIntInput("Choose 1 card to discard, input index (e.g. 0), or '-1' to stop discarding",player.myDeck.getCardsInHand().size()-1,-1);
	        if (cardIndex == -1) {
	            break;
	        }
	        player.myDeck.discardFromHand(cardIndex);
	        board.repaint();
        }
        
    }

    public void GameSession() {
        int turnNumber = 0;

        while (true) {
            for (int currentPlayerIndex = 0; currentPlayerIndex < players.size(); currentPlayerIndex++) {
                Player currentPlayer = players.get(currentPlayerIndex);
                System.out.println("Turn " + turnNumber + ": Player " + currentPlayer.getName() + "'s turn.");
                PlayerDrawCards(turnNumber, currentPlayerIndex, currentPlayer);
                During_game.PlayerMove(board,currentPlayer);

//              int[] position = InputHelper.getPositionInput(board);
//
//              currentPlayer.setPlayerPosition(position[0], position[1]);
//              During_game.caveExplore(board, currentPlayer);
              board.repaint();
              displayMarketInfo();
              PlayerBuy(currentPlayer);
              PlayerDiscard(currentPlayer);
              boolean discardCard=InputHelper.getYesNoInput("Your turn will finish now.");
              board.discardPhase=false;
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
