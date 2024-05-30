package org.set.boardPieces;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Map;
import java.util.Random;
	public class WinningPiece extends BoardPiece {

		private static int WinningCount = 0;
	    
	    public WinningPiece() {
	        super();
	        int index = ++WinningCount;
	        this.name="Winning_"+index;
	        this.pieceCount=6;
	    }
	    public static void resetCount() {
	        WinningCount = 0;
	    }
	    public void randomizeTiles(){
	    	Random random = new Random();
	    	double rand = random.nextDouble();
	    	Color color;
	        if (rand < 0.5) {
	            color = Color.GREEN;
	        } else {
	            color = Color.CYAN;
	        }
	    	for (Tile tile : tiles) {
	    		if(tile.getPoints()>0){
	    			tile.setColor(color);
	    			int points = random.nextInt(POINTS_MAX - POINTS_MIN + 1) + POINTS_MIN;
	                tile.setPoints(points);
	    		}
	    	}
	    }

	    @Override
	    public WinningPiece clone(int addRow, int addCol, Map<String, Tile> tilesMap) {
	    	WinningPiece clonedWinning = new WinningPiece();
	        for (Tile tile : tiles) {
	        	int[] result = calculateRowAndCol(tile, addRow, addCol);
	        	int newRow = result[0];
	        	int newCol = result[1];
	            
	        	String targetKey = newRow + "," + newCol;
	            Tile clonedTile = tilesMap.get(targetKey);
	            
	            try {
	                clonedTile.setColor(tile.getColor());
	                clonedTile.setPoints(tile.getPoints());
	                clonedWinning.addTile(clonedTile);
	            } catch(Exception e) {
	            	e.printStackTrace();
	                System.err.println("Tile not found for row " + newRow + ", col " + newCol);
	            }
	        }
	        return clonedWinning;
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
	    		if(points>0){
	    		tile.drawTile(g2d, x, y, hexSize, color, row, col,points);}
	    		else{
	    			tile.drawHexagon(g2d, x, y, hexSize, color, null);
	    		}
	    	}
	        
	        
	    }

		
	}



