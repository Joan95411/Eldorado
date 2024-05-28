package org.set.boardPieces;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Map;
import java.util.Random;

public class Terrain extends BoardPiece {
	private static int terrainCount = 0;
    private static final Color[] SPECIAL_COLOR_RANGE= {Color.GRAY,Color.RED,Color.BLACK};
    private static final double GREEN_PROBABILITY = 0.3; 
    private static final double specialColorProbability = 0.1; 
    

    public Terrain() {
    	super();
        int index = ++terrainCount;
        this.name="Terrain_"+index;
        this.pieceCount=37;
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
    

   

    
    @Override  
    public void draw(Graphics2D g2d,int size, Map<String, Tile> tilesMap){
    	for (Tile tile : tiles) {
    		String targetKey = tile.getRow()+","+tile.getCol();
            Tile temp = tilesMap.get(targetKey);
    		
    		int x=temp.getX();
    		int y=temp.getY();
    		int hexSize=size;
    		Color color=tile.getColor();
    		int row=tile.getRow();
    		int col=tile.getCol();
    		int points=tile.getPoints();
    		tile.drawTile(g2d, x, y, hexSize, color, row, col,points);
    	}

    }
    
    @Override  
    public Terrain clone(int addRow, int addCol, Map<String, Tile> tilesMap) {
        Terrain clonedTerrain = new Terrain();
        for (Tile tile : tiles) {
        	int[] result = calculateRowAndCol(tile, addRow, addCol);
        	int newRow = result[0];
        	int newCol = result[1];
            
            String targetKey = newRow + "," + newCol;
            Tile clonedTile = tilesMap.get(targetKey);
            
            try {
                clonedTile.setColor(tile.getColor());
                clonedTile.setPoints(tile.getPoints());
                clonedTerrain.addTile(clonedTile);
            } catch(Exception e) {
            	e.printStackTrace();
                System.err.println("Tile not found for row " + newRow + ", col " + newCol);
            }
        }
        return clonedTerrain;
    }
    

    

    



    

}



    

	


