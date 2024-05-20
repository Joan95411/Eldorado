package org.set;

public class CardActionHandler {
    public void getAction(String name) {
        switch (name) {
            case "Transmitter":
                System.out.println("Transmitter action performed");

                // Logic to take any expedition card without paying for it
                // Add the selected card to the discard pile
                // Remove the Transmitter card from the game

                return;

            case "Cartographer":
                System.out.println("Cartographer action performed");

                // Logic to draw 2 cards from the draw pile and play them this turn
                // player.drawCards(2);

                return;

            case "Scientist":
                System.out.println("Scientist action performed");

                // Logic for scientist

                return;

            case "Compass":
                System.out.println("Compass action performed");

                // Logic to draw 3 cards
                // player.drawCards(3);

                return;

            case "Travel Log":
                System.out.println("Travel Log action performed");
                return;

            case "Native":
                System.out.println("Native action performed");
                return;

            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
    }
}
