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
import org.set.marketplace.MarketPlace;
import org.set.player.Player;
import org.set.template.Team04Board;
import org.set.template.Template;
import org.set.tokens.CaveTokenType;
import org.set.tokens.Token;

/**
 * Test class for the {@link Player_buyTest} class.
 */
class Player_buyTest {
    private Template board;
    private List<Player> players;
    private static InputStream backupInputStream;
    private MarketPlace market;

    /**
     * Creating inputstream
     */
    @BeforeAll
    public static void start() {

        backupInputStream = System.in;
    }

    /**
     * Setting up the board and players
     */
    @BeforeEach
    public void setUp() {
        board = new Team04Board(25, 30, 25); // Example dimensions
        Random random = new Random();
        players = new ArrayList<>();
        players.add(new Player(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256))));
        market = new MarketPlace();
        board.players = players;

    }

    /**
     * Cleaning up the system
     */
    @AfterEach
    public void cleanup() {
        System.setIn(backupInputStream);
    }

    /**
     * Integrationtest
     * Classes used: ExpeditionCard, ExpeditionCardType, Template, Player, Asset,
     * Marketplace, Team04Board
     * Test for buying new cards with enough money
     */
    @Test
    public void testPlayerBuy() {
        Player player = players.get(0);
        player.myDeck.getDrawPile().clear();

        for (int i = 0; i < 4; i++) {
            player.myDeck.addCard(new ExpeditionCard(ExpeditionCardType.Traveller), false);
        }

        player.myDeck.draw(4);
        String input = "y\n0\n0,1,2,3\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        InputHelper.setInputStream(inputStream);

        Player_buy.PlayerBuy(board, player, market);
        assertEquals(5, player.myDeck.getDiscardPile().size());
    }

    /**
     * Integrationtest
     * Classes used: ExpeditionCard, ExpeditionCardType, Template, Player, Asset,
     * Marketplace, Team04Board
     * Test for buying new cards with not enough money
     */
    @Test
    public void testPlayerBuy2() {
        Player player = players.get(0);
        player.myDeck.getDrawPile().clear();

        for (int i = 0; i < 4; i++) {
            player.myDeck.addCard(new ExpeditionCard(ExpeditionCardType.Sailor), false);
        }
        player.myDeck.draw(1);
        String input = "y\n0\n0\nstop\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        InputHelper.setInputStream(inputStream);

        Player_buy.PlayerBuy(board, player, market);
        assertEquals(0, player.myDeck.getDiscardPile().size());
    }
	@Test
    public void testUseTokenToBuy() {
        Player player = players.get(0);
        player.myDeck.getDrawPile().clear();
        for(int i=0;i<4;i++) {
        Token token=new Token(CaveTokenType.CoinOne);
    	player.myDeck.addToken(token);}
    	String input = "y\n0\n0,1,2,3\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); 
        InputHelper.setInputStream(inputStream);
        Player_buy.PlayerBuy(board, player, market);
        assertEquals(1, player.myDeck.getDiscardPile().size());
    }
    /**
     * Integrationtest
     * Classes used: ExpeditionCard, ExpeditionCardType, Template.java, Player,
     * Asset, Marketplace, Team04Board
     * Testing if player can fill an empty marketslot
     */
    @Test
    public void testFillMarket() {
        market.getCurrentMarketBoard().clear();
        String input = "y\n0\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        InputHelper.setInputStream(inputStream);
        Player_buy.fillEmptyMarketSpace(board, market);
        assertEquals(1, market.getCurrentMarketBoard().size());
    }

}
