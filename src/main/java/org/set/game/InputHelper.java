package org.set.game;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.set.boardPieces.Tile;
import org.set.template.Template;

public class InputHelper {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void printPromptInRows(String prompt) {
        // Split the prompt by commas
        String[] parts = prompt.split(";");
        
        // Print each part in a new line
        for (String part : parts) {
            System.out.println(part.trim());
        }
    }
    public static String[] getInput(String question, int expectedLength) {
        while (true) {
        	printPromptInRows(question);
            System.out.print("> ");
            String input = scanner.nextLine();

            // Check if the user wants to stop
            if (input.equalsIgnoreCase("stop")) {
                return null; // Return null to indicate stopping
            }
            if (input.equalsIgnoreCase("block")) {
                return new String[] {"block"}; 
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
    public static int[] getPositionInput(Template board) {//to be deleted, only for testing use
        while (true) {
            String[] tokens = getInput("Enter row and column for player's position (e.g., '2,3'); or type 'stop' to stop with moving:", 2);
            if(tokens==null) {break;         }
            try {
                int row = Integer.parseInt(tokens[0].trim());
                int col = Integer.parseInt(tokens[1].trim());
                if (!board.isValidPosition(row, col)) {
                    System.out.println("Invalid position. Please enter valid coordinates.");
                    continue;
                }
                String targetKey = row + "," + col;
                Tile temp = board.ParentMap.get(targetKey);
                System.out.println("You are currently on " + temp.getParent());
                return new int[] { row, col };
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid integers for row and column.");
                continue;
            }

        }
    	return new int[] { -100, -100 };
    }

    public static int getIntInput(String prompt, int max,int min) {
        while (true) {
        	printPromptInRows(prompt);
            System.out.print("> ");
            try {
                int userInput = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                

                if (userInput >= min && userInput <= max) {
                    return userInput;
                } else {
                    System.out.println("Please input an index between 0 to " + max + ", or enter -1 to exit.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine(); // Consume the invalid input
            }
        }}
    
    public static List<Integer> getIntListInput(String prompt, int max) {
        while (true) {
        	printPromptInRows(prompt);
            System.out.print("> ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("stop")) {
            	List<Integer> stopList = new ArrayList<>();
                stopList.add(-1);
                return stopList;
            }
            String[] tokens = input.split(",");
            List<Integer> indices = new ArrayList<>();
            boolean valid = true;
            
            for (String token : tokens) {
                try {
                    int index = Integer.parseInt(token.trim());
                    if (index >= 0 && index <= max) {
                        indices.add(index);
                    } else {
                        System.out.println("Index out of range: " + index + ". Please enter indices between 0 and " + max);
                        valid = false;
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: " + token + ". Please enter valid integers separated by commas.");
                    valid = false;
                    break;
                }
            }

            if (valid) {
                return indices;
            }
        }}
    
    
    public static int[] getPlayerMoveInput(Template board, Tile tile) {
    	Set<String> neighborSet = new HashSet<>();
        for (int[] neighbor : tile.getNeighbors()) {
            neighborSet.add(neighbor[0] + "," + neighbor[1]);
        }
        while (true) {
            String[] tokens = getInput("Enter row and column for player's position (e.g., '2,3'); or type 'stop' to stop with moving:", 2);
            if(tokens==null) {break;}
            if(tokens[0]=="block") {return new int[] {-200,-200};}
            try {
                int row = Integer.parseInt(tokens[0].trim());
                int col = Integer.parseInt(tokens[1].trim());
                
                if (!board.isValidPosition(row, col)) {
                    System.out.println("Invalid position. Please enter valid coordinates.");
                    continue;
                }
                String moveKey = row + "," + col;
                if (!neighborSet.contains(moveKey)) {
                    System.out.println("You can only move one step at a time???");
                    continue;
                }
                
                
                return new int[] { row, col };
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid integers for row and column.");
                continue;
            }

        }return new int[] { -100, -100 };
    }

    public static boolean getYesNoInput(String question) {
        while (true) {
        	printPromptInRows(question);
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

    public static void setInputStream(InputStream inputStream){
        scanner = new Scanner(inputStream);
    }
}
