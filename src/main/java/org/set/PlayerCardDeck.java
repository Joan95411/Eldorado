package org.set;

import org.set.cards.Card;
import org.set.cards.expedition.ExpeditionCard;
import org.set.cards.expedition.ExpeditionCardType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PlayerCardDeck {
    private final List<Card> drawPile;
    private final List<Card> discardPile;
    private final List<Card> cardsInHand;
    private final List<Card> mustBePlayedCardsInHand;

    public PlayerCardDeck() {
        drawPile = new ArrayList<>();
        discardPile = new ArrayList<>();
        cardsInHand = new ArrayList<>();
        mustBePlayedCardsInHand = new ArrayList<>();
        int blueCount = 1;
        int greenCount = 3;
        int yellowCount = 4;

        Random random = new Random();

        // Add blue cards
        for (int i = 0; i < blueCount; i++) {
            drawPile.add(new ExpeditionCard(ExpeditionCardType.Sailor));
        }

        // Add green cards
        for (int i = 0; i < greenCount; i++) {
            drawPile.add(new ExpeditionCard(ExpeditionCardType.Explorer));
        }

        // Add yellow cards
        for (int i = 0; i < yellowCount; i++) {
            drawPile.add(new ExpeditionCard(ExpeditionCardType.Traveller));
        }

        Collections.shuffle(drawPile, random);
    }

    // shuffle the discard pile to create a new pile
    private void shuffle() {
        drawPile.addAll(discardPile);
        discardPile.clear();
        Collections.shuffle(drawPile);
    }

    // Add a card to the discard pile
    public void discard(Card card) {
        discardPile.add(card);
    }

    public void discard(List<Card> discardedCards) {
        discardPile.addAll(discardedCards);
    }

    // return the top card of the drawPile, if it is empty shuffle discardPile to
    // create new drawPile
    public void drawCards(boolean cardsMustBePlayedThisTurn) {
        if (drawPile.isEmpty()) {
            shuffle();
        }
        
        if (!drawPile.isEmpty()) {
            if (cardsMustBePlayedThisTurn) {
                mustBePlayedCardsInHand.add(drawPile.remove(drawPile.size() - 1));
            } else {
                cardsInHand.add(drawPile.remove(drawPile.size() - 1));
            }
        }
    }

    public void drawExpeditionCard(ExpeditionCardType expeditionCardType) {
        cardsInHand.add(new ExpeditionCard(expeditionCardType));
    }

    public void draw(int numberOfCards) {
        draw(numberOfCards, false);
    }

    public void draw(int numberOfCards, boolean cardsMustBePlayedThisTurn) {
        for (int i = 0; i < numberOfCards; i++) {
            drawCards(cardsMustBePlayedThisTurn);
        }
    }

    public List<Card> getDrawPile() {
        return drawPile;
    }

    public List<Card> getDiscardPile() {
        return discardPile;
    }

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

    public List<Card> getMustBePlayedCardsInHand() {
        return mustBePlayedCardsInHand;
    }
}
