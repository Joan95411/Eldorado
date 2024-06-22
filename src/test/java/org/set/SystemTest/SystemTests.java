package org.set.SystemTest;

import java.io.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import org.set.game.EldoradoGame;
import org.set.game.InputHelper;

import static org.junit.jupiter.api.Assertions.*;

public class SystemTests {
    private EldoradoGame eldoradoGame;
    private Thread gameThread;

    private OutputStream outputStream;

    @BeforeEach
    public void startGame() throws InterruptedException {
        eldoradoGame = new EldoradoGame();
        gameThread = new Thread(eldoradoGame);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        gameThread.start();
    }

    @AfterEach
    public void stopGame() throws InterruptedException {
        gameThread.interrupt();
        InputHelper.setInputStream(System.in);
    }

    public static void doUserInput(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        InputHelper.setInputStream(inputStream);
    }

    /**
     * SystemTest1:
     * Functional requirement: A game can be created with 2 players.
     * Expected Behavior:
     * A game should be set up for 2 players, all players should be able to play a turn, and all players should be given a different piece on the board according to their selected color. After selecting 2 players and 2 colors for those players the game should start. And display who’s turn it is.
     * @throws InterruptedException
     */
    @Test
    public void SystemTest1() throws InterruptedException {
        //insert inputs
        doUserInput("n\n2\nblue\nred\n");

        //wait for game to play out
        Thread.sleep(1000);

        //assert output
        assertTrue(outputStream.toString().contains("Turn 0: Player blue's turn."));
    }

    /**
     * Functional requirement:
     * A player should receive 4 cards at the start of the game.
     * Expected Behavior:
     * At the start of the game each player gets to draw 4 cards from the starting deck. The game should notify you that you have 4 cards.
     * @throws InterruptedException
     */
    @Test
    public void SystemTest2() throws InterruptedException {
        //insert inputs
        doUserInput("n\n2\nblue\nred\nn\n");

        //wait for game to play out
        Thread.sleep(1000);

        //assert output
        assertTrue(outputStream.toString().contains("Player blue drew cards"));
        assertTrue(outputStream.toString().contains("You have still 4 cards for this turn."));
    }

    @Test
    public void SystemTest3() throws InterruptedException {
        //insert inputs
        doUserInput("n\n4\nred\nblue\nyellow\nwhite\nn\nn\nn\nn\nn\nn\nn\nn\nn\nn\nn\nn\nn\nn\nn\nn\n");

        //wait for game to play out
        Thread.sleep(1000);

        //assert output
        assertTrue(outputStream.toString().contains("Turn 0: Player red's turn."));
        assertTrue(outputStream.toString().contains("Turn 0: Player blue's turn."));
        assertTrue(outputStream.toString().contains("Turn 0: Player yellow's turn."));
        assertTrue(outputStream.toString().contains("Turn 0: Player white's turn."));
        assertTrue(outputStream.toString().contains("Turn 1: Player red's turn."));
    }

    @Test
    public void SystemTest4() throws InterruptedException {
        //insert inputs
        doUserInput("n\n2\nred\nblue\nn\nn\ny\n0\ny\n0\ny\n0\ny\n0\ny\nn\nn\nn\nn\n");

        //wait for game to play out
        Thread.sleep(1000);

        //assert output
        String[] lines = outputStream.toString().split("\n");
        String cardsInHandTurn0 = "";
        String cardsInHandTurn1 = "";
        boolean foundTurn0 = false;
        int count0 = 0;
        for (String line : lines) {
            if (foundTurn0 && count0 < 5) {
                if (count0 > 0) { // Skip the first line after "Turn 0: Player red's turn."
                    cardsInHandTurn0 += line + "\n";
                }
                count0++;
            }
            if (line.contains("Turn 0: Player red's turn.")) {
                foundTurn0 = true;
            }
        }

        boolean foundTurn1 = false;
        int count1 = 0;
        for (String line : lines) {
            if (foundTurn1 && count1 < 5) {
                if (count1 > 0) { // Skip the first line after "Turn 0: Player red's turn."
                    cardsInHandTurn1 += line + "\n";
                }
                count1++;
            }
            if (line.contains("Turn 1: Player red's turn.")) {
                foundTurn1 = true;
            }
        }

        assertNotEquals(cardsInHandTurn1, cardsInHandTurn0);
        assertTrue(outputStream.toString().contains("Player red drew cards"));
        assertTrue(outputStream.toString().contains("You have still 4 cards for this turn."));
        assertFalse(outputStream.toString().contains("You have still 0 cards for this turn."));
    }

    @Test
    public void SystemTest5() throws InterruptedException {
        //insert inputs
        doUserInput("n\n2\nblue\nred\nn\n");

        //wait for game to play out
        Thread.sleep(1000);

        //assert output
        assertTrue(outputStream.toString().contains("Player blue drew cards"));
        assertTrue(outputStream.toString().contains("You have still 4 cards for this turn."));
    }
}