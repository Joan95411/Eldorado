package org.set.game;

import java.util.*;

import org.set.game.InputHelper;
import org.set.player.Player;
import org.set.tokens.Cave;
import org.set.tokens.CaveTokenType;
import org.set.tokens.Token;
import org.set.boardPieces.HexagonGameBoard;
import org.set.boardPieces.Tile;
import org.set.boardPieces.Util;

public class Before_game {

    
	public static ArrayList<Token> createTokens() {
		ArrayList<Token> tokens = new ArrayList<>();
		for(int i = 0; i < 3; i++) {
        tokens.add(new Token(CaveTokenType.CoinOne));
        tokens.add(new Token(CaveTokenType.CoinTwo));
        tokens.add(new Token(CaveTokenType.PaddleOne));
        tokens.add(new Token(CaveTokenType.PaddleTwo));
        tokens.add(new Token(CaveTokenType.MacheteOne));
        tokens.add(new Token(CaveTokenType.MacheteTwo));
        tokens.add(new Token(CaveTokenType.MacheteThree));

        tokens.add(new Token(CaveTokenType.Draw));
        tokens.add(new Token(CaveTokenType.Remove));
        tokens.add(new Token(CaveTokenType.Replace));
        tokens.add(new Token(CaveTokenType.ImmediatePlay));
        tokens.add(new Token(CaveTokenType.PassThrough));
        tokens.add(new Token(CaveTokenType.Adjacent));
        tokens.add(new Token(CaveTokenType.Symbol));}
		return tokens;
        }
	
	public static Map<String, Cave> allocateTokens(HexagonGameBoard board) {
	    Map<String, Cave> caves = new HashMap<>();
	    ArrayList<Token> tokenList = createTokens(); 
	    List<Tile> caveSet = board.findCaveTiles();
	    Random random = new Random();

	    for (Tile tile : caveSet) {
	        Cave cave = new Cave(tile);

	        // Randomly draw 4 unique tokens from the tokenList
	        List<Token> selectedTokens = new ArrayList<>();
	        for (int i = 0; i < 4; i++) {
	            int index = random.nextInt(tokenList.size());
	            selectedTokens.add(tokenList.remove(index));
	        }

	        for (Token token : selectedTokens) {
	            cave.addToken(token);
	        }

	        caves.put(tile.getRow()+","+tile.getCol(), cave); 
	    }

	    return caves;
	}

    public static List<Player> addPlayer(HexagonGameBoard board) {
        int numPlayers;
        do {
            numPlayers = InputHelper.getIntInput("How many players are playing?",4);
            if (numPlayers < 1 || numPlayers > 4) {
                System.out.println("Please enter a number between 1 and 4.");
            }
        } while (numPlayers < 1 || numPlayers > 4);

        // Create an array to store player instances
        List<Player> players = new ArrayList<>();

        // Loop through each player
        for (int i = 0; i < numPlayers; i++) {
            String color = InputHelper.getInput("Player " + (i + 1) + ", choose your color:", 1)[0];
            if(Util.getColorFromString(color)==null) {
            	System.out.println("Please choose a normal color, like red, blue or black");
            	i--;
            }
            else {
            Player player = new Player(Util.getColorFromString(color));
            player.setName(color);
            // Add the player to the players list
            players.add(player);}
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
