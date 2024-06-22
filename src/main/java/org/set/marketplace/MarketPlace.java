package org.set.marketplace;

import java.util.HashMap;

import org.set.boardPieces.Util;
import org.set.cards.*;
import org.set.cards.action.ActionCard;
import org.set.cards.action.ActionCardType;
import org.set.cards.expedition.ExpeditionCard;
import org.set.cards.expedition.ExpeditionCardType;
import org.json.JSONObject;

public class MarketPlace {
    private JSONObject cardData;

    private HashMap<Card, Integer> marketBoardOptions = new HashMap<Card, Integer>();
    private HashMap<Card, Integer> currentMarketBoard = new HashMap<Card, Integer>();

    private HashMap<String, String> cardType = new HashMap<String, String>();

    public MarketPlace() {
        try {
            cardData = Util.readJsonData("src/main/java/org/set/marketplace", "marketcardData.json", "MarketCards");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        LoadDataIntoVariables();
    }

    public HashMap<Card, Integer> getCurrentMarketBoard() {
        return currentMarketBoard;
    }

    public HashMap<Card, Integer> getMarketBoardOptions() {
        return marketBoardOptions;
    }

    public Card BuyCard(String cardName, Integer goldAmount) {
        Card card = this.CreateCard(cardName);
        if (this.currentMarketBoard.containsKey(card) && this.TakeCard(card, goldAmount)) {
            return card;
        } else if (this.currentMarketBoard.size() < 6 && this.AddCardToMarketBoard(card, goldAmount)) {
            return card;
        } else {
            return null;
        }
    }

    public void moveOptionToCurrentMarket(Card card) {
        if (marketBoardOptions.containsKey(card)) {
            // Retrieve the quantity from marketBoardOptions
            Integer quantity = marketBoardOptions.remove(card);
            // Add the card and quantity to currentMarketBoard
            currentMarketBoard.put(card, quantity);
            System.out.println("Moved card '" + card + "' to current market.");
        } else {
            System.out.println("Card '" + card + "' not found in market options.");
        }
    }

    private Card CreateCard(String cardName) {
        Card boughtCard = null;

        if (this.cardType.get(cardName).equals("PURPLE")) {
            boughtCard = new ActionCard(ActionCardType.valueOf(cardName));
        } else {
            boughtCard = new ExpeditionCard(ExpeditionCardType.valueOf(cardName));
        }

        return boughtCard;
    }

    private boolean AddCardToMarketBoard(Card card, Integer goldAmount) {
        boolean succes = false;

        if (this.marketBoardOptions.containsKey(card)) {
            this.marketBoardOptions.remove(card);
            this.currentMarketBoard.put(card, 3);
            succes = this.TakeCard(card, goldAmount);
        }

        return succes;
    }

    private boolean TakeCard(Card card, Integer goldAmount) {
        boolean succes = false;

        if (this.currentMarketBoard.containsKey(card)) {
            if (CheckSufficientGold(card, goldAmount)) {
                Integer numberOfCards = this.currentMarketBoard.get(card);
                if ((numberOfCards - 1) > 0) {
                    this.currentMarketBoard.put(card, (numberOfCards - 1));
                } else {
                    this.currentMarketBoard.remove(card);
                }
                succes = true;

                return succes;
            } else {
                throw new IllegalArgumentException("Not enough gold to buy this card");
            }
        }

        return succes;
    }

    private boolean CheckSufficientGold(Card card, Integer goldAmount) {
        if (goldAmount >= card.cost) {
            return true;
        } else {
            return false;
        }
    }

    private void LoadDataIntoVariables() {
        for (String currentKey : this.cardData.keySet()) {
            JSONObject currentCardInfo = this.cardData.getJSONObject(currentKey);

            String cardInfo = currentCardInfo.getString("cardType");
            this.cardType.put(currentKey, cardInfo);
            Card card = CreateCard(currentKey);
            Integer marketStart = currentCardInfo.getInt("marketStart");

            if (marketStart == 0) {
                this.marketBoardOptions.put(card, 1);
            } else if (marketStart == 1) {
                this.currentMarketBoard.put(card, 3);
            }

        }
    }

}
