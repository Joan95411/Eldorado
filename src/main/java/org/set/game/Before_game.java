package org.set.game;

import java.util.ArrayList;
import java.util.List;

import org.set.game.InputHelper;
import org.set.player.Player;
import org.set.boardPieces.HexagonGameBoard;
import org.set.boardPieces.Tile;
import org.set.boardPieces.TileDataDic;
import org.set.boardPieces.Util;

public class Before_game {


    public static List<Player> addPlayer(HexagonGameBoard board) {
        int numPlayers;
        do {
            numPlayers = InputHelper.getIntInput("How many players are playing?");
            if (numPlayers < 1 || numPlayers > 4) {
                System.out.println("Please enter a number between 1 and 4.");
            }
        } while (numPlayers < 1 || numPlayers > 4);

        // Create an array to store player instances
        List<Player> players = new ArrayList<>();

        // Loop through each player
        for (int i = 0; i < numPlayers; i++) {
            String color = InputHelper.getInput("Player " + (i + 1) + ", choose your color:", 1)[0];

            // Create a new player instance with the chosen color
            Player player = new Player(Util.getColorFromString(color));

            // Add the player to the players list
            players.add(player);
        }

        board.players = players;
        return players;
    }

    public static void placePlayersOnBoard(HexagonGameBoard board) {
    	List<Tile> starterTiles=board.findStarterTiles();
    	for (int i = 0; i < board.players.size(); i++) {
    	    Player player = board.players.get(i);
    	    Tile tile = starterTiles.get(i % starterTiles.size());
    	    player.setPlayerPosition(tile.getRow(), tile.getCol());
    	}

        board.repaint();
    }
}
