package org.set;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.set.boardPieces.HexagonGameBoard;
import org.set.boardPieces.Tile;

public class InputHelper {
    private static final Scanner scanner = new Scanner(System.in);


    public static String[] getInput(String question, int expectedLength) {
        while (true) {
            System.out.println(question);
            System.out.print("> ");
            String input = scanner.nextLine();

            // Check if the user wants to stop
            if (input.equalsIgnoreCase("stop")) {
                return null; // Return null to indicate stopping
            }

            String[] tokens = input.split(",");
            
            // Check if the input length matches the expected length
            if (tokens.length != expectedLength) {
                System.out.println("Invalid input. Please enter " + expectedLength + " values separated by commas.");
                continue; // Continue to the next iteration if the input length is invalid
            }

            return tokens;
        }
    }
    public static int getIntInput(String prompt) {
        int userInput;
        do {
            System.out.println(prompt);
            System.out.print("> ");
            try {
                userInput = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                return userInput;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine(); // Consume the invalid input
            }
        } while (true);
    }
    
    public static int[] getPositionInput(HexagonGameBoard board) {
        while (true) {
        	String[] tokens =getInput("Enter row and column for player's position (e.g., '2,3'), or type 'stop' to end the game:", 2);
        	try {
        		int row = Integer.parseInt(tokens[0].trim());
                int col = Integer.parseInt(tokens[1].trim());
    	        if (!board.isValidPosition(row, col)) {
    	            System.out.println("Invalid position. Please enter valid coordinates.");
    	            continue;
    	        }
    	        String targetKey = row+","+col;
    	        Tile temp = board.ParentMap.get(targetKey);
    	        System.out.println("You are currently on "+temp.getParent());
    	        return new int[] { row, col };
    	        }
        	catch (NumberFormatException e) {
    	        System.out.println("Invalid input. Please enter valid integers for row and column.");
                continue;
    	    }
        	           
        }
    }
    
    public static Tile getPlayerMoveInput(HexagonGameBoard board,Tile tile) {
    	List<int[]> neighbors=tile.getNeighbors();
        while (true) {
        	String[] tokens =getInput("Enter row and column for player's position (e.g., '2,3'), or type 'stop' to end the game:", 2);
        	try {
        		int row = Integer.parseInt(tokens[0].trim());
                int col = Integer.parseInt(tokens[1].trim());
    	        if (!board.isValidPosition(row, col)) {
    	            System.out.println("Invalid position. Please enter valid coordinates.");
    	            continue;
    	        }
    	        if(!neighbors.contains(new int[]{row, col})) {
    	        	System.out.println("You can only move one step at a time???");
    	            continue;
    	        }
    	        String targetKey = row+","+col;
    	        Tile temp = board.ParentMap.get(targetKey);
    	        
    	        System.out.println("You are currently on "+temp.getParent());
    	        return temp;
    	        }
        	catch (NumberFormatException e) {
    	        System.out.println("Invalid input. Please enter valid integers for row and column.");
                continue;
    	    }
        	           
        }
    }

    public static boolean getYesNoInput(String question) {
        while (true) {
            System.out.println(question);
            System.out.print("> ");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'yes'(y) or 'no'(n).");
            }
        }
    }

    public static void closeScanner() {
        scanner.close();
    }
}
