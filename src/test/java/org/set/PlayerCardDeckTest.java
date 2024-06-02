package org.set;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.cards.Card;
import org.set.cards.expedition.ExpeditionCard;
import org.set.cards.expedition.ExpeditionCardType;
import org.set.player.PlayerCardDeck;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerCardDeckTest {
    PlayerCardDeck playerCardDeck;
    Card blueCard;
    Card yellowCard;
    Card greenCard;

    @BeforeEach
    void SetUp() {
        blueCard = new ExpeditionCard(ExpeditionCardType.Sailor);
        yellowCard = new ExpeditionCard(ExpeditionCardType.Photographer);
        greenCard = new ExpeditionCard(ExpeditionCardType.Explorer);

        playerCardDeck = new PlayerCardDeck();
        playerCardDeck.getDrawPile().clear();
        playerCardDeck.getDiscardPile().clear();
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
        List<Card> cardsToDiscard = new ArrayList<>();
        cardsToDiscard.add(blueCard);
        cardsToDiscard.add(yellowCard);
        cardsToDiscard.add(greenCard);
        playerCardDeck.discard(cardsToDiscard);
        assertEquals(cardsToDiscard, playerCardDeck.getDiscardPile());
    }

    @Test
    public void TestDrawSingleCard() {
        playerCardDeck.discard(greenCard);
        playerCardDeck.draw(1);
        assertEquals(greenCard, playerCardDeck.getCardsInHand().get(0));
        playerCardDeck.discard(blueCard);
        playerCardDeck.draw(1);
        assertEquals(blueCard, playerCardDeck.getCardsInHand().get(1));
    }

    @Test
    public void TestDrawMultipleCards() {
        playerCardDeck.discard(greenCard);
        playerCardDeck.discard(blueCard);
        playerCardDeck.draw(2);
        List<Card> drawnCards = playerCardDeck.getCardsInHand();
        assertTrue(drawnCards.contains(blueCard));
        assertTrue(drawnCards.contains(greenCard));
    }

    @Test
    public void TestDrawTooManyCards() {
        playerCardDeck.discard(greenCard);
        playerCardDeck.discard(blueCard);
        playerCardDeck.draw(9);
        List<Card> drawnCards = playerCardDeck.getCardsInHand();
        assertEquals(2, drawnCards.size());
        assertTrue(drawnCards.contains(blueCard));
        assertTrue(drawnCards.contains(greenCard));
    }

    @Test
    public void TestShuffle() {
        playerCardDeck.discard(greenCard);
        playerCardDeck.discard(blueCard);
        playerCardDeck.discard(yellowCard);
        List<Card> discardPile = playerCardDeck.getDiscardPile();
        playerCardDeck.draw(3);
        List<Card> drawnCards = playerCardDeck.getCardsInHand();
        assertNotEquals(null, discardPile);
        assertNotEquals(discardPile.toArray(), drawnCards.toArray());
    }
}
