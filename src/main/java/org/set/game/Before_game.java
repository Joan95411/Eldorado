package org.set.game;

import java.awt.Color;
import java.util.*;

import org.set.game.InputHelper;
import org.set.player.Player;
import org.set.template.Template;
import org.set.tokens.Cave;
import org.set.tokens.CaveTokenType;
import org.set.tokens.Token;
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
	
	public static Map<String, Cave> allocateTokens(Template board2) {
		
	    Map<String, Cave> caves = new HashMap<>();
	    ArrayList<Token> tokenList = createTokens(); 
	    List<Tile> caveSet = board2.findCaveTiles();
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

    public static List<Player> addPlayer(Template board2) {
        int numPlayers;
        do {
            numPlayers = InputHelper.getIntInput("How many players are playing?",4,1);
            
        } while (numPlayers < 1 || numPlayers > 4);

        // Create an array to store player instances
        List<Player> players = new ArrayList<>();
        List <Color> colors=new ArrayList<>();
        // Loop through each player
        for (int i = 0; i < numPlayers; i++) {
            String color = InputHelper.getInput("Player " + (i + 1) + ", choose your color:", 1)[0];
            if(colors.contains(Util.getColorFromString(color))) {
            	System.out.println("This color is not available, choose another color.");
            	i--;
            }
            else {
            Player player = new Player(Util.getColorFromString(color));
            player.setName(color);
            colors.add(Util.getColorFromString(color));
            players.add(player);}
        }

        board2.players = players;
        return players;
    }

    public static void placePlayersOnBoard(Template board2) {
    	List<Tile> starterTiles=board2.findStarterTiles();
    	for (int i = 0; i < board2.players.size(); i++) {
    	    Player player = board2.players.get(i);
    	    Tile tile = starterTiles.get(i % starterTiles.size());
    	    player.setPlayerPosition(tile.getRow(), tile.getCol());
    	}

        board2.repaint();
    }
}
