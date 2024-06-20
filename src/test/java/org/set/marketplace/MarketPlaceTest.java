package org.set.marketplace;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.boardPieces.Util;
import org.set.cards.expedition.ExpeditionCard;
import org.set.cards.expedition.ExpeditionCardType;

import java.io.FileNotFoundException;
import java.util.HashMap;
import org.set.cards.*;
import org.set.cards.action.ActionCard;
import org.set.cards.action.ActionCardType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test class for the {@link MarketPlace} class.
 */
public class MarketPlaceTest {
    private static MarketPlace marketPlace;
    private static HashMap<Card, Integer> marketBoard = new HashMap<>();
    private static Card[] cards = { new ExpeditionCard(ExpeditionCardType.valueOf("Trailblazer")),
                                    new ExpeditionCard(ExpeditionCardType.valueOf("Jack")),
                                    new ExpeditionCard(ExpeditionCardType.valueOf("Photographer")),
                                    new ActionCard(ActionCardType.valueOf("Transmitter")),
                                    new ExpeditionCard(ExpeditionCardType.valueOf("Treasure_Chest")),
                                    new ExpeditionCard(ExpeditionCardType.valueOf("Scout"))};

    /**
     * Test creating marketplace
     */
    @BeforeEach
    public void setup() {
        marketPlace = new MarketPlace();

        for (Card card : cards) {
            marketBoard.put(card, 3);
        }
    }

    /**
     * Test class buying all cards.
     * The cards are generated and added to the market on new MarketPlace()
     * The logic of the method BuyCard() checks the cards in the current market and the gold amount
     * TODO: Suggestion: Add exceptions instead of true and false?
     */
    @Test
    public void BuyCards(){
        for (Card card : cards) {
            marketPlace.BuyCard(card.name, 5);
            Integer numberOfCards = marketBoard.get(card);
            marketBoard.put(card, (numberOfCards - 1));

        }

        for (Card card : cards) {
            marketPlace.BuyCard(card.name, 5);
            Integer numberOfCards = marketBoard.get(card);
            marketBoard.put(card, (numberOfCards - 1));

        }

        for (Card card : cards) {
            marketPlace.BuyCard(card.name, 5);
            marketBoard.remove(card);

        }
    }

    // Test class buying all cards without having enough gold.
    @Test
    public void BuyCardsWithoutEnoughGold(){
        for (Card card : cards) {
            try {
                marketPlace.BuyCard(card.name, 0);
            } catch (IllegalArgumentException e) {
                assertEquals(e.getMessage(), "Not enough gold to buy this card");
            }
        }
    }

    // Test trying to activate the this.AddCardToMarketBoard(cardName, goldAmount) function
    @Test
    public void BuyToAddToMarketBoardCard(){
        for (int i = 0; i < 10; i++) {
            marketPlace.BuyCard("Trailblazer", 10);
        }
    }

    // Test trying to buying non existing cards.
//    @Test
//    public void BuyNonExistingCard(){
//        marketPlace.BuyCard("Fake", 10);
//    }
}
