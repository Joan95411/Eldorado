package org.set.UI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.boardPieces.HexagonGameBoard;
import org.set.boardPieces.Terrain;
import org.set.boardPieces.boardPiece;

public class HexgonGameBoardDrawTest {
	private HexagonGameBoard board;
    private JFrame frame;

    int numRows = 15;
    int numCols = 35;
    int hexSize = 30;

    @BeforeEach
    void setUp() {
    	board = new HexagonGameBoard(numRows, numCols, hexSize);

        frame = new JFrame("GameBoardTest");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(board);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }

    private void waitForUserInput() {
        System.out.println("Press any key to continue to the next test. Press f if failed");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("f")) {
            fail("Test failed by user input.");
        }
        frame.dispose();
    }

    @Test
    public void testRemoveBlock() {
    	System.out.println("This should remove the first blockade");
    	board.removeBlockade(1);
    	board.repaint();
        waitForUserInput();
    }

    @Test
    public void testLoadModelData() {
    	System.out.println("This should load the original json file and draw the pieces on board.");
    	Map<String, boardPiece> bp=board.boardPieces;
 	   bp.clear();
 	   board.loadTileData();
 	   
    	board.repaint();
        waitForUserInput();
    }
    
    @Test
    public void testAddTerrain() {
    	System.out.println("This should add 2 terrains cloned after the json file terrain.");
    	System.out.println("The first cloned terrain should have a blockade after.");
    	System.out.println("This second cloned terrain shouldn't have a blockade after.");
    	Map<String, boardPiece> bp=board.boardPieces;
  	   bp.clear();
  	   board.loadTileData();
    	
    	board.addTerrain(0, 8, board.getAllTerrains().get(0));//should have block
    	board.addTerrain(5, 4, board.getAllTerrains().get(1));//should not have block
 	   
    	board.repaint();
        waitForUserInput();
    }
    
    
}
