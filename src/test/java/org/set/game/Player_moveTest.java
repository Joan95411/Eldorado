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
import org.set.template.Team04Board;
import org.set.template.Template;
import org.set.tokens.Cave;
import org.set.tokens.CaveTokenType;
import org.set.tokens.Token;

class Player_moveTest {
	private Template board;
    private List<Player> players;
    private static InputStream backupInputStream;
    
    @BeforeAll
    public static void start() {
       
        backupInputStream = System.in;
    }
    @BeforeEach
    public void setUp() {
    	board = new Team04Board(25,30,25); 
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
    	player.setPlayerPosition(3, 3);//save cave tile in json
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
    	player.setPlayerPosition(3, 3);
    	Player_move.caveExplore(board, player);
    	assertEquals(1,player.myDeck.getTokens().size());
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    	player.setPlayerPosition(4, 2);
    	Player_move.caveExplore(board, player);
    	String expectedOutput = "You cannot explore a cave twice in a row." + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    	assertEquals(1,player.myDeck.getTokens().size());
    }
    
	/**
	 * Integrationtest
	 * Classes used: ExpeditionCard, ExpeditionCardType, Template, Player, Marketplace, Team04Board, Tile, TileType, Cave
	 * Tesing actioncards
	*/
    @Test
    public void testCaveExplore3() {//go somewhere else and back to cave again
    	Player_move.caveMap=Before_game.allocateTokens(board);
    	Player player = players.get(0);
    	player.setPlayerPosition(3, 3);
    	Player_move.caveExplore(board, player);
    	assertEquals(1,player.myDeck.getTokens().size());
    	player.setPlayerPosition(3, 2);
    	Player_move.caveExplore(board, player);
    	assertEquals(1,player.myDeck.getTokens().size());
    	player.setPlayerPosition(4, 2);
    	Player_move.caveExplore(board, player);
    	assertEquals(2,player.myDeck.getTokens().size());
    }
    
