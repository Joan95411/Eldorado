package org.set.player;

import java.awt.*;
import java.util.ArrayList;

public class Player {
    private ArrayList<Color> usedColors = new ArrayList<>();
    private String name;
    private int currentRow;
    private int currentCol;
    public Color color;
    public PlayerCardDeck myDeck;
    private boolean lastActionWasCaveExplore;
    private boolean potentialWinner = false;

    public Player(Color selectedColor) {
        this.color = selectedColor;
        myDeck = new PlayerCardDeck();
        if (selectedColor == null || usedColors.contains(selectedColor)) {
            throw new IllegalArgumentException("The color " + selectedColor + " is already used");
        } else {
            usedColors.add(selectedColor);
        }

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

    public void setWinner(boolean winner) {
        this.potentialWinner = winner;
    }

    public boolean getWinner() {
        return potentialWinner;
    }

    public void setPlayerPosition(int row, int col) {
        this.currentRow = row;
        this.currentCol = col;
    }

    public boolean isAtPosition(int row, int col) {
        return this.currentRow == row && this.currentCol == col;
    }

    public boolean isLastActionCaveExplore() {
        return lastActionWasCaveExplore;
    }

    public void setLastActionCaveExplore(boolean lastActionWasCaveExplore) {
        this.lastActionWasCaveExplore = lastActionWasCaveExplore;
    }

    public void draw(Graphics2D g2d, int size) {
        float[] hsbValues = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        Color colorDraw = Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2] * 0.8f);

        int x = currentCol * (int) (1.5 * size);
        int y = currentRow * (int) (Math.sqrt(3) * size);
        if (currentCol % 2 == 1) {
            y += (int) (Math.sqrt(3) / 2 * size);
        }
        int centerX = x - size;
        int centerY = y + size / 5;

        // Draw a star representing player's position
        int[] xPoints = { centerX, centerX + size / 4, centerX + size / 2, centerX + size * 3 / 4, centerX + size,
                centerX + size * 3 / 4, centerX + size / 2, centerX + size / 4 };
        int[] yPoints = { centerY - size / 4, centerY - size / 4, centerY - size / 2, centerY - size / 4,
                centerY - size / 4, centerY, centerY + size / 4, centerY - size / 4 };
        g2d.setColor(colorDraw);
        g2d.fillPolygon(xPoints, yPoints, 8);
    }

}
