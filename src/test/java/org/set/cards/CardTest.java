package org.set.cards;

import org.junit.jupiter.api.Test;
import org.set.cards.action.ActionCard;
import org.set.cards.action.ActionCardType;
import org.set.cards.expedition.ExpeditionCard;
import org.set.cards.expedition.ExpeditionCardType;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link Card} class.
 */
public class CardTest {

    /**
     * Test for creating an action card.
     */
    @Test
    public void createActionCard() {
        // for (ActionCardType actionCardType: ActionCardType.values()) {
        // cards.add(new ActionCard(actionCardType));
        // }
        //
        // assertEquals(cards.size(), ActionCardType.values().length);
    }

    /**
     * Test for creating an expedition card.
     */
    @Test
    public void createExpeditionCard() {
        // for (ActionCardType actionCardType: ActionCardType.values()) {
        // cards.add(new ActionCard(actionCardType));
        // }
        //
        // assertEquals(cards.size(), ActionCardType.values().length);
    }

    /**
     * Test for double removing a single use action and expedition card.
     * This should not be possible because the single use card can only be remove
     * once.
     */
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

    /**
     * Test for removing a non single use action and expedition card.
     * This should not be possible because card is not a single use card, therefore
     * it cannot be removed from the game.
     */
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

    /**
     * Test for the different card types.
     */
    @Test
    public void cardTypes() {
        String[] types = { "GREEN", "YELLOW", "BLUE", "PURPLE", "JOKER" };
        for (CardType cardType : CardType.values()) {
            assertTrue(Arrays.asList(types).contains(cardType.toString()));
        }
    }
}
