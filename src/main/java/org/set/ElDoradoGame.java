package org.set;

import java.util.*;

//Enumeration for different types of terrain
enum Terrain {
    LANDSCAPE,
    RUBBLE,
    BASE_CAMP,
    MOUNTAIN
}

//Enumeration for different types of cards
enum CardType {
    GREEN,
    YELLOW,
    BLUE,
    RAINBOW
}

//Class to represent a card
abstract class Card {
    private CardType type;

    private String name;

    private boolean getsRemoved;

    private int cost;

    public Card(CardType type, String name, boolean getsRemoved, int cost) {
        this.type = type;
        this.name = name;
        this.getsRemoved = getsRemoved;
        this.cost = cost;
    }

    public CardType getType() {
        return type;
    }
}

class ExpeditionCard extends Card{
    private int power;
    public ExpeditionCard(CardType type, String name, boolean getsRemoved, int cost, int power) {
        super(type, name, getsRemoved, cost);
        this.power = power;
    }
}

class ActionCard extends Card{

    public ActionCard(CardType type, String name, boolean getsRemoved, int cost) {
        super(type, name, getsRemoved, cost);
    }
}

//Class to represent the game board
class GameBoard {
    private int numRows;
    private int numCols;
    private Terrain[][] terrain;

    public GameBoard(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        terrain = new Terrain[numRows][numCols];
        // Initialize terrain (you can customize this based on your game)
    }

    public Terrain getTerrain(int row, int col) {
        return terrain[row][col];
    }
}

//Class to represent a player
class Player {
    private List<Card> deck;
    private List<Card> hand;
    private int row;
    private int col;

    public Player() {
        deck = new ArrayList<>();
        hand = new ArrayList<>();
        // Initialize player's deck (you can customize this based on your game)
    }

    public void drawCard() {
        // Draw a card from the deck
        if (!deck.isEmpty()) {
            Card card = deck.remove(0);
            hand.add(card);
        }
    }

    public void discardPlayedCards() {
        // TODO Auto-generated method stub

    }

    // Other methods to play cards, move player's expedition, etc.
}

//Main class representing the game
public class ElDoradoGame {
    private GameBoard board;
    private List<Player> players;
    private int currentPlayerIndex;

    public ElDoradoGame(int numRows, int numCols, int numPlayers) {
        board = new GameBoard(numRows, numCols);
        players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player());
        }
        currentPlayerIndex = 0;
    }

    // Method to perform a player's turn
    public void performTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);
        // Phase 1: Play Cards
        // Implement logic to play cards, move expedition, and buy new cards

        // Phase 2: Discard Played Cards
        currentPlayer.discardPlayedCards();

        // Phase 3: Draw Cards
        currentPlayer.drawCard();

        // Next player's turn
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    // Other methods to handle game flow, winning condition, etc.
    private void startGame() {
        // TODO Auto-generated method stub

    }
    public static void main(String[] args) {
        // Example usage
        ElDoradoGame game = new ElDoradoGame(5, 5, 2); // Example: 5x5 game board, 2 players
        game.startGame();
        // Perform game turns, handle player actions, etc.
    }


}