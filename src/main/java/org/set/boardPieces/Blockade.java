package org.set.boardPieces;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.Map;
import java.util.Random;

public class Blockade extends BoardPiece {
    private static int blockadeCount = 0;
    private Color color;
    private int points;
    private int[] neighbors;
    private double[] addRowCol;
    public Blockade() {
        super();
        int index = ++blockadeCount;
        this.name = "Blockade_" + index;
        this.pieceCount = 5;
        this.neighbors = new int[2];
        this.addRowCol=new double[2];
    }

    public static void resetCount() {
        blockadeCount = 0;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setTerrainNeighbors(int neighbor1, int neighbor2) {
        this.neighbors[0] = neighbor1;
        this.neighbors[1] = neighbor2;
    }

    public int[] getTerrainNeighbors() {
        return neighbors;
    }
    public void setTerrainAddRowCol(double addRow, double addCol) {
        this.addRowCol[0] = addRow;
        this.addRowCol[1] = addCol;
    }
    
    public double[] getTerrainAddRowCol() {
        return addRowCol;
    }
    public double[] calcRowColChange(double addRow, double addCol) {
    	double[] addRowCol=new double[2];
    	addRowCol[0]=0;
    	addRowCol[1]=0;
    	if(addRow>1) {
    		addRowCol[0]+=1;}
            else if(addRow<-1) {
            	addRowCol[0]-=1;}
            else if (addCol>1){
            	addRowCol[0]-=0.5;
            	addRowCol[1]+= 1;
            }else if (addCol<-1){
            	addRowCol[0]-=0.5;
            	addRowCol[1]-= 1;
            }
    	
//            else if(addCol>1) {
//            	addRowCol[1]+= 1;}
//            else if(addCol<-1) {
//            	addRowCol[1]-= 1;}
            
            
		return addRowCol;
    	
    }
    @Override
    public Blockade clone(double addRow, double addCol,int hexSize) {
    	Blockade clonedBlock = new Blockade();
        int addX = (int)(addCol * 1.5 * hexSize);
        int addY = (int)(addRow *  Math.sqrt(3) * hexSize);
        if (addCol % 2 == 1) {
        	addY += (int) (Math.sqrt(3) / 2 * hexSize);
        }
        
        for (Tile tile : tiles) {
      	int newX = tile.getX()+addX;
      	int newY = tile.getY()+addY;
      	int[] closestCoordinate = TileDataDic.findClosestCoordinate(newX, newY);
      	
        Tile clonedTile = tile.clone();
          if (closestCoordinate != null) {
              
      	clonedTile.setRow(closestCoordinate[0]);
      	clonedTile.setCol(closestCoordinate[1]);
      	clonedTile.setX(closestCoordinate[2]);
      	clonedTile.setY(closestCoordinate[3]);
      	clonedBlock.addTile(clonedTile);
          }
      }
        clonedBlock.setColor(this.color);
        clonedBlock.setPoints(this.points);
      return clonedBlock;
    }
    
    @Override
    public void randomizeTiles() {
        Random random = new Random();
        int index = random.nextInt(COLOR_RANGE.length);
        Color temp = COLOR_RANGE[index];
        Color transparentColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 50);
        this.color = transparentColor;

        int points = random.nextInt(POINTS_MAX - POINTS_MIN + 1) + POINTS_MIN;
        this.points = points;
    }

    @Override
    public void draw(Graphics2D g2d, int size) {
        int totalX = 0;
        int totalY = 0;
        for (Tile tile : tiles) {
            String targetKey = tile.getRow() + "," + tile.getCol();
            int[] temp = TileDataDic.tilesMap.get(targetKey);
            int x = temp[0];
            int y = temp[1];
            totalX += x;
            totalY += y;
            tile.drawHexagon(g2d, x, y, size, color, null);
        }
        int centerX = totalX / tiles.size();
        int centerY = totalY / tiles.size();
        g2d.setColor(Color.BLACK);
        String pointText = points + "P"; // Unicode character for a bullet point
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(pointText);
        int textHeight = fm.getHeight();
        g2d.drawString(pointText, centerX - textWidth / 2, centerY + textHeight / 2);
    }
}
