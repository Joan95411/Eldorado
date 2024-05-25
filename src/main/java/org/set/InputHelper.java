package org.set;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHelper {
    private final Scanner scanner;

    public InputHelper() {
        scanner = new Scanner(System.in);
    }

    public String[] getInput(String question, int expectedLength) {
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
    public int getIntInput(String prompt) {
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

    public boolean getYesNoInput(String question) {
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

    public void closeScanner() {
        scanner.close();
    }
}
