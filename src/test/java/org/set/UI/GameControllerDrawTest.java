package org.set.UI;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.set.game.Before_game;
import org.set.template.Team03Board;
import org.set.template.Team04Board;
import org.set.template.Template;
import org.set.game.GameController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * UI user test class for the {@link GameController} class.
 */
public class GameControllerDrawTest {
    GameController gameController;
    static Template board = new Team03Board(25, 35, 25);

    @BeforeAll
    public static void setUp() {
        System.setIn(new ByteArrayInputStream("1\nred\n".getBytes()));

        Before_game.addPlayer(board);
        Before_game.placePlayersOnBoard(board);
        System.setIn(System.in);
    }

    // Test if it is possible to start the game on the board of team03 for two
    // players
    // @Test
    // public void basicGameControllerTeam03Test() {
    // try {
    // Template.java board = new Team03Board(25, 35, 25);
    //
    // String input = basicInput + "n\nstop\n";
    // System.setIn(new ByteArrayInputStream(input.getBytes()));
    //
    // new GameController(board);
    // System.setIn(System.in);
    // } catch (NullPointerException e) {
    // assertEquals("Cannot invoke \"org.set.boardPieces.Tile.getType()\" because
    // \"tile\" is null", e.getMessage());
    // System.setIn(System.in);
    // }
    // }

    // Test if it is possible to start the game on the board of team04 for two
    // players
    @Test
    public void basicGameControllerTeam04Test() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later

        try {
            Template board = new Team04Board(25, 35, 25);

            String input = basicInput + "n\nstop\n";
            System.setIn(new ByteArrayInputStream(input.getBytes()));

            new GameController(board);

            System.setIn(sysInBackup);
        } catch (NullPointerException e) {
            assertEquals("Cannot invoke \"org.set.boardPieces.Tile.getType()\" because \"tile\" is null",
                    e.getMessage());
            System.setIn(sysInBackup);
        }
    }

    // Test for choosing a card during the game
    @Test
    public void chooseCardGameControllerTest() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        try {
            Template board = new Team04Board(25, 35, 25);

            String input = basicInput + "y\n1\n";
            System.setIn(new ByteArrayInputStream(input.getBytes()));

            new GameController(board);

            System.setIn(sysInBackup);
        } catch (Exception e) {
            assertEquals("Cannot invoke \"org.set.boardPieces.Tile.getNeighbors()\" because \"tile\" is null",
                    e.getMessage());
            System.setIn(sysInBackup);
        }
    }

    // // Test input helper get player move input
    // // TODO
    // @Test
    // public void inputHelperGetPlayerMoveTest() {
    // System.setIn(new ByteArrayInputStream("stop\n".getBytes()));
    //
    // Player player = board.players.get(0);
    //
    // String coordinates = player.getCurrentRow() + "," + player.getCurrentCol();
    //
    // if (!coordinates.equals("0,0")) {
    // Tile playerStandingTile = board.ParentMap.get(coordinates);
    // int[] movingToInt = InputHelper.getPlayerMoveInput(board,
    // playerStandingTile);
    //
    // System.out.println(movingToInt);
    // }
    //
    // assertNotEquals("0,0", coordinates);
    // }
    //
    // // Test input helper get int list input
    // @Test
    // public void inputHelperGetIntListInput() {
    // System.setIn(new ByteArrayInputStream("stop\nskip\n".getBytes()));
    // Player player = new Player(new Color(0,0,255));
    //
    // List<Asset> assets = player.myDeck.getMyasset();
    //
    // List<Integer> assetIndices = InputHelper.getIntListInput("Please enter
    // indices for your buying assets, separated by commas, or 'stop' to stop this
    // transaction", assets.size() - 1);
    // assertEquals("[-1]", assetIndices.toString());
    //
    // assetIndices = InputHelper.getIntListInput("Please enter indices for your
    // buying assets, separated by commas, or 'stop' to stop this transaction",
    // assets.size() - 1);
    // assertEquals("[-2]", assetIndices.toString());
    // }
}
