package org.set.UI;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.set.template.Team03Board;
import org.junit.jupiter.api.Test;
import org.set.boardPieces.IntegrationWith03Board;
import org.set.boardPieces.Tile;
import org.set.game.Before_game;
import org.set.game.Player_move;
import org.set.player.Player;

class Team03SetupTest {
	
	@Test
    public void testSerpentine() {
		 Team03Board Board = new Team03Board(35, 35, 25);
		 Board.boardPieces.clear();
		 Board.setPathString("Serpentine"); 
		 Board.loadTileData();
		 Board.initBoard();
		assertEquals(6,Board.getAllTerrains().size());
		Tile tile=Board.findStarterTiles().get(0);
		assertEquals(4,tile.getRow());
		assertEquals(1,tile.getCol());
    }
	@Test
    public void testSwamplands() {
		 Team03Board Board = new Team03Board(35, 35, 25);
		 Board.boardPieces.clear();
		 Board.setPathString("Swamplands"); 
		 Board.loadTileData();
		 Board.initBoard();
		assertEquals(7,Board.getAllTerrains().size());
		Tile tile=Board.findStarterTiles().get(0);
		assertEquals(5,tile.getRow());
		assertEquals(1,tile.getCol());
    }
	
	@Test
    public void testwindingpaths() {
		 Team03Board Board = new Team03Board(35, 35, 25);
		 Board.boardPieces.clear();
		 Board.setPathString("windingpaths"); 
		 Board.loadTileData();
		 Board.initBoard();
		assertEquals(6,Board.getAllTerrains().size());
		Tile tile=Board.findStarterTiles().get(0);
		assertEquals(9,tile.getRow());
		assertEquals(1,tile.getCol());
    }
	@Test
    public void testwitchcauldron() {
		 Team03Board Board = new Team03Board(35, 35, 25);
		 Board.boardPieces.clear();
		 Board.setPathString("witchcauldron"); 
		 Board.loadTileData();
		 Board.initBoard();
		assertEquals(6,Board.getAllTerrains().size());
		Tile tile=Board.findStarterTiles().get(0);
		assertEquals(6,tile.getRow());
		assertEquals(4,tile.getCol());
    }
	@Test
    public void testHillsOfGold() {
		 Team03Board Board = new Team03Board(35, 35, 25);
		 Board.boardPieces.clear();
		 Board.setPathString("HillsOfGold"); 
		 Board.loadTileData();
		 Board.initBoard();
		assertEquals(6,Board.getAllTerrains().size());
		Tile tile=Board.findStarterTiles().get(0);
		assertEquals(14,tile.getRow());
		assertEquals(1,tile.getCol());
    }
}
