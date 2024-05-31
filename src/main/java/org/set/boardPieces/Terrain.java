package org.set.boardPieces;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Terrain extends BoardPiece {
    private static int terrainCount = 0;
    private static final Color[] SPECIAL_COLOR_RANGE = { Color.GRAY, Color.RED, Color.BLACK };
    private static final double GREEN_PROBABILITY = 0.3;
    private static final double specialColorProbability = 0.1;
    public Tile axisTile;
    
    public Terrain() {
        super();
        int index = ++terrainCount;
        this.name = "Terrain_" + index;
        this.pieceCount = 37;
    }

    public static void resetWinningCount() {
        terrainCount = 0;
    }

    public void randomizeTiles() {
        Random random = new Random();
        for (Tile tile : tiles) {
            // Randomly select color
            Color color = getRandomColor(random);
            tile.setColor(color);

            // Randomly select points
            int points = random.nextInt(POINTS_MAX - POINTS_MIN + 1) + POINTS_MIN;
            tile.setPoints(points);
        }
    }


    private Color getRandomColor(Random random) {
        double rand = random.nextDouble();
        if (rand < GREEN_PROBABILITY) {
            // Mostly green
            return Color.GREEN;
        } else {
            double randSpecial = random.nextDouble();

            if (randSpecial < specialColorProbability) {
                // Randomly select a special color with lower probability
                int index = random.nextInt(SPECIAL_COLOR_RANGE.length);
                return SPECIAL_COLOR_RANGE[index];
            } else {
                // Randomly select from the standard color range
                int index = random.nextInt(COLOR_RANGE.length);
                return COLOR_RANGE[index];
            }
        }
    }
    
    public void rotate() {
    	findAxisTile();
        List<int[]> originalCoordinates = new ArrayList<>();
        for (Tile tile : tiles) {
            originalCoordinates.add(new int[]{tile.getX(), tile.getY(),tile.getRow(),tile.getCol()});
        }

        // Rotate each tile and find the closest original coordinate
        for (Tile tile : tiles) {
        	int[] newCoordinate=tile.rotate60antiClockwise(axisTile.getX(),axisTile.getY());
            int[] closestCoordinate = findClosestCoordinate(originalCoordinates, newCoordinate[0], newCoordinate[1]);
            if (closestCoordinate != null) {
                // Update tile's position to the closest original coordinate
                tile.setRow(closestCoordinate[2]);
                tile.setCol(closestCoordinate[3]);
            }
        }
    }
    
    private void findAxisTile() {
    	for(Tile tile:tiles) {
    		if(tile.getQ()==0 && tile.getR()==0) {
    			this.axisTile=tile;
    		}
    	}
    }
    private int[] findClosestCoordinate(List<int[]> coordinates, int x, int y) {
        double minDistance = Double.MAX_VALUE;
        int[] closestCoordinate = null;
        for (int[] coordinate : coordinates) {
            double distance = Math.sqrt(Math.pow(coordinate[0] - x, 2) + Math.pow(coordinate[1] - y, 2));
            if (distance < minDistance) {
                minDistance = distance;
                closestCoordinate = coordinate;
            }
        }
        return closestCoordinate;
    }
    @Override
    public void draw(Graphics2D g2d, int size) {
        for (Tile tile : tiles) {
            String targetKey = tile.getRow() + "," + tile.getCol();
            Tile temp = TileDataDic.tilesMap.get(targetKey);

            int x = temp.getX();
            int y = temp.getY();
            int hexSize = size;
            Color color = tile.getColor();
            int row = tile.getRow();
            int col = tile.getCol();
            int points = tile.getPoints();
            tile.drawTile(g2d, x, y, hexSize, color, row, col, points);
        }
    }

    @Override
    public Terrain clone(int addRow, int addCol) {
        Terrain clonedTerrain = new Terrain();
        for (Tile tile : tiles) {
            int[] result = calculateRowAndCol(tile, addRow, addCol);
            int newRow = result[0];
            int newCol = result[1];

            String targetKey = newRow + "," + newCol;
            Tile clonedTile = TileDataDic.tilesMap.get(targetKey);

            try {
                clonedTile.setColor(tile.getColor());
                clonedTile.setPoints(tile.getPoints());
                clonedTile.setQ(tile.getQ());
                clonedTile.setR(tile.getR());
                clonedTerrain.addTile(clonedTile);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Tile not found for row " + newRow + ", col " + newCol);
            }
        }
        return clonedTerrain;
    }

}
