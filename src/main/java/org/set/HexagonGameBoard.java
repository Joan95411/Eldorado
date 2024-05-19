package org.set;
import org.json.JSONException;
import org.json.JSONObject;
import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
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
    public List<int[]> coordinateList;
    public List<Card> PlayerCards;
    public HexagonGameBoard(int numRows, int numCols, int hexSize) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.hexSize = hexSize;
        setPreferredSize(new Dimension((int) (numCols * 1.5 * hexSize), (int) (numRows * Math.sqrt(3) * hexSize)));
        tilesMap = new HashMap<>();
        terrains=new ArrayList<>();
        blocks=new ArrayList<>();
        WP=new ArrayList<>();
        coordinateList= Arrays.asList(
    		    new int[]{6, 4},
    		    new int[]{0, 8},
    		    new int[]{-6, 4},
    		    new int[]{0, 8}
    		);
    	loadTileData();
        initBoard();
        
    	
    }
    
    private void initBoard() {
    	int i=0;
    		for (int[] coordinates : coordinateList) {
    		    addTerrain(coordinates[0], coordinates[1], terrains.get(i));
    		    i++;
    		}
    		
    }
    private void loadTileData() {

        Terrain terrainA=new Terrain();
        WinningPiece wpa=new WinningPiece();
        try {
            String tileDataPath = "src\\main\\java\\org\\set\\tileData.json";
            String tileDataJson = new String(Files.readAllBytes(new File(tileDataPath).toPath()));
            JSONObject tileData = new JSONObject(tileDataJson);
            tileInfo = tileData.getJSONObject("Terrain");
            WinningPiece = tileData.getJSONObject("WinningPiece");
            
        } catch (IOException | JSONException e) {
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
        
        WP.get(WP.size() - 1).draw(g2d, hexSize,tilesMap);
        
        
        if (players != null && players.length > 0) {
            for (Player player : players) {
                player.draw(g2d, player.getCurrentRow(), player.getCurrentCol(), hexSize, player.getColor());
                
            } }
        if(PlayerCards!=null){
        g2d.setColor(Color.BLACK);
        int fontSize = 16; 
        Font font = new Font("Arial", Font.BOLD, fontSize); 
        g2d.setFont(font);
        g2d.drawString("Current Player's deck: ", 380, 60);
        for (int i = 0; i < PlayerCards.size(); i++) {
        	PlayerCards.get(i).draw(g2d,550+i*85,10);
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
        colorMap.put("cyan", Color.CYAN);
        return colorMap.getOrDefault(colorName.toLowerCase(), Color.WHITE);
    }
    
    public void addTerrain(int addRow, int addCol,Terrain terrainA){
    	addWinningPiece(addRow, addCol, WP.get(WP.size() - 1));
        Terrain terrainB = terrainA.clone(addRow, addCol,tilesMap);
        terrainB.randomizeTiles();
        terrains.add(terrainB);
        Set<int[]> neighbors=terrainA.findOverlappingNeighbors(terrainB);
        if (neighbors.size()<=5){
        Blockade blockade = new Blockade();
        for (int[] coordinate : neighbors) {
            int row = coordinate[0];
            int col = coordinate[1];
            Tile temp=new Tile(row, col);
            blockade.addTile(temp);
            
        }
        blockade.randomizeTiles();
        blocks.add(blockade);
        }
        
    }
    
    public void addWinningPiece(int addRow, int addCol,WinningPiece wpa){
    	WinningPiece wpb = wpa.clone(addRow, addCol,tilesMap);
    	for(Tile tile: wpa.getTiles()){
    		tile.setParent(null);
    	}
    	for(Tile tile: wpb.getTiles()){
    		tile.setParent("Winning");
    	}
        WP.add(wpb);
    	//System.out.println(addRow+" "+addCol);
        }
    
    public void removeBlockade(int currentTerrainIndex) {
    	blocks.remove(0);
        int[] change;
        if (coordinateList.get(currentTerrainIndex)[0] > 0) {
            change = new int[]{-1, 0}; // Move one unit up
        } else if (coordinateList.get(currentTerrainIndex)[0] < 0) {
            change = new int[]{1, 0}; // Move one unit down
        } else {
            change = new int[]{0, -1}; // Move one unit left
        }
        // Iterate over the terrains starting from the terrain after the current one
        for (int i = currentTerrainIndex + 1; i < terrains.size(); i++) {
        	
        	terrains.get(i).move(change[0], change[1], tilesMap);

        }
        WP.get(WP.size() - 1).move(change[0], change[1], tilesMap);
        for(int i = 0 ; i < blocks.size(); i++){
        	blocks.get(i).move(change[0], change[1], tilesMap);
        }
        
    }
    
    
    
    public boolean isValidPosition(int row, int col) {
        if (row < 0 || row >= numRows || col < 0 || col >= numCols) {
        	System.out.println("Going out of border. Choose another move! ");
            return false;
        }
        String targetKey = row+","+col;
        Tile temp = tilesMap.get(targetKey);
	    if(temp.getParent()==null){
	    	System.out.println("This tile doesn't belong in any board piece.");
	    	return false;
	    }
	    if (players != null && players.length > 0) {
        for (Player player : players) {
            if (player.isAtPosition(row, col)) {
                System.out.println("Someone's already here. Choose another move!");
                return false;
            }
        }
	    }
        return true;
    }
    
    
    
    
    
    
     

  
}

