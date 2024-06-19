package org.set.game;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.set.boardPieces.Tile;
import org.set.cards.action.ActionCardType;
import org.set.cards.expedition.ExpeditionCard;
import org.set.cards.expedition.ExpeditionCardType;
import org.set.template.Team04Board;
import org.set.template.Template;
import org.set.tokens.Cave;
import org.set.tokens.Token;
import org.set.player.Player;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link Card} class.
 */
public class BeforeGameTest {

    /**
     * Test for creating an action card.
     */
	@Test
    public void testCreateTokens() {
        ArrayList<Token> tokens = Before_game.createTokens();
        System.out.print(tokens);
        assertNotNull(tokens);
        assertFalse(tokens.isEmpty());
        assertEquals(42, tokens.size()); // Ensure all expected tokens are created
    }
	
	@Test
    public void testAllocateTokens() {
        Template mockTemplate = new Team04Board(25,30,25); // Create a mock Template object for testing
        Map<Tile, Cave> caves = Before_game.allocateTokens(mockTemplate);
        
        assertNotNull(caves);
        assertFalse(caves.isEmpty());
        // Perform assertions based on the logic of allocateTokens(), depending on mockTemplate's behavior
    }
//	@Test
//    public void testAddPlayer() {
//        Template mockTemplate = new Team04Board(25,30,25);
//        String input = "4\nred\nblue\nblack\nyellow"; // Prepare the input data
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); // Create a ByteArrayInputStream with the input data
//        System.setIn(inputStream);
//        List<Player> players = Before_game.addPlayer(mockTemplate);
//        assertNotNull(players);
//        assertFalse(players.isEmpty());
//        assertEquals(4, players.size()); // Ensure the correct number of players are added based on mock input
//    }
	 
}
