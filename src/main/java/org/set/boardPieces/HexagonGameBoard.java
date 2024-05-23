package org.set.boardPieces;
import org.json.JSONException;
import org.json.JSONObject;
import org.set.Card;
import org.set.Player;

import io.github.cdimascio.dotenv.Dotenv;


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
import java.util.stream.Collectors;

public class HexagonGameBoard extends JPanel  {
    public static Dotenv dotenv = Dotenv.configure().load();

    public int numRows;
    public int numCols;
    public int hexSize;
    public int cardWidth;
    public int cardHeight;
    public Player[] players;
    public JSONObject tileInfo;
    public JSONObject WinningPiece;
    public Map<String, Tile> tilesMap; 
    public Map<String, boardPiece> boardPieces;
    public List<int[]> coordinateList;
    public List<Card> PlayerCards;

    public HexagonGameBoard(int numRows, int numCols, int hexSize) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.hexSize = hexSize;
        this.cardWidth=hexSize*2;
        this.cardHeight=cardWidth/2*3;
        setPreferredSize(new Dimension((int) (numCols * 1.5 * hexSize), (int) (numRows * Math.sqrt(3) * hexSize)));
        tilesMap = new HashMap<>();
        boardPieces = new HashMap<>();
        coordinateList= Arrays.asList(
    		    new int[]{6, 4},
    		    new int[]{0, 8},
    		    new int[]{-6, 4}
    		    //new int[]{0, 8}
    		);
    	loadTileData();
        initBoard();
    }
    
    private void initBoard() {
    	int i=1;
    		for (int[] coordinates : coordinateList) {
            	Terrain modelter=(Terrain) boardPieces.get("Terrain_"+i);

    		    addTerrain(coordinates[0], coordinates[1], modelter);
    		    i++;
    		}

    }
    private void loadTileData() {
        Terrain terrainA = new Terrain();
        WinningPiece wpa = new WinningPiece();
        try {
        	String tileDataPath = dotenv.get("TILEDATA_PATH");
            if (tileDataPath == null) tileDataPath = "src/main/java/org/set/tileData.json";
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
                Color color = Util.getColorFromString(colorName);
                int points = currentTileInfo.getInt("points");
                tile.setColor(color);
                tile.setPoints(points);
                tilesMap.put(key, tile);

                }
            }
        boardPieces.put(terrainA.getName(),terrainA);
        boardPieces.put(wpa.getName(),wpa);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (boardPiece piece : boardPieces.values()) {
            piece.draw(g2d, hexSize, tilesMap);
        }
        

        if (players != null && players.length > 0) {
            for (Player player : players) {
                player.draw(g2d, player.getCurrentRow(), player.getCurrentCol(), hexSize, player.getColor());

            } }
        if(PlayerCards!=null){
        	drawPlayerDeck(g2d);
        }

    }

    public void drawPlayerDeck(Graphics2D g2d) {
    	Tile temp=tilesMap.get("1,6");
        g2d.setColor(Color.BLACK);
        int fontSize = 16;
        Font font = new Font("Arial", Font.BOLD, fontSize);
        g2d.setFont(font);

    	FontMetrics fm = g2d.getFontMetrics();
        String caption = "Current Player's deck: ";
        int captionWidth = fm.stringWidth(caption);
        g2d.drawString(caption, temp.getX(), temp.getY());

        for (int i = 0; i < PlayerCards.size(); i++) {
        	PlayerCards.get(i).draw(g2d,temp.getX()+captionWidth+i*(cardWidth+cardWidth/10),10,cardWidth,cardHeight);
        }
    }
    public List<Terrain> getAllTerrains() {
        return boardPieces.keySet().stream()
                .filter(key -> key.startsWith("Terrain_"))
                .map(key -> (Terrain) boardPieces.get(key))
                .collect(Collectors.toList());
    }
    public List<Blockade> getAllBlockades() {
        return boardPieces.keySet().stream()
                .filter(key -> key.startsWith("Blockade_"))
                .map(key -> (Blockade) boardPieces.get(key))
                .collect(Collectors.toList());
    }
    public List<WinningPiece> getAllWinningPieces() {
        return boardPieces.keySet().stream()
                .filter(key -> key.startsWith("Winning_"))
                .map(key -> (WinningPiece) boardPieces.get(key))
                .collect(Collectors.toList());
    }



    public void addTerrain(int addRow, int addCol,Terrain terrainA){
    	List<WinningPiece> WP=getAllWinningPieces();
    	addWinningPiece(addRow, addCol, WP.get(WP.size() - 1));
        Terrain terrainB = terrainA.clone(addRow, addCol,tilesMap);
        terrainB.randomizeTiles();
        boardPieces.put(terrainB.getName(), terrainB);
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
        boardPieces.put(blockade.getName(), blockade);
        }

    }
    
    public void addWinningPiece(int addRow, int addCol,WinningPiece wpa){
    	WinningPiece wpb = wpa.clone(addRow, addCol,tilesMap);
    	for(Tile tile: wpa.getTiles()){
    		tile.setParent(null);
    	}
    	boardPieces.remove(wpa.getName());
    	for(Tile tile: wpb.getTiles()){
    		tile.setParent("Winning");
    	}
        boardPieces.put(wpb.getName(), wpb);

        }
    
    public void removeBlockade(int currentTerrainIndex) {
    	boardPieces.remove("Blockade_"+(currentTerrainIndex+1));
        int[] change;
        if (coordinateList.get(currentTerrainIndex)[0] > 0) {
            change = new int[]{-1, 0}; // Move one unit up
        } else if (coordinateList.get(currentTerrainIndex)[0] < 0) {
            change = new int[]{1, 0}; // Move one unit down
        } else {
            change = new int[]{0, -1}; // Move one unit left
        }
        for (boardPiece piece : boardPieces.values()) {
        	if(piece.getName().startsWith("Terrain_")){
        		String indexString = piece.getName().substring("Terrain_".length()); // Extract the substring after "Terrain_"
        	    int index = Integer.parseInt(indexString);
        	    if(index<=currentTerrainIndex+1){
        		continue;}
        	}
            piece.move(change[0], change[1], tilesMap);
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

