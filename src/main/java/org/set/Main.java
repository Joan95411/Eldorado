package org.set;

import org.set.cards.ActionCard;
import org.set.cards.Card;
import org.set.cards.CardType;
import org.set.cards.ExpeditionCard;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Card[] cards = {
            new ExpeditionCard("Explorer", CardType.GREEN, 0, false, 1),
            new ExpeditionCard("Scout", CardType.GREEN,1, false, 2),
            new ExpeditionCard("Trail????", CardType.GREEN,3, false, 3),
            new ExpeditionCard("Pioneer", CardType.GREEN,5, false, 5),
            new ExpeditionCard("Giant Machete", CardType.GREEN,3, true, 6),
            new ExpeditionCard("Sailor", CardType.BLUE,0, false, 1),
            new ExpeditionCard("Captain", CardType.BLUE,2, false, 3),
            new ExpeditionCard("Traveller", CardType.YELLOW,0, false, 1),
            new ExpeditionCard("Photographer", CardType.YELLOW,2, false, 2),
            new ExpeditionCard("Journalist", CardType.YELLOW,3, false, 3),
            new ExpeditionCard("Treasure chest", CardType.YELLOW,3, true, 4),
            new ExpeditionCard("Millionaire", CardType.YELLOW,4, false, 4),
            new ExpeditionCard("Jack????", CardType.JOKER,2, false, 1),
            new ExpeditionCard("Adventure????", CardType.JOKER,4, false, 2),
            new ExpeditionCard("Prop Plane", CardType.JOKER,4, true, 4),
            new ActionCard("Transmitter", 4, true),
            new ActionCard("Cartographer", 4, false),
            new ActionCard("Scientist", 4, false),
            new ActionCard("Compass", 2, true),
            new ActionCard("Travel Log", 3, true),
            new ActionCard("Native", 5, false),
        };

        Player player = new Player(Color.BLACK);

        ActionCard actionCard = (ActionCard) cards[18];
        System.out.println(actionCard.isPlayable());
        actionCard.doAction(player);
        System.out.println(actionCard.isPlayable());
    }
}