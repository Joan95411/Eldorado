package org.set.cards;

import org.junit.jupiter.api.Test;
import org.set.cards.action.ActionCard;
import org.set.cards.action.ActionCardType;
import org.set.cards.expedition.ExpeditionCard;
import org.set.cards.expedition.ExpeditionCardType;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    @Test
    public void doubleRemoveSingleUseCard() {
        String expectedError = "This card is already removed and cannot be removed once again.";

        Card actionCard = new ActionCard(ActionCardType.Compass);
        Card expeditionCard = new ExpeditionCard(ExpeditionCardType.Giant_Machete);

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

        Card actionCard = new ActionCard(ActionCardType.Scientist);
        Card expeditionCard = new ExpeditionCard(ExpeditionCardType.Traveller);

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
        String[] types = {"GREEN", "YELLOW", "BLUE", "PURPLE", "JOKER"};
        for (CardType cardType : CardType.values()) {
            assertTrue(Arrays.asList(types).contains(cardType.toString()));
        }
    }
}
