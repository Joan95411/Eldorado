package org.set;

import org.set.cards.Card;
import org.set.cards.CardType;
import org.set.cards.ExpeditionCard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Player {
    private static int lastAssignedId = 0;

	public int id;
    private int currentRow;
    private int currentCol;
    public Color color;
    private List<Card> wholeDeck = new ArrayList<>();
    private List<Card> currentDeck;

    public Player(Color selectedColor) {
        this.id = ++lastAssignedId;
        this.color=selectedColor;

        float[] hsbValues = Color.RGBtoHSB(selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue(), null);
        this.color= Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2] * 0.8f);
        int blueCount = 1;
        int greenCount = 3;
        int yellowCount = 4;

        Random random = new Random();

        // Add green cards
        for (int i = 0; i < blueCount; i++) {
            wholeDeck.add(new ExpeditionCard("Sailor", CardType.BLUE, 0, false, 1));
        }

        // Add green cards
        for (int i = 0; i < greenCount; i++) {
        	wholeDeck.add(new ExpeditionCard("Explorer", CardType.GREEN,0, false, 1));
        }

        // Add yellow cards
        for (int i = 0; i < yellowCount; i++) {
            wholeDeck.add(new ExpeditionCard("Traveller", CardType.YELLOW, 0, false, 1));
        }

        Collections.shuffle(wholeDeck, random);
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

    public void setCards(List<Card> cards) {
        this.wholeDeck=cards;
    }

    public void drawCards(int currentTurn){
    	if (currentTurn % 2 == 0) {
        	Collections.shuffle(wholeDeck);
    		 currentDeck= wholeDeck.subList(0, 4);
        } else {
        	currentDeck= wholeDeck.subList(4, 8);
        }
    }
    public List<Card> getCards() {
        return currentDeck;
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
        int centerX = x -size;
        int centerY = y +size/5;

        // Draw a star representing player's position
        int[] xPoints = {centerX, centerX + size / 4, centerX + size / 2, centerX + size * 3 / 4, centerX + size, centerX + size * 3 / 4, centerX + size / 2, centerX + size / 4};
        int[] yPoints = {centerY - size / 4, centerY - size / 4, centerY - size / 2, centerY - size / 4, centerY - size / 4, centerY, centerY + size / 4, centerY - size / 4};
        g2d.setColor(color);
        g2d.fillPolygon(xPoints, yPoints, 8);
    }
}

