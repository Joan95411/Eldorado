package org.set.game;

import java.awt.Color;
import java.util.List;
import java.util.Set;

import org.set.boardPieces.Blockade;
import org.set.boardPieces.HexagonGameBoard;
import org.set.boardPieces.Tile;
import org.set.boardPieces.Util;
import org.set.cards.Card;
import org.set.cards.expedition.ExpeditionCard;
import org.set.player.Player;
import org.set.tokens.Cave;
import org.set.tokens.Token;

public class During_game {
	
	public static void caveExplore(HexagonGameBoard board,Player player) {
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
	        	player.addToken(token);
        		player.setLastActionCaveExplore(true);
        		board.repaint();}
        	    else {
        	    	System.out.println("There's no more tokens at this cave.");
        	    }
            } 
        }
            
    
	
	public static void PlayerMove(HexagonGameBoard board, Player player) {
	    List<Card> currentDeck = player.myDeck.getCardsInHand();
	    while (currentDeck.size()>0) {//expedition card size >0
	        boolean keepMoving = InputHelper.getYesNoInput("Do you want to make a movement?");
	        if (!keepMoving) {
	            break; // Exit the loop if the user doesn't want to keep moving
	        }
	        int cardIndex = InputHelper.getIntInput("Choose 1 card for Movement, input index (e.g. 0), or '-1' to stop with movement",currentDeck.size()-1);
	        if (cardIndex == -1) {
	            break;
	        }
	        if (cardIndex >= currentDeck.size()) {
	            System.out.println("Please enter a number between 0 to " + (currentDeck.size() - 1));
	            continue;
	        }

	        singleMove(board, player, currentDeck, cardIndex);
	    }
	    System.out.println("You have still "+player.myDeck.getCardsInHand().size()+" cards for this turn.");
	}

	public static void singleMove(HexagonGameBoard board, Player player, List<Card> currentDeck, int cardIndex) {
	    Card selectedCard = currentDeck.get(cardIndex);

	    if (!(selectedCard instanceof ExpeditionCard)) {
	        System.out.println("Please select an Expedition card.");
	        return;
	    }

	    ExpeditionCard expeditionCard = (ExpeditionCard) selectedCard;
	    int residualPower = expeditionCard.getPower();
	    while (residualPower > 0) {
	        System.out.println("You chose the " + selectedCard.cardType.toString() + " card with Power of " + residualPower);
	        String targetKey = player.getCurrentRow() + "," + player.getCurrentCol();
	        Tile playerStandingTile = board.ParentMap.get(targetKey);
	        Tile movingTo = InputHelper.getPlayerMoveInput(board, playerStandingTile);
	        if (movingTo.getRow() == -100) {
	            boolean discardCard = InputHelper.getYesNoInput("Are you sure to discard this card?");
	            if (discardCard) {residualPower=0;
	                break;
	            }else if(expeditionCard.getPower()>residualPower) {
	            	System.out.println("You can discard the card or make a movement with the remaining power."); 
	            		continue; }else {
	            			System.out.println("You didn't use the"+ selectedCard.cardType.toString() +" card.");
	            			break;}
	        }
	        Color cardColor = Util.getColorFromString(selectedCard.cardType.toString());
	        if (cardColor.equals(movingTo.getColor())) {
	            if (expeditionCard.getPower() >= movingTo.getPoints()) {
	                player.setPlayerPosition(movingTo.getRow(), movingTo.getCol());
	                residualPower -= movingTo.getPoints();
	                board.repaint();
	                caveExplore(board, player);
	            } else {
	                System.out.println("Your card does not have enough power to move here.");
	            }
	        } else {
	            System.out.println("Card color does not match tile color.");
	        }
	    }
	    if(residualPower==0) {
	    player.myDeck.discardFromHand(cardIndex);
	    board.repaint();}
	}

	
	public static void removeblock(HexagonGameBoard board) {
		while (true) {
			boolean wantsToContinue = InputHelper.getYesNoInput("Do you want to remove block?");
			
			if (wantsToContinue == false) {
				break;
			}
			
			int minIndex = 100;
			List<Blockade> blocks = board.getAllBlockades();
			
			if (blocks.size() > 0) {
				for (Blockade block : blocks) {
					String name = block.getName();
					int index = Integer.parseInt(name.substring("Blockade_".length()));

					if (index < minIndex) {
						minIndex = index;
					}
				}
			} else {
				System.out.println("Sorry no more blockade left");
			}

			board.removeBlockade(minIndex);

			board.repaint();
		}
	}
}
