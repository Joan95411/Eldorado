package org.set;

import java.awt.*;
import java.util.ArrayList;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionCardTest {
    private static ArrayList<ActionCard> cards = new ArrayList<>();
    private static Player player = new Player(Color.BLACK);
    @BeforeAll
    public static void createActionCards() {
        cards.add(new ActionCard("Transmitter", 4, true));
        cards.add(new ActionCard("Cartographer", 4, false));
        cards.add(new ActionCard("Scientist", 4, false));
        cards.add(new ActionCard("Compass", 2, true));
        cards.add(new ActionCard("Travel Log", 3, true));
        cards.add(new ActionCard("Native", 5, false));

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

    @Test
    public void doActionForNonExistingActionCard() {
        String cardName = "Non Existing";
        ActionCard card = new ActionCard(cardName, 5, false);

        try {
            card.doAction(player);
        } catch (Exception e) {
            assertEquals("Unexpected value: " + cardName, e.getMessage());
        }
    }
}
