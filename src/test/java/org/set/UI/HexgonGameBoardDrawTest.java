package org.set.UI;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.template.Team04Board;
import org.set.template.Template;
import org.set.boardPieces.BoardPiece;

/**
 * UI user test class for the {@link HexagonGameBoard} class.
 */
public class HexgonGameBoardDrawTest {
    private Template board;
    private JFrame frame;

    int numRows = 15;
    int numCols = 35;
    int hexSize = 30;

    /**
     * For each UI hexagon game board draw test a new hexagon game board should be
     * created.
     */
    @BeforeEach
    void setUp() {
        board = new Team04Board(numRows, numCols, hexSize);

        frame = new JFrame("GameBoardTest");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(board);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * This is used for every UI hexagon game board draw test to get the input of
     * the user
     * This does not test anything, it is just to get the user input
     */
    private void waitForUserInput() {
        System.out.println("Press any key to continue to the next test. Press f if failed");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("f")) {
            fail("Test failed by user input.");
        }
        frame.dispose();
    }

    /**
     * Test for removing a blockade
     */
    @Test
    public void testRemoveBlockade() {
        System.out.println("This should remove the first blockade");
        board.removeBlockade(1);
        board.repaint();
        waitForUserInput();
    }

    /**
     * Test for loading the board tile data
     */
    @Test
    public void testLoadModelData() {
        System.out.println("This should load the original json file and draw the pieces on board.");
        Map<String, BoardPiece> bp = board.boardPieces;
        bp.clear();
        board.loadTileData();

        board.repaint();
        waitForUserInput();
    }

    /**
     * Test for adding the terrain to the board
     */
    @Test
    public void testAddTerrain() {
        System.out.println("This should add 2 terrains cloned after the json file terrain.");
        System.out.println("The first cloned terrain should have a blockade after.");
        System.out.println("This second cloned terrain shouldn't have a blockade after.");
        Map<String, BoardPiece> bp = board.boardPieces;
        bp.clear();
        board.loadTileData();

        board.addTerrain(0, 8, board.getAllTerrains().get(0));// should have block
        board.addTerrain(5, 4, board.getAllTerrains().get(1));// should not have block

        board.repaint();
        waitForUserInput();
    }
}
