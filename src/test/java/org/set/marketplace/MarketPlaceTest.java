package org.set.marketplace;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.boardPieces.Util;
import org.set.cards.expedition.ExpeditionCardType;

import java.io.FileNotFoundException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test class for the {@link MarketPlace} class.
 */
public class MarketPlaceTest {
    private static MarketPlace marketPlace;
    private static HashMap<String, Integer> marketBoard = new HashMap<>();
    private static String[] cards = {"Trailblazer", "Jack_of_all_trades", "Photographer", "Transmitter", "Treasure_Chest", "Scout"};

    /**
     * Test creating marketplace
     */
    @BeforeEach
    public void setup() {
        marketPlace = new MarketPlace();

        for (String card : cards) {
            marketBoard.put(card, 3);
        }

        assertEquals(marketPlace.currentMarketBoard, marketBoard);
    }

    /**
     * Test class buying all cards.
     * The cards are generated and added to the market on new MarketPlace()
     * The logic of the method BuyCard() checks the cards in the current market and the gold amount
     * TODO: Suggestion: Add exceptions instead of true and false?
     */
    @Test
    public void BuyCards(){
        for (String card : cards) {
            marketPlace.BuyCard(card, 5);
            Integer numberOfCards = marketBoard.get(card);
            marketBoard.put(card, (numberOfCards - 1));

            assertEquals(marketPlace.currentMarketBoard, marketBoard);
        }

        for (String card : cards) {
            marketPlace.BuyCard(card, 5);
            Integer numberOfCards = marketBoard.get(card);
            marketBoard.put(card, (numberOfCards - 1));

            assertEquals(marketPlace.currentMarketBoard, marketBoard);
        }

        for (String card : cards) {
            marketPlace.BuyCard(card, 5);
            marketBoard.remove(card);

            assertEquals(marketPlace.currentMarketBoard, marketBoard);
        }
    }

    /**
     * Test class buying all cards without having enough gold.
     */
    @Test
    public void BuyCardsWithoutEnoughGold(){
        for (String card : cards) {
            try {
                marketPlace.BuyCard(card, 0);
                assertNotEquals(marketPlace.currentMarketBoard, marketBoard);
            } catch (IllegalArgumentException e) {
                assertEquals(e.getMessage(), "Not enough gold to buy this card");
                assertEquals(marketPlace.currentMarketBoard, marketBoard);
            }
        }
    }
}
