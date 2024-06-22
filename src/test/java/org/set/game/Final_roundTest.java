package org.set.game;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.cards.expedition.ExpeditionCard;
import org.set.cards.expedition.ExpeditionCardType;
import org.set.player.Player;
import org.set.template.Team04Board;
import org.set.template.Template;

class Final_roundTest {
    private Template board;
    private List<Player> players;
    private static InputStream backupInputStream;

    @BeforeAll
    public static void start() {

        backupInputStream = System.in;
    }

    @BeforeEach
    public void setUp() {
        board = new Team04Board(25, 30, 25);
        Random random = new Random();
        players = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            players.add(new Player(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256))));
        }
        board.players = players;
        Before_game.placePlayersOnBoard(board);
    }

    @AfterEach
    public void cleanup() {
        System.setIn(backupInputStream);
    }

    /**
     * Integrationtest
     * Classes used: ExpeditionCard, ExpeditionCardType, Template, Player,
     * Team04Board
     * Tesing whether there is just one winner
     */
    @Test
    public void testIsThereAWinnier_NoWinner() {
        Player player = players.get(0);
        assertFalse(Final_round.isThereAwinnier(board, player));
    }

    /**
     * Integrationtest
     * Classes used: ExpeditionCard, ExpeditionCardType, Template, Player,
     * Marketplace, Team04Board, Tile, TileType, Cave
     * Tesing whether there is a second winner
     */
    @Test
    public void testIsThereAWinnier_WithWinner() {
        Player player = players.get(1);
        player.setPlayerPosition(1, 23);
        assertTrue(Final_round.isThereAwinnier(board, player));
    }

    /**
     * Integrationtest
     * Classes used: ExpeditionCard, ExpeditionCardType, Template, Player,
     * Marketplace, Team04Board, Tile, TileType, Cave
     * Tesing the final round
     */
    @Test
    public void testFinal_round() {
        players.get(1).setPlayerPosition(1, 23);
        assertTrue(Final_round.isThereAwinnier(board, players.get(1)));
        players.get(0).setPlayerPosition(2, 23);
        players.get(0).myDeck.getDrawPile().clear();
        for (int i = 0; i < 4; i++) {
            players.get(0).myDeck.addCard(new ExpeditionCard(ExpeditionCardType.Explorer), false);
        }
        String input = "y\n0\n2,24\nn\n"; // Prepare the input data
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); // Create a ByteArrayInputStream
                                                                                       // with the input data
        InputHelper.setInputStream(inputStream);
        Final_round.FinalRound(board, 1, players);
        int i = 0;
        for (Player player : players) {
            if (player.getWinner()) {
                i++;
                System.out.println(players.indexOf(player));
            }
        }
        assertEquals(2, i);
    }
    
    @Test
    public void testEndGame() {//two player in final round in ending tiles, one with block
    	Player player1=players.get(1);
    	player1.setPlayerPosition(1, 23);
        assertTrue(Final_round.isThereAwinnier(board, player1));
        player1.myDeck.earnBlockade(board.getFirstBlockade());
        Player player2=players.get(2);
        player2.setPlayerPosition(1, 23);
        assertTrue(Final_round.isThereAwinnier(board, player2));
        List<Integer> maxIndexes =Final_round.EndGame(players);
        assertEquals(1,maxIndexes.size());
        assertEquals(1,maxIndexes.get(0));
    }
    
    @Test
    public void testEndGame2() {//two player in final round in ending tiles, both with 1 block
    	Player player1=players.get(1);
    	player1.setPlayerPosition(1, 23);
        assertTrue(Final_round.isThereAwinnier(board, player1));
        player1.myDeck.earnBlockade(board.getFirstBlockade());
        Player player2=players.get(2);
        player2.setPlayerPosition(1, 23);
        player2.myDeck.earnBlockade(board.getLastBlockade());
        assertTrue(Final_round.isThereAwinnier(board, player2));
        List<Integer> maxIndexes =Final_round.EndGame(players);
        assertEquals(2,maxIndexes.size());
    }
}
