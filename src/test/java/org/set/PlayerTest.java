package org.set;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class PlayerTest contains JUnit tests for the {@link Player} class.
 */
public class PlayerTest {
    protected static Player player = new Player(Color.MAGENTA);
    protected static ArrayList<Player> players = new ArrayList<>();

    /**
     * Tests that creates a player
     */
    @Test
    public void testCreatingAPlayer() {
        assertNotNull(player);
    }

    /**
     * Tests that creates a player with a duplicate color that should throw an exception.
     */
    @Test
    public void testCreatingPlayersWithSameColor() {
        try {
            Player player = new Player(Color.MAGENTA);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "The color " + Color.MAGENTA.toString() + " is already used", "A player's color is unique");
        }
    }

    /**
     * Tests that creates a multiple players
     */
    @Test
    public void testCreatingPlayers() {
        Color[] colors = {Color.RED, Color.GRAY, Color.YELLOW, Color.GREEN, Color.CYAN, Color.PINK, Color.BLUE};

        for (Color color : colors) {
            Player player = new Player(color);
            assertNotNull(player);
            assertEquals(player.getColor(), color);

            players.add(player);
            assertNotNull(players.get(players.size() - 1));

            testPlayerColor(player, color);
        }
    }

    /**
     * Tests the color of the created player.
     */
    private void testPlayerColor(Player player, Color color) {
        assertEquals(player.getColor(), color);
    }

    /**
     * Tests that test the players position.
     */
    @Test
    public void testPlayerPosition() {
        int row = 1;
        int col = 2;

        player.setPlayerPosition(row, col);

        assertEquals(player.getCurrentRow(), row, "The current row of the player should be equal to " + row);
        assertEquals(player.getCurrentCol(), col, "The current col of the player should be equal to " + col);
        assertTrue(player.isAtPosition(row, col), "The player should be in row " + row + " and col " + col);
    }
}