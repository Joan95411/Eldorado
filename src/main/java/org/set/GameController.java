package org.set;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.set.boardPieces.HexagonGameBoard;
import org.set.boardPieces.Tile;
import org.set.boardPieces.Util;
import org.set.cards.Card;


public class GameController {
    private HexagonGameBoard board;
    private List<Player> players;
    private String GameState;
    public int turnCounter;
    private InputHelper inputHelper;
    public GameController(HexagonGameBoard board) {
        inputHelper=new InputHelper();
        this.board=board;
        GameState="Game in process";
        //setSpecialColor();
        removeblock(0);
        removeblock(1);
        removeblock(2);
        addPlayer();
        placePlayersOnBoard();
        PlayerMoves();
        
    }
    
    
    
    public void setSpecialColor() {
        while (true) {
        	boolean wantsToContinue = inputHelper.getYesNoInput("Do you want to set any tile specially? (yes/no)");

            if (!wantsToContinue) {
                break; // Exit the loop if the user wants to stop
            }

            String[] response = inputHelper.getInput("Tell me the tile of row,column,color,points (e.g., '2,3,red,1') or type 'done' to finish:", 4);
            
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

            if (!board.isValidPosition(row, col)) {
                System.out.println("Invalid position. Please enter valid coordinates.");
                continue; // Continue to the next iteration if the position is invalid
            }

            Tile special = board.tilesMap.get(row + "," + col);
            if (special == null) {
                System.out.println("Tile not found at the specified position.");
                continue; // Continue to the next iteration if the tile is not found
            }

            special.setColor(Util.getColorFromString(color));
            special.setPoints(points);
            board.repaint();
            System.out.println("Special color and points set for tile at position (" + row + "," + col + ").");
        }

        }
        
    


    public void addPlayer() {
    	InputHelper inputHelper = new InputHelper();

    	int numPlayers;
    	do {
    	    numPlayers = inputHelper.getIntInput("How many players are playing?");
    	    if (numPlayers < 1 || numPlayers > 4) {
    	        System.out.println("Please enter a number between 1 and 4.");
    	    }
    	} while (numPlayers < 1 || numPlayers > 4);

        // Create an array to store player instances
        players = new ArrayList<>();

        // Loop through each player
        for (int i = 0; i < numPlayers; i++) {
        	String color = inputHelper.getInput("Player " + (i + 1) + ", choose your color:", 1)[0];
            

            // Create a new player instance with the chosen color
            Player player = new Player(Util.getColorFromString(color));

            // Add the player to the players list
            players.add(player);
        }board.players=players;

    }
    
    private void placePlayersOnBoard() {

		 for (Player player : players) {

       player.setPlayerPosition(1+player.id, 1);

		 }board.repaint();
    }
    
    private void PlayerDrawCards(int turn,int currentPlayerIndex) {

	    System.out.println("Player " + (currentPlayerIndex+1) + " drawing cards" );
    	Player currentPlayer = players.get(currentPlayerIndex);
    	List<Card> drawedCards=currentPlayer.mydeck.draw(5);
	    board.PlayerCards=drawedCards;
        board.repaint();
    }



    private void removeblock(int currentTerrainIndex){
    	boolean wantsToContinue = inputHelper.getYesNoInput("Do you want to remove block?");
    	board.removeBlockade(currentTerrainIndex);
    	board.repaint();
    }
    
    public void PlayerMoves() {
        int turnNumber = 0;
        while (true) {
            displayGameState();
            for (int currentPlayerIndex = 0; currentPlayerIndex < players.size(); currentPlayerIndex++) {
            Player currentPlayer = players.get(currentPlayerIndex);
            System.out.println("Turn " + turnNumber + ": Player " + (currentPlayerIndex+1) + "'s turn.");
    	    PlayerDrawCards(turnNumber,currentPlayerIndex);

    	    boolean invalidans=true;
    	    int row = -1;int col = -1;

    	    while(invalidans){
    	    String[] tokens = inputHelper.getInput("Enter row and column for player's position (e.g., '2,3'), or type 'stop' to end the game:", 2);

            if (tokens == null) {
                // Player chose to stop the game
                break;
            }

    	    try {
    	        row = Integer.parseInt(tokens[0]);
    	        col = Integer.parseInt(tokens[1]);
    	        if (!board.isValidPosition(row, col)) {
    	            System.out.println("Invalid position. Please enter valid coordinates.");
    	            continue;
    	        }
    	        }
    	     catch (NumberFormatException e) {
    	        System.out.println("Invalid input. Please enter valid integers for row and column.");
                continue;
    	    }
    	    invalidans=false;
    	    }

    	    currentPlayer.setPlayerPosition(row, col);
	        board.repaint();
	        String targetKey = row+","+col;
	        Tile temp = board.ParentMap.get(targetKey);
	        System.out.println("You are currently on "+temp.getParent());
            }

    	    turnNumber++;
            // Check for end conditions or other game logic
            if (isGameOver()) {
                break;
            }
        }

        // Game over, perform any cleanup or display final results
        endGame();
    }

    

    private String displayGameState() {
        // Display the current state of the game, including the board and player positions
        return GameState;
    }

    private boolean isGameOver() {
        // Check if the game is over based on any end conditions
        // Example: Check if a player has reached a certain position on the board
        // This logic can be customized based on the game rules
        return false; // Placeholder for now
    }

    private void endGame() {
        // Perform any cleanup or display final results
        // Example: Display the winner or final scores
        System.out.println("Game Over!");
    }
}

