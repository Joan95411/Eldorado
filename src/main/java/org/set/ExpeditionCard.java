package org.set;

public class ExpeditionCard extends Card {
    public int power;

    public ExpeditionCard (String name, CardType cardType, int cost, boolean singleUse, int power) {
        super(name, cardType, cost, singleUse);
        this.power = power;

        if (cardType == CardType.PURPLE) throw new IllegalStateException("The expedition card cannot be purple.");
    }
}
