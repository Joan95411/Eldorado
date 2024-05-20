package org.set;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public abstract class Card {
	public String name;
	public int cost;
	public boolean singleUse;
	public boolean removedCard = false;

	public Card(String name, int cost, boolean singleUse) {
		this.name = name;
		this.cost = cost;
		this.singleUse = singleUse;
	}

	public void removeCard() {
		if (!singleUse) {
			System.out.println("This is not a single use card, so this card cannot be removed.");
		} else if (removedCard) {
			System.out.println("This card is already removed and cannot be removed once again.");
		} else {
			removedCard = true;
			System.out.println("Card removed!");
		}
	}

}
