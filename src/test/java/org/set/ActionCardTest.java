package org.set;

import java.awt.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionCardTest {
    private ArrayList<ActionCard> cards = new ArrayList<>();

    @Test
    public void createActionCards() {
        cards.add(new ActionCard("Transmitter", 4, true));
        cards.add(new ActionCard("Cartographer", 4, false));
        cards.add(new ActionCard("Scientist", 4, false));
        cards.add(new ActionCard("Compass", 2, true));
        cards.add(new ActionCard("Travel Log", 3, true));
        cards.add(new ActionCard("Native", 5, false));

        assertEquals(cards.size(), 6);
    }

    @Test
    public void actionCardDoAction() {
        ActionCard card = new ActionCard("Transmitter", 4, false);
        assertEquals(card.isPlayable(), true);

        card.doAction(new Player(Color.BLACK));
        assertEquals(card.isPlayable(), true);

        card.doAction(new Player(Color.BLACK));
        assertEquals(card.isPlayable(), true);
    }

    @Test
    public void actionCardSingleUseDoAction() {
        ActionCard card = new ActionCard("Transmitter", 4, true);
        assertEquals(card.isPlayable(), true);

        card.doAction(new Player(Color.BLACK));
        assertEquals(card.isPlayable(), false);

        try {
            card.doAction(new Player(Color.BLACK));
        } catch (Exception e) {
            assertEquals("This card is not playable", e.getMessage());
        }
    }
}
