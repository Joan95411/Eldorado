package org.set;

import org.json.JSONException;
import org.json.JSONObject;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.List;
public class HexagonGameBoard extends JPanel {
    private int numRows;
    private int numCols;
    private int hexSize;
    private JSONObject tileInfo;
    private Player[] players;
    public HexagonGameBoard(int numRows, int numCols, int hexSize) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.hexSize = hexSize;
        
        
        setPreferredSize(new Dimension((int) (numCols * 1.5 * hexSize), (int) (numRows * Math.sqrt(3) * hexSize)));
        loadTileData();
        
        
        
    }
    
    private void loadTileData() {
        try {
            String tileDataPath = "D:\\Joan\\orderList\\bin\\Hex\\tileData.json";
            String tileDataJson = new String(Files.readAllBytes(new File(tileDataPath).toPath()));
            JSONObject tileData = new JSONObject(tileDataJson);
            tileInfo = tileData.getJSONObject("tiles");
        } catch (IOException | JSONException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (tileInfo == null) {
            loadTileData(); // Load tile data if not already loaded
        }
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int x = col * (int) (1.5 * hexSize);
                int y = row * (int) (Math.sqrt(3) * hexSize);
                if (col % 2 == 1) {
                    y += (int) (Math.sqrt(3) / 2 * hexSize);
                }
                String key = row + "," + col;
                JSONObject currentTileInfo = tileInfo.optJSONObject(key); // Get tile info for current row and col
                if (currentTileInfo == null) {
                    // Handle case when tile data is not found for current position
                    currentTileInfo = new JSONObject();
                    currentTileInfo.put("color", "White");
                    currentTileInfo.put("points", 0);
                }
                String colorName = currentTileInfo.getString("color");
                Color color = getColorFromString(colorName);
                int points = currentTileInfo.getInt("points");
                drawHexagon(g2d, x, y, hexSize, color, row, col);
                
            }
        }
        if(players != null && players.length > 0){
        for (Player player : players) {
            drawPlayer(g2d, player.getCurrentRow(), player.getCurrentCol(), player.getColor());
        }}
        
    }
    private Color getColorFromString(String colorName) {
        switch (colorName) {
        	case "Gray":
        		return Color.GRAY;
        	case "Red":
        		return Color.RED;
        	case "Yellow":
                return Color.YELLOW;
            case "Green":
                return Color.GREEN;
            case "Blue":
                return Color.BLUE;
            default:
                return Color.WHITE;
        }
    }
    public boolean isValidPosition(int row, int col) {
        if (row < 0 || row >= numRows || col < 0 || col >= numCols) {
        	System.out.println("Going out of border. Choose another move! ");
            return false;
        }
        for (Player player : players) {
            if (player.isAtPosition(row, col)) {
                System.out.println("Someone's already here. Choose another move!");
                return false;
            }
        }
        String key = row + "," + col;
        JSONObject currentTileInfo = tileInfo.optJSONObject(key); // Get tile info for current row and col
        if (currentTileInfo == null) {
        	System.out.println("Going out of border. Choose another move! ");
            return false; // Tile data not found for this position
        }
        return true;
    }
    private void drawHexagon(Graphics2D g2d, int x, int y, int size, Color color, int row, int col) {
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];
        for (int i = 0; i < 6; i++) {
            xPoints[i] = (int) (x + size * Math.cos(i * Math.PI / 3));
            yPoints[i] = (int) (y + size * Math.sin(i * Math.PI / 3));
        }
        Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 150); // Adjust the alpha value (0-255) as needed
        
        g2d.setColor(transparentColor);
        g2d.fillPolygon(xPoints, yPoints, 6);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(xPoints, yPoints, 6);
        
        // Draw row and column numbers
        FontMetrics fm = g2d.getFontMetrics();
        String rowColStr = row + "," + col;
        int rowColWidth = fm.stringWidth(rowColStr);
        int rowColHeight = fm.getHeight();
        int centerX = x + size-25;
        int centerY = y + size-45;
        g2d.drawString(rowColStr, centerX - rowColWidth / 2, centerY + rowColHeight / 2);
    }
    private void drawPlayer(Graphics2D g2d, int row, int col, Color color) {
        // Calculate center of hexagon
        int x = col * (int) (1.5 * hexSize);
        int y = row * (int) (Math.sqrt(3) * hexSize);
        if (col % 2 == 1) {
            y += (int) (Math.sqrt(3) / 2 * hexSize);
        }
        int centerX = x + hexSize-90;
        int centerY = y + hexSize-45;

        // Draw a star representing player's position
        int[] xPoints = {centerX, centerX + hexSize / 4, centerX + hexSize / 2, centerX + hexSize * 3 / 4, centerX + hexSize, centerX + hexSize * 3 / 4, centerX + hexSize / 2, centerX + hexSize / 4};
        int[] yPoints = {centerY - hexSize / 4, centerY - hexSize / 4, centerY - hexSize / 2, centerY - hexSize / 4, centerY - hexSize / 4, centerY, centerY + hexSize / 4, centerY - hexSize / 4};
        g2d.setColor(color);
        g2d.fillPolygon(xPoints, yPoints, 8);
        
    }
    public void initGame(Scanner scanner){
    	 System.out.println("How many players are playing?");
         int numPlayers = scanner.nextInt();

         // Create an array to store player instances
         players = new Player[numPlayers];

         // Loop through each player
         for (int i = 0; i < numPlayers; i++) {
             // Ask each player to choose a color
             System.out.println("Player " + (i + 1) + ", choose your color:");
             String color = scanner.next();

             // Create a new player instance with the chosen color
             Player player;

             // Place the player at specific positions
             switch (i) {
                 case 0:
                	 player = new Player(3,1,getColorFromString(color));
                	 
                     break;
                 case 1:
                	 player = new Player(4,1,getColorFromString(color));
                     break;
                 case 2:
                	 player = new Player(5,1,getColorFromString(color));
                     break;
                 default:
                     player = new Player(1,1, getColorFromString(color));
             }

             // Add the player to the players array
             players[i] = player;
         }repaint();
         

    }
    public void playermove(Scanner scanner){
    	int currentPlayerIndex = 0;


    	while (true) {
    	    Player currentPlayer = players[currentPlayerIndex];
    	    System.out.println("Player " + (currentPlayerIndex + 1) + "'s turn.");
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
    	        if (!isValidPosition(row, col)) {
    	            System.out.println("Invalid position. Please enter valid coordinates.");
    	            continue; // Continue to the next iteration of the loop
    	        }
    	        
    	        currentPlayer.setPlayerPosition(row, col);
    	        repaint();
    	    } catch (NumberFormatException e) {
    	        System.out.println("Invalid input. Please enter valid integers for row and column.");
    	    }

    	    // Move to the next player
    	    currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
    	}



        
    }
    

    
    
    
}

