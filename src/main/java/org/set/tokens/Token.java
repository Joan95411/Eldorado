package org.set.tokens;

import org.set.cards.CardActionHandler;
import org.set.cards.action.ActionCardType;
import org.set.cards.expedition.ExpeditionCardType;
import org.set.player.Player;
import org.set.cards.CardType;

public class Token {
    //can you put all possible tokens in a list when init game? so i can randomly draw 4 for each cave
    public CardType cardType;
    public CaveTokenType caveTokenType;
    public int power;

    public Token(CaveTokenType caveTokenType) {
        this.cardType = caveTokenType.getCardType();
        this.caveTokenType = caveTokenType;
        this.power = caveTokenType.getPower();
    }

    public void useToken(Player player) {
        TokenActionHandler actionHandler = new TokenActionHandler();
        actionHandler.doAction(this, player);
    }
}
