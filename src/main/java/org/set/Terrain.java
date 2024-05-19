package org.set;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Random;

public class Terrain {
    private List<Tile> tiles;
    private static int terrainCount = 0;
    public int index;
    private static final Color[] COLOR_RANGE = {Color.GREEN, Color.YELLOW, Color.CYAN};
    private static final Color[] SPECIAL_COLOR_RANGE= {Color.GRAY,Color.RED,Color.BLACK};
    private static final int POINTS_MIN = 1;
    private static final int POINTS_MAX = 3;
    private static final double GREEN_PROBABILITY = 0.3; 
    private static final double specialColorProbability = 0.1; 

    public Terrain() {
        this.tiles = new ArrayList<>();
        index = ++terrainCount;
    }

    public void addTile(Tile tile) {
        if (tiles.size() < 37) {
            tile.setParent("Terrain_"+index);
            tiles.add(tile);
               
        } else {
            System.out.println("Terrain already contains 37 tiles.");
        }
    }

    // Getters and setters
    public List<Tile> getTiles() {
        return tiles;
    }
    
        
    public void randomizeTiles(){
        Random random = new Random();
        for (Tile tile : tiles) {
            // Randomly select color
            Color color = getRandomColor(random);
            tile.setColor(color);

            // Randomly select points
            int points = random.nextInt(POINTS_MAX - POINTS_MIN + 1) + POINTS_MIN;
            tile.setPoints(points);
        }
        //adjustBlueNeighbors();
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
    

   

    

    public void draw(Graphics2D g2d,int size, Map<String, Tile> tilesMap){
    	for (Tile tile : tiles) {
    		String targetKey = tile.getRow()+","+tile.getCol();
            Tile temp = tilesMap.get(targetKey);
    		int x=temp.getX();
    		int y=temp.getY();
    		int hexSize=size;
    		Color color=tile.getColor();
    		//System.out.println(tile.getColor());
    		int row=tile.getRow();
    		int col=tile.getCol();
    		int points=tile.getPoints();
    		tile.drawTile(g2d, x, y, hexSize, color, row, col,points);
    	}

    }
    public void move(int addRow, int addCol, Map<String, Tile> tilesMap) {
    	int row;
        int col;
        for (Tile tile : tiles) {
        	if (addCol % 2 == 0) {
            row = tile.getRow() + addRow; 
            col = tile.getCol() + addCol; 
            }
        	else{
        		row = tile.getRow() + addRow; 
                col = tile.getCol() + addCol; 
                if(col% 2 == 0){
                	if(addRow% 2 == 0){
                	row=row+1;}
                	else{
                		row=row-1;
                	}
                }
        	}
        
        tile.setRow(row);
        tile.setCol(col);
        
        }
    }
    
    public Terrain clone(int addRow, int addCol, Map<String, Tile> tilesMap) {
        Terrain clonedTerrain = new Terrain();
        int row;
        int col;
        for (Tile tile : tiles) {
        	if (addCol % 2 == 0) {
            row = tile.getRow() + addRow; 
            col = tile.getCol() + addCol; 
            }
        	else{
        		row = tile.getRow() + addRow; 
                col = tile.getCol() + addCol; 
                if(col% 2 == 0){
                	if(addRow% 2 == 0){
                	row=row+1;}
                	else{
                		row=row-1;
                	}
                }
        	}
            
            Tile clonedTile = new Tile(row, col);
            String targetKey = row+","+col;
            clonedTile = tilesMap.get(targetKey);
            try {
                clonedTile.setColor(tile.getColor());
                clonedTile.setPoints(tile.getPoints());
                clonedTerrain.addTile(clonedTile);
            } catch(Exception e) {
            	e.printStackTrace();
                System.err.println("Tile not found for row " + row + ", col " + col);
            }
        }
        return clonedTerrain;
    }
    
    public Set<int[]> findOverlappingNeighbors(Terrain terrainB) {
        Set<int[]> overlappingNeighbors = new LinkedHashSet<>();
        Set<int[]> neighborsA = new LinkedHashSet<>(getAllNeighbors());
        Set<int[]> neighborsB = new LinkedHashSet<>(terrainB.getAllNeighbors());
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

    

    
    /*public void adjustBlueNeighbors() {
	List<Tile> blueTileclub = new ArrayList<>();

	// First, find all blue tiles
	for (Tile tile : tiles) {
	    if (tile.getColor() != null && tile.getColor().equals(Color.CYAN)) {
	        blueTileclub.add(tile);
	    }
	}

	// Iterate over each blue tile
	for (Tile blueTile : blueTileclub) {
	    List<int[]> neighborsRC = blueTile.getNeighbors();

	    // Create a list to store neighbors with the same parent
	    List<Tile> sameParentNeighbors = new ArrayList<>();

	    // Iterate over neighbors of the blue tile
	    for (int[] neighbor : neighborsRC) {
	        Tile neighborTile = TerrainTilesMap.get(neighbor[0] + "," + neighbor[1]);
	        if (neighborTile != null && neighborTile.getParent() != null && neighborTile.getParent().equals(blueTile.getParent())) {
	            sameParentNeighbors.add(neighborTile);
	            System.out.println(neighborTile.getRow()+" "+neighborTile.getCol());
	        }
	    }

	    // Create a set to detect duplicates
	    Set<Tile> seenNeighbors = new HashSet<>();

	    // Iterate over same parent neighbors
	    for (Tile neighbor : sameParentNeighbors) {
	        if (!seenNeighbors.add(neighbor)) {
	        	System.out.println(neighbor.getRow()+" "+neighbor.getCol());
	            neighbor.setColor(Color.CYAN);
	        }
	    }
	}

    
}*/


    

}



    

	


