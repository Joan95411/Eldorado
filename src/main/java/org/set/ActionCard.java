package org.set;

public class ActionCard extends Card {
    public ActionCard(String name, int cost, boolean singleUse) {
        super(name, cost, singleUse);
    }

    public void doAction(Card card, Player player) {
        System.out.println("PERFORM THE ACTION OF THE ACTION CARD");
        System.out.println(player.getColor());

        CardActionHandler handler = new CardActionHandler();
        handler.getAction(card);
    }

    public boolean isPlayable() {
        return !super.singleUse || !super.removedCard;
    }
}
