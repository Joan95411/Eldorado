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

public class During_game {
	
	public static void caveExplore(HexagonGameBoard board,Player player) {
		//Your piece must stop there, you can't explore while passing a cave).
		if (player.isLastActionCaveExplore()) {
	        System.out.println("You cannot explore a cave twice in a row.");
	        return;
	    }

		String targetKey = player.getCurrentRow() + "," + player.getCurrentCol();
        Tile PlayerStandingTile = board.ParentMap.get(targetKey);
        Set<Tile> AroundPlayerCave=board.nextToCave(PlayerStandingTile);
        if(AroundPlayerCave.size()>0) {
        	boolean wantsToExplore = InputHelper.getYesNoInput("You're standing next to a cave, Do you want to explore?");
        	if(wantsToExplore) {
        		//get a cave coin
        		System.out.println("You'll get a cave coin here, to be implemented");
        		player.setLastActionCaveExplore(true);
            } else {
                player.setLastActionCaveExplore(false);
            }
        } else {
            player.setLastActionCaveExplore(false);
        }
    }
	
	public static void PlayerMove(HexagonGameBoard board, Player player) {
	    List<Card> currentDeck = player.myDeck.getCardsInHand();
	    while (true) {
	        boolean keepMoving = InputHelper.getYesNoInput("Do you want to make a movement?");
	        if (!keepMoving) {
	            break; // Exit the loop if the user doesn't want to keep moving
	        }
	        int cardIndex = InputHelper.getIntInput("Choose 1 card for Movement, input index (e.g. 0), or '-1' to stop with movement");
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
	            }else  {continue; }
	        }
	        Color cardColor = Util.getColorFromString(selectedCard.cardType.toString());
	        if (cardColor.equals(movingTo.getColor())) {
	            if (expeditionCard.getPower() >= movingTo.getPoints()) {
	                player.setPlayerPosition(movingTo.getRow(), movingTo.getCol());
	                residualPower -= movingTo.getPoints();
	                board.repaint();
	                During_game.caveExplore(board, player);
	            } else {
	                System.out.println("Your card does not have enough power to move here.");
	            }
	        } else {
	            System.out.println("Card color does not match tile color.");
	        }
	    }
	    if(residualPower==0) {
	    player.myDeck.discardFromHand(cardIndex);
	    board.PlayerCards = player.myDeck.getCardsInHand();
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
