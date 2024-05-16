package org.set;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {
	public int id;
    private int currentRow;
    private int currentCol;
    public Color color;

    public Player(int id,Color selectedColor) {
        this.id=id;
        this.color=selectedColor;
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
    public List<String> getNeighborLocations() {
        List<String> neighbors = new ArrayList<>();
        if (currentCol % 2 == 0) { // Check if current column is even
            neighbors.add((currentRow - 1) + "," + currentCol); // Above
            neighbors.add((currentRow + 1) + "," + currentCol); // Below
            neighbors.add(currentRow + "," + (currentCol - 1)); // Left
            neighbors.add((currentRow - 1) + "," + (currentCol - 1)); // Top-left
            neighbors.add(currentRow + "," + (currentCol + 1)); // Right
            neighbors.add((currentRow - 1) + "," + (currentCol + 1)); // Bottom-right
        } else { // Current column is odd
            neighbors.add((currentRow - 1) + "," + currentCol); // Above
            neighbors.add((currentRow + 1) + "," + currentCol); // Below
            neighbors.add(currentRow + "," + (currentCol - 1)); // Left
            neighbors.add((currentRow + 1) + "," + (currentCol - 1)); // Top-right
            neighbors.add(currentRow + "," + (currentCol + 1)); // Right
            neighbors.add((currentRow + 1) + "," + (currentCol + 1)); // Bottom-right
        }
        return neighbors;
    }
    public void printNeighbors(){
		List<String> neighborLocations = getNeighborLocations(); // Get neighbor locations
        System.out.println("Neighbor locations:");
        for (String location : neighborLocations) {
            System.out.println(location);
        }
	}
    public void draw(Graphics2D g2d, int row, int col, int size, Color color) {
        // Calculate center of hexagon
        int x = col * (int) (1.5 * size);
        int y = row * (int) (Math.sqrt(3) * size);
        if (col % 2 == 1) {
            y += (int) (Math.sqrt(3) / 2 * size);
        }
        int centerX = x + size-90;
        int centerY = y + size-45;

        // Draw a star representing player's position
        int[] xPoints = {centerX, centerX + size / 4, centerX + size / 2, centerX + size * 3 / 4, centerX + size, centerX + size * 3 / 4, centerX + size / 2, centerX + size / 4};
        int[] yPoints = {centerY - size / 4, centerY - size / 4, centerY - size / 2, centerY - size / 4, centerY - size / 4, centerY, centerY + size / 4, centerY - size / 4};
        g2d.setColor(color);
        g2d.fillPolygon(xPoints, yPoints, 8);
        
    }
	
    
}

