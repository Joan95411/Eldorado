package org.set.cards.expedition;

import java.awt.Graphics2D;

import org.set.cards.Card;

public class ExpeditionCard extends Card {
    public int power;

    public ExpeditionCard (ExpeditionCardType name, int cost, boolean singleUse, int power) {
        super(name.toString(), cost, singleUse);
        this.power = power;
    }
    
    @Override
    public void draw(Graphics2D g2d, int x, int y, int width, int height) {
    	super.draw(g2d, x, y, width, height);
    	g2d.drawString("Power: "+power, x+5, y+height-5);
    }
    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

}
