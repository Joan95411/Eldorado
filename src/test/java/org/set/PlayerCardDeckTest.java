package org.set;

import java.util.LinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.cards.Card;
import org.set.cards.expedition.ExpeditionCard;
import org.set.cards.expedition.ExpeditionCardType;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerCardDeckTest {
    PlayerCardDeck playerCardDeck;
    Card blueCard;
    Card yellowCard;
    Card greenCard;

    @BeforeEach
    void SetUp() {
        blueCard = new ExpeditionCard(ExpeditionCardType.Sailor, 2, true, 2);
        yellowCard = new ExpeditionCard(ExpeditionCardType.Photographer, 2, true, 2);
        greenCard = new ExpeditionCard(ExpeditionCardType.Explorer, 2, true, 2);

        playerCardDeck = new PlayerCardDeck();
    }

    @Test
    public void TestCardPile() {
        assertNotNull(playerCardDeck);
        assertNotNull(playerCardDeck.getDrawPile());
        assertNotNull(playerCardDeck.getDiscardPile());
    }

    @Test
    public void TestDiscard() {
        assertNotEquals(playerCardDeck.getDiscardPile(), null);
        assertNotEquals(playerCardDeck.getDrawPile(), null);
        assertFalse(playerCardDeck.getDiscardPile().contains(blueCard));
        assertFalse(playerCardDeck.getDrawPile().contains(blueCard));
        playerCardDeck.discard(blueCard);
        assertTrue(playerCardDeck.getDiscardPile().contains(blueCard));
        assertFalse(playerCardDeck.getDrawPile().contains(blueCard));
    }

    @Test
    public void TestDiscardMultipleCards() {
        LinkedList<Card> cardsToDiscard = new LinkedList<>();
        cardsToDiscard.add(blueCard);
        cardsToDiscard.add(yellowCard);
        cardsToDiscard.add(greenCard);
        playerCardDeck.discard(cardsToDiscard);
        assertEquals(cardsToDiscard, playerCardDeck.getDiscardPile());
    }

    @Test
    public void TestDrawSingleCard() {
        playerCardDeck.discard(greenCard);
        assertEquals(greenCard, playerCardDeck.draw());
        playerCardDeck.discard(blueCard);
        assertEquals(blueCard, playerCardDeck.draw());
    }

    @Test
    public void TestDrawSingleCardWithParameter() {
        playerCardDeck.discard(greenCard);
        assertEquals(greenCard, playerCardDeck.draw(1).remove());
        playerCardDeck.discard(blueCard);
        assertEquals(blueCard, playerCardDeck.draw(1).remove());
    }

    @Test
    public void TestDrawMultipleCardWithParameter() {
        playerCardDeck.discard(greenCard);
        playerCardDeck.discard(blueCard);
        LinkedList<Card> drawnCards = playerCardDeck.draw(2);
        assertTrue(drawnCards.contains(blueCard));
        assertTrue(drawnCards.contains(greenCard));
    }

    @Test
    public void TestDrawTooManyCards() {
        playerCardDeck.discard(greenCard);
        playerCardDeck.discard(blueCard);
        LinkedList<Card> drawnCards = playerCardDeck.draw(9);
        assertEquals(2, drawnCards.size());
        assertTrue(drawnCards.contains(blueCard));
        assertTrue(drawnCards.contains(greenCard));
    }

    @Test
    public void TestShuffle() {
        playerCardDeck.discard(greenCard);
        playerCardDeck.discard(blueCard);
        playerCardDeck.discard(yellowCard);
        LinkedList<Card> discardPile = playerCardDeck.getDiscardPile();
        LinkedList<Card> drawnCards = playerCardDeck.draw(3);
        assertNotEquals(null, discardPile);
        assertNotEquals(discardPile.toArray(), drawnCards.toArray());
    }
}
