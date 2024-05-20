package org.set;

public class ActionCard extends Card {
    public ActionCard(String name, int cost, boolean singleUse) {
        super(name, cost, singleUse);
    }

    public void doAction(Player player) {
        if (!this.isPlayable()) {
            System.out.println("This card is not playable");
            return;
        }

        CardActionHandler handler = new CardActionHandler();
        handler.getAction(this.name);
        this.removeCard();

        System.out.println("Player is still empty");
    }

    public boolean isPlayable() {
        return !super.singleUse || !super.removedCard;
    }
}
