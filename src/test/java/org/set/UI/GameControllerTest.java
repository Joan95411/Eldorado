package org.set.UI;

import java.awt.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.player.Player;

import org.set.game.GameController;

import org.set.boardPieces.HexagonGameBoard;
import org.set.game.GameController;

/**
 * UI user test class for the {@link GameController} class.
 */
public class GameControllerTest {
    GameController gameController;

    /**
     * For each UI game controller test a new game controller should be created.
     */
    @BeforeEach
    public void setUp(){
        gameController = new GameController(new HexagonGameBoard(0,0,0,false));
    }

    /**
     * Test for drawing the players cards
     * TODO: This test is not finished
     */
    @Test
    public void playerDrawCardTest(){
        Player player = new Player(Color.GREEN);
    }

}
