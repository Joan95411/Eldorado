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
import org.set.cards.expedition.ExpeditionCard;
import org.set.marketplace.MarketPlace;

public class GameController {
    private Template board;
    private List<Player> players;
    public static Map<String,Cave> caveMap;
    private boolean isGameOver = false;
    public int turnCounter;
    public MarketPlace market=new MarketPlace();
    public GameController(Template board2) {
        this.board = board2;
        caveMap=Before_game.allocateTokens(board2);
//        During_game.removeblock(board2);
        
        players = Before_game.addPlayer(board2);
        Before_game.placePlayersOnBoard(board2);
        displayMarketInfo();
        GameSession();
    }
    
    
    
    private void displayMarketInfo() {
    	HashMap<Card, Integer> marketoption = market.getMarketBoardOptions();
    	for (Map.Entry<Card, Integer> entry : marketoption.entrySet()) {
    	    entry.setValue(3); // Set every value to 3, because it doesn't make sense when it's not on market, the value is 1
    	    //there are always 3 cards, doesn't matter if you put the pile on market or not
    	}
        board.market=market;
    }
    
    
    private void PlayerDrawCards(Player player) {

    	board.currentPlayer=player;
        System.out.println("Player " + player.getName() + " drew cards");
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
    	List<Asset> assets = player.myDeck.getMyasset();
    	double totalGold = player.myDeck.getTotalValue();
    	System.out.println("You have "+totalGold+" gold now.");
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
	        else if(totalGold<theCard.getCost()){
	        	System.out.println("You do not have enough gold for this card, choose another card.");
	        	continue;
	        }

	        double buyingGold = 0;
	        List<Integer> assetIndices = new ArrayList<>();

	        while (buyingGold < theCard.getCost()) {
	            assetIndices.clear();
	            assetIndices = InputHelper.getIntListInput("Please enter indices for your buying assets, separated by commas, or 'stop' to stop this transaction", assets.size() - 1);
	            if (assetIndices != null && assetIndices.size() == 1 && assetIndices.get(0) == -1) {
	                assetIndices.clear();
	                break;
	            }
	            buyingGold=player.myDeck.getSelectedValue(assetIndices);
	            System.out.println("You gathered " + buyingGold + " gold so far for the buy.");
	            
	            if (buyingGold >= theCard.getCost()) {
	                break;
	            } else {
	                System.out.println("You do not have enough assets to buy this card. Please choose more assets.");
	            }
	        }

	        if (buyingGold >= theCard.getCost()) {
	            player.myDeck.discard(theCard);
	            System.out.println("Deal! This card has been placed on your discard pile to be reshuffled into the draw pile.");
	            currentMarketBoard.put(theCard, currentMarketBoard.get(theCard) - 1);
	            if (currentMarketBoard.get(theCard) == 0) {
	                currentMarketBoard.remove(theCard);
	            }
	            // Handle discarding assets
	            Collections.sort(assetIndices, Collections.reverseOrder());
	            for (int i : assetIndices) {
	                player.myDeck.discardAsset(i);
	            }
	            break;
	        }
	    }

	    board.discardPhase = true;
	    board.currentPlayer = player;
	    board.market = market;
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
        int currentPlayerIndex = 0;
        while (!isGameOver) {
            for (currentPlayerIndex = 0; currentPlayerIndex < players.size(); currentPlayerIndex++) {
                Player currentPlayer = players.get(currentPlayerIndex);
                System.out.println("Turn " + turnNumber + ": Player " + currentPlayer.getName() + "'s turn.");
                PlayerDrawCards(currentPlayer);
                During_game.PlayerMove2(board,currentPlayer);
                
//                int[] position = InputHelper.getPositionInput(board);
//                currentPlayer.setPlayerPosition(position[0], position[1]);
                
                if(isThereAwinnier(currentPlayer)) {
                	System.out.println("Final Round!");	
                	isGameOver = true;
                	break;
                }
//              board.repaint();
              PlayerBuy(currentPlayer);
              PlayerDiscard(currentPlayer);
              boolean discardCard=InputHelper.getYesNoInput("Your turn will finish now.");
              board.discardPhase=false;
              if (isGameOver) break;
            }

            turnNumber++;
            List<Player> restPlayers=new ArrayList<>(players);
        	restPlayers.remove(currentPlayerIndex);
            if (isFinalRound(restPlayers)) {
            	isGameOver = true;
                break;
            }
        }

        EndGame();
    }

    public boolean isThereAwinnier(Player player) {
        
    	String targetKey = player.getCurrentRow() + "," + player.getCurrentCol();
        Tile PlayerStandingTile = board.ParentMap.get(targetKey);
        boolean isWinning=board.isWinning(PlayerStandingTile);
        if(isWinning) {
        	WinningPiece wp=board.getAllWinningPieces().get(0);
        	for(Tile tile:wp.getTiles()) {
        		if(tile.getType()==TileType.Eldorado) {
        			player.setPlayerPosition(tile.getRow(), tile.getCol());
        			System.out.println("You've reached the ending tile!");	
        			break;
        		}
        	}
            if(players.indexOf(player)==players.size()-1) {
            	EndGame();
            }
        return true;}
        else {return false;}
        
    }

    private boolean isFinalRound(List<Player> restPlayers) {
    	for (int i = 0; i < restPlayers.size(); i++) {
    		Player currentPlayer=restPlayers.get(i);
    		System.out.println("Final round: Player " + currentPlayer.getName() + "'s turn.");
            PlayerDrawCards(currentPlayer);
            During_game.PlayerMove2(board,currentPlayer);
//            int[] position = InputHelper.getPositionInput(board);
//            currentPlayer.setPlayerPosition(position[0], position[1]);
            if(isThereAwinnier(currentPlayer)) {
            	System.out.println("Another potential winner!");
            	isGameOver = true;
            	return true;
            }
            PlayerBuy(currentPlayer);//final round still need to buy cards?
            PlayerDiscard(currentPlayer);
            boolean discardCard=InputHelper.getYesNoInput("Your turn will finish now.");
            board.discardPhase=false;
    	}
        return true;
    }

    private void EndGame() {
    	
        InputHelper.closeScanner();
        System.out.println("Game Over!");
        isGameOver = true;
        
        //to be implemented, why final round? if there's already a winner
    }
}
