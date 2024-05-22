package org.set;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    @Test
    public void doubleRemoveSingleUseCard() {
        String expectedError = "This card is already removed and cannot be removed once again.";

        Card actionCard = new ActionCard("Name", 2, true);
        Card expeditionCard = new ExpeditionCard("Name", 2, true, 2, CardType.GREEN);

        try {
            actionCard.removeCard();
            actionCard.removeCard();
        } catch (Exception e) {
            assertEquals(expectedError, e.getMessage());
        }

        try {
            expeditionCard.removeCard();
            expeditionCard.removeCard();
        } catch (Exception e) {
            assertEquals(expectedError, e.getMessage());
        }
    }

    @Test
    public void removeNonSingleUseCard() {
        String expectedError = "This is not a single use card, so this card cannot be removed.";

        Card actionCard = new ActionCard("Name", 2, false);
        Card expeditionCard = new ExpeditionCard("Name", 2, false, 2, CardType.GREEN);

        try {
            actionCard.removeCard();
            actionCard.removeCard();
        } catch (Exception e) {
            assertEquals(expectedError, e.getMessage());
        }

        try {
            expeditionCard.removeCard();
            expeditionCard.removeCard();
        } catch (Exception e) {
            assertEquals(expectedError, e.getMessage());
        }
    }

    @Test
    public void cardTypes() {
        String[] types = {"GREEN", "YELLOW", "BLUE", "JOKER"};
        for (CardType cardType : CardType.values()) {
            assertTrue(Arrays.asList(types).contains(cardType.toString()));
        }
    }
}
