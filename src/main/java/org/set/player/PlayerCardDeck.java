package org.set.player;

import org.set.cards.Card;
import org.set.cards.expedition.ExpeditionCard;
import org.set.cards.expedition.ExpeditionCardType;

import java.util.*;

public class PlayerCardDeck {
    private final List<Card> drawPile;
    private final List<Card> discardPile;
    private final List<Card> cardsInHand;
    private final List<Card> mustBePlayedCardsInHand;

    public PlayerCardDeck() {
        drawPile = new ArrayList<>();
        discardPile = new ArrayList<>();
        cardsInHand = new ArrayList<>();
        mustBePlayedCardsInHand = new ArrayList<>();
        int blueCount = 1;
        int greenCount = 3;
        int yellowCount = 4;

        Random random = new Random();

        // Add blue cards
        for (int i = 0; i < blueCount; i++) {
            drawPile.add(new ExpeditionCard(ExpeditionCardType.Sailor));
        }

        // Add green cards
        for (int i = 0; i < greenCount; i++) {
            drawPile.add(new ExpeditionCard(ExpeditionCardType.Explorer));
        }

        // Add yellow cards
        for (int i = 0; i < yellowCount; i++) {
            drawPile.add(new ExpeditionCard(ExpeditionCardType.Traveller));
        }

        Collections.shuffle(drawPile, random);
    }

    // shuffle the discard pile to create a new pile
    private void shuffle() {
        drawPile.addAll(discardPile);
        discardPile.clear();
        Collections.shuffle(drawPile);
    }

    // Add a card to the discard pile
    public void removeCard(Card card) {
        cardsInHand.remove(card);
    }

    // Add a card to the discard pile
    public void discard(Card card) {
        discardPile.add(card);
    }

    public void discard(List<Card> discardedCards) {
        discardPile.addAll(discardedCards);
    }

    // return the top card of the drawPile, if it is empty shuffle discardPile to create new drawPile
    public void drawCards(boolean cardsMustBePlayedThisTurn) {
        if (drawPile.isEmpty()) {
            shuffle();
        }

        if (!drawPile.isEmpty()) {
            int cardIndex = drawPile.size() - 1;

            if (cardsMustBePlayedThisTurn) {
                mustBePlayedCardsInHand.add(drawPile.get(cardIndex));
            } else {
                cardsInHand.add(drawPile.remove(cardIndex));
            }
        }
    }

    public void drawAndRemoveCards(Player player, Scanner scanner, int drawAmount, int minRemoveAmount , int maxRemoveAmount) {
        player.myDeck.draw(drawAmount);

        for (int i = 0; i < maxRemoveAmount; i++) {
            System.out.println("Do you want to remove a card from your hand? (y/n):");
            String userInput = scanner.nextLine();

            if (userInput.equals("y")) {
                System.out.println("You have the following cards in your hand:");

                for (int j = 0; j < player.myDeck.getCardsInHand().size(); j++) {
                    System.out.println(j + " - " + player.myDeck.getCardsInHand().get(j).name);
                }

                System.out.println("What card do you want to remove?");
                int cardIndex = scanner.nextInt();

                // TODO: Do we need to remove the card from the game, or do you need to place it on the discard pile?
                if (cardIndex >= 0 && cardIndex <= player.myDeck.getCardsInHand().size()) {
                    player.myDeck.removeCard(player.myDeck.getCardsInHand().get(cardIndex));
                }
            } else if (i < minRemoveAmount) {
                System.out.println("You still have to throw away some cards");
            }
        }
    }

    public void drawExpeditionCard(ExpeditionCardType expeditionCardType) {
        cardsInHand.add(new ExpeditionCard(expeditionCardType));
    }

    public void draw(int numberOfCards) {
        draw(numberOfCards, false);
    }

    public void discardFromHand(int index){
        discardPile.add(cardsInHand.remove(index));
    }

    public void draw(int numberOfCards, boolean cardsMustBePlayedThisTurn) {
        for (int i = 0; i < numberOfCards; i++) {
            drawCards(cardsMustBePlayedThisTurn);
        }
    }

    public List<Card> getDrawPile() {
        return drawPile;
    }

    public List<Card> getDiscardPile() {
        return discardPile;
    }

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

    public List<Card> getMustBePlayedCardsInHand() {
        return mustBePlayedCardsInHand;
    }
}
