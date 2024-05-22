package org.set.tokens;

import org.set.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.*;
import org.set.cards.ActionCard;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenTest {
    private static ArrayList<Token> tokens = new ArrayList<>();
    private static Player player = new Player(Color.BLACK);

    @BeforeAll
    public static void createTokens() {
        tokens.add(new Token(CaveTokenType.Coin, 1));
        tokens.add(new Token(CaveTokenType.Coin, 2));
        tokens.add(new Token(CaveTokenType.Paddle, 1));
        tokens.add(new Token(CaveTokenType.Paddle, 2));
        tokens.add(new Token(CaveTokenType.Machete, 1));
        tokens.add(new Token(CaveTokenType.Machete, 2));
        tokens.add(new Token(CaveTokenType.Machete, 3));

        tokens.add(new Token(CaveTokenType.Draw));
        tokens.add(new Token(CaveTokenType.Remove));
        tokens.add(new Token(CaveTokenType.Replace));
        tokens.add(new Token(CaveTokenType.ImmediatePlay));
        tokens.add(new Token(CaveTokenType.PassThrough));
        tokens.add(new Token(CaveTokenType.Adjacent));
        tokens.add(new Token(CaveTokenType.Symbol));

        assertEquals(tokens.size(), 14);
    }

    @Test
    public void creatingIllegalTokensWithoutPower() {
        CaveTokenType[] caveTokenTypes = {CaveTokenType.Coin, CaveTokenType.Machete, CaveTokenType.Paddle};

        for (CaveTokenType tokenType : caveTokenTypes) {
            try {
                tokens.add(new Token(tokenType));
            } catch (Exception e) {
                assertEquals("The cave token type (" + tokenType + ") must have a power", e.getMessage());
            }
        }
    }

    @Test
    public void creatingIllegalTokensWithPower() {
        CaveTokenType[] caveTokenTypes = {CaveTokenType.Draw, CaveTokenType.Remove, CaveTokenType.Replace, CaveTokenType.ImmediatePlay, CaveTokenType.PassThrough, CaveTokenType.Adjacent, CaveTokenType.Symbol};

        for (CaveTokenType tokenType : caveTokenTypes) {
            try {
                tokens.add(new Token(tokenType, new Random().nextInt(3)));
            } catch (Exception e) {
                assertEquals("The cave token type (" + tokenType + ") cannot have a power", e.getMessage());
            }
        }
    }

    @Test
    public void useToken() {
        for (Token token : tokens) {
            token.useToken(player);
        }
    }
}
