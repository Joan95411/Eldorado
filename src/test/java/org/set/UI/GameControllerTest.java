package org.set.UI;

import java.awt.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.Player;
import org.set.boardPieces.HexagonGameBoard;
import org.set.gameController.GameController;

public class GameControllerTest {

    GameController gameController;
    @BeforeEach
    public void setUp(){
        gameController = new GameController(new HexagonGameBoard(0,0,0,false));
    }

    @Test
    public void playerDrawCardTest(){
        Player player = new Player(Color.GREEN);
    }

}