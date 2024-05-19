package org.set;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Map;
	public class WinningPiece extends boardPiece {

		private static int WinningCount = 0;
	    
	    public WinningPiece() {
	        super();
	        int index = ++WinningCount;
	        this.name="Winning_"+index;
	        this.pieceCount=6;
	    }


	    @Override
	    public WinningPiece clone(int addRow, int addCol, Map<String, Tile> tilesMap) {
	    	WinningPiece clonedWinning = new WinningPiece();
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
	                clonedWinning.addTile(clonedTile);
	            } catch(Exception e) {
	            	e.printStackTrace();
	                System.err.println("Tile not found for row " + row + ", col " + col);
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



