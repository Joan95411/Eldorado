package org.set;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
public class Blockade extends boardPiece {
	private static int blockadeCount = 0;
    private Color color;
    private int points;

    public Blockade() {
        super();
        int index = ++blockadeCount;
        this.name="Blockade_"+index;
        this.pieceCount=5;
    }

    

    
    public void setColor(Color color) {
        this.color = color;
    }
    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public Blockade clone(int addRow, int addCol, Map<String, Tile> tilesMap) {
    	Blockade clonedBlock = new Blockade();
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
                clonedBlock.addTile(clonedTile);
            } catch(Exception e) {
            	e.printStackTrace();
                System.err.println("Tile not found for row " + row + ", col " + col);
            }
        }
        clonedBlock.setColor(this.color);
        clonedBlock.setPoints(this.points);
        return clonedBlock;
    }
    
    
    
    public void randomizeTiles() {
    	Random random = new Random();
    	int index = random.nextInt(COLOR_RANGE.length);
    	Color temp=COLOR_RANGE[index];
    	Color transparentColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 50);
    	this.color=transparentColor;
    	
    	int points = random.nextInt(POINTS_MAX - POINTS_MIN + 1) + POINTS_MIN;
    	this.points=points;
    }

    @Override
    public void draw(Graphics2D g2d,int size, Map<String, Tile> tilesMap){
    	int totalX = 0;
        int totalY = 0;
    	for (Tile tile : tiles) {
    		String targetKey = tile.getRow()+","+tile.getCol();
            Tile temp = tilesMap.get(targetKey);
    		int x=temp.getX();
    		int y=temp.getY();
    		totalX += x;
            totalY += y;
    		int hexSize=size;
    		tile.drawHexagon(g2d, x, y, hexSize, color, null);
    	}
    	int centerX = totalX / tiles.size();
        int centerY = totalY / tiles.size();
        g2d.setColor(Color.BLACK);
        String pointText = points+"P"; // Unicode character for a bullet point
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(pointText);
        int textHeight = fm.getHeight();
        g2d.drawString(pointText, centerX - textWidth / 2, centerY + textHeight / 2);
    }
    
}

