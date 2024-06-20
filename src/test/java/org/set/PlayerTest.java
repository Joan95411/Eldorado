package org.set;

import org.junit.jupiter.api.Test;
import org.set.player.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link Player} class.
 */
public class PlayerTest {
    protected static Player player = new Player(new Color(255,254,253));
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
            new Player(new Color(255,254,253));
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "The color " + new Color(255,254,253).toString() + " is already used", "A player's color is unique");
        }
    }

    /**
     * Tests that creates a multiple players
     */
    @Test
    public void testCreatingPlayers() {
        Color[] colors = { Color.GRAY, Color.YELLOW, Color.GREEN, Color.CYAN, Color.PINK};

        for (Color color : colors) {
            try {
                Player player = new Player(color);
                assertNotNull(player);
                assertEquals(player.getColor(), color);

                players.add(player);
                assertNotNull(players.get(players.size() - 1));

                testPlayerColor(player, color);
            } catch (IllegalArgumentException e) {
                assertEquals(e.getMessage(), "The color " + color.toString() + " is already used", "A player's color is unique");
            }
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

    /**
     * Test for drawing player.
     */
    @Test
    public void drawingPlayer() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        Player player = new Player(new Color(0,1,2));
        player.draw(g2d, 1);
    }
}