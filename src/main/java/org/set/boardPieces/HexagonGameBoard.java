package org.set.boardPieces;

import org.json.JSONArray;
import org.json.JSONObject;

import org.set.player.Player;

import org.set.cards.Card;
import java.awt.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HexagonGameBoard extends JPanel {
    public int numRows;
    public int numCols;
    public int hexSize;
    public int cardWidth;
    public int cardHeight;
    public List<Player> players;
    public Map<String, Tile> ParentMap;
    public Map<String, BoardPiece> boardPieces;
    public List<double[]> coordinateList;
    public JSONArray pathInfo;
    public List<Card> PlayerCards;
    public boolean setupChange;
    public HexagonGameBoard(int numRows, int numCols, int hexSize,boolean setupChange) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.hexSize = hexSize;
        this.cardWidth = hexSize * 2;
        this.cardHeight = cardWidth / 2 * 3;
        this.setupChange=setupChange;
        setPreferredSize(new Dimension((int) (numCols * 1.5 * hexSize), (int) (numRows * Math.sqrt(3) * hexSize)));

        ParentMap = new HashMap<>();
        boardPieces = new HashMap<>();
        Terrain.resetWinningCount();
        Blockade.resetCount();
        WinningPiece.resetCount();
        coordinateList = new ArrayList<>(List.of(
                new double[]{6, 4},
                new double[]{0, 8},
                new double[]{-6, 4}
        ));
        
        loadTileData();
        if(pathInfo!=null) {
        	initBoardTeam3();
        }else {
        initBoard();
    }}

    public void initBoard() {
    	getLastTerrain().rotate(2);
        for (double[] coordinates : coordinateList) {
            Terrain terrainLatest=getLastTerrain();
            addTerrain(coordinates[0], coordinates[1], terrainLatest);
        }
        
    }
    public void initBoardTeam3() {
    	coordinateList.clear();
    	 for (int i = 0; i < pathInfo.length(); i++) {
             JSONObject jsonObject = pathInfo.getJSONObject(i);
             double addRow = jsonObject.getDouble("addRow");
             double addCol = jsonObject.getDouble("addCol");
             String sectionType = jsonObject.getString("sectionType");
             int rotation = jsonObject.getInt("rotation");
             
             if(addRow!=0 || addCol!=0) {
             addTerrain(addRow, addCol, getLastTerrain());
             if(i==0) {
            	 boardPieces.remove("Terrain_1"); //remove the template
            	 boardPieces.remove("Blockade_1"); 
             }else{
            	 coordinateList.add(new double[]{addRow, addCol});}
             }

             if(sectionType.startsWith("ElDorado")) {
            	WinningPiece wps=getLastWinningPiece();
            	Tile axisTile=getLastTerrain().axisTile;
            	int[] temp=TileDataDic.tilesMap.get(axisTile.getRow()+","+axisTile.getCol());
            	wps.rotate(rotation, temp[0], temp[1]);
            	if(sectionType.equals("ElDoradoTwo")) {
            		wps.setColor(TileType.Paddle);
            	}
             }else {

                 getLastTerrain().reFillTile(sectionType);
                 getLastTerrain().rotate(rotation);
             }
    	 }
        
        
    }

    public void loadTileData() {
        TileDataDic tdd = new TileDataDic(numRows, numCols, hexSize);
        boardPieces.put(tdd.terrainA.getName(), tdd.terrainA);
        boardPieces.put(tdd.wpa.getName(), tdd.wpa);
        if(setupChange) {
        	IntegrationWith03Board game03= new IntegrationWith03Board();
        	pathInfo=game03.Get03Path();
        	
        }
    }

    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (BoardPiece piece : boardPieces.values()) {
            for (Tile tile : piece.getTiles()) {
                ParentMap.put(tile.getRow() + "," + tile.getCol(), tile);
                
            }
            piece.draw(g2d, hexSize);
        }

        if (players != null && players.size() > 0) {
            for (Player player : players) {
                player.draw(g2d, hexSize);
            }
        }

        if (PlayerCards != null) {
            drawPlayerDeck(g2d);
        }

    }

    public void drawPlayerDeck(Graphics2D g2d) {
        int[] temp = TileDataDic.tilesMap.get("1,"+(numCols-10));
        int startY=10;
        g2d.setColor(Color.BLACK);
        int fontSize = hexSize/3;
        Font font = new Font("Arial", Font.BOLD, fontSize);
        g2d.setFont(font);

        FontMetrics fm = g2d.getFontMetrics();
        String caption = "Current Player's deck: ";
        int captionWidth = fm.stringWidth(caption);
        g2d.drawString(caption, temp[0], temp[1]);
        int maxCardsPerRow = 4;
        int cardSpacing = cardWidth / 10;
        int totalCards = PlayerCards.size();
        int cardsDrawn = 0;

        for (int i = 0; i < totalCards; i++) {
            int row = i / maxCardsPerRow; // Calculate the row index
            int col = i % maxCardsPerRow; // Calculate the column index

            int x = temp[0] + captionWidth + col * (cardWidth + cardSpacing);
            int y = startY + row * (cardHeight + cardSpacing);

            PlayerCards.get(i).draw(g2d, x, y, cardWidth, cardHeight);
            g2d.drawString("Index: " + i, x + 5, y + cardHeight / 2);
            cardsDrawn++;

            if (cardsDrawn >= totalCards) {
                break;
            }
        }
    }
    public Terrain getLastTerrain() {
    	 int maxIndex = 0;
         List<Terrain> terrains = getAllTerrains();
         
         for (Terrain terrain : terrains) {
             String name = terrain.getName();
             int index = Integer.parseInt(name.substring("Terrain_".length()));

             // Check if this index is greater than the current maxIndex
             if (index > maxIndex) {
                 maxIndex = index;
             }

         }
    	return (Terrain) boardPieces.get("Terrain_"+maxIndex);
    }
    public Terrain getFirstTerrain() {
   	 int minIndex = 100;
        List<Terrain> terrains = getAllTerrains();
        
        for (Terrain terrain : terrains) {
            String name = terrain.getName();
            int index = Integer.parseInt(name.substring("Terrain_".length()));

            // Check if this index is greater than the current maxIndex
            if (index < minIndex) {
            	minIndex = index;
            }

        }
   	return (Terrain) boardPieces.get("Terrain_"+minIndex);
   }
    public WinningPiece getLastWinningPiece() {
   	 int maxIndex = 0;
        List<WinningPiece> WinningPieces = getAllWinningPieces();
        
        for (WinningPiece wp : WinningPieces) {
            String name = wp.getName();
            int index = Integer.parseInt(name.substring("Winning_".length()));

            // Check if this index is greater than the current maxIndex
            if (index > maxIndex) {
                maxIndex = index;
            }

        }
   	return (WinningPiece) boardPieces.get("Winning_"+maxIndex);
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
 
    public List<Tile> findStarterTiles(){
    	List<Tile> starterTiles = new ArrayList<>();
    	for (Tile tile : getFirstTerrain().getTiles()) {
    	    if (tile.getType().equals(TileType.Start)) {
    	    	starterTiles.add(tile);
    	    }
    	} return starterTiles;
    }
    
    public void addTerrain(double addRow, double addCol, Terrain terrainA) {
        addWinningPiece(addRow, addCol, getLastWinningPiece());
        Terrain terrainB = terrainA.clone(addRow, addCol,hexSize);
        terrainB.randomizeTiles();
        boardPieces.put(terrainB.getName(), terrainB);
        Set<int[]> neighbors = terrainA.findOverlappingNeighbors(terrainB);
        if (neighbors.size() >= 3 && neighbors.size() <= 5) {
            Blockade blockade = new Blockade();

            for (int[] coordinate : neighbors) {
                int row = coordinate[0];
                int col = coordinate[1];
                Tile temp = new Tile(row,col);
                int[] xy = TileDataDic.tilesMap.get(row+","+col);
                temp.setX(xy[0]);
                temp.setY(xy[1]);
                blockade.addTile(temp);
            }

            blockade.randomizeTiles();
            int indexA = Integer.parseInt(terrainA.getName().substring("Terrain_".length()));
            int indexB = Integer.parseInt(terrainB.getName().substring("Terrain_".length()));
            blockade.setTerrainNeighbors(indexA, indexB);
            blockade.setTerrainAddRowCol(addRow, addCol);
            System.out.println(addRow+" "+ addCol+" "+blockade.name);
            boardPieces.put(blockade.getName(), blockade);
        }
    }

    public void addWinningPiece(double addRow, double addCol, WinningPiece wpa) {
        WinningPiece wpb = wpa.clone(addRow, addCol,hexSize);

        for (Tile tile : wpa.getTiles()) {
            tile.setParent(null);
        }

        boardPieces.remove(wpa.getName());
        boardPieces.put(wpb.getName(), wpb);
    }

    public void removeBlockade(int blockRemoveIndex) {
        Blockade blockRemove = (Blockade) boardPieces.get("Blockade_" + (blockRemoveIndex));
        double[] blockAddRowCol=blockRemove.getTerrainAddRowCol();
        double[] change2=blockRemove.calcRowColChange(blockAddRowCol[0], blockAddRowCol[1]);
        int indexTerrain = blockRemove.getTerrainNeighbors()[0];
        boardPieces.remove("Blockade_" + (blockRemoveIndex));
        
        System.out.println(blockAddRowCol[0]+" "+ blockAddRowCol[1]);
        System.out.println((-change2[0])+" "+(-change2[1]));
        
        for (BoardPiece piece : boardPieces.values()) {
            if (piece.getName().startsWith("Terrain_")) {
                String indexString = piece.getName().substring("Terrain_".length()); 
                int index = Integer.parseInt(indexString);
                if (index <= indexTerrain) {
                    continue;
                }
            }
            
            piece.move(-change2[0], -change2[1],hexSize);
        }
    }
    public Set<Tile> nextToCave(Tile tile) {
    	//you cant move on a cave right?
    	Set<Tile> caveSet = new HashSet<>();
        for (int[] neighbor : tile.getNeighbors()) {
        	Tile temp = ParentMap.get(neighbor[0]+","+neighbor[1]);
        	if(temp==null) {continue;}
        	if(temp.getType()==TileType.Cave) {
        		caveSet.add(temp);
        	}
        }
        
		return caveSet;
    	
    }
    public boolean isValidPosition(int row, int col) {
        String targetKey = row + "," + col;//maybe change change this to Tile 
        Tile temp = ParentMap.get(targetKey);
        if (temp == null || temp.getParent() == null || temp.getParent().startsWith("Blockade_")) {
            System.out.println("This tile doesn't belong in any board piece or is a block.");
            return false;
        }
        if(temp.getType()==TileType.Mountain) {
        	System.out.println("Cannot move to a mountain.");
            return false;
        }
        //can you move on a cave???
        if (players != null && players.size() > 0) {
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