package org.set.boardPieces;

import org.json.JSONArray;
import org.json.JSONObject;
import org.set.InputHelper;
import org.set.Player;

import org.set.cards.Card;

import java.awt.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    public HexagonGameBoard(int numRows, int numCols, int hexSize) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.hexSize = hexSize;
        this.cardWidth = hexSize * 2;
        this.cardHeight = cardWidth / 2 * 3;
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
             }else{
            	 coordinateList.add(new double[]{addRow, addCol});}
             }

             if(sectionType.startsWith("ElDorado")) {
            	WinningPiece wps=getLastWinningPiece();
            	Tile axisTile=getLastTerrain().axisTile;
            	Tile temp=TileDataDic.tilesMap.get(axisTile.getRow()+","+axisTile.getCol());
            	wps.rotate(rotation, temp.getX(), temp.getY());
            	if(sectionType.equals("ElDoradoTwo")) {
            		wps.setColor(Color.CYAN);
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
        boolean changeBoard=InputHelper.getYesNoInput("Do you want to use Team 3's set up?");
        if(changeBoard) {
        	//Board.getPath(); //get this from team 3
        	String path="Serpentine";//placeholder
        	pathInfo=tdd.readPathData(path);
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
        Tile temp = TileDataDic.tilesMap.get("1,"+(numCols-10));
        int startY=10;
        g2d.setColor(Color.BLACK);
        int fontSize = hexSize/3;
        Font font = new Font("Arial", Font.BOLD, fontSize);
        g2d.setFont(font);

        FontMetrics fm = g2d.getFontMetrics();
        String caption = "Current Player's deck: ";
        int captionWidth = fm.stringWidth(caption);
        g2d.drawString(caption, temp.getX(), temp.getY());
        int maxCardsPerRow = 4;
        int cardSpacing = cardWidth / 10;
        int totalCards = PlayerCards.size();
        int cardsDrawn = 0;

        for (int i = 0; i < totalCards; i++) {
            int row = i / maxCardsPerRow; // Calculate the row index
            int col = i % maxCardsPerRow; // Calculate the column index

            int x = temp.getX() + captionWidth + col * (cardWidth + cardSpacing);
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
    	Color targetColor=new Color(0,100,0);
    	for (Tile tile : getFirstTerrain().getTiles()) {
    	    if (tile.getColor().equals(targetColor)) {
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
                Tile temp = TileDataDic.tilesMap.get(row + "," + col);
                blockade.addTile(temp);
            }

            blockade.randomizeTiles();
            int indexA = Integer.parseInt(terrainA.getName().substring("Terrain_".length()));
            int indexB = Integer.parseInt(terrainB.getName().substring("Terrain_".length()));
            blockade.setTerrainNeighbors(indexA, indexB);
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
        int indexTerrain = blockRemove.getTerrainNeighbors()[0];
        boardPieces.remove("Blockade_" + (blockRemoveIndex));
        double[] change;

        if (coordinateList.get(indexTerrain - 1)[0] > 0) {
            change = new double[] { -1, 0 }; // Move one unit up
        } else if (coordinateList.get(indexTerrain - 1)[0] < 0) {
            change = new double[] { 1, 0 }; // Move one unit down
        } else {
            change = new double[] { -0.5, -0.5 }; // Move one unit left
        }

        for (BoardPiece piece : boardPieces.values()) {
            if (piece.getName().startsWith("Terrain_")) {
                String indexString = piece.getName().substring("Terrain_".length()); 
                int index = Integer.parseInt(indexString);
                if (index <= indexTerrain) {
                    continue;
                }
            }
            piece.move(change[0], change[1],hexSize);
        }
    }

    public boolean isValidPosition(int row, int col) {
        String targetKey = row + "," + col;
        Tile temp = ParentMap.get(targetKey);
        if (temp == null || temp.getParent() == null || temp.getParent().startsWith("Blockade_")) {
            System.out.println("This tile doesn't belong in any board piece or is a block.");
            return false;
        }

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