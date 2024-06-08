package org.set.boardPieces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;


public class TileDataDic {
    public static Dotenv dotenv;
    public Terrain terrainA;
    public WinningPiece wpa;
    public static Map<String, int[]> tilesMap;
    public static Map<String, int[]> coordinateMap;
    
    
    public TileDataDic(int numRows, int numCols, int hexSize) {
        terrainA = new Terrain();
        wpa = new WinningPiece();
        tilesMap = new HashMap<>();
        coordinateMap = new HashMap<>();
        String tileDataPath;

        try {
            dotenv = Dotenv.configure().load();
            tileDataPath = dotenv.get("TILEDATA_PATH");
        } catch (DotenvException e) {
            tileDataPath = "src/main/java/org/set/boardPieces";
        }

        String filename = "tileData2.json";
        JSONObject tileInfo = Util.readJsonData(tileDataPath, filename, "Terrain");
        JSONObject winningPieceInfo = Util.readJsonData(tileDataPath, filename, "WinningPiece");

        if (tileInfo == null || winningPieceInfo == null) {
            System.err.println("Tile data not found or is not in the expected format.");
            return;
        }

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
                    currentTileInfo.put("qr", "-100,-100");
                } else {
                    currentTileInfo = new JSONObject();
                    currentTileInfo.put("color", "White");
                    currentTileInfo.put("points", 0);
                    currentTileInfo.put("qr", "-100,-100");
                }

                String colorName = currentTileInfo.getString("color");
                TileType type=Util.getTileTypeFromString(colorName);
                int points = currentTileInfo.getInt("points");
                
                String qrString=currentTileInfo.getString("qr");
                String[] parts = qrString.split(",");
                tile.setQ(Integer.parseInt(parts[0]));
                tile.setR(Integer.parseInt(parts[1]));;
                tile.setType(type);
                tile.setPoints(points);
                
                tilesMap.put(key, new int[] {x,y});
                coordinateMap.put(x+","+y, new int[] {row,col});
            }
        }
        
    }
    public static int[] findClosestCoordinate(int x, int y) {
        double minDistance = Double.MAX_VALUE;
        int[] closestCoordinate = null;
        int[] tileFoundCorMap=coordinateMap.get(x+","+y);
        if(tileFoundCorMap!=null) {
        	closestCoordinate = new int[] { tileFoundCorMap[0], tileFoundCorMap[1],x,y };
        	return closestCoordinate; }
        for (Entry<String, int[]> entry : tilesMap.entrySet()) {
            String key = entry.getKey();
            String[] parts = key.split(",");

            int tileX = entry.getValue()[0];
            int tileY = entry.getValue()[1];

            double distance = Math.sqrt(Math.pow(tileX - x, 2) + Math.pow(tileY - y, 2));
            if (distance < minDistance) {
                minDistance = distance;
                closestCoordinate = new int[] { Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),tileX,tileY };
            }
        }

        return closestCoordinate;
    }
    
    

	
}
