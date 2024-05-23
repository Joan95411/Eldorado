package org.set;

import org.set.cards.Card;

import java.util.Collections;
import java.util.LinkedList;

public class CardPile {
    private final LinkedList<Card> drawPile;
    private final LinkedList<Card> discardPile;

    public CardPile() {
        drawPile = new LinkedList<>();
        discardPile = new LinkedList<>();
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
        //TODO: implement
    }

    //return the top card of the drawPile, if it is empty shuffle discardPile to create new drawPile
    public Card draw() {
        if (drawPile.isEmpty()) {
            shuffle();
        }
        return drawPile.remove();
    }

    public LinkedList<Card> draw(int numberOfCards){
        //TODO: implement
        return null;
    }

    public LinkedList<Card> getDrawPile() {
        return drawPile;
    }

    public LinkedList<Card> getDiscardPile() {
        return discardPile;
    }

}