package org.set;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {
    private ArrayList<Player> players = new ArrayList<>();

    @Test
    public void testPlayer() {
        players.add(new Player(Color.RED));

        //        TODO: this should not be possible because the color has already been used
        players.add(new Player(Color.RED));
    }

    @Test
    public void testPlayerColor() {
        Color color = Color.BLACK;

        players.add(new Player(color));

        assertEquals(players.get(0).getColor(), color);
    }

    @Test
    public void testPlayerPosition() {
        int row = 1;
        int col = 2;

        players.add(new Player(Color.BLUE));

        players.get(0).setPlayerPosition(row,col);

        assertEquals(players.get(0).getCurrentRow(), row);
        assertEquals(players.get(0).getCurrentCol(), col);

        assertTrue(players.get(0).isAtPosition(row, col));
    }

    @Test
    public void testPlayerNeighbor() {
        players.add(new Player(Color.YELLOW));

        assertEquals(players.get(0).getNeighborLocations().toString(),"[-1,0, 1,0, 0,-1, -1,-1, 0,1, -1,1]");
    }
}