package org.set.boardPieces;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

public abstract class BoardPiece {
    protected String name;
    protected List<Tile> tiles;
    protected int pieceCount;
    protected static final TileType[] COLOR_RANGE = { TileType.Machete, TileType.Paddle, TileType.Coin };
    protected static final int POINTS_MIN = 1;
    protected static final int POINTS_MAX = 3;

    public BoardPiece() {
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

    public abstract void randomizeTiles();
    
    public abstract void draw(Graphics2D g2d, int size);

    public abstract BoardPiece clone(double addRow, double addCol,int hexSize);

    
    public void move(double addRow, double addCol,int hexSize) {
    	int addX = (int)(addCol * 1.5 * hexSize);
        int addY = (int)(addRow *  Math.sqrt(3) * hexSize);
        if (addCol % 2 == 1) {
        	addY += (int) (Math.sqrt(3) / 2 * hexSize);
        }
        for (Tile tile : tiles) {
        	int[] temp = TileDataDic.tilesMap.get(tile.getRow()+","+tile.getCol());
        	int newX = temp[0]+addX;
          	int newY = temp[1]+addY;
          	int[] closestCoordinate = TileDataDic.findClosestCoordinate(newX, newY);
//          	System.out.println(tile.getX()+" "+tile.getY()+" "+addX+" "+addY+" "+closestCoordinate[2]+" "+closestCoordinate[3]);
//          	System.out.println(tile.getRow()+" "+tile.getCol()+" "+tile.getParent()+" "+closestCoordinate[0]+" "+closestCoordinate[1]);
          	 if (closestCoordinate != null) {
            tile.setRow(closestCoordinate[0]);
            tile.setCol(closestCoordinate[1]);}
        }
    }
    
    protected void cloneTiles(BoardPiece clonedPiece, double addRow, double addCol, int hexSize) {
        int addX = (int)(addCol * 1.5 * hexSize);
        int addY = (int)(addRow * Math.sqrt(3) * hexSize);
        if (addCol % 2 == 1) {
            addY += (int) (Math.sqrt(3) / 2 * hexSize);
        }

        for (Tile tile : tiles) {
            int newX = tile.getX() + addX;
            int newY = tile.getY() + addY;
            int[] closestCoordinate = TileDataDic.findClosestCoordinate(newX, newY);

            Tile clonedTile = tile.clone();
            if (closestCoordinate != null) {
                clonedTile.setRow(closestCoordinate[0]);
                clonedTile.setCol(closestCoordinate[1]);
                clonedTile.setX(closestCoordinate[2]);
                clonedTile.setY(closestCoordinate[3]);
                clonedTile.setType(tile.getType());
                clonedTile.setPoints(tile.getPoints());
                clonedTile.setQ(tile.getQ());
                clonedTile.setR(tile.getR());
                clonedPiece.addTile(clonedTile);
            }
        }
    }

    public Set<int[]> findOverlappingNeighbors(BoardPiece bpB) {
        Set<int[]> overlappingNeighbors = new LinkedHashSet<>();
        Set<int[]> neighborsA = new LinkedHashSet<>(getAllNeighbors());
        Set<int[]> neighborsB = new LinkedHashSet<>(bpB.getAllNeighbors());
        // Find overlapping neighbors by comparing coordinates
        for (int[] neighborA : neighborsA) {
            for (int[] neighborB : neighborsB) {
                if (Arrays.equals(neighborA, neighborB)) {
                    if (!overlappingNeighbors.contains(neighborA)) {
                        overlappingNeighbors.add(neighborA);
                    }
                    break; // No need to continue searching for this neighbor in terrainB
                }
            }
        }

        return overlappingNeighbors;
    }

    public Set<int[]> getAllNeighbors() {
        Set<String> neighborStrings = new HashSet<>();
        Set<int[]> neighbors = new LinkedHashSet<>();
        for (Tile tile : tiles) {
            List<int[]> neighbor = tile.getNeighbors();
            for (int[] oneNeighbor : neighbor) {
                String neighborString = Arrays.toString(oneNeighbor);
                if (!neighborStrings.contains(neighborString)) {
                    neighborStrings.add(neighborString);
                    neighbors.add(oneNeighbor);
                }
            }
        }
        return neighbors;
    }
}