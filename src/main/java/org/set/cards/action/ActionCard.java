package org.set.cards.action;

import org.set.player.Player;
import org.set.cards.Card;
import org.set.cards.CardActionHandler;

public class ActionCard extends Card {
    public ActionCard(ActionCardType card) {
        super(card.toString(), card.getCost(), card.isSingleUse());
    }

    public void doAction(Player player) {
        if (!this.isPlayable()) {
            throw new IllegalStateException("This card is not playable");
        }

        CardActionHandler handler = new CardActionHandler();
        handler.doAction(this, player);

        if (this.singleUse) {
            this.removeCard();
        } else {
            player.myDeck.discard(this);
        }
    }

    public boolean isPlayable() {
        return !super.singleUse || !super.removedCard;
    }
}
