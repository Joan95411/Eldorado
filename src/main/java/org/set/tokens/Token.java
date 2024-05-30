package org.set.tokens;

import org.set.Player;
import org.set.cards.CardType;

public class Token {
    public CardType cardType;
    public CaveTokenType caveTokenType;
    public int power;

    public Token(CaveTokenType caveTokenType, int power) {
        this.cardType = setCardType(caveTokenType);
        this.caveTokenType = caveTokenType;
        this.power = power;

        if (!(caveTokenType == CaveTokenType.Coin || caveTokenType == CaveTokenType.Paddle
                || caveTokenType == CaveTokenType.Machete)) {
            throw new IllegalStateException("The cave token type (" + caveTokenType + ") cannot have a power");
        }
    }

    public Token(CaveTokenType caveTokenType) {
        this.cardType = setCardType(caveTokenType);
        this.caveTokenType = caveTokenType;

        if (caveTokenType == CaveTokenType.Coin || caveTokenType == CaveTokenType.Paddle
                || caveTokenType == CaveTokenType.Machete) {
            throw new IllegalStateException("The cave token type (" + caveTokenType + ") must have a power");
        }
    }

    private CardType setCardType(CaveTokenType caveTokenType) {
        if (caveTokenType == CaveTokenType.Coin) {
            return CardType.YELLOW;
        } else if (caveTokenType == CaveTokenType.Paddle) {
            return CardType.BLUE;
        } else if (caveTokenType == CaveTokenType.Machete) {
            return CardType.GREEN;
        } else {
            return CardType.PURPLE;
        }
    }

    public void useToken(Player player) {
        TokenActionHandler actionHandler = new TokenActionHandler();
        actionHandler.doAction(this, player);
    }
}
