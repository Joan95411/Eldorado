package org.set;

import org.junit.jupiter.api.Test;
import org.set.boardPieces.Tile;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Arrays;

class TileTest {
    @Test
    void testTileInitialization() {
        Tile tile = new Tile(2, 3);
        assertEquals(2, tile.getRow());
        assertEquals(3, tile.getCol());
        assertNull(tile.getColor());
        assertEquals(0, tile.getPoints());
        assertNull(tile.getParent());
    }

    @Test
    void testSettersAndGetters() {
        Tile tile = new Tile(1, 1);
        tile.setX(10);
        tile.setY(20);
        tile.setColor(Color.RED);
        tile.setPoints(5);
        tile.setParent("Parent");
        
        assertEquals(10, tile.getX());
        assertEquals(20, tile.getY());
        assertEquals(Color.RED, tile.getColor());
        assertEquals(5, tile.getPoints());
        assertEquals("Parent", tile.getParent());
    }

    @Test
    void testDrawTile() {
        // Create a mock Graphics2D object
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Create a tile and draw it
        Tile tile = new Tile(3, 4);
        tile.drawTile(g2d, 50, 50, 20, Color.BLUE, 3, 4, 10);
        
        // Since this is just a visualization test, we cannot assert anything. 
        // But you can manually inspect the image to verify if the tile is drawn correctly.
    }

    @Test
    void testGetNeighbors() {
        Tile tile = new Tile(3, 4);
        List<int[]> expectedNeighbors = Arrays.asList(
            new int[]{2, 4}, new int[]{4, 4},
            new int[]{2, 3}, new int[]{3, 3},
            new int[]{2, 5}, new int[]{3, 5}
        );

        List<int[]> actualNeighbors = tile.getNeighbors();
        
        assertEquals(expectedNeighbors.size(), actualNeighbors.size());
        for (int[] expectedNeighbor : expectedNeighbors) {
            boolean found = false;
            for (int[] actualNeighbor : actualNeighbors) {
                if (Arrays.equals(expectedNeighbor, actualNeighbor)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Expected neighbor " + Arrays.toString(expectedNeighbor) + " not found in actual neighbors.");
        }
    }
}
