package org.set.cards;

import org.set.Player;
import org.set.cards.action.ActionCard;
import org.set.cards.action.ActionCardType;
import org.set.cards.expedition.ExpeditionCardType;

import java.util.Objects;
import java.util.Scanner;

public class CardActionHandler {
    private Scanner scanner = new Scanner(System.in);

    public void doAction(Card card, Player player) {
        ActionCardType actionCardType = ActionCardType.valueOf(card.name);

        switch (actionCardType) {
            case Transmitter:
                // Take any expedition card without paying for it
                ExpeditionCardType selectedCardType = null;

                while (selectedCardType == null) {
                    System.out.println("Please enter a card type from the following options:");
                    for (ExpeditionCardType type : ExpeditionCardType.values()) {
                        System.out.println("- " + type);
                    }

                    String userInput = scanner.nextLine();
                    selectedCardType = getExpeditionCardType(userInput);

                    if (selectedCardType == null) {
                        System.out.println("Invalid input. Please try again.");
                    } else {
                        System.out.println("You have selected: " + selectedCardType);
                    }
                }

                player.myDeck.drawExpeditionCard(selectedCardType);
                scanner.close();

                return;

            case Cartographer:
                // Draw 2 cards from the draw pile and play them this turn
                player.myDeck.draw(2, true);

                return;

            case Scientist:
                // Logic for scientist
                player.myDeck.drawAndRemoveCards(player, scanner, 1, 0,1);

                return;

            case Compass:
                // Draw 3 cards
                player.myDeck.draw(3);

                return;

            case Travel_Log:
                // Logic for travel log
                player.myDeck.drawAndRemoveCards(player, scanner, 2, 0,2);

                System.out.println("Travel Log action performed");
                return;

            case Native:
                System.out.println("Native action performed");
                return;

            default:
                throw new IllegalStateException("Unexpected value: " + actionCardType);
        }
    }

    protected static ExpeditionCardType getExpeditionCardType(String userInput) {
        try {
            return ExpeditionCardType.valueOf(userInput);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}


