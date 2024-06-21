package org.set.UI;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.set.template.Team03Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.boardPieces.IntegrationWith03Board;
import org.set.boardPieces.Tile;
import org.set.boardPieces.Util;
import org.set.game.Before_game;
import org.set.game.GameController;
import org.set.game.Player_move;
import org.set.player.Player;

class Team03SetupTest {
	 private JFrame frame;
	 private Team03Board Board;
	 @BeforeEach
	    void setUp() {
		 frame = new JFrame("Hexagon Game Board");
		 Board = new Team03Board(35, 38, 25);
		 Board.boardPieces.clear();
		 JScrollPane scrollPane = new JScrollPane(Board);
	        frame.add(scrollPane);
	        frame.setSize(1250, 700);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setVisible(true);
	        
	 }
	 
	 private void waitForUserInput() {
	    	// Prompt the user to continue
	        System.out.println("Press any key to continue to the next test. Press f if failed");
	        Scanner scanner = new Scanner(System.in);
	        String input = scanner.nextLine();
	        
	        if (input.equalsIgnoreCase("f")) {
	            fail("Test failed by user input.");
	        }

	        // Dispose the frame after the test is finished
	        frame.dispose();
	    }
	@Test
    public void testSerpentine() {
		 
		 Board.setPathString("Serpentine"); 
		 Board.loadTileData();
		 Board.pathInfo=Util.readPathData("Serpentine");
		 Board.initBoard();
		 Board.repaint();
		 waitForUserInput();
    }
	@Test
    public void testSwamplands() {
		 Board.setPathString("Swamplands"); 
		 Board.loadTileData();
		 Board.pathInfo=Util.readPathData("Swamplands");
		 Board.initBoard();
		 Board.repaint();
		 waitForUserInput();
    }
	
	@Test
    public void testwindingpaths() {
		 Board.setPathString("WindingPaths"); 
		 Board.loadTileData();
		 Board.pathInfo=Util.readPathData("WindingPaths");
		 Board.initBoard();
		 Board.repaint();
		 waitForUserInput();
    }
	@Test
    public void testwitchcauldron() {
		 Board.setPathString("WitchCauldron"); 
		 Board.loadTileData();
		 Board.pathInfo=Util.readPathData("WitchCauldron");
		 Board.initBoard();
		 Board.repaint();
		 waitForUserInput();
    }
	@Test
    public void testHillsOfGold() {
		 Board.setPathString("HillsOfGold"); 
		 Board.loadTileData();
		 Board.pathInfo=Util.readPathData("HillsOfGold");
		 Board.initBoard();
		 Board.repaint();
		 waitForUserInput();
    }
}
