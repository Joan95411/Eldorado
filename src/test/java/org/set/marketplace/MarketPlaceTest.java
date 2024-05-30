package org.set.marketplace;

import org.junit.jupiter.api.Test;

public class MarketPlaceTest {
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
