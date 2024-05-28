package org.set.marketplace;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import io.github.cdimascio.dotenv.Dotenv;

public class MarketPlace {
    public static Dotenv dotenv = Dotenv.configure().load();
    private JSONObject cardData = GetJsonData();

    private HashMap<String, Integer> currentMarketBoard = this.LoadMarketBoard();
    private HashMap<String, Integer> cardValues = this.LoadCardValues();
    private String[] marketBoardOptions = LoadCardTypes();

    public void MarketPlaceTest(){
        // for (String i : cardValues.keySet()) {
        //     System.out.println(i);
        //     System.out.println(cardValues.get(i));
        // }
        //this.TakeCard("SCOUT", 0);
    }


    public void BuyCard(String cardType, Integer goldAmount){

    }
    
    private void TakeCard(String cardType, Integer goldAmount){
        if(this.currentMarketBoard.containsKey(cardType)){
            if(CheckSufficientGold(cardType,goldAmount)){
                Integer numberOfCards = this.currentMarketBoard.get(cardType);
                if((numberOfCards-1) < 0){
                    this.currentMarketBoard.put(cardType,numberOfCards-1);
                }else{
                    this.currentMarketBoard.remove(cardType);
                }
                System.out.println(this.currentMarketBoard.get(cardType));
            }
        }
    }

    private void RemoveCardFromMarket(){

    }

    
    private boolean CheckSufficientGold(String cardType, Integer goldAmount){
        if(this.cardValues.containsKey(cardType)){
            Integer value = this.cardValues.get(cardType);
            if(value >= goldAmount){
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

    // private void LoadMarketBoardValues() {
    //     for (String currentKey : this.cardData.keySet()) {
    //         JSONObject currentCardInfo = this.cardData.getJSONObject(currentKey);
    //         Integer cost = currentCardInfo.getInt("cost");
    //         this.cardValues.put(currentKey,cost);
    //     }
    //     System.out.println(this.cardValues);
    //     System.out.println(this.cardValues.get("Transmitter"));
    // }

    private String[] LoadCardTypes(){
        String[] cardTypes = new String[(this.cardData.keySet().size()-6)];
        Integer currentArraySpot = 0;
        for (String currentKey : this.cardData.keySet()) {
            JSONObject currentCardInfo = this.cardData.getJSONObject(currentKey);
            Integer marketStart = currentCardInfo.getInt("marketStart");
            if(marketStart == 0){
                cardTypes[currentArraySpot] = currentKey;
                currentArraySpot++;
            }
        }
        return cardTypes;
    }

    private JSONObject GetJsonData(){
        try {
        	String cardDataPath = dotenv.get("CARDDATA_PATH");
            if (cardDataPath == null) cardDataPath = "src/main/java/org/set/marketplace/marketcardData.json";
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
