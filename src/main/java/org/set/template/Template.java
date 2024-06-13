package org.set.template;

import org.json.JSONArray;
import org.json.JSONObject;
import org.set.player.Asset;
import org.set.player.Player;
import org.set.tokens.Token;
import org.set.boardPieces.Blockade;
import org.set.boardPieces.BoardPiece;
import org.set.boardPieces.IntegrationWith03Board;
import org.set.boardPieces.Terrain;
import org.set.boardPieces.Tile;
import org.set.boardPieces.TileDataDic;
import org.set.boardPieces.TileType;
import org.set.boardPieces.WinningPiece;
import org.set.cards.Card;
import org.set.marketplace.MarketPlace;

import java.awt.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public abstract class Template extends JPanel {
    public int numRows;
    public int numCols;
    public int hexSize;
    public int cardWidth;
    public int cardHeight;
    public List<Player> players;
    public Map<String, Tile> ParentMap;
    public List<Tile> caveSet;
    public Map<String, BoardPiece> boardPieces;
    
    public JSONArray pathInfo;
    public Player currentPlayer;
    public MarketPlace market;
    public boolean discardPhase;
    public Template(int numRows, int numCols, int hexSize) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.hexSize = hexSize;
        this.cardWidth = hexSize * 2;
        this.cardHeight = cardWidth / 2 * 3;
        setPreferredSize(new Dimension((int) (numCols * 1.5 * hexSize), (int) (numRows * Math.sqrt(3) * hexSize)));
        caveSet = new ArrayList<>();
        ParentMap = new HashMap<>();
        boardPieces = new HashMap<>();
        discardPhase=false;
        Terrain.resetWinningCount();
        Blockade.resetCount();
        WinningPiece.resetCount();
        
        loadTileData();
        initBoard();
    }


	abstract public void initBoard();

	abstract public void loadTileData();

    
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
        
        if (currentPlayer != null) {
        	List<Asset> assets=currentPlayer.myDeck.getMyasset();
        	if(assets.size()>0) {
        		int[] temp = TileDataDic.tilesMap.get((numRows - 10) + ",1");
        		drawAssets(g2d, assets, "Current Player's assets: ", temp, 10, cardWidth / 10);
        		}
        	List<Card> playerDiscardCards = currentPlayer.myDeck.getDiscardPile();
        	if (playerDiscardCards != null && discardPhase) {
        	    int[] temp = TileDataDic.tilesMap.get((numRows - 6) + ",19");
        	    drawAssets(g2d, playerDiscardCards, "Current Player's Discard pile: ", temp, 8, cardWidth / 10);
        	}
        }
        if(market!=null) {
        if(market.getCurrentMarketBoard()!=null) {
        	int[] temp = TileDataDic.tilesMap.get((numRows - 6) + ",1");
            Map<Card, Integer> currentMarket = market.getCurrentMarketBoard();
            drawPiles(g2d, currentMarket, "Current Market: ", temp, 6,cardWidth / 4);
        }
        if(market.getMarketBoardOptions()!=null&&discardPhase==false) {
        	int[] temp = TileDataDic.tilesMap.get((numRows-6)+",19");
            HashMap<Card, Integer> marketoption = market.getMarketBoardOptions();
            drawPiles(g2d, marketoption, "More Market Options: ", temp, 6,cardWidth / 4);
        }}
    }
    
    
    
    
    
    
    public void drawPiles(Graphics2D g2d, Map<Card, Integer> currentMarket, String caption, int[] temp, int maxCardsPerRow,int cardSpacing) {
        g2d.setColor(Color.BLACK);
        int fontSize = hexSize / 3;
        Font font = new Font("Arial", Font.BOLD, fontSize);
        g2d.setFont(font);

        FontMetrics fm = g2d.getFontMetrics();
        int captionWidth = fm.stringWidth(caption);
        g2d.drawString(caption, temp[0], temp[1]);
        int totalCards = currentMarket.size();
        int cardsDrawn = 0;
        int i=0;
        for (Entry<Card, Integer> entry : currentMarket.entrySet()) {
            Card key = entry.getKey();
            Integer value = entry.getValue(); 
            int row = i / maxCardsPerRow; // Calculate the row index
            int col = i % maxCardsPerRow; // Calculate the column index

            int x = temp[0] + captionWidth + col * (cardWidth + cardSpacing);
            int y = temp[1] + row * (cardHeight + cardSpacing);

            key.draw(g2d, x, y, cardWidth, cardHeight);
            g2d.drawString("Index: " + i, x + 5, y + cardHeight / 2);
            g2d.drawString("Left: " + value, x + 5, (int)(y + cardHeight / 1.5));
            g2d.drawString( key.name, x , y );
            cardsDrawn++;
            
            if (cardsDrawn >= totalCards) {
                break;
            }i++;
        }
    }
    public void drawAssets(Graphics2D g2d, List<? extends Asset> assets, String caption, int[] temp, int maxAssetsPerRow, int assetSpacing) {
        g2d.setColor(Color.BLACK);
        int fontSize = hexSize / 3;
        Font font = new Font("Arial", Font.BOLD, fontSize);
        g2d.setFont(font);

        FontMetrics fm = g2d.getFontMetrics();
        int captionWidth = fm.stringWidth(caption);
        g2d.drawString(caption, temp[0], temp[1]);

        int assetWidth = cardWidth; // Assuming cardWidth is the same for both cards and tokens
        int assetHeight = cardHeight; // Assuming cardHeight is the same for both cards and tokens
        int totalAssets = assets.size();
        int assetsDrawn = 0;

        for (int i = 0; i < totalAssets; i++) {
            int row = i / maxAssetsPerRow; // Calculate the row index
            int col = i % maxAssetsPerRow; // Calculate the column index

            int x = temp[0] + captionWidth + col * (assetWidth + assetSpacing);
            int y = temp[1] + row * (assetHeight + assetSpacing);

            assets.get(i).draw(g2d, x, y, assetWidth, assetHeight);
            g2d.drawString("Index: " + i, x + 10, y + assetHeight / 2);
            g2d.drawString(assets.get(i).getName(), x, y);
            assetsDrawn++;

            if (assetsDrawn >= totalAssets) {
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
    
    public List<Tile> findCaveTiles(){
    	for(Terrain terrain:getAllTerrains()) {
    	for (Tile tile : terrain.getTiles()) {
    	    if (tile.getType().equals(TileType.Cave)) {
    	    	caveSet.add(tile);
    	    }}
    	} return caveSet;
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
            System.out.println(addRow+" "+ addCol+" "+blockade.getName());
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
    public String nextToCave(Tile tile) {
        for (int[] neighbor : tile.getNeighbors()) {
        	Tile temp = ParentMap.get(neighbor[0]+","+neighbor[1]);
        	if(temp==null) {continue;}
        	if(temp.getType()==TileType.Cave) {
        		return temp.getRow()+","+temp.getCol();
        	}
        } return null;
    }
    public int nextToBlockade(Tile tile) {
        for (int[] neighbor : tile.getNeighbors()) {
        	Tile temp = ParentMap.get(neighbor[0]+","+neighbor[1]);
        	if(temp==null) {continue;}
        	if(temp.getParent().startsWith("Blockade_")) {
        		int blockIndex=Integer.parseInt(temp.getParent().substring("Blockade_".length()));
        		return blockIndex;
        	}
        } return -1;
    }
    public boolean isWinning(Tile tile) {
    	if(tile.getType()==TileType.Winning) {
    		System.out.println("this triggers the final round");
    		return true;
    	} return false;
    }
    public boolean isValidPosition(int row, int col) {
        String targetKey = row + "," + col;//maybe change change this to Tile 
        Tile temp = ParentMap.get(targetKey);
        if (temp == null || temp.getParent() == null || temp.getParent().startsWith("Blockade_")) {
            System.out.println("This tile doesn't belong in any board piece or is a block.");
            return false;
        }
        if(temp.getType()==TileType.Mountain ||temp.getType()==TileType.Cave) {
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