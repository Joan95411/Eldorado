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
	public void setColor(TileType color) {
		for (Tile tile : tiles) {
			if (tile.getType()==TileType.Winning) {
				tile.setType(color);
			}
		}
    }
	
	@Override
	public void randomizeTiles() {
		Random random = new Random();
		double rand = random.nextDouble();
		TileType color;
		if (rand < 0.5) {
			color = TileType.Machete;
		} else {
			color = TileType.Paddle;
		}
		setColor(color);
		
	}
	public void rotate(int degree, int axisX, int axisY) {
		for (Tile tile : tiles) {
			int[] temp=TileDataDic.tilesMap.get(tile.getRow()+","+tile.getCol());
			tile.setX(temp[0]);
			tile.setY(temp[1]);
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
        cloneTiles(clonedWinning, addRow, addCol, hexSize);
        return clonedWinning;
    }
	
	@Override
	public void draw(Graphics2D g2d, int size) {
		for (Tile tile : tiles) {
			Color color=tile.getColor();
			int points = tile.getPoints();
			
			if (points > 0) {
				tile.drawTile(g2d,  size,color, Color.BLACK);
			} else {
				tile.drawTile(g2d, size,color, null);
			}
		}

	}


}
