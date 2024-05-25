package org.set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.boardPieces.HexagonGameBoard;
import org.set.boardPieces.Terrain;
import org.set.boardPieces.Util;
import org.set.boardPieces.boardPiece;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class HexagonGameBoardTest {
   public static HexagonGameBoard hexagonGameBoard;
   
   @BeforeEach
   void setUp() {
	   hexagonGameBoard = new HexagonGameBoard(15, 35, 30);
   }
   @Test
   public void testHexagonGameBoard() {
       assertNotNull(hexagonGameBoard);
       assertNotNull(hexagonGameBoard.getPreferredSize());

       assertEquals(hexagonGameBoard.numRows, 15);
       assertEquals(hexagonGameBoard.numCols, 35);
       assertEquals(hexagonGameBoard.hexSize, 30);
   }
   @Test
   public void testBoardPieces() {
	   Map<String, boardPiece> bp=hexagonGameBoard.boardPieces;
	   assertNotNull(bp);
	   int countTerrain = hexagonGameBoard.getAllTerrains().size();
	    
	    assertEquals(4, countTerrain, "Actual count: " + countTerrain);
	    int countWinning = hexagonGameBoard.getAllWinningPieces().size();
	    
	    assertEquals(1, countWinning, "WinningPiece Actual count: " + countWinning);
	    
	}
   
   @Test
   public void testLoadTileData() {
	   Map<String, boardPiece> bp=hexagonGameBoard.boardPieces;
	   bp.clear();
	   assertEquals(0,hexagonGameBoard.boardPieces.size(),"current board: " + hexagonGameBoard.boardPieces.size());
	   hexagonGameBoard.loadTileData();
	   assertNotNull(hexagonGameBoard.boardPieces);
	   assertEquals(2,hexagonGameBoard.boardPieces.size(),"current board: " + hexagonGameBoard.boardPieces.size());
	   int countTerrain = hexagonGameBoard.getAllTerrains().size();
	    assertEquals(1, countTerrain, "Actual count: " + countTerrain);
	    int countWinning = hexagonGameBoard.getAllWinningPieces().size();
	    assertEquals(1, countWinning, "Actual count: " + countWinning);
	    hexagonGameBoard.initBoard();
	    int countTerrain1 = hexagonGameBoard.getAllTerrains().size();
	    
	    assertEquals(4, countTerrain1, "Actual count: " + countTerrain1);
	    
	}
   @Test
   public void testAddTerrain() {
	   Map<String, boardPiece> bp=hexagonGameBoard.boardPieces;
	   bp.clear();
	   hexagonGameBoard.loadTileData();
	   Terrain ta=hexagonGameBoard.getAllTerrains().get(0);
	   hexagonGameBoard.addTerrain(0, 8, ta); //blockade auto added
	   int countBlockade=hexagonGameBoard.getAllBlockades().size();
	   assertEquals(1, countBlockade, "Actual count: " + countBlockade);
	   hexagonGameBoard.addTerrain(5, 5, ta); //blockade shouldn't be added
	   countBlockade=hexagonGameBoard.getAllBlockades().size();
	   assertEquals(1, countBlockade, "Actual count: " + countBlockade);
	}
   
   @Test
   public void testHexagonGameBoardLocation() {
       assertEquals(hexagonGameBoard.getLocation(), new Point(0,0));
       hexagonGameBoard.setLocation(new Point(1,1));
       assertEquals(hexagonGameBoard.getLocation(), new Point(1,1));
   }
   

   @Test
   public void testHexagonGameBoardPlayer() {
       Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
       int row = 0;
       int col = 0;
       int maxPlayers = 4;
       hexagonGameBoard.players=new ArrayList<>();
       hexagonGameBoard.repaint();
       
       for (int i = 0; i < maxPlayers; i++) {
    	   
           hexagonGameBoard.players.add(new Player(colors[i]));
           assertEquals(hexagonGameBoard.players.size(), i + 1);

           int playerId = hexagonGameBoard.players.size() - 1;


//            Set the players position and check if it actually does set the position
           hexagonGameBoard.players.get(playerId).setPlayerPosition(row + i, col + i);

           assertEquals(hexagonGameBoard.players.get(playerId).getCurrentCol(), col + i);
           assertEquals(hexagonGameBoard.players.get(playerId).getCurrentRow(), row + i);
       }

//        Check if the players are added
       assertTrue(hexagonGameBoard.players.size() <= maxPlayers);

//        TODO: This should fail because the max amount of players is reached
       hexagonGameBoard.players.add(new Player(colors[0]));
   }

   
}