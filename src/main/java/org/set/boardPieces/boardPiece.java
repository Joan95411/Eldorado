package org.set.boardPieces;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

public abstract class BoardPiece {
    protected String name;
    protected List<Tile> tiles;
    protected int pieceCount;
    protected static final Color[] COLOR_RANGE = {Color.GREEN, Color.CYAN, Color.YELLOW};
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

    public abstract BoardPiece clone(int addRow, int addCol, Map<String, Tile> tilesMap);

    public void move(int addRow, int addCol) {
        for (Tile tile : tiles) {
            int[] result = calculateRowAndCol(tile, addRow, addCol);
            int newRow = result[0];
            int newCol = result[1];
            tile.setRow(newRow);
            tile.setCol(newCol);
        }
    }

    public Set<int[]> findOverlappingNeighbors(BoardPiece bpB) {
        Set<int[]> overlappingNeighbors = new LinkedHashSet<>();
        Set<int[]> neighborsA = new LinkedHashSet<>(getAllNeighbors());
        Set<int[]> neighborsB = new LinkedHashSet<>(bpB.getAllNeighbors());
        // Find overlapping neighbors by comparing coordinates
        for (int[] neighborA : neighborsA) {
            for (int[] neighborB : neighborsB) {
                if (Arrays.equals(neighborA, neighborB) ) {
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