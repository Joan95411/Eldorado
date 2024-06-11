package org.set.tokens;

import org.set.cards.CardType;

public enum CaveTokenType {
    MacheteOne(CardType.GREEN, 1),
    MacheteTwo(CardType.GREEN, 2),
    MacheteThree(CardType.GREEN,3),
    CoinOne(CardType.YELLOW,1),
    CoinTwo(CardType.YELLOW,2),
    PaddleOne(CardType.BLUE,1),
    PaddleTwo(CardType.BLUE,2),
    Draw(CardType.PURPLE),
    Remove(CardType.PURPLE),
    Replace(CardType.PURPLE),
    ImmediatePlay(CardType.PURPLE),
    PassThrough(CardType.PURPLE),
    Adjacent(CardType.PURPLE),
    Symbol(CardType.PURPLE);

    private final CardType cardType;
    private int power;

    CaveTokenType(CardType cardType) {
        this.cardType = cardType;
    }

    CaveTokenType(CardType cardType, int power) {
        this.cardType = cardType;
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public CardType getCardType() {
        return cardType;
    }
}
