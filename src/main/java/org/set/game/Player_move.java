package org.set.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.set.boardPieces.Blockade;
import org.set.boardPieces.Tile;
import org.set.boardPieces.TileType;
import org.set.boardPieces.Util;
import org.set.cards.Card;
import org.set.cards.CardType;
import org.set.cards.expedition.ExpeditionCard;
import org.set.player.Asset;
import org.set.player.Player;
import org.set.template.Template;
import org.set.tokens.Cave;
import org.set.tokens.Token;

public class Player_move {
	public static Map<Tile, Cave> caveMap;
	public static void caveExplore(Template board,Player player) {
		//Your piece must stop there, you can't explore while passing a cave.
		String targetKey = player.getCurrentRow() + "," + player.getCurrentCol();
        Tile PlayerStandingTile = board.ParentMap.get(targetKey);
        Tile AroundPlayerCave=board.nextToCave(PlayerStandingTile);
        
        if(AroundPlayerCave==null) {
        	player.setLastActionCaveExplore(false);
        }else if (player.isLastActionCaveExplore()) {
	        System.out.println("You cannot explore a cave twice in a row.");
	        return;
	    }
        else {	
        		System.out.println("You're standing next to a cave, You can explore. ");
        		Cave correspondingCave = caveMap.get(AroundPlayerCave);
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
	        
	        int index = InputHelper.getIntInput("Choose 1 card/token for Movement; "
	        		+ "Input index (e.g. 0), or '-1' to stop with movement", player.myDeck.getMyasset().size() - 1, -1);
	        if (index == -1) {
	            break;
	        }
	        
	        singleMove2(board, player,  index);
	    }

	    System.out.println("You have still " + player.myDeck.getCardsInHand().size() + " cards for this turn.");
	    
	}
	
	private static List<Card> chooseCardsToDiscard(Player player){
		List<Integer> indexs = InputHelper.getIntListInput("Choose cards indices to discard;"
				+ " Separate by comma, (e.g. 0,1,2);"
				+ " Or 'stop' to stop the move", player.myDeck.getCardsInHand().size() - 1);
        
        if (indexs.get(0) == -1) {
            return null;
        }
		List<Card> toDiscard=new ArrayList<>();
		for (int i : indexs) {toDiscard.add(player.myDeck.getCardsInHand().get(i));  }
		return toDiscard;
	}
	
