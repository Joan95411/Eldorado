package org.set;

public class ExpeditionCard extends Card {
    public int power;
    public CardType.cardType cardType;

    public ExpeditionCard (String name, int cost, boolean singleUse, int power, CardType.cardType cardType) {
        super(name, cost, singleUse);

        this.power = power;
        this.cardType = cardType;
    }
}
