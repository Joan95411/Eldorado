package org.set.game;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.boardPieces.Blockade;
import org.set.boardPieces.Tile;
import org.set.boardPieces.TileType;
import org.set.cards.expedition.ExpeditionCard;
import org.set.cards.expedition.ExpeditionCardType;
import org.set.marketplace.MarketPlace;
import org.set.player.Player;
import org.set.template.Team03Board;
import org.set.template.Team04Board;
import org.set.template.Template;
import org.set.tokens.Cave;

class Player_moveTest_Twinteamboard {
	private Template board;
    private List<Player> players;
    private static InputStream backupInputStream;
    
    @BeforeAll
    public static void start() {
       
        backupInputStream = System.in;
    }
    @BeforeEach
    public void setUp() {
    	board = new Team03Board(35, 35, 25); 
        Random random = new Random();
        players = new ArrayList<>();
        players.add(new Player(new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256))));
        board.players = players;
    }
    
    @AfterEach
    public void cleanup(){
        System.setIn(backupInputStream);
    }

	/**
	 * Integrationtest
	 * Classes used: ExpeditionCard, ExpeditionCardType, Template, Player, Marketplace, Team04Board, Tile, TileType, Cave
	 * Tesing the exploration of caves
	*/
    @Test
    public void testCaveExplore() {
    	Player_move.caveMap=Before_game.allocateTokens(board);
    	Player player = players.get(0);
    	player.setPlayerPosition(16, 6);//save cave tile in json
    	Player_move.caveExplore(board, player);
    	assertEquals(1,player.myDeck.getTokens().size());
    }

	/**
	 * Integrationtest
	 * Classes used: ExpeditionCard, ExpeditionCardType, Template, Player, Marketplace, Team04Board, Tile, TileType, Cave
	 * Tesing cave exploration twice
	*/
    @Test
    public void testCaveExplore2() {//explore twice
    	Player_move.caveMap=Before_game.allocateTokens(board);
    	Player player = players.get(0);
    	player.setPlayerPosition(16, 6);
    	Player_move.caveExplore(board, player);
    	assertEquals(1,player.myDeck.getTokens().size());
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    	player.setPlayerPosition(16, 5);
    	Player_move.caveExplore(board, player);
    	String expectedOutput = "You cannot explore a cave twice in a row." + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    	assertEquals(1,player.myDeck.getTokens().size());
    }
    
    @Test
    public void testCaveExplore3() {//go somewhere else and back to cave again
    	Player_move.caveMap=Before_game.allocateTokens(board);
    	Player player = players.get(0);
    	player.setPlayerPosition(16, 6);
    	Player_move.caveExplore(board, player);
    	assertEquals(1,player.myDeck.getTokens().size());
    	player.setPlayerPosition(15, 5);
    	Player_move.caveExplore(board, player);
    	assertEquals(1,player.myDeck.getTokens().size());
    	player.setPlayerPosition(16, 5);
    	Player_move.caveExplore(board, player);
    	assertEquals(2,player.myDeck.getTokens().size());
    }
    
	/**
	 * Integrationtest
	 * Classes used: ExpeditionCard, ExpeditionCardType, Template, Player, Marketplace, Team04Board, Tile, TileType, Cave
	 * Tesing actioncards
	*/
    @Test
    public void testMovewithEnoughPower() {
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.getDrawPile().clear();
        for(int i=0;i<4;i++) {
        player.myDeck.addCard(new ExpeditionCard(ExpeditionCardType.Explorer));}
    	player.myDeck.draw(4);
    	String input = "y\n0\n14,2\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(14,player.getCurrentRow());
    	assertEquals(2,player.getCurrentCol());
    }

	/**
	 * Integrationtest
	 * Classes used: ExpeditionCard, ExpeditionCardType, Template, Player, Marketplace, Team04Board, Tile, TileType, Cave
	 * Tesing moving without sufficient power
	*/
    @Test
    public void testMovewithNotCorrectColor() {
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.getDrawPile().clear();
        player.myDeck.addCard(new ExpeditionCard(ExpeditionCardType.Sailor));
    	player.myDeck.draw(1);
    	String input = "y\n0\n14,2\nstop\nn\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(14,player.getCurrentRow());
    	assertEquals(1,player.getCurrentCol());
    }
    
    @Test
    public void testMovewithNotEnoughPower() {
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.getDrawPile().clear();
        player.myDeck.addCard(new ExpeditionCard(ExpeditionCardType.Explorer));
    	player.myDeck.draw(1);
    	player.setPlayerPosition(8, 15);
    	String input = "y\n0\n9,16\nstop\nn\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(8,player.getCurrentRow());
    	assertEquals(15,player.getCurrentCol());
    	
    }
	/**
	 * Integrationtest
	 * Classes used: ExpeditionCard, ExpeditionCardType, Template, Player, Marketplace, Team04Board, Tile, TileType, Cave
	 * Tesing removing blockade
	*/
    @Test
    public void testRemoveBlock() {
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.getDrawPile().clear();
        player.myDeck.addCard(new ExpeditionCard(ExpeditionCardType.Sailor));
    	player.myDeck.draw(1);
    	Blockade block=(Blockade) board.boardPieces.get("Blockade_1");
    	block.setColor(TileType.Paddle);
    	block.setPoints(1);
    	player.setPlayerPosition(13, 5);
    	String input = "y\n0\nblock\ny\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(1,player.myDeck.calculateBlockPoint());
    }
    
	/**
	 * Integrationtest
	 * Classes used: ExpeditionCard, ExpeditionCardType, Template, Player, Marketplace, Team04Board, Tile, TileType, Cave
	 * Tesing basecamp with the cards needed to go to the basecamp
	*/
    @Test
    public void testBaseCampMoveWithEnoughCardsToDiscard() {
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.draw(4);
    	player.setPlayerPosition(11, 5);
    	String input = "y\n-2\n0\n11,6\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(11,player.getCurrentRow());
    	assertEquals(6,player.getCurrentCol());
    	assertEquals(3,player.myDeck.getCardsInHand().size());
    }
}
