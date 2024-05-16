package org.set;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
	public class WinningPiece  {
	    private List<Tile> tiles;
	    public int index;
	    
	    public WinningPiece() {
	        this.tiles = new ArrayList<>();
	    }

	    public void addTile(Tile tile) {
	        if (tiles.size() < 6) {
	        	if (!tiles.contains(tile)) {
	                tiles.add(tile);
	            } else {
	                System.out.println("Tile already exists in the WinningPiece.");
	            }
	        } else {
	            System.out.println("Blockade already contains 6 tiles.");
	        }
	    }

	    // Getters and setters
	    public List<Tile> getTiles() {
	        return tiles;
	    }

	    public void setTiles(List<Tile> tiles) {
	        this.tiles = tiles;
	    }
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



