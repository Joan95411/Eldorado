package org.set.cards.expedition;

import java.awt.Graphics2D;

import org.set.cards.Card;

public class ExpeditionCard extends Card {
    private int power;

    public ExpeditionCard(ExpeditionCardType card) {
        super(card.toString(), card.getCost(), card.isSingleUse());
        this.power = card.getPower();
    }

    @Override
    public void draw(Graphics2D g2d, int x, int y, int width, int height) {
        super.draw(g2d, x, y, width, height);
        g2d.drawString("Power: " + power, x + 5, y + 15);
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

}
