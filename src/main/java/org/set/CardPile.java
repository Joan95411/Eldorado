package org.set;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.set.Card;

public class CardPile {
    private LinkedList<Card> drawPile;
    private LinkedList<Card> discardPile;
    public Exception NoCardsToDrawException = new Exception("The drawPile and discardPile are empty, can't draw any cards");




    public CardPile(){
    }

    //shuffle the discard pile to create a new pile
    private void shuffle(){

    }

    //Add a card to the discard pile
    public void discard(Card card){

    }

    //return the top card of the drawPile, if it is empty shuffle discardPile to create new drawPile
    public Card draw() throws Exception{
        return null;
    }

    public LinkedList<Card> getDrawPile(){
        return drawPile;
    }

    public LinkedList<Card> getDiscardPile(){
        return discardPile;
    }

}
