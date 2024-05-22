package org.set;

import java.util.Scanner;


public class GameController {
    private HexagonGameBoard board;
    private Player[] players;
    private String GameState;
    private Scanner scanner;
    public int turnCounter;
    public GameController(HexagonGameBoard board) {
        scanner = new Scanner(System.in);
        this.board=board;
        GameState="Game in process";
        //setSpecialColor();
        //removeblock(0);
        //removeblock(1);
        //removeblock(2);
        addPlayer();
        placePlayersOnBoard();
        PlayerMoves();
        
    }
    
    public void setSpecialColor() {
        
        while (true) {
            System.out.println("Do you want to set any tile specially? (y/n)");
            String input = scanner.nextLine();
            
            if (input.equalsIgnoreCase("n")) {
                break; // Exit the loop if the user doesn't want to set any more special tiles
            } else if (!input.equalsIgnoreCase("y")) {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
                continue; // Continue to the next iteration if the input is invalid
            }

            System.out.println("Tell me the tile of row,column,color,points (e.g., '2,3,red,1') or type 'done' to finish:");
            String rowcol = scanner.nextLine();

            if (rowcol.equalsIgnoreCase("done")) {
                break; // Exit the loop if the user types 'done'
            }

            String[] tokens = rowcol.split(",");
            if (tokens.length != 4) {
                System.out.println("Invalid input. Please enter row, column, color, and points separated by comma.");
                continue; // Continue to the next iteration if the input is invalid
            }

            try {
                int row = Integer.parseInt(tokens[0]);
                int col = Integer.parseInt(tokens[1]);
                String color = tokens[2];
                int points = Integer.parseInt(tokens[3]);
                
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
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid integers for row and column, and a valid color.");
            }
        }
        
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
            Player player = new Player(Util.getColorFromString(color));
            
            // Add the player to the players array
            players[i] = player;
            
    }board.players=players;
        
    }
    
    private void placePlayersOnBoard() {
    	
		 for (Player player : players) {
			 
       player.setPlayerPosition(1+player.id, 1);
       
		 }board.repaint();
    }
    
    private void PlayerDrawCards(int turn,int currentPlayerIndex) {

	    System.out.println("Player " + (currentPlayerIndex+1) + " drawing cards" );
    	Player currentPlayer = players[currentPlayerIndex];
    	currentPlayer.drawCards(turn);
	    board.PlayerCards=currentPlayer.getCards();
        board.repaint();
    }


    
    private void removeblock(int currentTerrainIndex){
    	System.out.println("Remove blockade");
    	scanner.next();
    	board.removeBlockade(currentTerrainIndex);
    	board.repaint();
    }
    
    public void PlayerMoves() {
        int turnNumber = 0;
        while (true) {
            displayGameState();
            for (int currentPlayerIndex = 0; currentPlayerIndex < players.length; currentPlayerIndex++) {
            Player currentPlayer = players[currentPlayerIndex];
            System.out.println("Turn " + turnNumber + ": Player " + (currentPlayerIndex+1) + "'s turn.");
    	    PlayerDrawCards(turnNumber,currentPlayerIndex);
    	    
    	    boolean invalidans=true;
    	    int row = -1;int col = -1;
    	    
    	    while(invalidans){
    	    System.out.println("Enter row and column for player's position (e.g., '2 3'), or type 'stop' to end the game:");
    	    System.out.print("> "); 
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
	        Tile temp = board.tilesMap.get(targetKey);
	        System.out.println("You are currently on "+temp.getParent());
            }
    	    // Move to the next player
    	    //currentPlayerIndex = (currentPlayerIndex+1) % players.length;
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

