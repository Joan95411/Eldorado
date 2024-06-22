package org.set.cards.action;

import org.set.player.Actionable;
import org.set.player.Player;

import java.awt.Graphics2D;

import org.set.cards.Card;
import org.set.cards.CardActionHandler;

public class ActionCard extends Card implements Actionable {
    public ActionCard(ActionCardType card) {
        super(card.toString(), card.getCost(), card.isSingleUse());
    }

    @Override
    public void doAction(Player player) {
        if (!this.isPlayable()) {
            System.out.println("This card is not playable");
            return;
        }

        CardActionHandler handler = new CardActionHandler();
        handler.doAction(this, player);

        if (this.singleUse) {
            this.removeCard();
            player.myDeck.removeCard(this);
        } else {
            player.myDeck.discard(this);
        }
    }

    public boolean isPlayable() {
        return !super.singleUse || !super.removedCard;
    }

    @Override
    public void draw(Graphics2D g2d, int x, int y, int width, int height) {
        super.draw(g2d, x, y, width, height);
        if (this.singleUse) {
            g2d.drawString("SingleUse ", x + 5, y + 15);
        }
    }
}
