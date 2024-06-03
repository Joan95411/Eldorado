package org.set.boardPieces;

import org.json.JSONObject;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;


public class TileDataDic {
    public static Dotenv dotenv;
    public Terrain terrainA;
    public WinningPiece wpa;
    public Map<String, Tile> tilesMap;

    public TileDataDic(int numRows, int numCols, int hexSize) throws FileNotFoundException {
        terrainA = new Terrain();
        wpa = new WinningPiece();
        tilesMap = new HashMap<>();

        JSONObject tileInfo = Util.getFile("src/main/java/org/set/boardPieces", "tileData.json", "Terrain");
        JSONObject winningPieceInfo = Util.getFile("src/main/java/org/set/boardPieces", "tileData.json", "WinningPiece");

        fillTilesMap(numRows, numCols, hexSize, tileInfo, winningPieceInfo);
    }

    public void fillTilesMap(int numRows, int numCols, int hexSize, JSONObject tileInfo, JSONObject winningPieceInfo) {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int x = col * (int) (1.5 * hexSize);
                int y = row * (int) (Math.sqrt(3) * hexSize);

                if (col % 2 == 1) {
                    y += (int) (Math.sqrt(3) / 2 * hexSize);
                }

                String key = row + "," + col;
                Tile tile = new Tile(row, col);
                tile.setX(x);
                tile.setY(y);
                JSONObject currentTileInfo = tileInfo.optJSONObject(key);
                JSONObject currentWinning = winningPieceInfo.optJSONObject(key);

                if (currentTileInfo != null) {
                    terrainA.addTile(tile);
                } else if (currentWinning != null) {
                    wpa.addTile(tile);
                    currentTileInfo = currentWinning;
                } else {
                    currentTileInfo = new JSONObject();
                    currentTileInfo.put("color", "White");
                    currentTileInfo.put("points", 0);
                }

                String colorName = currentTileInfo.getString("color");
                Color color = Util.getColorFromString(colorName);
                int points = currentTileInfo.getInt("points");
                tile.setColor(color);
                tile.setPoints(points);
                tilesMap.put(key, tile);
            }
        }
    }
}
