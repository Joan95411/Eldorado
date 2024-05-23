package org.set;

import org.set.cards.Card;
import org.set.cards.expedition.ExpeditionCard;
import org.set.cards.expedition.ExpeditionCardType;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class PlayerCardDeck {
    private final LinkedList<Card> drawPile;
    private final LinkedList<Card> discardPile;

    public PlayerCardDeck() {
        drawPile = new LinkedList<>();
        discardPile = new LinkedList<>();
        int blueCount = 1;
        int greenCount = 3;
        int yellowCount = 4;

        Random random = new Random();

        // Add green cards
        for (int i = 0; i < blueCount; i++) {
        	drawPile.add(new ExpeditionCard(ExpeditionCardType.Sailor, 0, false, 1));
        }

        // Add green cards
        for (int i = 0; i < greenCount; i++) {
        	drawPile.add(new ExpeditionCard(ExpeditionCardType.Explorer,0, false, 1));
        }

        // Add yellow cards
        for (int i = 0; i < yellowCount; i++) {
        	drawPile.add(new ExpeditionCard(ExpeditionCardType.Traveller, 0, false, 1));
        }

        Collections.shuffle(drawPile, random);
    }

    //shuffle the discard pile to create a new pile
    private void shuffle() {
        drawPile.addAll(discardPile);
        discardPile.clear();
        Collections.shuffle(drawPile);
    }

    //Add a card to the discard pile
    public void discard(Card card) {
        discardPile.add(card);
    }

    public void discard(LinkedList<Card> discardedCards){
        discardPile.addAll(discardedCards);
    }

    //return the top card of the drawPile, if it is empty shuffle discardPile to create new drawPile
    public Card draw() {
        if (drawPile.isEmpty()) {
            if(discardPile.isEmpty()){
                return null;
            }
            shuffle();
        }
        return drawPile.remove();
    }

    public LinkedList<Card> draw(int numberOfCards){
        LinkedList<Card> drawnCards = new LinkedList<>();
        Card card;
        for (int i = 0; i < numberOfCards; i ++){
            card = draw();
            if(card != null){
                drawnCards.add(card);
            }
        }
        return drawnCards;
    }

    public LinkedList<Card> getDrawPile() {
        return drawPile;
    }

    public LinkedList<Card> getDiscardPile() {
        return discardPile;
    }

}