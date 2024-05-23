package org.set.cards;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.set.cards.expedition.ExpeditionCard;
import org.set.cards.expedition.ExpeditionCardType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpeditionCardTest {
    @Test
    public void createExpeditionCards() {
        ArrayList<Card> cards = new ArrayList<>();

        int maxCost = 5;
        int maxPower = 5;

        for (ExpeditionCardType explorerCardType: ExpeditionCardType.values()) {
            for (int i = 0; i < maxCost; i++) {
                for (int j = 0; j < maxPower; j++) {
                    for (int k = 0; k < 2; k++) {
                        cards.add(new ExpeditionCard(explorerCardType, i,k == 0, j));
                    }
                }
            }
        }

        assertEquals(cards.size(), maxCost * maxPower * 2 * ExpeditionCardType.values().length);
    }

    @Test
    public void removeExpeditionCards() {
        ExpeditionCard card = new ExpeditionCard(ExpeditionCardType.Sailor, 1,false, 1);
        assertEquals(card.singleUse, false);
        assertEquals(card.removedCard, false);

        try {
            card.removeCard();
        } catch (Exception e) {
            assertEquals("This is not a single use card, so this card cannot be removed.", e.getMessage());
            assertEquals(card.singleUse, false);
            assertEquals(card.removedCard, false);
        }
    }

    @Test
    public void removeSingleUseExpeditionCards() {
        ExpeditionCard card = new ExpeditionCard(ExpeditionCardType.Sailor, 1,true, 1);
        assertEquals(card.singleUse, true);
        assertEquals(card.removedCard, false);

         card.removeCard();
         assertEquals(card.singleUse, true);
         assertEquals(card.removedCard, true);
    }
}
