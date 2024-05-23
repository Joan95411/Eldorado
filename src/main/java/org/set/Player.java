package org.set;

import java.awt.*;

public class Player {
    private static int lastAssignedId = 0;

	public int id;
    private int currentRow;
    private int currentCol;
    public Color color;
    public PlayerCardDeck mydeck;
    public Player(Color selectedColor) {
        this.id = ++lastAssignedId;
        this.color=selectedColor;

        float[] hsbValues = Color.RGBtoHSB(selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue(), null);
        this.color= Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2] * 0.8f);
        mydeck=new PlayerCardDeck();
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }
    
    public Color getColor() {
        return color;
    }

    public void setPlayerPosition(int row, int col) {
        this.currentRow = row;
        this.currentCol = col;
    }


    public boolean isAtPosition(int row, int col) {
        return this.currentRow == row && this.currentCol == col;
    }

    

    public void draw(Graphics2D g2d, int row, int col, int size, Color color) {
        // Calculate center of hexagon
        int x = col * (int) (1.5 * size);
        int y = row * (int) (Math.sqrt(3) * size);
        if (col % 2 == 1) {
            y += (int) (Math.sqrt(3) / 2 * size);
        }
        int centerX = x -size;
        int centerY = y +size/5;

        // Draw a star representing player's position
        int[] xPoints = {centerX, centerX + size / 4, centerX + size / 2, centerX + size * 3 / 4, centerX + size, centerX + size * 3 / 4, centerX + size / 2, centerX + size / 4};
        int[] yPoints = {centerY - size / 4, centerY - size / 4, centerY - size / 2, centerY - size / 4, centerY - size / 4, centerY, centerY + size / 4, centerY - size / 4};
        g2d.setColor(color);
        g2d.fillPolygon(xPoints, yPoints, 8);
    }
}

