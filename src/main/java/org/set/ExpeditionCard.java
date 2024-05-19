package org.set;

public class ExpeditionCard extends Card {
    public int power;
    public CardType.cardType cardType;

    public ExpeditionCard (String name, int cost, boolean removeCard, int power, CardType.cardType cardType) {
        super(name, cost, removeCard);

        this.power = power;
        this.cardType = cardType;
    }
}
