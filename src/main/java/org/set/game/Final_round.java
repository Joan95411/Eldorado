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
       
	}
	System.out.println("Game Over!");
    
}
public static List<Integer> EndGame(List<Player> players) {
	 int maxPoints = Integer.MIN_VALUE;
     List<Integer> maxIndexes = new ArrayList<>();

     // Iterate through players to find max points and corresponding indexes
     for (Player player : players) {
         if (player.getWinner()) {
             int points = player.myDeck.calculateBlockPoint();
             if (points > maxPoints) {
                 maxPoints = points;
                 maxIndexes.clear(); // Clear previous max indexes
                 maxIndexes.add(players.indexOf(player));
             } else if (points == maxPoints) {
                 maxIndexes.add(players.indexOf(player));
             }
         }
     }

     if (maxIndexes.isEmpty()) {
         System.out.println("No players found with points.");
     } else {
         System.out.println("The winner is:");
         for (int index : maxIndexes) {
             System.out.println("Player: " + players.get(index).getName());
         }
     }return maxIndexes;}
}
