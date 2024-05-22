package org.set.cards.expedition;

import org.set.cards.Card;

public class ExpeditionCard extends Card {
    public int power;

    public ExpeditionCard (ExpeditionCardType name, int cost, boolean singleUse, int power) {
        super(name.toString(), cost, singleUse);
        this.power = power;
    }



}
