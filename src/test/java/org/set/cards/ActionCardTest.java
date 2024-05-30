package org.set.cards;

import java.awt.*;
import java.util.ArrayList;
import org.junit.jupiter.api.*;
import org.set.Player;
import org.set.cards.action.ActionCard;
import org.set.cards.action.ActionCardType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionCardTest {
    private static ArrayList<ActionCard> cards = new ArrayList<>();
    private static Player player = new Player(Color.BLACK);

    @BeforeAll
    public static void createActionCards() {
        cards.add(new ActionCard(ActionCardType.Transmitter, 4, true));
        cards.add(new ActionCard(ActionCardType.Cartographer, 4, false));
        cards.add(new ActionCard(ActionCardType.Scientist, 4, false));
        cards.add(new ActionCard(ActionCardType.Compass, 2, true));
        cards.add(new ActionCard(ActionCardType.Travel_Log, 3, true));
        cards.add(new ActionCard(ActionCardType.Native, 5, false));

        assertEquals(cards.size(), 6);
    }

    @Test
    public void actionCardDoActions() {
        for (int i = 0; i < cards.size(); i++) {
            ActionCard card = cards.get(i);

            if(card.singleUse == false) {
                assertEquals(card.isPlayable(), true);

                card.doAction(player);
                assertEquals(card.isPlayable(), true);

                card.doAction(player);
                assertEquals(card.isPlayable(), true);
            }
        }
    }

    @Test
    public void actionCardSingleUseDoActions() {
        for (int i = 0; i < cards.size(); i++) {
            ActionCard card = cards.get(i);

            if(card.singleUse) {
                assertEquals(card.isPlayable(), true);

                card.doAction(player);
                assertEquals(card.isPlayable(), false);

                try {
                    card.doAction(player);
                } catch (Exception e) {
                    assertEquals("This card is not playable", e.getMessage());
                }
            }
        }
    }
}
