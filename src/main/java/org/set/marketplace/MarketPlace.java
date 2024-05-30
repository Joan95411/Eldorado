package org.set.marketplace;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import io.github.cdimascio.dotenv.DotenvException;
import org.set.cards.*;

import org.json.JSONException;
import org.json.JSONObject;

import io.github.cdimascio.dotenv.Dotenv;

public class MarketPlace {
    public static Dotenv dotenv;
    private JSONObject cardData = GetJsonData();

    private HashMap<String, Integer> currentMarketBoard = this.LoadMarketBoard();
    private HashMap<String, Integer> cardValues = this.LoadCardValues();
    private HashMap<String, Integer> marketBoardOptions = this.LoadCardTypes();

    public Card BuyCard(String cardType, Integer goldAmount){
        if(this.currentMarketBoard.containsKey(cardType)){
            if(this.TakeCard(cardType, goldAmount)){
                return CreateCard(cardType);
            }
        }else if(this.currentMarketBoard.size()<6){
            if(this.AddCardToMarketBoard(cardType, goldAmount)){
                return CreateCard(cardType);
            }
        }else{
            return null;
        }
        return null;
    }

    private Card CreateCard(String cardType){

        return null;
    }

    private boolean AddCardToMarketBoard(String cardType, Integer goldAmount){
        boolean succes = false;
        if(this.marketBoardOptions.containsKey(cardType)){
            System.err.println("added "+cardType);
            this.marketBoardOptions.remove(cardType);
            this.currentMarketBoard.put(cardType, 3);
            succes = this.TakeCard(cardType, goldAmount);
            return succes;
        }else{
            //System.out.println("This card is not available anymore");
            return succes;
        }
    }
    
    private boolean TakeCard(String cardType, Integer goldAmount){
        boolean succes = false;
        if(this.currentMarketBoard.containsKey(cardType)){
            if(CheckSufficientGold(cardType,goldAmount)){
                Integer numberOfCards = this.currentMarketBoard.get(cardType);
                if((numberOfCards-1) > 0){
                    this.currentMarketBoard.put(cardType,(numberOfCards-1));
                }else{
                    this.currentMarketBoard.remove(cardType);
                }
                succes = true;
                return succes;
            }
        }
        return succes;
    }

    
    private boolean CheckSufficientGold(String cardType, Integer goldAmount){
        if(this.cardValues.containsKey(cardType)){
            Integer value = this.cardValues.get(cardType);
            if(goldAmount >= value){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    private HashMap<String, Integer> LoadCardValues(){
        cardValues = new HashMap<String, Integer>();
        for (String currentKey : this.cardData.keySet()) {
            JSONObject currentCardInfo = this.cardData.getJSONObject(currentKey);
            Integer cost = currentCardInfo.getInt("cost");
            cardValues.put(currentKey, cost);
        }     
        return cardValues;
    }

    private HashMap<String, Integer> LoadMarketBoard() {
        currentMarketBoard = new HashMap<String, Integer>();
        for (String currentKey : this.cardData.keySet()) {
            JSONObject currentCardInfo = this.cardData.getJSONObject(currentKey);
            Integer marketStart = currentCardInfo.getInt("marketStart");
            if(marketStart == 1){
                currentMarketBoard.put(currentKey, 3);
            }
        }
        return currentMarketBoard;
    }

    private HashMap<String, Integer> LoadCardTypes(){
        marketBoardOptions = new HashMap<String, Integer>();
        for (String currentKey : this.cardData.keySet()) {
            JSONObject currentCardInfo = this.cardData.getJSONObject(currentKey);
            Integer marketStart = currentCardInfo.getInt("marketStart");
            if(marketStart == 0){
                marketBoardOptions.put(currentKey, 1);
            }
        }
        return marketBoardOptions;
    }

    private JSONObject GetJsonData(){
        try {
            String cardDataPath;
            try {
                dotenv = Dotenv.configure().load();
                cardDataPath = dotenv.get("CARDDATA_PATH");
            } catch (DotenvException e) {
                cardDataPath = "src/main/java/org/set/marketplace/marketcardData.json";
            }
            System.out.println(cardDataPath);
            String cardDataJson = new String(Files.readAllBytes(new File(cardDataPath).toPath()));
            JSONObject cardData = new JSONObject(cardDataJson);
            cardData = cardData.getJSONObject("MarketCards");
            return cardData;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
