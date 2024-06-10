package org.set.tokens;

import org.set.player.Player;
import org.set.cards.CardType;

public class Token {
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
