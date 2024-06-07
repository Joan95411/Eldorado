package org.set.tokens;

import org.set.cards.Card;
import org.set.cards.expedition.ExpeditionCardType;
import org.set.player.Player;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.*;
import java.io.ByteArrayInputStream;
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
//    @Test
//    public void useToken() {
//        for (Token token : tokens) {
//            token.useToken(player);
//        }
//    }

    // Test the draw token action
    @Test
    public void useDrawToken() {
        int initialCardsInHand = player.myDeck.getCardsInHand().size();
        int initialMustBePlayedCardsInHand = player.myDeck.getMustBePlayedCardsInHand().size();

        Token drawToken = new Token(CaveTokenType.Draw);
        drawToken.useToken(player);

        assertEquals(initialCardsInHand, player.myDeck.getCardsInHand().size(), "The player should not have any cards in his hand");
        assertEquals(initialMustBePlayedCardsInHand + 1, player.myDeck.getMustBePlayedCardsInHand().size(), "The player should have one card in his hand that must be played");
    }

    // Test the remove token action and if it can be played without having any cards in hand to remove
    @Test
    public void useRemoveTokenWhenNoCardsInDeck() {
        for (int i = 0; i < player.myDeck.getCardsInHand().size(); i++) {
            player.myDeck.removeCard(player.myDeck.getCardsInHand().get(i));
        }

        int initialCardsInHandSize = player.myDeck.getCardsInHand().size();
        int initialDiscardPileSize = player.myDeck.getDiscardPile().size();

        Token removeToken = new Token(CaveTokenType.Remove);
        removeToken.useToken(player);

        assertEquals(initialCardsInHandSize, player.myDeck.getCardsInHand().size(), "The player should have the same amount of cards in his hands");
        assertEquals(initialDiscardPileSize, player.myDeck.getDiscardPile().size(), "The player should have the same amount of cards on the discard pile");
    }

    // Test the remove token action and if it can be played when you have at least one card in hand to remove
    @Test
    public void useRemoveTokenWithCardsInDeck() {
        player.myDeck.draw(1);

        int initialCardsInHandSize = player.myDeck.getCardsInHand().size();
        int initialDiscardPileSize = player.myDeck.getDiscardPile().size();

        Token removeToken = new Token(CaveTokenType.Remove);

        String input = "0\n"; // Prepare the input data
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); // Create a ByteArrayInputStream with the input data
        System.setIn(inputStream); // Set System.in to use the ByteArrayInputStream

        removeToken.useToken(player);

        assertEquals(initialCardsInHandSize - 1, player.myDeck.getCardsInHand().size(),  "The players hand should have one card less");
        assertEquals(initialDiscardPileSize, player.myDeck.getDiscardPile().size(),  "The player's deck should not have changed");
    }

    // Test the replace token action
    @Test
    public void useReplaceToken() {
        Token replaceToken = new Token(CaveTokenType.Replace);
        replaceToken.useToken(player);
    }

    // Test the immediate play token action
    @Test
    public void useImmediatePlayToken() {
        Token immediatePlayToken = new Token(CaveTokenType.ImmediatePlay);
        immediatePlayToken.useToken(player);
    }

    // Test the pass through token action
    @Test
    public void usePassThroughToken() {
        Token passThroughToken = new Token(CaveTokenType.PassThrough);
        passThroughToken.useToken(player);
    }

    // Test the adjecent token action
    @Test
    public void useAdjacentToken() { System.out.println(player.myDeck.getCardsInHand().size());
        Token adjecentToken = new Token(CaveTokenType.Adjacent);
        adjecentToken.useToken(player);
    }

    // Test the symbol token action
    // TODO: the expedition card is not played, does it need to be played?
    @Test
    public void useSymbolToken() {
        ExpeditionCardType initialSymbolToken = ExpeditionCardType.Explorer;
        ExpeditionCardType finalSymbolToken = ExpeditionCardType.Sailor;

        player.myDeck.drawExpeditionCard(initialSymbolToken);

        assertEquals(1, player.myDeck.getCardsInHand().size(), 1, "The player should have 1 card in his hand");
        assertEquals(initialSymbolToken.toString(), player.myDeck.getCardsInHand().get(0).name,  "The player must have 1 Expedition (" + initialSymbolToken.toString() + ") card in his hand");
        assertEquals(0, player.myDeck.getDiscardPile().size(), "The player should not have discarded any cards");

        Token symbolToken = new Token(CaveTokenType.Symbol);

//        String input = ("0\n" + finalSymbolToken + "\n");
//        System.out.println(input);
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); // Create a ByteArrayInputStream with the input data
//        System.setIn(inputStream); // Set System.in to use the ByteArrayInputStream
//
//        symbolToken.useToken(player);
//
//        assertEquals(1, player.myDeck.getCardsInHand().size(), "The player should have 1 card in his hand");
//        assertEquals(finalSymbolToken.toString(), player.myDeck.getCardsInHand().get(0).name, "The player must have 1 Expedition (" + finalSymbolToken.toString() + ") card in his hand");
//        assertEquals(0, player.myDeck.getDiscardPile().size(), "The player should not have discarded any cards");
    }
}
