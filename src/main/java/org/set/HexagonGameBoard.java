package org.set;

import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HexagonGameBoard extends JPanel  {
    public int numRows;
    public int numCols;
    public int hexSize;
    public ArrayList<Player> players = new ArrayList<>();
    private Map<String, Color> colorMap = new HashMap<>();
    public JSONObject tileInfo;

    public HexagonGameBoard(int numRows, int numCols, int hexSize) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.hexSize = hexSize;
        setPreferredSize(new Dimension((int) (numCols * 1.5 * hexSize), (int) (numRows * Math.sqrt(3) * hexSize)));
        setPlayerColors();
        loadTileData();
    }
    
    private void loadTileData() {
        try {
            String tileDataPath = "/Users/basabbink/Development/set2024team04project/src/main/java/org/set/tileData.json";
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
        if (tileInfo == null) loadTileData(); // Load tile data if not already loaded

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
                drawHexagon(g2d, x, y, hexSize, color, row, col,points);
                
            }
        }
        if (players != null) {
            for (Player player : players) {
                drawPlayer(g2d, player.getCurrentRow(), player.getCurrentCol(), player.getColor());
            }
        }
        
    }

    public String getUniquePlayerColors(ArrayList<String> usedColors) {
        for (String usedColor : usedColors) colorMap.keySet().remove(usedColor);
        return colorMap.keySet().toString();
    }

    public void setPlayerColors() {
        colorMap.put("gray", Color.GRAY);
        colorMap.put("red", Color.RED);
        colorMap.put("yellow", Color.YELLOW);
        colorMap.put("green", Color.GREEN);
        colorMap.put("blue", Color.BLUE);
        colorMap.put("pink", Color.PINK);
        colorMap.put("black", Color.BLACK);
        colorMap.put("white", Color.WHITE);
    }

    public Color getColorFromString(String colorName) {
        return colorMap.get(colorName.toLowerCase());
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
    
    private void drawHexagon(Graphics2D g2d, int x, int y, int size, Color color, int row, int col,int points) {
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
        String Points=points+"P";
        int rowColWidth = fm.stringWidth(rowColStr);
        int rowColHeight = fm.getHeight();
        int centerX = x + size-25;
        int centerY = y + size-45;
        g2d.drawString(rowColStr, centerX - rowColWidth / 2, centerY + rowColHeight / 2);
        if(points!=0){
        g2d.drawString(Points, (centerX-25) - rowColWidth / 2, (centerY-45) + rowColHeight / 2);}
    }

    public void drawPlayer(Graphics2D g2d, int row, int col, Color color) {
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
}

