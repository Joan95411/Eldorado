package org.set.cards;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import org.junit.jupiter.api.*;
import org.set.player.Player;
import org.set.cards.action.ActionCard;
import org.set.cards.action.ActionCardType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionCardTest {
    private static int lastAssignedColorId = 0;
    private static ArrayList<ActionCard> cards = new ArrayList<>();
    private static Player player = new Player(Color.BLACK);

    @BeforeAll
    public static void createActionCards() {
        for (ActionCardType actionCardType: ActionCardType.values()) {
            cards.add(new ActionCard(actionCardType));
        }

        assertEquals(cards.size(), ActionCardType.values().length);
    }

    @Test
    public void testTransmitterActionCard() {
        Player player = new Player(new Color(123,123, ++lastAssignedColorId));
        ActionCard transmitter = new ActionCard(ActionCardType.Transmitter);

        assertEquals(player.myDeck.getCardsInHand().size(), 0);
        assertEquals(player.myDeck.getDiscardPile().size(), 0);

        String input = "Explorer\n"; // Prepare the input data
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); // Create a ByteArrayInputStream with the input data
        System.setIn(inputStream); // Set System.in to use the ByteArrayInputStream

        transmitter.doAction(player);

        assertEquals(player.myDeck.getCardsInHand().size(), 1);
        assertEquals(player.myDeck.getDiscardPile().size(), 0);
    }

    @Test
    public void testCartographerActionCard() {
        Player player = new Player(new Color(123,123,++lastAssignedColorId));
        ActionCard cartographer = new ActionCard(ActionCardType.Cartographer);

        assertEquals(player.myDeck.getCardsInHand().size(), 0);
        assertEquals(player.myDeck.getMustBePlayedCardsInHand().size(), 0);
        assertEquals(player.myDeck.getDiscardPile().size(), 0);

        cartographer.doAction(player);

        assertEquals(player.myDeck.getCardsInHand().size(), 0);
        assertEquals(player.myDeck.getMustBePlayedCardsInHand().size(), 2);
        assertEquals(player.myDeck.getDiscardPile().size(), 1);
    }

    @Test
    public void testScientistActionCard() {
        Player player = new Player(new Color(123,123,++lastAssignedColorId));
        ActionCard scientist = new ActionCard(ActionCardType.Scientist);

        String input = "y\n0\n"; // Prepare the input data
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); // Create a ByteArrayInputStream with the input data
        System.setIn(inputStream); // Set System.in to use the ByteArrayInputStream

        assertEquals(player.myDeck.getCardsInHand().size(), 0);
        assertEquals(player.myDeck.getDiscardPile().size(), 0);

        scientist.doAction(player);

        assertEquals(player.myDeck.getCardsInHand().size(), 0);
        assertEquals(player.myDeck.getDiscardPile().size(), 1);
    }

    @Test
    public void testCompassActionCard() {
        Player player = new Player(new Color(123,123,++lastAssignedColorId));
        ActionCard compass = new ActionCard(ActionCardType.Compass);

        assertEquals(player.myDeck.getCardsInHand().size(), 0);
        assertEquals(player.myDeck.getDiscardPile().size(), 0);

        compass.doAction(player);

        assertEquals(player.myDeck.getCardsInHand().size(), 3);
        assertEquals(player.myDeck.getDiscardPile().size(), 0);
    }

    @Test
    public void testTravelLogActionCard() {
        Player player = new Player(new Color(123,123,++lastAssignedColorId));
        ActionCard travelLog = new ActionCard(ActionCardType.Travel_Log);

        System.out.println("TODO: create travel log action card test");

        String input = "y\n0\nn\n"; // Prepare the input data
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); // Create a ByteArrayInputStream with the input data
        System.setIn(inputStream); // Set System.in to use the ByteArrayInputStream

        assertEquals(player.myDeck.getCardsInHand().size(), 0);
        assertEquals(player.myDeck.getDiscardPile().size(), 0);

        travelLog.doAction(player);

        // TODO: Does the travel log card needs to be placed on the discard pile?
        assertEquals(player.myDeck.getCardsInHand().size(), 1);
        assertEquals(player.myDeck.getDiscardPile().size(), 0);
    }

    @Test
    public void testNativeActionCard() {
        Player player = new Player(new Color(123,123,++lastAssignedColorId));
        ActionCard nativeCard = new ActionCard(ActionCardType.Native);

        System.out.println("TODO: create native action card test");
    }
}
