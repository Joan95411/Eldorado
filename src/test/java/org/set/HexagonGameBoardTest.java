package org.set;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.set.boardPieces.HexagonGameBoard;
import org.set.boardPieces.Util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HexagonGameBoardTest {
   public static Dotenv dotenv = Dotenv.configure().load();
   public static HexagonGameBoard hexagonGameBoard = new HexagonGameBoard(5, 5, 30);

   @Test
   public void testHexagonGameBoard() {
       assertNotNull(hexagonGameBoard);
       assertNotNull(hexagonGameBoard.getPreferredSize());

       assertEquals(hexagonGameBoard.numRows, 5);
       assertEquals(hexagonGameBoard.numCols, 5);
       assertEquals(hexagonGameBoard.hexSize, 50);
   }

   @Test
   public void testHexagonGameBoardLocation() {
       assertEquals(hexagonGameBoard.getLocation(), new Point(0,0));
       hexagonGameBoard.setLocation(new Point(1,1));
       assertEquals(hexagonGameBoard.getLocation(), new Point(1,1));
   }

   @Test
   public void testHexagonGameBoardPlayer() {
       Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
       int row = 0;
       int col = 0;
       int maxPlayers = 4;

//        TODO: This worked but is there a nicer way?
       BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
       Graphics2D g2d = image.createGraphics();

       hexagonGameBoard.paintComponent(g2d);

       for (int i = 0; i < maxPlayers; i++) {
    	   
           hexagonGameBoard.players.add(new Player(colors[i]));
           assertEquals(hexagonGameBoard.players.size(), i + 1);

           int playerId = hexagonGameBoard.players.size() - 1;

//            Check the id and color of the player
//            assertEquals(hexagonGameBoard.players.get(playerId).id, hexagonGameBoard.players.size());
           assertEquals(hexagonGameBoard.players.get(playerId).color, colors[i]);

//            Set the players position and check if it actually does set the position
           hexagonGameBoard.players.get(playerId).setPlayerPosition(row + i, col + i);

           assertEquals(hexagonGameBoard.players.get(playerId).getCurrentCol(), col + i);
           assertEquals(hexagonGameBoard.players.get(playerId).getCurrentRow(), row + i);
       }

//        Check if the players are added
       assertTrue(hexagonGameBoard.players.size() <= maxPlayers);

//        TODO: This should fail because the max amount of players is reached
       hexagonGameBoard.players.add(new Player(colors[0]));
   }

   @Test
   public void testHexagonLoadTileData() throws IOException {
       

       HexagonGameBoard hexagonGameBoard = new HexagonGameBoard(5, 5, 50);
       String tileDataPath = dotenv.get("TILEDATA_PATH");
       if (tileDataPath == null) tileDataPath = "src/main/java/org/set/boardPieces";
       String filename="tileData.json";
       JSONObject tileInfo = Util.readJsonData(tileDataPath, filename, "Terrain");
       JSONObject winningPieceInfo = Util.readJsonData(tileDataPath, filename, "WinningPiece");

       
   }
}