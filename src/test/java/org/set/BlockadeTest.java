package org.set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.boardPieces.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.set.PlayerTest.players;

/**
 * Test class for the {@link org.set.boardPieces.Blockade} class.
 */
public class BlockadeTest {
    private static HexagonGameBoard hexagonGameBoard;
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
       hexagonGameBoard = new HexagonGameBoard(numRows, numCols, hexSize);
   }

    /**
     * Test to set the color of the blockade.
     */
    @Test
    public void setBlockadeColor() {
        blockade.setColor(Color.BLACK);
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
        hexagonGameBoard.loadTileData();
        blockade.clone(0, 1);
    }

    /**
     * Test for drawing blockade.
     */
    @Test
    public void drawingBlockade() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // TODO: fix drawing blockade
//        hexagonGameBoard.loadTileData();
//        blockade.draw(g2d, 1, hexagonGameBoard.tilesMap);
    }
}

