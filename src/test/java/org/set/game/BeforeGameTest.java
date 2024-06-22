package org.set.game;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.set.boardPieces.Tile;
import org.set.template.Team04Board;
import org.set.template.Template;
import org.set.tokens.Cave;
import org.set.tokens.Token;
import org.set.player.Player;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link BeforeGameTest} class.
 */
public class BeforeGameTest {
	private static InputStream backupInputStream;
    
	@BeforeAll
    public static void start() {
       
        backupInputStream = System.in;
    }

    @AfterEach
    public void cleanup(){
        System.setIn(backupInputStream);
    }
    
    /**
	 * Integrationtest
	 * Classes used: Template, Player, Cave, Team04Board and token
	 * Testing the creation of tokens
	*/
	@Test
    public void testCreateTokens() {
        ArrayList<Token> tokens = Before_game.createTokens();
        System.out.print(tokens);
        assertNotNull(tokens);
        assertFalse(tokens.isEmpty());
        assertEquals(42, tokens.size()); // Ensure all expected tokens are created
    }
	
    /**
	 * Integrationtest
	 * Classes used: ExpeditionCard, ExpeditionCardType, Template, Player, Marketplace, Team04Board, Tile, TileType, Cave
	 * Tesing the allocation of tokens
	*/
	@Test
    public void testAllocateTokens() {
        Template mockTemplate = new Team04Board(25,30,25); // Create a mock Template object for testing
        Map<Tile, Cave> caves = Before_game.allocateTokens(mockTemplate);
        
        assertNotNull(caves);
        assertFalse(caves.isEmpty());
        // Perform assertions based on the logic of allocateTokens(), depending on mockTemplate's behavior
    }

    /**
	 * Integrationtest
	 * Classes used: ExpeditionCard, ExpeditionCardType, Template, Player, Marketplace, Team04Board, Tile, TileType, Cave
	 * Tesing multiple players
	*/
	@Test
    public void testAddPlayer() {
        Template mockTemplate = new Team04Board(25,30,25);
        String input = "3\norange\nwhite\nyellow"; // Prepare the input data
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); // Create a ByteArrayInputStream with the input data
        InputHelper.setInputStream(inputStream);
        List<Player> players = Before_game.addPlayer(mockTemplate);
        assertNotNull(players);
        assertFalse(players.isEmpty());
        assertEquals(3, players.size()); // Ensure the correct number of players are added based on mock input
        Before_game.placePlayersOnBoard(mockTemplate);
        assertEquals(3, mockTemplate.players.size());
    }
	
	
	 
}