	public static int executeMove(Template board, Player player, Tile movingTo, int residualPower) {
        player.setPlayerPosition(movingTo.getRow(), movingTo.getCol());
        board.repaint();
        caveExplore(board, player);
        residualPower -= movingTo.getPoints();
        return residualPower;
    }
	public static int executeRemoveBlock(Template board, Player player, int NextToBlockade,Blockade block, int residualPower) {
        board.removeBlockade(NextToBlockade);
        player.myDeck.earnBlockade(block);
        System.out.println("You have the blockade in your possession now");
        board.repaint();
        caveExplore(board, player);
        residualPower -= block.getPoints();
        return residualPower;
    }
	public static void singleMove2(Template board, Player player,  int index) {
		List<Asset> myAsset=player.myDeck.getMyasset();
	    Asset selectedAsset = myAsset.get(index);
	    System.out.println("You chose the " + selectedAsset);
	    int residualPower = selectedAsset.getPower();
	    
	    while (residualPower > 0) {
	        System.out.println("You have residual Power of " + residualPower);
	        String targetKey = player.getCurrentRow() + "," + player.getCurrentCol();
	        Tile playerStandingTile = board.ParentMap.get(targetKey);
	        int[] movingToInt = InputHelper.getPlayerMoveInput(board, playerStandingTile);
	        
	        if (movingToInt[0] == -100) {
	        	if(selectedAsset.getPower()==residualPower) {
        			System.out.println("You can keep the "+ selectedAsset +" and choose another card.");
        			break;}
	            boolean discardMovable = InputHelper.getYesNoInput("Are you sure to discard this " + selectedAsset + "?");
	            if (discardMovable) {
	                residualPower = 0;
	                break;
	            } else if(selectedAsset.getPower()>residualPower) {
	                System.out.println("You can discard the " + selectedAsset + " or make a movement with the remaining power.");
	                continue;
	            } 
	        }
	        
	        else if(movingToInt[0] == -200) {
	        	int NextToBlockade=board.nextToBlockade(playerStandingTile);
	        	if(NextToBlockade==-1) {System.out.println("You're not next to a blockade.");}
	        	else {
	        	Blockade block=(Blockade) board.boardPieces.get("Blockade_"+NextToBlockade);
	        	
	        	if (Util.getColorFromString(selectedAsset.getCardType().toString()).equals(block.getColor())
	        			||selectedAsset.getCardType().equals(CardType.JOKER)
	        			) {
		            if (residualPower >= block.getPoints()) {
		                residualPower=executeRemoveBlock(board, player,NextToBlockade, block, residualPower);
		                
		            } else {
		                System.out.println("Your " + selectedAsset + " does not have enough power to move blockade.");
		            }
		        } else if(block.getTiles().getFirst().getTileType()==TileType.BaseCamp
	        			||block.getTiles().getFirst().getTileType()==TileType.Discard){
		        	if(block.getPoints()==1) {
		        		boolean discardMovable = InputHelper.getYesNoInput("Are you sure to discard this " + selectedAsset + "?");
			            if (discardMovable) {
			            	executeRemoveBlock(board, player,NextToBlockade, block, residualPower);}
			            
		        	}else {System.out.println("You need to discard more card to get here.");
		        	List<Card> toDiscard=chooseCardsToDiscard(player);
			        if(toDiscard.size()>=block.getPoints()) {
			        	executeRemoveBlock(board, player,NextToBlockade, block, residualPower);
		        	}else {
		        		System.out.println("You need to discard more card to remove this block.");}
			        if(block.getTiles().getFirst().getTileType()==TileType.Discard) {
			        	player.myDeck.discard(toDiscard);
		        	}else {player.myDeck.removeCard(toDiscard);}
			        board.repaint();
			        residualPower=-1;}
		        	
		        }else {
		            System.out.println("Color of " + selectedAsset + " does not match blockade color.");
		            
		           }
	        	}
	        }
	        
	        else {
	        Tile movingTo=board.ParentMap.get(movingToInt[0]+","+movingToInt[1]);
	        if (Util.getColorFromString(selectedAsset.getCardType().toString()).equals(movingTo.getColor())
	        		||selectedAsset.getCardType().equals(CardType.JOKER)) {
	            if (residualPower >= movingTo.getPoints()) {
	            	residualPower= executeMove( board,  player, movingTo,residualPower);
	            } 
	            	else {
	                System.out.println("Your " + selectedAsset + " does not have enough power to move here.");
	            }
	        }else if(movingTo.getTileType()==TileType.BaseCamp
        			||movingTo.getTileType()==TileType.Discard){
	        	if(movingTo.getPoints()==1) {
	        		boolean discardMovable = InputHelper.getYesNoInput("Are you sure to discard this " + selectedAsset + "?");
		            if (discardMovable) {
		            	executeMove( board,  player, movingTo,residualPower);}
	        	}else {System.out.println("You need to discard more card to get here.");
	        	List<Card> toDiscard=chooseCardsToDiscard(player);
		        if(toDiscard.size()>=movingTo.getPoints()) {
		        	executeMove( board,  player, movingTo,residualPower);
	        	}else {
	        		System.out.println("You need to discard more card to get here.");}
		        if(movingTo.getTileType()==TileType.Discard) {
		        	player.myDeck.discard(toDiscard);
	        	}else {player.myDeck.removeCard(toDiscard);}
		        board.repaint();
		        residualPower=-1;}
	        }
	        else {
	            System.out.println("Color of " + selectedAsset + " does not match tile color."+selectedAsset.getCardType());
	        }
	    }}
	    if (residualPower == 0) {
	        player.myDeck.discardAsset(index);
	        board.repaint();
	    }
	}

	

	

	
}
