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

    

    public abstract void draw(Graphics2D g2d, int size, Map<String, Tile> tilesMap);

    public abstract boardPiece clone(int addRow, int addCol, Map<String, Tile> tilesMap);

    public void move(int addRow, int addCol, Map<String, Tile> tilesMap) {
        int row;
        int col;
        for (Tile tile : tiles) {
            if (addCol % 2 == 0) {
                row = tile.getRow() + addRow;
                col = tile.getCol() + addCol;
            } else {
                row = tile.getRow() + addRow;
                col = tile.getCol() + addCol;
                if (col % 2 == 0) {
                    if (addRow % 2 == 0) {
                        row = row + 1;
                    } else {
                        row = row - 1;
                    }
                }
            }
            tile.setRow(row);
            tile.setCol(col);
        }
    }

    
}

