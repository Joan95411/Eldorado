package org.set.tokens;

import org.set.Player;

public class TokenActionHandler {
    public void doAction(Token token, Player player) {
        CaveTokenType caveTokenType = token.caveTokenType;

        switch (caveTokenType) {
            case Machete:
                System.out.println("Machete token played");
                // Play machete, coin, or paddle tokens to move onto same-colored spaces.
                // Alternatively, you can use coin tokens to buy a card.

                return;

            case Coin:
                System.out.println("Coin token played");
                // Play machete, coin, or paddle tokens to move onto same-colored spaces.
                // Alternatively, you can use coin tokens to buy a card.

                return;

            case Paddle:
                System.out.println("Paddle token played");
                // Play machete, coin, or paddle tokens to move onto same-colored spaces.
                // Alternatively, you can use coin tokens to buy a card.

                return;

            case Draw:
                System.out.println("Draw token played");
                // These tokens allow you to draw an additional card from your draw pile.
                // Just like the Cartographer, this 4x token allows you to play that card in the
                // same turn you drew it.

                return;

            case Remove:
                System.out.println("Remove token played");
                // Play these tokens to remove any card in your hand from the game.

                return;

            case Replace:
                System.out.println("Replace token played");
                // These tokens allow you to replace the cards in your hand.
                // When you play the token, play up to 4 cards 3x from your hand above your
                // expedition board, then draw that many cards from your draw pile.

                return;

            case ImmediatePlay:
                System.out.println("ImmediatePlay token played");
                // Play this token immediately after using an item card.
                // Instead of removing that item from the game, put it on your discard pile
                // during phase 2 or your turn.

                return;

            case PassThrough:
                System.out.println("PassThrough token played");
                // After playing this token, you are allowed to move onto or past an occupied
                // space for the rest of your turn.
                // Mountains are still off-limits.

                return;

            case Adjacent:
                System.out.println("Adjacent token played");
                // This token works just like the Native.
                // Use this token to move onto any adjacent space, ignoring 2x its requirements.
                // You can't use this token to move onto an occupied space or a mountain.

                return;

            case Symbol:
                System.out.println("Symbol token played");
                // Play this token to change the symbol of the next card you play.
                // For example, you can use a Trailblazer (machete 3) as a coin 3 or paddle 3.

                return;

            default:
                throw new IllegalStateException("Unexpected value: " + caveTokenType);
        }
    }
}
