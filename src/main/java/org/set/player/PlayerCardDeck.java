package org.set.player;

import org.set.boardPieces.Blockade;
import org.set.cards.Card;
import org.set.cards.CardType;
import org.set.cards.expedition.ExpeditionCard;
import org.set.cards.expedition.ExpeditionCardType;
import org.set.game.InputHelper;
import org.set.tokens.Token;

import java.util.*;

public class PlayerCardDeck {
    private final List<Card> drawPile;
    private final List<Card> discardPile;
    private final List<Card> cardsInHand;
    private final List<Card> mustBePlayedCardsInHand;
    private List<Token> myTokens;
    private List<Blockade> blocks;

    public PlayerCardDeck() {
        drawPile = new ArrayList<>();
        discardPile = new ArrayList<>();
        cardsInHand = new ArrayList<>();
        mustBePlayedCardsInHand = new ArrayList<>();
        myTokens = new ArrayList<>();
        blocks = new ArrayList<>();
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

    public double getCardsValue() {
        double totalGold = 0;
        for (int i = 0; i < cardsInHand.size(); i++) {
            totalGold += cardsInHand.get(i).getValue();
        }
        return totalGold;
    }

    // Add a card to the discard pile
    public void removeCard(Card card) {
        cardsInHand.remove(card);
    }

    public void removeCard(List<Card> discardedCards) {
        cardsInHand.removeAll(discardedCards);
    }

    // Add a card to the discard pile
    public void discard(Card card) {
        cardsInHand.remove(card);
        discardPile.add(card);
    }

    public void discard(List<Card> discardedCards) {
        cardsInHand.removeAll(discardedCards);
        discardPile.addAll(discardedCards);
    }

    // return the top card of the drawPile, if it is empty shuffle discardPile to
    // create new drawPile
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

    public void drawAndRemoveCards(Player player, int drawAmount, int minRemoveAmount, int maxRemoveAmount) {
        System.out.println("Action performed");

        player.myDeck.draw(drawAmount);

        for (int i = 0; i < maxRemoveAmount; i++) {
            boolean userInput = InputHelper.getYesNoInput("Do you want to remove a card from your hand? (y/n):");

            if (userInput) {
                System.out.println("You have the following cards in your hand:");

                for (int j = 0; j < player.myDeck.getCardsInHand().size(); j++) {
                    System.out.println(j + " - " + player.myDeck.getCardsInHand().get(j).name);
                }

                int cardIndex = InputHelper.getIntInput("What card do you want to remove?",
                        player.myDeck.getCardsInHand().size() - 1, 0);
                // TODO: Do we need to remove the card from the game, or do you need to place it
                // on the discard pile?
                if (cardIndex >= 0 && cardIndex <= player.myDeck.getCardsInHand().size()) {
                    player.myDeck.removeCard(player.myDeck.getCardsInHand().get(cardIndex));
                }
            } else if (i < minRemoveAmount) {
                System.out.println("You still have to throw away some cards");
            }
        }
    }

    public void addCard(Card card, boolean cardsMustBePlayedThisTurn) {
        cardsInHand.add(card);

        if (cardsMustBePlayedThisTurn) {
            mustBePlayedCardsInHand.add(card);
        }
    }

    public void drawExpeditionCard(ExpeditionCardType expeditionCardType) {
        ExpeditionCard expeditionCard = new ExpeditionCard(expeditionCardType);
        cardsInHand.add(expeditionCard);


    }

    public void draw(int numberOfCards) {
        draw(numberOfCards, false);
    }

    public void discardFromHand(int index) {
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

    public void addToken(Token token) {
        myTokens.add(token);
    }

    public List<Token> getTokens() {
        return myTokens;
    }

    public double getTokenValue() {
        int totalGold = 0;
        for (int i = 0; i < myTokens.size(); i++) {
            totalGold += myTokens.get(i).getValue();
        }
        return totalGold;
    }

    public void discardToken(int i) {
        myTokens.remove(i);
    }

    public void discardToken(Token token) {
        myTokens.remove(token);
    }

    public int getTokenPower() {
        int totalPower = 0;
        for (int i = 0; i < myTokens.size(); i++) {
            totalPower += myTokens.get(i).getPower();
        }
        return totalPower;
    }

    public List<Asset> getMyasset() {
        List<Asset> assets = new ArrayList<>();
        assets.addAll(cardsInHand);
        assets.addAll(myTokens);
        return assets;
    }

    public List<Integer> isThereActionAsset() {
        List<Integer> actionAsset = new ArrayList<>();
        for (Asset asset : getMyasset()) {
            if (asset.getCardType() == CardType.PURPLE) {
                actionAsset.add(getMyasset().indexOf(asset));
            }
        }
        return actionAsset;
    }

    public double getTotalValue() {

        return getCardsValue() + getTokenValue();
    }

    public double getSelectedValue(List<Integer> assetIndices) {
        double buyingGold = 0;
        for (int i : assetIndices) {
            Asset asset = getMyasset().get(i);
            buyingGold += asset.getValue();
        }
        return buyingGold;
    }

    public void discardAsset(int assetIndex) {
        List<Asset> assets = getMyasset();
        Asset selectedAsset = assets.get(assetIndex);

        if (selectedAsset instanceof Card) {
            // Find the corresponding index in cardsInHand and discard the card
            int cardIndex = cardsInHand.indexOf(selectedAsset);
            if (cardIndex != -1) {
                discardFromHand(cardIndex);
            }
        } else if (selectedAsset instanceof Token) {
            // Find the corresponding index in myTokens and discard the token
            int tokenIndex = myTokens.indexOf(selectedAsset);
            if (tokenIndex != -1) {
                discardToken(tokenIndex);
            }
        }
    }

    public void earnBlockade(Blockade block) {
        blocks.add(block);
    }

    public int calculateBlockPoint() {
        int blockPoints = 0;
        for (Blockade block : blocks) {
            blockPoints = block.getPoints();
        }
        return blockPoints;
    }

}
