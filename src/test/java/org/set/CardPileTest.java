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
	PlayerCardDeck cardPile;
    Card blueCard;
    Card yellowCard;
    Card greenCard;

    @BeforeEach
    void SetUp(){
        blueCard = new ExpeditionCard(ExpeditionCardType.Sailor, 2, true, 2);
        yellowCard = new ExpeditionCard(ExpeditionCardType.Photographer, 2, true, 2);
        greenCard = new ExpeditionCard(ExpeditionCardType.Explorer, 2, true, 2);

        cardPile = new PlayerCardDeck();
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
    public void TestNoCardsToDrawException(){
        assertThrows(Exception.class , () -> cardPile.draw());
    }

    @Test
    public void TestDrawSingleCard() throws Exception {
        cardPile.discard(greenCard);
        assertEquals(greenCard, cardPile.draw());
        cardPile.discard(blueCard);
        assertEquals(blueCard, cardPile.draw());
    }

    @Test
    public void TestShuffle() throws Exception{
        cardPile.discard(greenCard);
        cardPile.discard(blueCard);
        cardPile.discard(yellowCard);
        LinkedList<Card> discardPile = cardPile.getDiscardPile();
        LinkedList<Card> drawnCards = new LinkedList<Card>();
        for (int i = 0; i < 3; i++){
            drawnCards.add(cardPile.draw());
        }
        assertTrue(drawnCards.contains(greenCard));
        assertTrue(drawnCards.contains(blueCard));
        assertTrue(drawnCards.contains(yellowCard));
        assertNotEquals(discardPile.toArray(), drawnCards.toArray());
    }
}
