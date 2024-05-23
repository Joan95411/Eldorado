package org.set;

import java.awt.*;
import java.util.LinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.cards.Card;
import org.set.cards.expedition.ExpeditionCard;
import org.set.cards.expedition.ExpeditionCardType;

import static org.junit.jupiter.api.Assertions.*;

public class CardPileTest {
    CardPile cardPile;
    Card blueCard;
    Card yellowCard;
    Card greenCard;

    @BeforeEach
    void SetUp(){
        blueCard = new ExpeditionCard(ExpeditionCardType.Sailor, 2, true, 2);
        yellowCard = new ExpeditionCard(ExpeditionCardType.Photographer, 2, true, 2);
        greenCard = new ExpeditionCard(ExpeditionCardType.Explorer, 2, true, 2);

        cardPile = new CardPile();
    }

    @Test
    public void TestCardPile(){
        assertNotNull(cardPile);
        assertNotNull(cardPile.getDrawPile());
        assertNotNull(cardPile.getDiscardPile());
    }

    @Test
    public void TestDiscard(){
        assertNotEquals(cardPile.getDiscardPile(), null);
        assertNotEquals(cardPile.getDrawPile(), null);
        assertFalse(cardPile.getDiscardPile().contains(blueCard));
        assertFalse(cardPile.getDrawPile().contains(blueCard));
        cardPile.discard(blueCard);
        assertTrue(cardPile.getDiscardPile().contains(blueCard));
        assertFalse(cardPile.getDrawPile().contains(blueCard));
    }

    @Test
    public void TestDiscardMultipleCards(){
        LinkedList<Card> cardsToDiscard = new LinkedList<>();
        cardsToDiscard.add(blueCard);
        cardsToDiscard.add(yellowCard);
        cardsToDiscard.add(greenCard);
        cardPile.discard(cardsToDiscard);
        assertEquals(cardsToDiscard, cardPile.getDiscardPile());
    }

    @Test
    public void TestDrawSingleCard() {
        cardPile.discard(greenCard);
        assertEquals(greenCard, cardPile.draw());
        cardPile.discard(blueCard);
        assertEquals(blueCard, cardPile.draw());
    }

    @Test
    public void TestDrawSingleCardWithParameter() {
        cardPile.discard(greenCard);
        assertEquals(greenCard, cardPile.draw(1).remove());
        cardPile.discard(blueCard);
        assertEquals(blueCard, cardPile.draw(1).remove());
    }

    @Test
    public void TestDrawMultipleCardWithParameter() {
        cardPile.discard(greenCard);
        cardPile.discard(blueCard);
        LinkedList<Card> drawnCards = cardPile.draw(2);
        assertTrue(drawnCards.contains(blueCard));
        assertTrue(drawnCards.contains(greenCard));
    }

    @Test
    public void TestDrawTooManyCards(){
        cardPile.discard(greenCard);
        cardPile.discard(blueCard);
        LinkedList<Card> drawnCards = cardPile.draw(9);
        assertEquals(2, drawnCards.size());
        assertTrue(drawnCards.contains(blueCard));
        assertTrue(drawnCards.contains(greenCard));
    }

    @Test
    public void TestShuffle() {
        cardPile.discard(greenCard);
        cardPile.discard(blueCard);
        cardPile.discard(yellowCard);
        LinkedList<Card> discardPile = cardPile.getDiscardPile();
        LinkedList<Card> drawnCards = cardPile.draw(3);
        assertNotEquals(null, discardPile);
        assertNotEquals(discardPile.toArray(), drawnCards.toArray());
    }
}
