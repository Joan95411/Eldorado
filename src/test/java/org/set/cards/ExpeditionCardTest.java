package org.set.cards;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.set.cards.expedition.ExpeditionCard;
import org.set.cards.expedition.ExpeditionCardType;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the {@link ExpeditionCard} class.
 */
public class ExpeditionCardTest {
    private static ArrayList<Card> cards = new ArrayList<>();

    /**
     * Test for creating all the expedition cards.
     */
    @BeforeAll
    public static void createActionCards() {
        for (ExpeditionCardType expeditionCardType: ExpeditionCardType.values()) {
            cards.add(new ExpeditionCard(expeditionCardType));
        }

        assertEquals(cards.size(), ExpeditionCardType.values().length);
    }

    /**
     * Test for removing expedition cards.
     * Also testing if it is not possible to remove a not single use card
     */
    @Test
    public void removeExpeditionCards() {
        ExpeditionCard card = new ExpeditionCard(ExpeditionCardType.Sailor);
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

    /**
     * Test for removing single use expedition cards.
     */
    @Test
    public void removeSingleUseExpeditionCards() {
        ExpeditionCard card = new ExpeditionCard(ExpeditionCardType.Giant_Machete);
        assertEquals(card.singleUse, true);
        assertEquals(card.removedCard, false);

         card.removeCard();
         assertEquals(card.singleUse, true);
         assertEquals(card.removedCard, true);
    }

    /**
     * Test for drawing expedition cards.
     */
    @Test
    public void setExpeditionCardPower() {
        ExpeditionCard card = new ExpeditionCard(ExpeditionCardType.Sailor);
        card.setPower(0);

        assertEquals(card.getPower(), 0);
    }

    /**
     * Test for drawing expedition cards.
     */
    @Test
    public void drawingExpeditionCard() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        ExpeditionCard card = new ExpeditionCard(ExpeditionCardType.Sailor);
        card.draw(g2d, 0, 0 ,1,2);
    }

    /**
     * Test for getting expedition card value.
     */
    @Test
    public void gettingExpeditionCardValue() {
        for (Card card: cards) {
            ExpeditionCard expeditionCard = (ExpeditionCard) card;

            if (expeditionCard.getCardType() == CardType.YELLOW) {
                assertEquals(card.getPower(), card.getValue());
            } else {
                assertEquals(0.5, card.getValue());
            }

            assertEquals("Card{cardType=" + card.getCardType() + ", name=" + card.getName() + ", power=" + card.getPower() + '}', card.toString());
        }
    }
}
