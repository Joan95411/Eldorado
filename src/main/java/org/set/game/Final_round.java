package org.set.game;

import java.util.ArrayList;
import java.util.List;

import org.set.boardPieces.Tile;
import org.set.boardPieces.TileType;
import org.set.boardPieces.WinningPiece;
import org.set.player.Player;
import org.set.template.Template;

public class Final_round {
	
public static boolean isThereAwinnier(Template board,Player player) {
        
    	String targetKey = player.getCurrentRow() + "," + player.getCurrentCol();
        Tile PlayerStandingTile = board.ParentMap.get(targetKey);
        boolean isWinning=board.isWinning(PlayerStandingTile);
        
        if(isWinning) {
        	WinningPiece wp=board.getAllWinningPieces().get(0);
        	for(Tile tile:wp.getTiles()) {
        		if(tile.getType()==TileType.Eldorado&&board.isValidPosition(tile.getRow(), tile.getCol())) {
        			player.setPlayerPosition(tile.getRow(), tile.getCol());
        			System.out.println("You've reached the ending tile!");	
        			player.setWinner(true);
        			break;
        		}
        	}
               return true;}
        else {return false;}
        
    }
public static void FinalRound(Template board,int FirstWinningIndex,List<Player> players) {
	for (int i = 0; i < FirstWinningIndex; i++) {
		Player currentPlayer=players.get(i);
		System.out.println("Final round: Player " + currentPlayer.getName() + "'s turn.");
		Player_draw_discard.PlayerDrawCards(board,currentPlayer);
        Player_move.PlayerMove2(board,currentPlayer);
        if(isThereAwinnier(board,currentPlayer)) {
        	System.out.println("Another potential winner!");
        	
        	continue;
        }
//        Player_buy.PlayerBuy(board,currentPlayer,market);//final round still need to buy cards?
//        Player_draw_discard.PlayerDiscard(board,currentPlayer);
        boolean discardCard=InputHelper.getYesNoInput("Your turn will finish now.");
        board.discardPhase=false;
	}
	System.out.println("Game Over!");
    
}
public static void EndGame(List<Player> players,int FirstWinningIndex) {
	int maxPoints = 0;
	int index=-1;
    InputHelper.closeScanner();
    for(Player player:players) {
    	if(player.getWinner()) {
    		int points=player.myDeck.calculateBlockPoint();
    		if (points > maxPoints) {
    			maxPoints = points;
    			index=players.indexOf(player);
            }
    	}
    System.out.println("The winner is "+players.get(index));
    }
    
    
    //to be implemented, why final round? if there's already a winner
}
}
