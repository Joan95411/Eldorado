package org.set;

import java.util.ArrayList;
import java.util.List;

import org.set.boardPieces.HexagonGameBoard;
import org.set.boardPieces.Tile;
import org.set.boardPieces.TileDataDic;
import org.set.boardPieces.Util;

public class Before_game {

    public static void setSpecialColor(HexagonGameBoard board) {
        while (true) {
            boolean wantsToContinue = InputHelper.getYesNoInput("Do you want to set any tile specially? (yes/no)");

            if (!wantsToContinue) {
                break; // Exit the loop if the user wants to stop
            }

            String[] response = InputHelper.getInput("Tell me the tile of row,column,color,points (e.g., '2,3,red,1') or type 'done' to finish:", 4);

            if (response == null) {
                break;
            }

            int row, col, points;
            String color;

            try {
                row = Integer.parseInt(response[0]);
                col = Integer.parseInt(response[1]);
                color = response[2];
                points = Integer.parseInt(response[3]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid integers for row and column, and a valid color.");
                continue; // Continue to the next iteration if the input is invalid
            }

            Tile special = TileDataDic.tilesMap.get(row + "," + col);

            if (!board.isValidPosition(row, col)) {
                System.out.println("Invalid position. Please enter valid coordinates.");
                continue; // Continue to the next iteration if the position is invalid
            }
            
            special.setColor(Util.getColorFromString(color));
            special.setPoints(points);
            board.repaint();

            System.out.println("Special color and points set for tile at position (" + row + "," + col + ").");
        }
    }

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
