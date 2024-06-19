package org.set.UI;

import java.awt.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.player.Player;
import org.set.template.Team04Board;
import org.set.template.Template;
import org.set.game.GameController;

/**
 * UI user test class for the {@link GameController} class.
 */
public class GameControllerDrawTest {
    GameController gameController;

    /**
     * For each UI game controller test a new game controller should be created.
     */
    @BeforeEach
    public void setUp(){
    	Template board=new Team04Board(25,35,25);
        gameController = new GameController(board);
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
