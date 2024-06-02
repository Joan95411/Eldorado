package org.set.cards.action;

public enum ActionCardType {
    Transmitter(4, true),
    Cartographer(4, false),
    Scientist(4, false),
    Compass(2, true),
    Travel_Log(3, true),
    Native(5, false);

    private final int cost;
    private final boolean singleUse;

    ActionCardType(int cost, boolean singleUse) {
        this.cost = cost;
        this.singleUse = singleUse;
    }

    public int getCost() {
        return cost;
    }

    public boolean isSingleUse() {
        return singleUse;
    }
}
