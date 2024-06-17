package org.set.cards;

import org.set.player.Player;
import org.set.cards.action.ActionCardType;
import org.set.cards.expedition.ExpeditionCardType;
import org.set.game.InputHelper;

import java.util.Scanner;

public class CardActionHandler {
    

    public void doAction(Card card, Player player) {
        ActionCardType actionCardType = ActionCardType.valueOf(card.name);

        if (actionCardType == ActionCardType.Transmitter) {
            TransmitterAction(player);
        }

        if (actionCardType == ActionCardType.Cartographer) {
            CartographerAction(player);
        }

        if (actionCardType == ActionCardType.Scientist) {
            ScientistAction(player);
        }

        if (actionCardType == ActionCardType.Compass) {
            CompassAction(player);
        }

        if (actionCardType == ActionCardType.Travel_Log) {
            TravelLogAction(player);
        }

        if (actionCardType == ActionCardType.Native) {
            NativeAction(player);
        }
    }

    private void TransmitterAction(Player player) {
        // Take any expedition card without paying for it
        ExpeditionCardType selectedCardType = null;

        while (selectedCardType == null) {
            System.out.println("Please enter a card type from the following options:");
            for (ExpeditionCardType type : ExpeditionCardType.values()) {
                System.out.println("- " + type);
            }

            String userInput = InputHelper.getInput("", 1)[0];
            selectedCardType = getExpeditionCardType(userInput);

            if (selectedCardType == null) {
                System.out.println("Invalid input. Please try again.");
            } else {
                System.out.println("You have selected: " + selectedCardType);
            }
        }

        player.myDeck.drawExpeditionCard(selectedCardType);
       
    }

    private void CartographerAction(Player player) {
        // Draw 2 cards from the draw pile and play them this turn
        player.myDeck.draw(2, true);
    }

    private void ScientistAction(Player player) {
        // Logic for scientist
        player.myDeck.drawAndRemoveCards(player, 1, 0,1);
    }

    private void CompassAction(Player player) {
        // Draw 3 cards
        player.myDeck.draw(3);
    }

    private void TravelLogAction(Player player) {
        // Logic for travel log
        player.myDeck.drawAndRemoveCards(player,  2, 0,2);

        System.out.println("Travel Log action performed");
    }

    private void NativeAction(Player player) {
        System.out.println("Native action performed");
    }

    protected static ExpeditionCardType getExpeditionCardType(String userInput) {
        try {
            return ExpeditionCardType.valueOf(userInput);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}


