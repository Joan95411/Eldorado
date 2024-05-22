package org.set;

import org.set.cards.*;
import org.set.cards.action.ActionCard;
import org.set.cards.action.ActionCardType;
import org.set.cards.expedition.ExpeditionCard;
import org.set.cards.expedition.ExpeditionCardType;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Card[] cards = {
            new ExpeditionCard(ExpeditionCardType.Explorer, 0, false, 1),
            new ExpeditionCard(ExpeditionCardType.Scout, 1, false, 2),
            new ExpeditionCard(ExpeditionCardType.Trail,3, false, 3),
            new ExpeditionCard(ExpeditionCardType.Pioneer,5, false, 5),
            new ExpeditionCard(ExpeditionCardType.Giant_Machete,3, true, 6),
            new ExpeditionCard(ExpeditionCardType.Sailor,0, false, 1),
            new ExpeditionCard(ExpeditionCardType.Captain,2, false, 3),
            new ExpeditionCard(ExpeditionCardType.Traveller,0, false, 1),
            new ExpeditionCard(ExpeditionCardType.Photographer,2, false, 2),
            new ExpeditionCard(ExpeditionCardType.Journalist,3, false, 3),
            new ExpeditionCard(ExpeditionCardType.Treasure_Chest,3, true, 4),
            new ExpeditionCard(ExpeditionCardType.Millionaire,4, false, 4),
            new ExpeditionCard(ExpeditionCardType.Jack,2, false, 1),
            new ExpeditionCard(ExpeditionCardType.Adventure,4, false, 2),
            new ExpeditionCard(ExpeditionCardType.Prop_Plane,4, true, 4),
            new ActionCard(ActionCardType.Transmitter, 4, true),
            new ActionCard(ActionCardType.Cartographer, 4, false),
            new ActionCard(ActionCardType.Scientist, 4, false),
            new ActionCard(ActionCardType.Compass, 2, true),
            new ActionCard(ActionCardType.Travel_Log, 3, true),
            new ActionCard(ActionCardType.Native, 5, false),
        };

        Player player = new Player(Color.BLACK);

        ActionCard actionCard = (ActionCard) cards[18];
        System.out.println(actionCard.isPlayable());
        actionCard.doAction(player);
        System.out.println(actionCard.isPlayable());
    }
}