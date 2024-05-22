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
                    cards.add(new ExpeditionCard("Green Expedition Card", CardType.GREEN, i,k == 0, j));
                    cards.add(new ExpeditionCard("Blue Expedition Card", CardType.BLUE, i,k == 0, j));
                    cards.add(new ExpeditionCard("Yellow Expedition Card", CardType.YELLOW, i,k == 0, j));
                    cards.add(new ExpeditionCard("Joker Expedition Card", CardType.JOKER, i,k == 0, j));
                }
            }
        }

        assertEquals(cards.size(), maxCost * maxPower * 2 * 4);
    }

    @Test
    public void removeExpeditionCards() {
        ExpeditionCard card = new ExpeditionCard("Name", CardType.GREEN, 1,false, 1);
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
        ExpeditionCard card = new ExpeditionCard("Name", CardType.GREEN, 1,true, 1);
        assertEquals(card.singleUse, true);
        assertEquals(card.removedCard, false);

         card.removeCard();
         assertEquals(card.singleUse, true);
         assertEquals(card.removedCard, true);
    }

    @Test
    public void createPurpleExpeditionCard() {
        try {
            ExpeditionCard card = new ExpeditionCard("Name", CardType.PURPLE, 1,true, 1);
        } catch (Exception e) {
            assertEquals("The expedition card cannot be purple.", e.getMessage());
        }
    }
}
