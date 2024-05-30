package org.set.UI;

import java.awt.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.GameController;
import org.set.Player;
import org.set.boardPieces.HexagonGameBoard;

public class GameControllerTest {

    GameController gameController;
    @BeforeEach
    public void setUp(){
        gameController = new GameController(new HexagonGameBoard(0,0,0));
    }

    @Test
    public void playerDrawCardTest(){
        Player player = new Player(Color.GREEN);
    }

}
