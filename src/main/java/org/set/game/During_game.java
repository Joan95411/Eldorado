package org.set.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.set.boardPieces.Blockade;
import org.set.boardPieces.Tile;
import org.set.boardPieces.TileType;
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
	    
	    board.discardPhase=true;
	    while (player.myDeck.getMyasset().size() > 0) {
	        boolean keepMoving = InputHelper.getYesNoInput("Do you want to make a movement?");
	        if (!keepMoving) {
	            break; // Exit the loop if the user doesn't want to keep moving
	        }

	        int index = InputHelper.getIntInput("Choose 1 card/token for Movement, input index (e.g. 0), or '-1' to stop with movement, or choose -2 to move to basecamp", player.myDeck.getMyasset().size() - 1, -2);
	        if (index == -1) {
	            break;
	        }
	        if(index==-2) {
	        	baseCampMove(board, player);
	        	continue;
	        }

	        singleMove2(board, player,  index);
	    }

	    System.out.println("You have still " + player.myDeck.getCardsInHand().size() + " cards for this turn.");
	    board.discardPhase=false;
	}

	
	public static void baseCampMove(Template board, Player player) {
		List<Integer> indexs = InputHelper.getIntListInput("Choose cards indices for basecamp movement, separate by comma, (e.g. 0,1,2), or 'stop' to stop the move", player.myDeck.getMyasset().size() - 1);
        
        if (indexs.get(0) == -1) {
            return;
        }
		List<Card> myCards=player.myDeck.getCardsInHand();
		List<Card> toDiscard=new ArrayList<>();
		for (int i : indexs) {
		    if (i >= myCards.size()) {
		        System.out.println("Incorrect index, need to be index from cards in your hand.");
		        return; 
		    }else{toDiscard.add(myCards.get(i));}
		    }
		System.out.println("You have the base camp moving Power of " + indexs.size());
        String targetKey = player.getCurrentRow() + "," + player.getCurrentCol();
        Tile playerStandingTile = board.ParentMap.get(targetKey);
        int[] movingToInt = InputHelper.getPlayerMoveInput(board, playerStandingTile);
        if (movingToInt[0] == -100) {
        	return;
        }
        Tile movingTo=board.ParentMap.get(movingToInt[0]+","+movingToInt[1]);
        if(movingTo.getType()==TileType.BaseCamp||movingTo.getType()==TileType.Discard) {
        	if(indexs.size()>=movingTo.getPoints()) {
        		player.setPlayerPosition(movingTo.getRow(), movingTo.getCol());
                if(movingTo.getType()==TileType.BaseCamp) {
                	player.myDeck.discard(toDiscard);
                }else {
                	player.myDeck.removeCard(toDiscard);
                }
                board.repaint();
                caveExplore(board, player);
        	}else {
        		System.out.println("This base camp has "+movingTo.getPoints()+" points. You need to discard more cards to get here.");
        	}
        }else {
        	System.out.println("This is not a base camp, you can't discard multiple cards to move here.");
        }
	}
	
	
	public static void singleMove2(Template board, Player player,  int index) {
		List<Asset> myAsset=player.myDeck.getMyasset();
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
	        	if(NextToBlockade==-1) {System.out.println("You're not next to a blockade.");}
	        	else {
	        	Blockade block=(Blockade) board.boardPieces.get("Blockade_"+NextToBlockade);
	        	if (Util.getColorFromString(selectedMovable.getCardType().toString()).equals(block.getColor())) {
		            if (residualPower >= block.getPoints()) {
		                board.removeBlockade(NextToBlockade);
		                residualPower -= block.getPoints();
		                player.myDeck.earnBlockade(block);
		                System.out.println("You have the blockade in your possession now");
		                board.repaint();
		                caveExplore(board, player);
		            } else {
		                System.out.println("Your " + selectedMovable + " does not have enough power to move blockade.");
		            }
		        } else {
		            System.out.println("Color of " + selectedMovable + " does not match blockade color.");
		        }}
	        }
	        
	        else {
	        Tile movingTo=board.ParentMap.get(movingToInt[0]+","+movingToInt[1]);
	        if (Util.getColorFromString(selectedMovable.getCardType().toString()).equals(movingTo.getColor())) {
	            if (residualPower >= movingTo.getPoints()) {
	                player.setPlayerPosition(movingTo.getRow(), movingTo.getCol());
	                residualPower -= movingTo.getPoints();
	                board.repaint();
	                caveExplore(board, player);
	            } 
	            	else {
	                System.out.println("Your " + selectedMovable + " does not have enough power to move here.");
	            }
	        }
	        else {
	            System.out.println("Color of " + selectedMovable + " does not match tile color.");
	        }
	    }}
	    if (residualPower == 0) {
	        player.myDeck.discardAsset(index);
	        board.repaint();
	    }
	}
	
	
	public static void removeblock(Template board) {//to be deleted, only for testing
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