    @Test
    public void testCaveExplore4() {//explore cave 4 times, no coin left
    	Player_move.caveMap=Before_game.allocateTokens(board);
    	Player player = players.get(0);
    	for(int i=0;i<5;i++) {
    	player.setPlayerPosition(3, 2);
    	Player_move.caveExplore(board, player);
    	assertEquals(i,player.myDeck.getTokens().size());
    	player.setPlayerPosition(3, 3);
    	Player_move.caveExplore(board, player);}
    	assertEquals(4,player.myDeck.getTokens().size());
    }
	/**
	 * Integrationtest
	 * Classes used: ExpeditionCard, ExpeditionCardType, Template, Player, Marketplace, Team04Board, Tile, TileType, Cave
	 * Tesing moving without sufficient power
	*/
    @Test
    public void testMovewithEnoughPower() {
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.getDrawPile().clear();
        for(int i=0;i<4;i++) {
        player.myDeck.addCard(new ExpeditionCard(ExpeditionCardType.Explorer));}
    	player.myDeck.draw(4);
    	String input = "y\n0\n2,4\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(2,player.getCurrentRow());
    	assertEquals(4,player.getCurrentCol());
    }
    @Test
    public void testMovewithTooMuchPower() {//discard the leftover power
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.getDrawPile().clear();
        for(int i=0;i<4;i++) {
        player.myDeck.addCard(new ExpeditionCard(ExpeditionCardType.Giant_Machete));}
    	player.myDeck.draw(4);
    	String input = "y\n0\n2,4\nstop\nn\nstop\ny\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(2,player.getCurrentRow());
    	assertEquals(4,player.getCurrentCol());
    	assertEquals(3,player.myDeck.getCardsInHand().size());
    }
    @Test
    public void testMovewithNotCorrectColor() {
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.getDrawPile().clear();
        player.myDeck.addCard(new ExpeditionCard(ExpeditionCardType.Sailor));
    	player.myDeck.draw(1);
    	String input = "y\n0\n2,4\nstop\nn\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(1,player.getCurrentRow());
    	assertEquals(4,player.getCurrentCol());
    }
    @Test
    public void testMovewithNotEnoughPower() {
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.getDrawPile().clear();
        player.myDeck.addCard(new ExpeditionCard(ExpeditionCardType.Sailor));
    	player.myDeck.draw(1);
    	player.setPlayerPosition(4, 4);
    	String input = "y\n0\n5,4\nstop\nn\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(4,player.getCurrentRow());
    	assertEquals(4,player.getCurrentCol());
    }
    @Test
    public void testUseToKenToMove() {
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.getDrawPile().clear();
    	Token token=new Token(CaveTokenType.MacheteOne);
        player.myDeck.addToken(token);
    	String input = "y\n0\n2,4\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(2,player.getCurrentRow());
    	assertEquals(4,player.getCurrentCol());
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
    	player.setPlayerPosition(6, 6);
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
    public void testRemoveBlockWithNotRightColor() {
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.getDrawPile().clear();
        player.myDeck.addCard(new ExpeditionCard(ExpeditionCardType.Explorer));
    	player.myDeck.draw(1);
    	Blockade block=(Blockade) board.boardPieces.get("Blockade_1");
    	block.setColor(TileType.Paddle);
    	block.setPoints(1);
    	player.setPlayerPosition(6, 6);
    	String input = "y\n0\nblock\nstop\nn\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(0,player.myDeck.calculateBlockPoint());
    }
    @Test
    public void testRemoveBlockWithNotRightPower() {
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.getDrawPile().clear();
        player.myDeck.addCard(new ExpeditionCard(ExpeditionCardType.Sailor));
    	player.myDeck.draw(1);
    	Blockade block=(Blockade) board.boardPieces.get("Blockade_1");
    	block.setColor(TileType.Paddle);
    	block.setPoints(2);
    	player.setPlayerPosition(6, 6);
    	String input = "y\n0\nblock\nstop\nn\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(0,player.myDeck.calculateBlockPoint());
    }
    @Test
    public void testRemoveBlockwithTooMuchPower() {//discard the leftover power
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.getDrawPile().clear();
        player.myDeck.addCard(new ExpeditionCard(ExpeditionCardType.Giant_Machete));
    	player.myDeck.draw(1);
    	Blockade block=(Blockade) board.boardPieces.get("Blockade_1");
    	block.setColor(TileType.Machete);
    	block.setPoints(2);
    	player.setPlayerPosition(6, 6);
    	String input = "y\n0\nblock\nstop\nn\nstop\ny\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(0,player.myDeck.getCardsInHand().size());
    	assertEquals(2,player.myDeck.calculateBlockPoint());
    }
    @Test
    public void testBaseCampMoveWith1CardsToDiscard() {
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.draw(4);
    	String input = "y\n0\n1,5\ny\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(1,player.getCurrentRow());
    	assertEquals(5,player.getCurrentCol());
    	assertEquals(3,player.myDeck.getCardsInHand().size());
    }
    @Test
    public void testBaseCampMoveWith1CardsToDiscardRegret() {
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.draw(4);
    	String input = "y\n0\n1,5\nn\n1\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(1,player.getCurrentRow());
    	assertEquals(5,player.getCurrentCol());
    	assertEquals(3,player.myDeck.getCardsInHand().size());
    }
    @Test
    public void testBaseCampMoveWithNotMoreCardsToDiscard() {
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.draw(4);
    	Tile tile=board.ParentMap.get("1,5");
    	tile.setPoints(3);
    	String input = "y\n0\n1,5\n0,1,2\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(1,player.getCurrentRow());
    	assertEquals(5,player.getCurrentCol());
    	assertEquals(1,player.myDeck.getCardsInHand().size());
    }
    @Test
    public void testMoveToMountain() {
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.draw(4);
    	Tile tile=board.ParentMap.get("1,5");
    	tile.setType(TileType.Mountain);;
    	String input = "y\n0\n1,5\nstop\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(1,player.getCurrentRow());
    	assertEquals(4,player.getCurrentCol());
    	assertEquals(4,player.myDeck.getCardsInHand().size());
    }
    @Test
    public void testMoveToBlock() {
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.getDrawPile().clear();
        player.myDeck.addCard(new ExpeditionCard(ExpeditionCardType.Giant_Machete));
    	player.myDeck.draw(1);
    	player.setPlayerPosition(6, 6);
    	String input = "y\n0\n7,6\nstop\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(6,player.getCurrentRow());
    	assertEquals(6,player.getCurrentCol());
    }
    @Test
    public void testBaseCampBlockRemove() {//remove discard block
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.draw(4);
    	Blockade block=(Blockade) board.getFirstBlockade();
    	block.setColor(TileType.Discard);
    	block.setPoints(2);
    	player.setPlayerPosition(6, 6);
    	String input = "y\n0\nblock\n0,1\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(2,player.myDeck.calculateBlockPoint());
    	assertEquals(2,player.myDeck.getDiscardPile().size());
    }
    
    @Test
    public void testBaseCampBlockRemove2() {//remove basecamp block
    	Player player = players.get(0);
    	Before_game.placePlayersOnBoard(board);
    	player.myDeck.draw(4);
    	Blockade block=(Blockade) board.getFirstBlockade();
    	block.setColor(TileType.BaseCamp);
    	block.setPoints(2);
    	player.setPlayerPosition(6, 6);
    	String input = "y\n0\nblock\n0,1\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
    	Player_move.PlayerMove2(board, player);
    	assertEquals(2,player.myDeck.calculateBlockPoint());
    	assertEquals(0,player.myDeck.getDiscardPile().size());
    }
}
