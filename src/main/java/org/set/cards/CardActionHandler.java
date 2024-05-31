package org.set.cards;

import org.set.Player;
import org.set.cards.action.ActionCardType;
import org.set.cards.expedition.ExpeditionCardType;

import java.util.Scanner;

public class CardActionHandler {
    public void doAction(Card card, Player player) {
        ActionCardType actionCardType = ActionCardType.valueOf(card.name);

        switch (actionCardType) {
            case Transmitter:
                // Take any expedition card without paying for it
                Scanner scanner = new Scanner(System.in);
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

                return;

            case Cartographer:
                // Draw 2 cards from the draw pile and play them this turn
                player.myDeck.draw(2, true);

                return;

            case Scientist:
                System.out.println("Scientist action performed");

                // Logic for scientist

                return;

            case Compass:
                // Draw 3 cards
                player.myDeck.draw(3);

                return;

            case Travel_Log:
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


