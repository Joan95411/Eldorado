package org.set;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpeditionCardTest {
    @Test
    public void createExpeditionCards() {
        ArrayList<Card> cards = new ArrayList<>();

        int maxCost = 5;
        int maxPower = 5;

        for (int i = 0; i < maxCost; i++) {
            for (int j = 0; j < maxPower; j++) {
                for (int k = 0; k < 2; k++) {
                    cards.add(new ExpeditionCard("Green Expedition Card", i,k == 0, j, CardType.GREEN));
                    cards.add(new ExpeditionCard("Blue Expedition Card", i,k == 0, j, CardType.BLUE));
                    cards.add(new ExpeditionCard("Yellow Expedition Card", i,k == 0, j, CardType.YELLOW));
                    cards.add(new ExpeditionCard("Joker Expedition Card", i,k == 0, j, CardType.JOKER));
                }
            }
        }

        assertEquals(cards.size(), maxCost * maxPower * 2 * 4);
    }

    @Test
    public void removeExpeditionCards() {
        ExpeditionCard card = new ExpeditionCard("Name", 1,false, 1, CardType.GREEN);
        assertEquals(card.singleUse, false);
        assertEquals(card.removedCard, false);

        card.removeCard();
        assertEquals(card.singleUse, false);
        assertEquals(card.removedCard, false);
    }

    @Test
    public void removeSingleUseExpeditionCards() {
        ExpeditionCard card = new ExpeditionCard("Name", 1,true, 1, CardType.GREEN);
        assertEquals(card.singleUse, true);
        assertEquals(card.removedCard, false);

         card.removeCard();
         assertEquals(card.singleUse, true);
         assertEquals(card.removedCard, true);
    }
}
