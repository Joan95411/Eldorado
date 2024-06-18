package org.set.cards;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import org.junit.jupiter.api.*;
import org.set.player.Player;
import org.set.cards.action.ActionCard;
import org.set.cards.action.ActionCardType;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the {@link ActionCard} class.
 */
public class ActionCardTest {
    private static int lastAssignedColorId = 0;
    private static ArrayList<ActionCard> cards = new ArrayList<>();
    private static Player player = new Player(Color.BLACK);

    /**
     * Test for creating all the action cards.
     */
    @BeforeAll
    public static void createActionCards() {
        for (ActionCardType actionCardType : ActionCardType.values()) {
            cards.add(new ActionCard(actionCardType));
        }

        assertEquals(cards.size(), ActionCardType.values().length);
    }

    /**
     * TODO: WRITE TEST TEXT
     */
    @Test
    public void tryMakingAnActionCardUnplayable() {
        for (ActionCard card : cards) {
            if (card.name.equals("Compass")) {
                assertEquals(true, card.isPlayable());
                card.doAction(player);
                assertEquals(false, card.isPlayable());

                try {
                    card.doAction(player);
                } catch (IllegalStateException e) {
                    assertEquals("This card is not playable", e.getMessage());
                }
            }
        }
    }

    /**
     * Test for playing the transmitter action card.
     */
//    @Test
//    public void testTransmitterActionCard() {
//        Player player = new Player(new Color(123, 123, ++lastAssignedColorId));
//        ActionCard transmitter = new ActionCard(ActionCardType.Transmitter);
//
//        assertEquals(0, player.myDeck.getCardsInHand().size());
//        assertEquals(0, player.myDeck.getDiscardPile().size());
//
//        String input = "Explorer\n"; // Prepare the input data
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); // Create a ByteArrayInputStream
//                                                                                       // with the input data
//        System.setIn(inputStream); // Set System.in to use the ByteArrayInputStream
//
//        if (transmitter.isPlayable()) {
//            transmitter.doAction(player);
//        }
//
//        assertEquals(1, player.myDeck.getCardsInHand().size());
//        assertEquals(0, player.myDeck.getDiscardPile().size());
//    }

    /**
     * Test for playing the transmitter action card.
     * An error was included for trying an invalid card type
     */
//    @Test
//    public void testTransmitterActionCardWithInvalidCardType() {
//        Player player = new Player(new Color(123, 123, ++lastAssignedColorId));
//        ActionCard transmitter = new ActionCard(ActionCardType.Transmitter);
//
//        assertEquals(0, player.myDeck.getCardsInHand().size());
//        assertEquals(0, player.myDeck.getDiscardPile().size());
//
//        if (transmitter.isPlayable()) {
//            String input = "Jack of all trades\nJack_of_all_trades\n"; // Prepare the input data
//            ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); // Create a
//                                                                                           // ByteArrayInputStream with
//                                                                                           // the input data
//            System.setIn(inputStream); // Set System.in to use the ByteArrayInputStream
//
//            transmitter.doAction(player);
//        }
//
//        assertEquals(1, player.myDeck.getCardsInHand().size());
//        assertEquals(0, player.myDeck.getDiscardPile().size());
//    }

    /**
     * Test for playing the cartographer action card.
     */
    @Test
    public void testCartographerActionCard() {
        Player player = new Player(new Color(123, 123, ++lastAssignedColorId));
        ActionCard cartographer = new ActionCard(ActionCardType.Cartographer);

        assertEquals(0, player.myDeck.getCardsInHand().size());
        assertEquals(0, player.myDeck.getMustBePlayedCardsInHand().size());
        assertEquals(0, player.myDeck.getDiscardPile().size());

        if (cartographer.isPlayable()) {
            cartographer.doAction(player);
        }

        assertEquals(0, player.myDeck.getCardsInHand().size());
        assertEquals(2, player.myDeck.getMustBePlayedCardsInHand().size());
        assertEquals(1, player.myDeck.getDiscardPile().size());
    }

    /**
     * Test for playing the scientist action card.
     */
//    @Test
//    public void testScientistActionCard() {
//        Player player = new Player(new Color(123, 123, ++lastAssignedColorId));
//        ActionCard scientist = new ActionCard(ActionCardType.Scientist);
//
//        assertEquals(0, player.myDeck.getCardsInHand().size());
//        assertEquals(0, player.myDeck.getDiscardPile().size());
//
//        String input = "y\n0\n";
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
//        System.setIn(inputStream);
//
//        if (scientist.isPlayable()) {
//            scientist.doAction(player);
//        }
//
//        assertEquals(0, player.myDeck.getCardsInHand().size());
//        assertEquals(1, player.myDeck.getDiscardPile().size());
//    }

    /**
     * Test for playing the compass action card.
     */
    @Test
    public void testCompassActionCard() {
        Player player = new Player(new Color(123, 123, ++lastAssignedColorId));
        ActionCard compass = new ActionCard(ActionCardType.Compass);

        assertEquals(0, player.myDeck.getCardsInHand().size());
        assertEquals(0, player.myDeck.getDiscardPile().size());

        if (compass.isPlayable()) {
            compass.doAction(player);
        }

        assertEquals(3, player.myDeck.getCardsInHand().size());
        assertEquals(0, player.myDeck.getDiscardPile().size());
    }

    /**
     * Test for playing the travel log action card.
     * TODO: Does the travel log card needs to be placed on the discard pile?
     */
//    @Test
//    public void testTravelLogActionCard() {
//        Player player = new Player(new Color(123, 123, ++lastAssignedColorId));
//        ActionCard travelLog = new ActionCard(ActionCardType.Travel_Log);
//
//        System.out.println("TODO: create travel log action card test");
//
//        String input = "y\n0\nn\n"; // Prepare the input data
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); // Create a ByteArrayInputStream
//                                                                                       // with the input data
//        System.setIn(inputStream); // Set System.in to use the ByteArrayInputStream
//
//        assertEquals(0, player.myDeck.getCardsInHand().size());
//        assertEquals(0, player.myDeck.getDiscardPile().size());
//
//        if (travelLog.isPlayable()) {
//            travelLog.doAction(player);
//        }
//
//        assertEquals(1, player.myDeck.getCardsInHand().size());
//        assertEquals(0, player.myDeck.getDiscardPile().size());
//    }

    /**
     * Test for playing the native action card.
     * TODO: THIS TEST STILL NEEDS TO BE CREATED
     */
    @Test
    public void testNativeActionCard() {
        Player player = new Player(new Color(123, 123, ++lastAssignedColorId));
        ActionCard nativeCard = new ActionCard(ActionCardType.Native);
        nativeCard.doAction(player);
    }
}
