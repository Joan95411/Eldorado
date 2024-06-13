package org.set.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.set.boardPieces.Blockade;
import org.set.boardPieces.Tile;
import org.set.boardPieces.Util;
import org.set.cards.Card;
import org.set.cards.expedition.ExpeditionCard;
import org.set.player.Asset;
import org.set.player.Player;
import org.set.template.Template;
import org.set.tokens.Cave;
import org.set.tokens.Token;

public class During_game {
	
	public static void caveExplore(Template board,Player player) {
		//Your piece must stop there, you can't explore while passing a cave.
		String targetKey = player.getCurrentRow() + "," + player.getCurrentCol();
        Tile PlayerStandingTile = board.ParentMap.get(targetKey);
        String AroundPlayerCave=board.nextToCave(PlayerStandingTile);
        
        if(AroundPlayerCave==null) {
        	player.setLastActionCaveExplore(false);
        }else if (player.isLastActionCaveExplore()) {
	        System.out.println("You cannot explore a cave twice in a row.");
	        return;
	    }
        else {	
        		System.out.println("You're standing next to a cave, You can explore.");
        		Cave correspondingCave = GameController.caveMap.get(AroundPlayerCave);
        	    System.out.println("Adjacent to cave: " + correspondingCave.tile);
        	    Token token=correspondingCave.getAtoken();
        	    if(token!=null) {
	        	player.myDeck.addToken(token);
        		player.setLastActionCaveExplore(true);
        		board.repaint();}
        	    else {
        	    	System.out.println("There's no more tokens at this cave.");
        	    }
            } 
        }
            
	public static void PlayerMove2(Template board, Player player) {
	    
	    
	    while (player.myDeck.getMyasset().size() > 0) {
	        boolean keepMoving = InputHelper.getYesNoInput("Do you want to make a movement?");
	        if (!keepMoving) {
	            break; // Exit the loop if the user doesn't want to keep moving
	        }

	        int index = InputHelper.getIntInput("Choose 1 card/token for Movement, input index (e.g. 0), or '-1' to stop with movement", player.myDeck.getMyasset().size() - 1, -1);
	        if (index == -1) {
	            break;
	        }

	        singleMove2(board, player, player.myDeck.getMyasset(), index);
	    }

	    System.out.println("You have still " + player.myDeck.getCardsInHand().size() + " cards for this turn.");
	}

	public static void singleMove2(Template board, Player player, List<Asset> myAsset, int index) {
	    Asset selectedMovable = myAsset.get(index);
	    System.out.println("You chose the " + selectedMovable);
	    int residualPower = selectedMovable.getPower();
	    
	    while (residualPower > 0) {
	        System.out.println("You have residual Power of " + residualPower);
	        String targetKey = player.getCurrentRow() + "," + player.getCurrentCol();
	        Tile playerStandingTile = board.ParentMap.get(targetKey);
	        int[] movingToInt = InputHelper.getPlayerMoveInput(board, playerStandingTile);
	        if (movingToInt[0] == -100) {
	            boolean discardMovable = InputHelper.getYesNoInput("Are you sure to discard this " + selectedMovable + "?");
	            if (discardMovable) {
	                residualPower = 0;
	                break;
	            } else if(selectedMovable.getPower()>residualPower) {
	                System.out.println("You can discard the " + selectedMovable + " or make a movement with the remaining power.");
	                continue;
	            } else {
            			System.out.println("You didn't use the "+ selectedMovable );
            			break;}
	        }
	        else if(movingToInt[0] == -200) {
	        	int NextToBlockade=board.nextToBlockade(playerStandingTile);
	        	Blockade block=(Blockade) board.boardPieces.get("Blockade_"+NextToBlockade);
	        	if (Util.getColorFromString(selectedMovable.getCardType().toString()).equals(block.getColor())) {
		            if (residualPower >= block.getPoints()) {
		                board.removeBlockade(NextToBlockade);
		                residualPower -= block.getPoints();
		                board.repaint();
		                caveExplore(board, player);
		            } else {
		                System.out.println("Your " + selectedMovable + " does not have enough power to move here.");
		            }
		        } else {
		            System.out.println("Color of " + selectedMovable + " does not match tile color.");
		        }
	        }
	        else {
	        Tile movingTo=board.ParentMap.get(movingToInt[0]+","+movingToInt[1]);
	        if (Util.getColorFromString(selectedMovable.getCardType().toString()).equals(movingTo.getColor())) {
	            if (residualPower >= movingTo.getPoints()) {
	                player.setPlayerPosition(movingTo.getRow(), movingTo.getCol());
	                residualPower -= movingTo.getPoints();
	                board.repaint();
	                caveExplore(board, player);
	            } else {
	                System.out.println("Your " + selectedMovable + " does not have enough power to move here.");
	            }
	        } else {
	            System.out.println("Color of " + selectedMovable + " does not match tile color.");
	        }
	    }}
	    if (residualPower == 0) {
	        player.myDeck.discardAsset(index);
	        board.repaint();
	    }
	}

	
	
}
