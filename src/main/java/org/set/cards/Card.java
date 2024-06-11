package org.set.cards;

import org.set.cards.action.ActionCardType;
import org.set.cards.expedition.ExpeditionCardType;

import java.awt.*;
import java.util.Arrays;
import org.set.boardPieces.Util;

public abstract class Card {
	public String name;
	public CardType cardType;
	public double cost;
	public boolean singleUse;
	public boolean removedCard = false;

	public Card(String name, double cost, boolean singleUse) {
		this.name = name;
		this.cardType = getCardType();
		this.cost = cost;
		this.singleUse = singleUse;
	}

	private CardType getCardType() {
		String[] greenCardTypes = { ExpeditionCardType.Explorer.toString(), ExpeditionCardType.Scout.toString(), ExpeditionCardType.Trailblazer.toString(), ExpeditionCardType.Pioneer.toString(), ExpeditionCardType.Giant_Machete.toString() };
		String[] blueCardTypes = { ExpeditionCardType.Sailor.toString(), ExpeditionCardType.Captain.toString() };
		String[] yellowCardTypes = { ExpeditionCardType.Traveller.toString(), ExpeditionCardType.Photographer.toString(), ExpeditionCardType.Journalist.toString(), ExpeditionCardType.Treasure_Chest.toString(), ExpeditionCardType.Millionaire.toString() };
		String[] jokerCardTypes = { ExpeditionCardType.Jack.toString(), ExpeditionCardType.Adventurer.toString(), ExpeditionCardType.Prop_Plane.toString() };
		String[] purpleCardTypes = { ActionCardType.Transmitter.toString(), ActionCardType.Cartographer.toString(), ActionCardType.Scientist.toString(), ActionCardType.Compass.toString(), ActionCardType.Travel_Log.toString(), ActionCardType.Native.toString() };

		if (Arrays.asList(greenCardTypes).contains(this.name)) {
			return CardType.GREEN;
		} else if (Arrays.asList(blueCardTypes).contains(this.name)) {
			return CardType.BLUE;
		} else if (Arrays.asList(yellowCardTypes).contains(this.name)) {
			return CardType.YELLOW;
		} else if (Arrays.asList(jokerCardTypes).contains(this.name)) {
			return CardType.JOKER;
		} else if (Arrays.asList(purpleCardTypes).contains(this.name)) {
			return CardType.PURPLE;
		} else {
			throw new IllegalStateException("The explorer card type is illegal.");
		}
	}
	
	public double getCost() {
		if(cost>0) {
        return cost;}
		else {return 0.5;}
    }
	public void removeCard() {
		if (!singleUse) {
			throw new IllegalArgumentException("This is not a single use card, so this card cannot be removed.");
		} else if (removedCard) {
			throw new IllegalStateException("This card is already removed and cannot be removed once again.");
		} else {
			removedCard = true;
		}
	}
	@Override
    public String toString() {
        return "Card{" +
                "cardType=" + cardType +
                ", name=" + name +
                ", cost=" + cost +
                '}';
    }

	public void draw(Graphics2D g2d, int x, int y, int width, int height) {
		Color color = Util.getColorFromString(cardType.toString());
		Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
		g2d.setColor(transparentColor);
		g2d.fillRect(x, y, width, height);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(x, y, width, height);
		g2d.drawString("Value: " + cost, x + 5, y + height - 5);
	}

}
