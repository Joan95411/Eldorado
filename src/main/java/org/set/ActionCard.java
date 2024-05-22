package org.set;

public class ActionCard extends Card {
    public ActionCard(String name, int cost, boolean singleUse) {
        super(name, CardType.PURPLE, cost, singleUse);
    }

    public void doAction(Player player) {
        if (!this.isPlayable()) throw new IllegalStateException("This card is not playable");

        CardActionHandler handler = new CardActionHandler();
        handler.getAction(this.name);
        if(this.singleUse) this.removeCard();

        System.out.println("Player is still empty");
    }

    public boolean isPlayable() {
        return !super.singleUse || !super.removedCard;
    }
}
