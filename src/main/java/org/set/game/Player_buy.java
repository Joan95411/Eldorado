package org.set.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.set.cards.Card;
import org.set.marketplace.MarketPlace;
import org.set.player.Asset;
import org.set.player.Player;
import org.set.template.Template;

public class Player_buy {

	public static void fillEmptyMarketSpace(Template board, MarketPlace market) {
		HashMap<Card, Integer> marketoption = market.getMarketBoardOptions();
		HashMap<Card, Integer> currentMarket = market.getCurrentMarketBoard();
		List<Card> cardList = new ArrayList<>(marketoption.keySet());
		if (currentMarket.size() < 6) {
			boolean fillEmptyMarket = InputHelper
					.getYesNoInput("There's empty space on market, do you want to fill in with any pile?");
			if (fillEmptyMarket) {
				int marketOptionIndex = InputHelper.getIntInput(
						"Choose 1 card pile to fill, input index (e.g. 0), or '-1' to stop", cardList.size() - 1, -1);
				if (marketOptionIndex == -1) {
					return;
				}
				Card thePile = cardList.get(marketOptionIndex);
				market.moveOptionToCurrentMarket(thePile);
				board.repaint();
			}
		}
	}

	public static void PlayerBuy(Template board, Player player, MarketPlace market) {

		List<Asset> assets = player.myDeck.getMyasset();
		double totalGold = player.myDeck.getTotalValue();
		System.out.println("You have " + totalGold + " gold now.");
		while (totalGold > 0) {
			boolean toBuyCard = InputHelper.getYesNoInput("Do you want to buy any cards?");
			if (!toBuyCard) {
				break;
			}
			fillEmptyMarketSpace(board, market);
			Map<Card, Integer> currentMarketBoard = market.getCurrentMarketBoard();
			List<Card> cardList = new ArrayList<>(currentMarketBoard.keySet());
			int cardIndex = InputHelper.getIntInput(
					"Choose 1 card to buy; Input index (e.g. 0), or '-1' to stop buying", cardList.size() - 1, -1);
			if (cardIndex == -1) {
				break;
			}
			Card theCard = cardList.get(cardIndex);
			if (currentMarketBoard.get(theCard) == 0) {
				System.out.println("There's no card of this type in the market, choose another card.");
			} else if (totalGold < theCard.getCost()) {
				System.out.println("You do not have enough gold for this card, choose another card.");
				continue;
			}

			double buyingGold = 0;
			List<Integer> assetIndices = new ArrayList<>();

			while (buyingGold < theCard.getCost()) {
				assetIndices.clear();
				assetIndices = InputHelper.getIntListInput("Please enter indices for your buying assets;"
						+ " Separated by commas, (e.g. 0,1,2);"
						+ " Or 'stop' to stop this transaction", assets.size() - 1);
				if (assetIndices != null && assetIndices.size() == 1 && assetIndices.get(0) == -1) {
					assetIndices.clear();
					break;
				}
				buyingGold = player.myDeck.getSelectedValue(assetIndices);
				System.out.println("You gathered " + buyingGold + " gold so far for the buy.");

				if (buyingGold >= theCard.getCost()) {
					break;
				} else {
					System.out.println("You do not have enough assets to buy this card. Please choose more assets.");
				}
			}

			if (buyingGold >= theCard.getCost()) {
				player.myDeck.discard(theCard);
				System.out.println(
						"Deal! This card has been placed on your discard pile to be reshuffled into the draw pile.");
				currentMarketBoard.put(theCard, currentMarketBoard.get(theCard) - 1);
				if (currentMarketBoard.get(theCard) == 0) {
					currentMarketBoard.remove(theCard);
				}
				// Handle discarding assets
				Collections.sort(assetIndices, Collections.reverseOrder());
				for (int i : assetIndices) {
					player.myDeck.discardAsset(i);
				}
				break;
			}
		}

		board.currentPlayer = player;
		board.market = market;
		board.repaint();
	}
}
