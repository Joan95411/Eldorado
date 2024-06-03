package org.set.marketplace;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import io.github.cdimascio.dotenv.DotenvException;
import org.set.boardPieces.Util;
import org.set.cards.*;
import org.set.cards.action.ActionCard;
import org.set.cards.action.ActionCardType;
import org.set.cards.expedition.ExpeditionCard;
import org.set.cards.expedition.ExpeditionCardType;
import org.json.JSONException;
import org.json.JSONObject;

import io.github.cdimascio.dotenv.Dotenv;

public class MarketPlace {
    public static Dotenv dotenv;
    private JSONObject cardData;

    private HashMap<String, Integer> marketBoardOptions = new HashMap<String, Integer>();
    private HashMap<String, Integer> currentMarketBoard = new HashMap<String, Integer>();
    private HashMap<String, Integer> cardValues = new HashMap<String, Integer>();
    private HashMap<String, String>  cardType = new HashMap<String, String>();
    private HashMap<String, Boolean> singleUse = new HashMap<String, Boolean>();
    private HashMap<String, Integer> cardPower = new HashMap<String, Integer>();
    
    public MarketPlace() {
        try {
            cardData = Util.getFile("src/main/java/org/set/marketplace", "marketcardData.json", "MarketCards");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        LoadDataIntoVariables();
    }

    public Card BuyCard(String cardName, Integer goldAmount) {
        if (this.currentMarketBoard.containsKey(cardName) && this.TakeCard(cardName, goldAmount)) {
            return CreateCard(cardName);
        } else if(this.currentMarketBoard.size() < 6 && this.AddCardToMarketBoard(cardName, goldAmount)){
            return CreateCard(cardName);
        } else {
            return null;
        }
    }

    private Card CreateCard(String cardName){
        Card boughtCard = null;
        if (this.cardType.get(cardName).equals("PURPLE")) {
            boughtCard = new ActionCard(ActionCardType.valueOf(cardName));
        } else {
            boughtCard = new ExpeditionCard(ExpeditionCardType.valueOf(cardName));
        }

        return boughtCard;
    }

    private boolean AddCardToMarketBoard(String cardName, Integer goldAmount){
        boolean succes = false;
        if (this.marketBoardOptions.containsKey(cardName)) {
            System.err.println("added "+cardName);

            this.marketBoardOptions.remove(cardName);
            this.currentMarketBoard.put(cardName, 3);
            succes = this.TakeCard(cardName, goldAmount);

            return succes;
        } else {
            return succes;
        }
    }
    
    private boolean TakeCard(String cardName, Integer goldAmount) {
        boolean succes = false;
        
        if (this.currentMarketBoard.containsKey(cardName)) {
            if (CheckSufficientGold(cardName,goldAmount)) {
                Integer numberOfCards = this.currentMarketBoard.get(cardName);
                if ((numberOfCards-1) > 0) {
                    this.currentMarketBoard.put(cardName,(numberOfCards-1));
                } else {
                    this.currentMarketBoard.remove(cardName);
                }
                succes = true;

                return succes;
            }
        }

        return succes;
    }

    private boolean CheckSufficientGold(String cardName, Integer goldAmount) {
        if (this.cardValues.containsKey(cardName)) {
            Integer value = this.cardValues.get(cardName);
            if (goldAmount >= value) {
                return true;
            } else {
                return false;
            }
        }
        
        return false;
    }

    private void LoadDataIntoVariables(){
        for (String currentKey : this.cardData.keySet()) {
            JSONObject currentCardInfo = this.cardData.getJSONObject(currentKey);

            Integer marketStart = currentCardInfo.getInt("marketStart");
            if (marketStart == 0) {
                this.marketBoardOptions.put(currentKey, 1);
            } else if (marketStart == 1) {
                this.currentMarketBoard.put(currentKey, 3);
            }

            Integer cardValuesInfo = currentCardInfo.getInt("cost");
            this.cardValues.put(currentKey, cardValuesInfo);

            String cardInfo = currentCardInfo.getString("cardType");
            this.cardType.put(currentKey, cardInfo);

            Integer singleUseInfo = currentCardInfo.getInt("singleUse");
            
            if (singleUseInfo == 1) {
                this.singleUse.put(currentKey, true);
            } else {
                this.singleUse.put(currentKey, false);
            }
            
            Integer cardPowerInfo = currentCardInfo.getInt("cardPower");
            this.cardPower.put(currentKey, cardPowerInfo);
        }
    }
}
