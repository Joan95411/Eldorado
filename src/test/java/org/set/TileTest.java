package org.set;

import org.junit.jupiter.api.Test;
import org.set.boardPieces.Tile;
import org.set.boardPieces.TileType;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import java.util.List;
import java.util.Arrays;

/**
 * Test class for the {@link TileTest} class.
 */
class TileTest {
    // HexagonGameBoard gameBoard = new HexagonGameBoard(15, 35, 30);
    @Test
    void testTileInitialization() {
        Tile tile = new Tile(2, 3);
        assertEquals(2, tile.getRow());
        assertEquals(3, tile.getCol());
        assertEquals(Color.WHITE, tile.getColor());
        assertEquals(0, tile.getPoints());
        assertNull(tile.getParent());
    }

    @Test
    void testSettersAndGetters() {
        Tile tile = new Tile(1, 1);
        tile.setX(10);
        tile.setY(20);
        tile.setType(TileType.Machete);
        tile.setPoints(5);
        tile.setParent("Parent");

        assertEquals(10, tile.getX());
        assertEquals(20, tile.getY());
        assertEquals(Color.GREEN, tile.getColor());
        assertEquals(5, tile.getPoints());
        assertEquals("Parent", tile.getParent());
    }

    @Test
    void testGetNeighbors() {
        Tile tile = new Tile(3, 4);
        List<int[]> expectedNeighbors = Arrays.asList(
                new int[] { 2, 4 }, new int[] { 4, 4 },
                new int[] { 2, 3 }, new int[] { 3, 3 },
                new int[] { 2, 5 }, new int[] { 3, 5 });

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
            assertTrue(found,
                    "Expected neighbor " + Arrays.toString(expectedNeighbor) + " not found in actual neighbors.");
        }
    }
}
