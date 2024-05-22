package org.set.cards;

public abstract class Card {
	public String name;
	public CardType cardType;
	public int cost;
	public boolean singleUse;
	public boolean removedCard = false;

	public Card(String name, CardType cardType, int cost, boolean singleUse) {
		this.name = name;
		this.cardType = cardType;
		this.cost = cost;
		this.singleUse = singleUse;
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
}
