package org.set.cards.action;

import org.set.Player;
import org.set.cards.Card;
import org.set.cards.CardActionHandler;

public class ActionCard extends Card {
    public ActionCard(ActionCardType name, int cost, boolean singleUse) {
        super(name.toString(), cost, singleUse);
    }

    public void doAction(Player player) {
        if (!this.isPlayable()) throw new IllegalStateException("This card is not playable");

        CardActionHandler handler = new CardActionHandler();
        handler.doAction(this, player);
        if(this.singleUse) this.removeCard();

        System.out.println("Player is still empty");
    }

    public boolean isPlayable() {
        return !super.singleUse || !super.removedCard;
    }
}
