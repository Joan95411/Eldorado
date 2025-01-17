package org.set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.set.boardPieces.*;
import org.set.template.Team04Board;
import org.set.template.Template;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Test class for the {@link Blockade} class.
 */
public class BlockadeTest {
    private static Template board;
    private static int numRows = 15;
    private static int numCols = 35;
    private static int hexSize = 35;
    private static Blockade blockade;

    /**
     * Test that a new blockade can be created.
     */
    @BeforeAll
    public static void setUp() {
        blockade = new Blockade();
        board = new Team04Board(25, 30, 25);
    }

    /**
     * Test to set the color of the blockade.
     */
    @Test
    public void setBlockadeColor() {
        blockade.setColor(TileType.BaseCamp);
    }

    /**
     * Test to set the points of the blockade.
     */
    @Test
    public void setBlockadePoints() {
        blockade.setPoints(5);

    }

    /**
     * Tests for cloning a blockade.
     */
    @Test
    public void cloneBlockade() {
        // hexagonGameBoard.loadTileData();
        // blockade.clone(0, 1,hexSize);// this would fail, as there's no tiles in the
        // blockade
    }

    /**
     * Test for drawing blockade.
     * TODO: This still needs some work
     */
    @Test
    public void drawingBlockade() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // TODO: fix drawing blockade
        // hexagonGameBoard.loadTileData();
        // blockade.draw(g2d, 1, hexagonGameBoard.tilesMap);
    }
}
