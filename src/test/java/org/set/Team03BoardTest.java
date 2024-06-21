package org.set;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.boardPieces.Tile;
import org.set.boardPieces.TileDataDic;
import org.set.boardPieces.Util;
import org.set.boardPieces.WinningPiece;
import org.set.template.Team03Board;
import org.set.template.Template;

class Team03BoardTest {

	private Template board;
    
    @BeforeEach
    public void setUp() {
    	board = new Team03Board(35, 40, 25); 
    }
    @Test
    public void testWinningPieceRotate() {
    	WinningPiece wps=board.getLastWinningPiece();
    	for(Tile tile:wps.getTiles()) {
    		assertTrue(tile.getCol()<=26 || tile.getCol()>=24);
    	}
    	Tile axisTile=board.getLastTerrain().axisTile;
    	int[] temp=TileDataDic.tilesMap.get(axisTile.getRow()+","+axisTile.getCol());
    	wps.rotate(-1, temp[0], temp[1]);
    	for(Tile tile:wps.getTiles()) {
    		assertTrue(tile.getCol()<=30 || tile.getCol()>=28);
    	}
    }
    
    @Test
    public void testtestwitchcauldron() {
    	board.boardPieces.clear();
    	board.loadTileData();
    	board.pathInfo=Util.readPathData("WitchCauldron");
    	board.initBoard();
    	Tile tile=board.getLastWinningPiece().getTiles().getFirst();
		assertEquals(14,tile.getRow());
		assertEquals(10,tile.getCol());
    }
    
    @Test
    public void testHillsOfGold() {
    	board.boardPieces.clear();
    	board.loadTileData();
    	board.pathInfo=Util.readPathData("HillsOfGold");
    	board.initBoard();
    	Tile tile=board.getLastWinningPiece().getTiles().getFirst();
		assertEquals(17,tile.getRow());
		assertEquals(31,tile.getCol());
    }
    
    @Test
    public void testwindingpaths() {
    	board.boardPieces.clear();
    	board.loadTileData();
    	board.pathInfo=Util.readPathData("WindingPaths");
    	board.initBoard();
    	Tile tile=board.getLastWinningPiece().getTiles().getFirst();
		assertEquals(4,tile.getRow());
		assertEquals(33,tile.getCol());
//    	JFrame frame = new JFrame("GameBoardTest");
//    	JScrollPane scrollPane = new JScrollPane(board);
//        frame.add(scrollPane);
//        frame.setSize(1250, 700);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//        waitForUserInput();
    }
    private void waitForUserInput() {
    	// Prompt the user to continue
        System.out.println("Press any key to continue to the next test. Press f if failed");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        
        if (input.equalsIgnoreCase("f")) {
            fail("Test failed by user input.");
        }

    }
}
