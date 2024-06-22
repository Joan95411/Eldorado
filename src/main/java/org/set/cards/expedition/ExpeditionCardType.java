package org.set.cards.expedition;

public enum ExpeditionCardType {
    Explorer(0, false, 1),
    Scout(1, false, 2),
    Trailblazer(3, false, 3),
    Pioneer(5, false, 5),
    Giant_Machete(3, true, 6),
    Sailor(0, false, 1),
    Captain(2, false, 3),
    Traveller(1, false, 1),
    Photographer(2, false, 2),
    Journalist(3, false, 3),
    Millionaire(5, false, 4),
    Treasure_Chest(3, true, 4),
    Jack(2, false, 1),
    Adventurer(4, false, 2),
    Prop_Plane(4, true, 4);

    private final int cost;
    private final boolean singleUse;
    private final int power;

    ExpeditionCardType(int cost, boolean singleUse, int power) {
        this.cost = cost;
        this.singleUse = singleUse;
        this.power = power;
    }

    public int getCost() {
        return cost;
    }

    public boolean isSingleUse() {
        return singleUse;
    }

    public int getPower() {
        return power;
    }
}
