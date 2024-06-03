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
		this.name = "Winning_" + index;
		this.pieceCount = 6;
	}

	public static void resetCount() {
		WinningCount = 0;
	}
	public void setColor(Color color) {
		for (Tile tile : tiles) {
			if (tile.getPoints() > 0) {
				tile.setColor(color);
			}
		}
    }
	public void randomizeTiles() {
		Random random = new Random();
		double rand = random.nextDouble();
		Color color;
		if (rand < 0.5) {
			color = Color.GREEN;
		} else {
			color = Color.CYAN;
		}
		setColor(color);
		
	}
	public void rotate(int degree, int axisX, int axisY) {
		for (Tile tile : tiles) {
			Tile temp=TileDataDic.tilesMap.get(tile.getRow()+","+tile.getCol());
			tile.setX(temp.getX());
			tile.setY(temp.getY());
        	if(degree==0) {
        		break;
        	}
        	else {
        	int[] newCoordinate=tile.rotate(axisX, axisY, degree);
//        	System.out.println(newCoordinate[0]+" "+newCoordinate[1]);
        	 int[] closestCoordinate = TileDataDic.findClosestCoordinate(newCoordinate[0], newCoordinate[1]);
             if (closestCoordinate != null) {
                 // Update tile's position to the closest original coordinate
                 tile.setRow(closestCoordinate[0]);
                 tile.setCol(closestCoordinate[1]);
             }
        	}
		}
	}
	
	
	@Override
	public WinningPiece clone(double addRow, double addCol,int hexSize) {
		WinningPiece clonedWinning = new WinningPiece();
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
              // Update tile's position to the closest original coordinate
          	clonedTile.setRow(closestCoordinate[0]);
          	clonedTile.setCol(closestCoordinate[1]);
          	clonedTile.setX(closestCoordinate[2]);
          	clonedTile.setY(closestCoordinate[3]);
          	clonedTile.setColor(tile.getColor());
          clonedTile.setPoints(tile.getPoints());
          clonedTile.setQ(tile.getQ());
          clonedTile.setR(tile.getR());
          clonedWinning.addTile(clonedTile);
          }
      }
      return clonedWinning;
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
			
			if (points > 0) {
				tile.drawTile(g2d, x, y, hexSize, color, row, col, points);
			} else {
				tile.drawHexagon(g2d, x, y, hexSize, color, null);
			}
		}

	}


}
