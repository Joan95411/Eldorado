package org.set.boardPieces;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

public abstract class boardPiece {
	protected String name;
    protected List<Tile> tiles;
    protected int pieceCount;
    protected static final Color[] COLOR_RANGE = {Color.GREEN, Color.YELLOW, Color.CYAN};
    protected static final int POINTS_MIN = 1;
    protected static final int POINTS_MAX = 3;

    public boardPiece() {
        this.tiles = new ArrayList<>();
    }
    public String getName() {
    	return name;
    }

    public void addTile(Tile tile) {
        if (tiles.size() < pieceCount) {
            tile.setParent(this.name);
            tiles.add(tile);
               
        } else {
            System.out.println("Overflow of tiles");
        }
    } 

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
        
    }

    protected int[] calculateRowAndCol(Tile tile, int addRow, int addCol) {
        int[] result = new int[2];
        
        if (addCol % 2 == 0) {
            result[0] = tile.getRow() + addRow; 
            result[1] = tile.getCol() + addCol; 
        } else {
            result[0] = tile.getRow() + addRow; 
            result[1] = tile.getCol() + addCol; 
            if (result[1] % 2 == 0) {
                if (addRow % 2 == 0) {
                    result[0]++;
                } else {
                    result[0]--;
                }
            }
        }
        
        return result;
    }

    public abstract void draw(Graphics2D g2d, int size, Map<String, Tile> tilesMap);

    public abstract boardPiece clone(int addRow, int addCol, Map<String, Tile> tilesMap);

    public void move(int addRow, int addCol, Map<String, Tile> tilesMap) {
        for (Tile tile : tiles) {
        	int[] result = calculateRowAndCol(tile, addRow, addCol);
        	int newRow = result[0];
        	int newCol = result[1];
            tile.setRow(newRow);
            tile.setCol(newCol);
        }
    }

    
}

