package org.set.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.set.boardPieces.Tile;
import org.set.cards.action.ActionCard;
import org.set.cards.action.ActionCardType;
import org.set.player.Player;
import org.set.template.Team04Board;
import org.set.template.Template;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class Player_draw_discardTest {

	    private Template board;
	    private List<Player> players;
	    private static InputStream backupInputStream;
	    
	    @BeforeAll
	    public static void start() {
	       
	        backupInputStream = System.in;
	    }
	    @BeforeEach
	    public void setUp() {
	        board = new Team04Board(25,30,25); // Example dimensions
	        Random random = new Random();
	        players = new ArrayList<>();
	        for(int i=0;i<4;i++) {
	        players.add(new Player(new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256))));}
	        
	        // Set the players in the template
	        board.players = players;

	    }
	    
	    @AfterEach
	    public void cleanup(){
	        System.setIn(backupInputStream);
	    }
	    @Test
	    public void testPlacePlayersOnBoard() {
	        Before_game.placePlayersOnBoard(board);

	        List<Tile> starterTiles = board.findStarterTiles();
	        for (int i = 0; i < players.size(); i++) {
	            Player player = players.get(i);
	            Tile tile = starterTiles.get(i % starterTiles.size());
	            assertEquals(tile.getRow(), player.getCurrentRow());
	            assertEquals(tile.getCol(), player.getCurrentCol());
	        }
	    }

	    @Test
	    public void testPlayerDrawCards() {
	        Player player = players.get(0);

	        Player_draw_discard.PlayerDrawCards(board, player);
	        assertEquals(4, player.myDeck.getCardsInHand().size());
	    }
	    
	    @Test
	    public void testPlayerDiscard() {
	        Player player = players.get(0);
	        Player_draw_discard.PlayerDrawCards(board, player);
	        String input = "y\n0\nn\n";
	        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); // Create a ByteArrayInputStream with the input data
	        InputHelper.setInputStream(inputStream);
	        Player_draw_discard.PlayerDiscard(board, player);
	        assertEquals(3, player.myDeck.getCardsInHand().size());
	        assertEquals(1, player.myDeck.getDiscardPile().size());
	    }
	    
	    @Test
	    public void testPlayActionCardWithActionCard() {
	    	Player player = players.get(0);
	    	ActionCard ac=new ActionCard(ActionCardType.Cartographer);
	    	player.myDeck.getDrawPile().clear();
	    	player.myDeck.addCard(ac);
	    	player.myDeck.draw(1);
	    	String input = "y\n0\nn\n";
	        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); // Create a ByteArrayInputStream with the input data
	        InputHelper.setInputStream(inputStream);
	        Player_draw_discard.PlayActionCard(board, player);
	        assertEquals(0,player.myDeck.isThereActionAsset());
	    }

	}



