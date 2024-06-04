package org.set.tokens;

import org.set.player.Player;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Test class for the {@link Token} class.
 */
public class TokenTest {
    private static ArrayList<Token> tokens = new ArrayList<>();
    private static Player player = new Player(Color.WHITE);

    /**
     * Test for creating all the tokens.
     */
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

    /**
     * Test for creating a token without a power.
     * This should not be possible because the tokens: Coin, Machete and Paddle should have a power.
     */
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

    /**
     * Test for creating a token with a power.
     * This should not be possible because the tokens: Draw, Remove, Replace, ImmediatePlay, PassThrough, Adjacent and Symbol cannot have a power.
     */
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

    /**
     * Test for using all the tokens.
     * TODO: When the actions for the tokens are finished they should be tested individually like {@link org.set.cards.ActionCardTest}
     */
    @Test
    public void useToken() {
        for (Token token : tokens) {
            token.useToken(player);
        }
    }
}
