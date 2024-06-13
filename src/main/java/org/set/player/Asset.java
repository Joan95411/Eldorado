package org.set.player;

import java.awt.Color;
import java.awt.Graphics2D;

import org.set.cards.CardType;

public abstract class Asset {
    protected int power;
    protected CardType cardType; // Assuming cardType is a String. Adjust if it's a different type.
    protected Color color; // Assuming Movable objects have a color.

    public int getPower() {
        return power;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Color getColor() {
        return color;
    }
    abstract public String getName();
    abstract public double getValue();
    abstract public void draw(Graphics2D g2d, int x, int y, int width, int height);
}
