package org.set.tokens;

import org.set.cards.Card;
import org.set.cards.CardType;
import org.set.cards.expedition.ExpeditionCard;
import org.set.player.Player;

import java.util.Scanner;

public class TokenActionHandler {
    private final Scanner scanner = new Scanner(System.in);

    public void doAction(Token token, Player player) {
        CaveTokenType caveTokenType = token.caveTokenType;
        CardType cardType = token.caveTokenType.getCardType();

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
        CardType cardType = caveTokenType.getCardType();

        if (cardType == CardType.GREEN) {
            System.out.println("Machete token played");
        }

        if (cardType == CardType.YELLOW) {
            System.out.println("Coin token played");
        }

        if (cardType == CardType.BLUE) {
            System.out.println("Paddle token played");
        }

        System.out.println(player.getCurrentCol() + player.getCurrentRow());
        System.out.println("Power" + caveTokenType.getPower());
    }

    private void drawAction(Player player) {
        // Draw 1 cards from the draw pile and play it this turn
        player.myDeck.draw(1, true);
    }

    private void removeAction(Player player) {
        // Remove any card in your hand from the game.

        if (!player.myDeck.getCardsInHand().isEmpty()) {
            System.out.println("What card do you want to remove?");

            int counter = 0;
            for (Card card : player.myDeck.getCardsInHand()) {
                System.out.println("(" + counter + ") - " + card.name);
                counter++;
            }

            String userInput = scanner.nextLine();
            Card selectedCard = player.myDeck.getCardsInHand().get(Integer.parseInt(userInput));

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

        System.out.println("What expedition card do you want to play and change the symbol?");

        int counter = 0;
        for (Card card : player.myDeck.getCardsInHand()) {
            ExpeditionCard expeditionCard = (ExpeditionCard) card;
            System.out.println("(" + counter + ") - " + card.name + " with power: " + expeditionCard.getPower());
            counter++;
        }

        int cardIndex = scanner.nextInt();
        ExpeditionCard expeditionCard = (ExpeditionCard) player.myDeck.getCardsInHand().get(cardIndex);

        System.out.println("What symbol do you want to change " + expeditionCard.name + " to?");
        String symbol = scanner.nextLine();

        System.out.println(symbol);

//        System.out.println(expeditionCard.name);

        expeditionCard.name = symbol;

//        System.out.println(expeditionCard.name);
    }
}
