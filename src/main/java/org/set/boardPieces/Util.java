package org.set.boardPieces;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class Util {
	public static Color getColorFromString(String colorName) {
        Map<String, Color> colorMap = new HashMap<>();
        colorMap.put("gray", Color.GRAY);
        colorMap.put("JOKER", Color.GRAY);
        colorMap.put("red", Color.RED);
        colorMap.put("yellow", Color.YELLOW);
        colorMap.put("green", Color.GREEN);
        colorMap.put("blue", Color.BLUE);
        colorMap.put("pink", Color.PINK);
        colorMap.put("black", Color.BLACK);
        colorMap.put("cyan", Color.CYAN);
        colorMap.put("purple", new Color(102, 0 ,153));
        return colorMap.getOrDefault(colorName.toLowerCase(), Color.WHITE);
    }
	
	public static JSONObject readJsonData(String basePath, String fileName, String type) {
	    try {
	        // Construct the full path
	        Path fullPath = Paths.get(basePath, fileName);

	        // Check if the file exists and is a regular file
	        if (Files.exists(fullPath) && Files.isRegularFile(fullPath)) {
	            // Read the file content
	            String tileDataJson = new String(Files.readAllBytes(fullPath));

	            // Parse the JSON data
	            JSONObject jsonData = new JSONObject(tileDataJson);

	            // Lookup the JSON object based on the provided type
	            JSONObject result = jsonData.optJSONObject(type);
	            return result;
	        } else {
	            // Handle if the file does not exist or is not a regular file
	            System.err.println("Tile data file not found or is not a regular file.");
	        }
	    } catch (IOException | JSONException e) {
	        e.printStackTrace();
	    }
	    return null; // Return null if an exception occurs or the file is not found
	}
}
