package org.set.tokens;

import org.set.cards.Card;
import org.set.cards.CardType;
import org.set.cards.expedition.ExpeditionCard;
import org.set.game.InputHelper;
import org.set.player.Player;

public class TokenActionHandler {

    public void doAction(Token token, Player player) {
        CaveTokenType caveTokenType = token.getType();
        CardType cardType = token.getType().getCardType();

        if (cardType == CardType.PURPLE) {
            handleActionTokens(caveTokenType, player);
        } else {
            handleBasicTokens(caveTokenType, player);
        }
    }

    private void handleActionTokens(CaveTokenType caveTokenType, Player player) {
        if (caveTokenType == CaveTokenType.Draw) {
            drawAction(player);
        }

        if (caveTokenType == CaveTokenType.Remove) {
            removeAction(player);
        }

        if (caveTokenType == CaveTokenType.Replace) {
            replaceAction(player);
        }

        if (caveTokenType == CaveTokenType.ImmediatePlay) {
            immediatePlayAction(player);
        }

        if (caveTokenType == CaveTokenType.PassThrough) {
            passThroughAction(player);
        }

        if (caveTokenType == CaveTokenType.Adjacent) {
            adjacentAction(player);
        }

        if (caveTokenType == CaveTokenType.Symbol) {
            symbolAction(player);
        }
    }

    private void handleBasicTokens(CaveTokenType caveTokenType, Player player) {
        // Play machete, coin, or paddle tokens to move onto same-colored spaces.
         player.myDeck.addCard(new ExpeditionCard(caveTokenType.name(), 0, true, caveTokenType.getPower()), true);
    }

    private void drawAction(Player player) {
        // Draw 1 cards from the draw pile and play it this turn
        player.myDeck.draw(1, true);
    }

    private void removeAction(Player player) {
        // Remove any card in your hand from the game.

        if (!player.myDeck.getCardsInHand().isEmpty()) {

            int counter = 0;
            for (Card card : player.myDeck.getCardsInHand()) {
                System.out.println("(" + counter + ") - " + card.name);
                counter++;
            }
            int cardIndex = InputHelper.getIntInput(
                    "What card do you want to remove?; Input index (e.g. 0), or '-1' to stop",
                    player.myDeck.getCardsInHand().size() - 1, -1);
            Card selectedCard = player.myDeck.getCardsInHand().get(cardIndex);

            player.myDeck.removeCard(selectedCard);
        }
    }

    private void replaceAction(Player player) {
        System.out.println("Replace token played");
        // These tokens allow you to replace the cards in your hand.
        // When you play the token, play up to 4 cards 3x from your hand above your
        // expedition board, then draw that many cards from your draw pile.

        System.out.println(player.getCurrentCol() + player.getCurrentRow());
    }

    private void immediatePlayAction(Player player) {
        System.out.println("ImmediatePlay token played");
        // Play this token immediately after using an item card.
        // Instead of removing that item from the game, put it on your discard pile
        // during phase 2 or your turn.

        System.out.println(player.getCurrentCol() + player.getCurrentRow());
    }

    private void passThroughAction(Player player) {
        System.out.println("PassThrough token played");
        // After playing this token, you are allowed to move onto or past an occupied
        // space for the rest of your turn.
        // Mountains are still off-limits.

        System.out.println(player.getCurrentCol() + player.getCurrentRow());
    }

    private void adjacentAction(Player player) {
        System.out.println("Adjacent token played");
        // This token works just like the Native.
        // Use this token to move onto any adjacent space, ignoring 2x its requirements.
        // You can't use this token to move onto an occupied space or a mountain.

        System.out.println(player.getCurrentCol() + player.getCurrentRow());
    }

    private void symbolAction(Player player) {
        // Play this token to change the symbol of the next card you play.
        // For example, you can use a Trailblazer (machete 3) as a coin 3 or paddle 3.

        int counter = 0;
        for (Card card : player.myDeck.getCardsInHand()) {
            ExpeditionCard expeditionCard = (ExpeditionCard) card;
            System.out.println("(" + counter + ") - " + card.name + " with power: " + expeditionCard.getPower());
            counter++;
        }
        int cardIndex = InputHelper.getIntInput(
                "What expedition card do you want to play and change the symbol?; Input index (e.g. 0), or '-1' to stop",
                player.myDeck.getCardsInHand().size() - 1, -1);

        ExpeditionCard expeditionCard = (ExpeditionCard) player.myDeck.getCardsInHand().get(cardIndex);

        counter = 0;
        for (CardType ct : CardType.values()) {
            System.out.println("(" + counter + ") - " + ct);
            counter++;
        }
        int cardTypeIndex = InputHelper.getIntInput("What symbol do you want to change " + expeditionCard.name + " to?",
                4, 0);
        // System.out.println(expeditionCard.name);

        expeditionCard.cardType = CardType.values()[cardTypeIndex];

        // System.out.println(expeditionCard.name);
    }
}
