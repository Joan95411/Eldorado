package org.set;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private static int lastAssignedId = 0;
    protected static ArrayList<Color> usedColors = new ArrayList<>();
	public int id;
    private int currentRow;
    private int currentCol;
    public Color color;

    public Player(Color selectedColor) {
        if (selectedColor == null || usedColors.contains(selectedColor)){
//            TODO: throw a proper error
            System.out.println("This color is already used");
        }

        this.id = ++lastAssignedId;
        this.color = selectedColor;
        usedColors.add(selectedColor);
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
        if (currentCol % 2 == 0) {
            // Check if current column is even
            neighbors.add((currentRow - 1) + "," + currentCol); // Above
            neighbors.add((currentRow + 1) + "," + currentCol); // Below
            neighbors.add(currentRow + "," + (currentCol - 1)); // Left
            neighbors.add((currentRow - 1) + "," + (currentCol - 1)); // Top-left
            neighbors.add(currentRow + "," + (currentCol + 1)); // Right
            neighbors.add((currentRow - 1) + "," + (currentCol + 1)); // Bottom-right
        } else {
            // Current column is odd
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
}

