package org.set.boardPieces;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.Map;
import java.util.Random;

public class Blockade extends BoardPiece {
    private static int blockadeCount = 0;
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

    public void setColor(TileType color) {
    	
    	for (Tile tile : tiles) {
    		tile.setType(color);
    	}
    }
    public String getName() {
    	return name;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    public int getPoints() {
    	return points;
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
    	if (tiles.isEmpty()) {
            throw new IllegalStateException("Cannot clone a Blockade with no tiles added.");
            // or return null; if you prefer
        }
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
        clonedBlock.setColor(tiles.get(0).getType());
        clonedBlock.setPoints(this.points);
      return clonedBlock;
    }
    
    @Override
    public void randomizeTiles() {
        Random random = new Random();
        int index = random.nextInt(COLOR_RANGE.length);
        TileType temp = COLOR_RANGE[index];
        setColor(temp);

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
            totalX += temp[0];
            totalY += temp[1];

            Color color=tile.getColor();
            Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 50); 
            tile.drawTile(g2d,  size,transparentColor, null);
        }
        int centerX = totalX / tiles.size();
        int centerY = totalY / tiles.size();
        g2d.setColor(Color.BLACK);
        String pointText = points + "P"; 
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(pointText);
        int textHeight = fm.getHeight();
        g2d.drawString(pointText, centerX - textWidth / 2, centerY + textHeight / 2);
    }
}
