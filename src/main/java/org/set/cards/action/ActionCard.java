package org.set.cards.action;

import org.set.player.Actionable;
import org.set.player.Player;
import org.set.cards.Card;
import org.set.cards.CardActionHandler;

public class ActionCard extends Card implements Actionable{
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
}
