package org.set.boardPieces;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;

public class Tile {
    private int x;
    private int y;
    private int row;
    private int col;
    private TileType type;
    private Color color;
    private int points;
    private String parent;
    private int q;
    private int r;
    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
        this.type=TileType.Default;
    }
    
    @Override
    public String toString() {
        return "Tile{" +
                "row=" + row +
                ", col=" + col +
                ", type=" + type +
                '}';
    }
    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParent() {
        return parent;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public TileType getType() {
        return type;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }
    
    
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Color getColor() {
    	color=Util.getColorFromString(type.toString());
    	
        return color;
    }

    

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
    protected Tile clone() {
        return new Tile(this.row,this.col);
    }
    
    public int[] rotate(int originX, int originY, int degree) {
        int[] newCoordinate = new int[2]; // Array to hold the new coordinates
        final double COS_60 = 0.5;
        final double SIN_60 = Math.sqrt(3) / 2;
        
        int translatedX = x - originX;
        int translatedY = y - originY;
        
        int rotatedX = translatedX;
        int rotatedY = translatedY;
        
        int rotations = Math.abs(degree);
        boolean clockwise = degree > 0;

        for (int i = 0; i < rotations; i++) {
            if (clockwise) {
                int tempX = (int) Math.round(rotatedX * COS_60 - rotatedY * SIN_60);
                rotatedY = (int) Math.round(rotatedX * SIN_60 + rotatedY * COS_60);
                rotatedX = tempX;
            } else {
                int tempX = (int) Math.round(rotatedX * COS_60 + rotatedY * SIN_60);
                rotatedY = (int) Math.round(-rotatedX * SIN_60 + rotatedY * COS_60);
                rotatedX = tempX;
            }
        }
        
        newCoordinate[0] = rotatedX + originX;
        newCoordinate[1] = rotatedY + originY;

        return newCoordinate;
    }



    

    public void drawTile(Graphics2D g2d, int size, Color tileColor, Color border) {
    	int row = this.getRow();
        int col = this.getCol();
    	String targetKey = row + "," + col;
        int[] temp = TileDataDic.tilesMap.get(targetKey);
        int x = temp[0];
        int y = temp[1];
        int points = this.getPoints();
        if(this.type==TileType.Start) {
        	tileColor=new Color(0,200,0);
    	}
        drawHexagon(g2d, x, y, size, tileColor, border);

        // Draw row and column numbers
        FontMetrics fm = g2d.getFontMetrics();
        String rowColStr = row + "," + col;
        String Points = points + "P";
        int rowColWidth = fm.stringWidth(rowColStr);
        int rowColHeight = fm.getHeight();
        int centerX = x + size / 2;
        int centerY = y + size / 10;
        g2d.drawString(rowColStr, centerX - rowColWidth, centerY + rowColHeight);

        if (points != 0) {
            g2d.drawString(Points, (centerX - size / 2) - rowColWidth / 2,
                    (centerY - size * 9 / 10) + rowColHeight / 2);
        }
    }

    public void drawHexagon(Graphics2D g2d, int x, int y, int size, Color color, Color border) {
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];

        for (int i = 0; i < 6; i++) {
            xPoints[i] = (int) (x + size * Math.cos(i * Math.PI / 3));
            yPoints[i] = (int) (y + size * Math.sin(i * Math.PI / 3));
        }

        g2d.setColor(color);
        g2d.fillPolygon(xPoints, yPoints, 6);
        if (border != null) {
            g2d.setColor(border);
        }
        g2d.drawPolygon(xPoints, yPoints, 6);
    }

    public List<int[]> getNeighbors() {
        List<int[]> neighbors = new ArrayList<>();
        int[][] directions;

        if (col % 2 == 0) { // Check if current column is even
            directions = new int[][] {{ -1, 0 }, { 1, 0 }, { 0, -1 }, { -1, -1 }, { 0, 1 }, { -1, 1 }};
        } else { // Current column is odd
            directions = new int[][] {{ -1, 0 }, { 1, 0 }, { 0, -1 }, { 1, -1 }, { 0, 1 }, { 1, 1 }};
        }

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            neighbors.add(new int[] { newRow, newCol });
        }

        return neighbors;
    }
}
