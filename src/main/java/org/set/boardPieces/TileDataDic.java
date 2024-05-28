package org.set.boardPieces;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import io.github.cdimascio.dotenv.DotenvException;
import org.json.JSONObject;

import io.github.cdimascio.dotenv.Dotenv;

public class TileDataDic {
	public static Dotenv dotenv;
	public Terrain terrainA;
	public WinningPiece wpa;
	public Map<String, Tile> tilesMap;

	public TileDataDic(int numRows, int numCols, int hexSize) {
        terrainA = new Terrain();
        wpa = new WinningPiece();
        tilesMap = new HashMap<>();

        String tileDataPath;

        try {
            dotenv = Dotenv.configure().load();
            tileDataPath = dotenv.get("TILEDATA_PATH");
        } catch (DotenvException e) {
            tileDataPath = "src/main/java/org/set/boardPieces";
        }

        String filename = "tileData.json";
        JSONObject tileInfo = Util.readJsonData(tileDataPath, filename, "Terrain");
        JSONObject winningPieceInfo = Util.readJsonData(tileDataPath, filename, "WinningPiece");

        if (tileInfo == null || winningPieceInfo == null) {
            System.err.println("Tile data not found or is not in the expected format.");
            return;
        }

        fillTilesMap(numRows, numCols, hexSize,tileInfo,winningPieceInfo);
    }

	public void fillTilesMap(int numRows, int numCols, int hexSize,JSONObject tileInfo, JSONObject winningPieceInfo) {
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
                } else if (currentWinning!=null) {
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
