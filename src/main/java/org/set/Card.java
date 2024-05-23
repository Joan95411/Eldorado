package org.set;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import org.set.boardPieces.Util;

public abstract class Card {
	public String name;
	public CardType cardType;
	public int cost;
	public boolean singleUse;
	public boolean removedCard = false;

	public Card(String name, CardType cardType, int cost, boolean singleUse) {
		this.name = name;
		this.cardType = cardType;
		this.cost = cost;
		this.singleUse = singleUse;
	}

	public void removeCard() {
		if (!singleUse) {
			throw new IllegalArgumentException("This is not a single use card, so this card cannot be removed.");
		} else if (removedCard) {
			throw new IllegalStateException("This card is already removed and cannot be removed once again.");
		} else {
			removedCard = true;
		}
	}
	public void draw(Graphics2D g2d, int x, int y,int width, int height) {
		Color color=Util.getColorFromString(cardType.toString());
		Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
        g2d.setColor(transparentColor);
        g2d.fillRect(x, y, width, height);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, width, height);
        int fontSize = 15; // Adjust as needed
        Font font = new Font("Arial", Font.BOLD, fontSize); 
        g2d.setFont(font);
        g2d.drawString("Cost: "+cost, x+5, y+15);
        //g2d.drawString("Index: "+id, x+5, y+(height-20)/2);
        //g2d.drawString("Worth: "+worthValue, x+5, y+height-5);
    }
}
