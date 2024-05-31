package org.set.UI;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.fail;

import java.awt.*;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.boardPieces.Terrain;
import org.set.boardPieces.Tile;
import org.set.boardPieces.WinningPiece;
import org.set.boardPieces.TileDataDic;

public class tileDataDicTest extends JPanel {
    private TileDataDic tdd;
    private JFrame frame;
    private Graphics2D g2d;

    int numRows = 15;
    int numCols = 15;
    int hexSize = 30;

    @BeforeEach
    void setUp() {
        tdd = new TileDataDic(numRows, numCols, hexSize);
        setPreferredSize(new Dimension((int) (numCols * 1.5 * hexSize), (int) (numRows * Math.sqrt(3) * hexSize)));

        // Create a JFrame and add the panel to it
        frame = new JFrame("GameBoardTest");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the frame only
        frame.getContentPane().add(this);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);

        // Get the Graphics2D object
        g2d = (Graphics2D) getGraphics();
        if (g2d == null) {
            System.err.println("Graphics context is null. Skipping the test.");
            frame.dispose(); // Close the frame if unable to get the graphics context
        }
        repaint();
    }

    private void waitForUserInput() {
    	// Prompt the user to continue
        System.out.println("Press any key to continue to the next test. Press f if failed");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        
        if (input.equalsIgnoreCase("f")) {
            fail("Test failed by user input.");
        }

        // Dispose the frame after the test is finished
        frame.dispose();
    }

    @Test
    public void testTerrainDraw() {
        Terrain ta = tdd.terrainA;
        
        ta.draw(g2d, hexSize);
        waitForUserInput();
    }
    
    @Test
    public void testWPDraw() {
        // Draw the terrain
    	WinningPiece wp = tdd.wpa;
        
        wp.draw(g2d, hexSize);
        waitForUserInput();
    }
    @Test
    public void testTileRotate() {
        // Draw the terrain
    	WinningPiece wp = tdd.wpa;
//        for(Tile tile:wp.getTiles()) {
//        	tile.rotate60Clockwise();
//        }

        wp.draw(g2d, hexSize);
        waitForUserInput();
    }
}


