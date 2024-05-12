package org.set;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;

import org.json.JSONObject;

public class GameController {
    private HexagonGameBoard board;
    private Player[] players;
    private String GameState;
    private Scanner scanner;

    public GameController(HexagonGameBoard board) {
        scanner = new Scanner(System.in);
        this.board=board;
        GameState="Game in process";
        addPlayer();
        placePlayersOnBoard();
        PlayerMoves();
    }
    
    

    public void addPlayer() {
    	System.out.println("How many players are playing?");
        int numPlayers = scanner.nextInt();

        // Create an array to store player instances
        players = new Player[numPlayers];

        // Loop through each player
        for (int i = 0; i < numPlayers; i++) {
            // Ask each player to choose a color
            System.out.println("Player " + (i+1) + ", choose your color:");
            String color = scanner.next();

            // Create a new player instance with the chosen color
            Player player;

            player = new Player((i+1), board.getColorFromString(color));
            
            // Add the player to the players array
            players[i] = player;
            
    }board.players=players;
        
    }
    private void placePlayersOnBoard() {
    	
		 for (Player player : players) {
			 
       player.setPlayerPosition(2+player.id, 1);
       
}board.repaint();
}
    
    public void PlayerMoves() {
        int currentPlayerIndex = 0;
        // Main game loop
        while (true) {
            // Display the game state (e.g., board, player positions)
            displayGameState();

            Player currentPlayer = players[currentPlayerIndex];
    	    System.out.println("Player " + (currentPlayerIndex+1) + "'s turn.");
    	    System.out.println("Enter row and column for player's position (e.g., '2 3'), or type 'stop' to end the game:");

    	    String input = scanner.nextLine();
    	    if (input.equalsIgnoreCase("stop")) {
    	        break;
    	    }

    	    String[] tokens = input.split("\\s+");
    	    if (tokens.length != 2) {
    	        System.out.println("Invalid input. Please enter row and column separated by space.");
    	        continue;
    	    }

    	    try {
    	        int row = Integer.parseInt(tokens[0]);
    	        int col = Integer.parseInt(tokens[1]);
    	        if (!board.isValidPosition(row, col)) {
    	            System.out.println("Invalid position. Please enter valid coordinates.");
    	            continue; // Continue to the next iteration of the loop
    	        }
    	        
    	        currentPlayer.setPlayerPosition(row, col);
    	        board.repaint();
    	    } catch (NumberFormatException e) {
    	        System.out.println("Invalid input. Please enter valid integers for row and column.");
    	    }

    	    // Move to the next player
    	    currentPlayerIndex = (currentPlayerIndex+1) % players.length;

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

