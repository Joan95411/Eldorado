package org.set.marketplace;

import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link MarketPlace} class.
 */
public class MarketPlaceTest {
    /**
     * Test class buying a card.
     * The cards are generated and added to the market on new MarketPlace()
     * The logic of the method BuyCard() checks the cards in the current market and the gold amount
     * TODO: Suggestion: Add exceptions instead of true and false?
     */
    @Test
    public void BuyCard(){
        MarketPlace marketPlace = new MarketPlace();

        marketPlace.BuyCard("JACK-OF-ALL-TRADERS", 0);
        marketPlace.BuyCard("JACK-OF-ALL-TRADERS", 0);
        marketPlace.BuyCard("JACK-OF-ALL-TRADERS", 2);
        marketPlace.BuyCard("JACK-OF-ALL-TRADERS", 2);
        marketPlace.BuyCard("JACK-OF-ALL-TRADERS", 2);
        marketPlace.BuyCard("JACK-OF-ALL-TRADERS", 2);
        marketPlace.BuyCard("PIONEER", 5);
        marketPlace.BuyCard("PIONEER", 5);
        marketPlace.BuyCard("PIONEER", 5);
        marketPlace.BuyCard("PIONEER", 5);
        marketPlace.BuyCard("PIONEER", 5);
    }
}
