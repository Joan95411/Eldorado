package org.set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HexagonGameBoard extends JPanel  {
    public int numRows;
    public int numCols;
    public int hexSize;
    public Player[] players;
    public JSONObject tileInfo;
    public JSONObject WinningPiece;
    public Map<String, Tile> tilesMap; 
    public List<Terrain> terrains;
    public List<Blockade> blocks;
    public List<WinningPiece> WP;
    public HexagonGameBoard(int numRows, int numCols, int hexSize) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.hexSize = hexSize;
        setPreferredSize(new Dimension((int) (numCols * 1.5 * hexSize), (int) (numRows * Math.sqrt(3) * hexSize)));
        tilesMap = new HashMap<>();
        terrains=new ArrayList<>();
        blocks=new ArrayList<>();
        WP=new ArrayList<>();
        loadTileData();
        addTerrain(6, 4,terrains.get(0));
        addTerrain(0, 8,terrains.get(1));
        addTerrain(-6, 3,terrains.get(2));
        addTerrain(0, 8,terrains.get(3));
        addWinningPiece(0,24,WP.get(0));
        
    }
    
    
    private void loadTileData() {

        Terrain terrainA=new Terrain();
        WinningPiece wpa=new WinningPiece();
        try {
            String tileDataPath = "D:\\Joan\\orderList\\src\\Hex\\tileData.json";
            String tileDataJson = new String(Files.readAllBytes(new File(tileDataPath).toPath()));
            JSONObject tileData = new JSONObject(tileDataJson);
            tileInfo = tileData.getJSONObject("Terrain");
            WinningPiece = tileData.getJSONObject("WinningPiece");
            
        } catch (IOException | JSONException e) {
            // Handle exceptions
            e.printStackTrace();
        }
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
                JSONObject currentWinning = WinningPiece.optJSONObject(key); 
                if (currentTileInfo != null) {

                	terrainA.addTile(tile);
                }
                else if(currentWinning!=null){
                	wpa.addTile(tile);
                	currentTileInfo=currentWinning;
                }
                else{
                	
                	currentTileInfo = new JSONObject();
                    currentTileInfo.put("color", "White");
                    currentTileInfo.put("points", 0);
                }
                String colorName = currentTileInfo.getString("color");
                Color color = getColorFromString(colorName);
                int points = currentTileInfo.getInt("points");
                tile.setColor(color);
                tile.setPoints(points);
                tilesMap.put(key, tile);
                
                }
            }
        terrains.add(terrainA);
        WP.add(wpa);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Terrain terrain : terrains) {
            terrain.draw(g2d, hexSize,tilesMap);
        }
        for (Blockade blockade : blocks) {
        	blockade.draw(g2d, hexSize,tilesMap);
        }
        for (WinningPiece wp : WP) {
        	wp.draw(g2d, hexSize,tilesMap);
        }
        if (players != null && players.length > 0) {
            for (Player player : players) {
                player.draw(g2d, player.getCurrentRow(), player.getCurrentCol(), hexSize, player.getColor());
            }
        }
        
        
    }
    public Color getColorFromString(String colorName) {
        Map<String, Color> colorMap = new HashMap<>();
        colorMap.put("gray", Color.GRAY);
        colorMap.put("red", Color.RED);
        colorMap.put("yellow", Color.YELLOW);
        colorMap.put("green", Color.GREEN);
        colorMap.put("blue", Color.BLUE);
        colorMap.put("pink", Color.PINK);
        colorMap.put("black", Color.BLACK);
        return colorMap.getOrDefault(colorName.toLowerCase(), Color.WHITE);
    }
    
    public void addTerrain(int addRow, int addCol,Terrain terrainA){
        Terrain terrainB = terrainA.clone(addRow, addCol,tilesMap);
        terrainB.randomizeTiles();
        terrains.add(terrainB);
        Set<int[]> neighbors=terrainA.findOverlappingNeighbors(terrainB);
        if (neighbors.size()==5){
        Blockade blockade = new Blockade();
        for (int[] coordinate : neighbors) {
            int row = coordinate[0];
            int col = coordinate[1];
            blockade.addTile(new Tile(row, col));
            //System.out.println(row+" "+col);
        }
        blockade.randomizeTiles();
        blocks.add(blockade);}
        
    }
    public void addWinningPiece(int addRow, int addCol,WinningPiece wpa){
    	WinningPiece wpb = wpa.clone(addRow, addCol,tilesMap);
    	WP.clear();
        WP.add(wpb);
        }
        
    
    
    
    public boolean isValidPosition(int row, int col) {
        if (row < 0 || row >= numRows || col < 0 || col >= numCols) {
        	System.out.println("Going out of border. Choose another move! ");
            return false;
        }
        for (Player player : players) {
            if (player.isAtPosition(row, col)) {
                System.out.println("Someone's already here. Choose another move!");
                return false;
            }
        }
        String key = row + "," + col;
        JSONObject currentTileInfo = tileInfo.optJSONObject(key); // Get tile info for current row and col
        if (currentTileInfo == null) {
        	System.out.println("Going out of border. Choose another move! ");
            return false; // Tile data not found for this position
        }
        return true;
    }
    
    
    
    
    
    
     

  
}

