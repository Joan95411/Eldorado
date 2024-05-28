package org.set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.boardPieces.HexagonGameBoard;
import org.set.boardPieces.Terrain;
import org.set.boardPieces.BoardPiece;
import org.set.boardPieces.Blockade;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link HexagonGameBoard} class.
 */
public class HexagonGameBoardTest {
    private static HexagonGameBoard hexagonGameBoard;
    private int numRows = 15;
    private int numCols = 35;
    private int hexSize = 30;

    /**
     * Creating a new instance of HexagonGameBoard for every test.
     */
   @BeforeEach
   void setUp() {
	   hexagonGameBoard = new HexagonGameBoard(numRows, numCols, hexSize);
       assertNotNull(hexagonGameBoard, "hexagonGameBoard should not be null");
   }

    /**
     * Test that a new HexagonGameBoard can be created and has the expected dimensions.
     */
   @Test
   public void testHexagonGameBoard() {
       assertNotNull(hexagonGameBoard.getPreferredSize(), "getPreferredSize() should not return null");

       assertEquals(hexagonGameBoard.numRows, numRows, "numRows should be " + numRows);
       assertEquals(hexagonGameBoard.numCols, numCols, "numCols should be " + numCols);
       assertEquals(hexagonGameBoard.hexSize, hexSize, "hexSize should be " + hexSize);
   }

   /**
    * Tests the loadData() from the hexagonGameBoard.
    * It checks the amount of boardPieces, terrains and winning pieces that are created.
    */
   @Test
   public void testLoadTileData() {
        Map<String, BoardPiece> bp = hexagonGameBoard.boardPieces;
        bp.clear();
        assertEquals(0, hexagonGameBoard.boardPieces.size(),"current board: " + hexagonGameBoard.boardPieces.size());

        hexagonGameBoard.loadTileData();
        assertNotNull(hexagonGameBoard.boardPieces);
        assertEquals(2, hexagonGameBoard.boardPieces.size(),"current board: " + hexagonGameBoard.boardPieces.size());

        int countTerrain = hexagonGameBoard.getAllTerrains().size();
        assertEquals(1, countTerrain, "Actual count: " + countTerrain);

        int countWinning = hexagonGameBoard.getAllWinningPieces().size();
        assertEquals(1, countWinning, "Actual count: " + countWinning);

        hexagonGameBoard.initBoard();
        int countTerrain1 = hexagonGameBoard.getAllTerrains().size();
        assertEquals(4, countTerrain1, "Actual count: " + countTerrain1);
   }

   /**
    * Tests the addTerrain() from the hexagonGameBoard.
    * It checks the amount of blockades that are created when adding a new terrain.
    */
   @Test
   public void testAddTerrain() {
	   Map<String, BoardPiece> bp=hexagonGameBoard.boardPieces;
	   bp.clear();
	   hexagonGameBoard.loadTileData();
	   Terrain ta = hexagonGameBoard.getAllTerrains().get(0);
	   assertEquals(1, hexagonGameBoard.getAllTerrains().size(), "Actual count: " + hexagonGameBoard.getAllTerrains().size());
	   
	   hexagonGameBoard.addTerrain(0, 8, ta); //blockade auto added
	   int countBlockade = hexagonGameBoard.getAllBlockades().size();
	   assertEquals(1, countBlockade, "Actual count: " + countBlockade);

	   hexagonGameBoard.addTerrain(5, 5, ta); //blockade shouldn't be added
	   countBlockade = hexagonGameBoard.getAllBlockades().size();
	   assertEquals(1, countBlockade, "Actual count: " + countBlockade);
   }
   /**
    * Tests the removeBlockade() from the hexagonGameBoard.
    * It checks the blockade gets correctly removed and in its order.
    */
   
   @Test
   public void testRemoveBlock() {
	   int countBlockade1=hexagonGameBoard.getAllBlockades().size();

	   hexagonGameBoard.removeBlockade(1);
	   int countBlockade2=hexagonGameBoard.getAllBlockades().size();
	   assertEquals(1,countBlockade1-countBlockade2, "Actual count: " + (countBlockade1-countBlockade2));
	   //remove 1 blockade, should have 1 less;
   }

   /**
    * Tests the getLocation() method of HexagonGameBoard works as expected.
    */
   @Test
   public void testHexagonGameBoardDefaultLocation() {
       Point defaultLocation = new Point(0,0);

       assertEquals(hexagonGameBoard.getLocation(), defaultLocation, "Default getLocation() should return " + defaultLocation);
   }

    /**
     * Test that the setLocation() method of HexagonGameBoard works as expected.
     */
    @Test
    public void testHexagonGameBoardSetLocation() {
        Point newPoint = new Point(1,1);

        hexagonGameBoard.setLocation(newPoint);
        assertEquals(hexagonGameBoard.getLocation(), newPoint, "The newly setLocation(newPoint) should have set the location to " + newPoint);
    }
   

   @Test
   public void testHexagonGameBoardPlayer() {
       int maxPlayers = 4;

       hexagonGameBoard.players = new ArrayList<>();
       Color[] COLOR_RANGE = {Color.GREEN, Color.CYAN, Color.YELLOW,Color.MAGENTA};
       int counter = 0;

       for (int i = 0; i < maxPlayers; i++) {
    	   Player player=new Player(COLOR_RANGE[i]);
           hexagonGameBoard.players.add(player);

           assertEquals(hexagonGameBoard.players.size(), counter + 1);

           int playerId = hexagonGameBoard.players.size() - 1;
           assertEquals(hexagonGameBoard.players.get(playerId).color, player.getColor());

           counter++;
       }

       // Check if the players are added
       assertTrue(hexagonGameBoard.players.size() == maxPlayers);

       // TODO: This should fail because the max amount of players is reached
       // hexagonGameBoard.players.add(PlayerTest.players.get(maxPlayers));

       assertFalse(hexagonGameBoard.isValidPosition(0, 0));//check if player can go outside the boards
       System.out.println(hexagonGameBoard.players.size());
       Player pl1=hexagonGameBoard.players.get(0);
       pl1.setPlayerPosition(2, 3);
       assertEquals(pl1.getCurrentRow(),2);
       assertEquals(pl1.getCurrentCol(),3);//check player1 is set at place
       Player pl2=hexagonGameBoard.players.get(1);
       pl2.setPlayerPosition(2, 3);

       //player1 is already at 2,3 so it's not a valid position for others to come here
       assertFalse(hexagonGameBoard.isValidPosition(2, 3));
   }
}

