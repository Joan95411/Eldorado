package org.set.boardPieces;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Util {
	public static Color getColorFromString(String colorName) {
        Map<String, Color> colorMap = new HashMap<>();
        colorMap.put("gray", Color.GRAY);
        colorMap.put("discard", Color.GRAY);//???
        colorMap.put("joker", Color.GRAY);
        colorMap.put("red", Color.RED);
        colorMap.put("basecamp", Color.RED);
        colorMap.put("yellow", Color.YELLOW);
        colorMap.put("coin", Color.YELLOW);
        colorMap.put("green", Color.GREEN);
        colorMap.put("machete", Color.GREEN);  
        colorMap.put("blue", Color.CYAN);
        colorMap.put("paddle", Color.CYAN);
        colorMap.put("pink", Color.PINK);
        colorMap.put("black", Color.BLACK);
        colorMap.put("mountain", Color.BLACK);
        colorMap.put("cave", Color.BLACK);
        colorMap.put("cyan", Color.CYAN);
        colorMap.put("purple", Color.BLUE);
        colorMap.put("magenta", Color.MAGENTA);
        colorMap.put("orange", Color.ORANGE);
        colorMap.put("start", new Color(0, 100, 0));

        return colorMap.getOrDefault(colorName.toLowerCase(), Color.WHITE);
    }
	public static JSONObject readJsonData(String basePath, String fileName) {
		
		String tileDataJson = readFile(basePath, fileName);
	    if (tileDataJson != null) {
	        return new JSONObject(tileDataJson);
	    }
	    return null;
    }
	
	public static String readFile(String basePath, String fileName) {
		try {
			// Construct the full path
			Path fullPath = Paths.get(basePath, fileName);

			// Check if the file exists and is a regular file
			if (Files.exists(fullPath) && Files.isRegularFile(fullPath)) {
				// Read the file content
				String tileDataJson = new String(Files.readAllBytes(fullPath));
				return tileDataJson;
			}
			else {
				// Handle if the file does not exist or is not a regular file
				System.err.println("Data file not found or is not a regular file.");
			}
		
		
	}catch (IOException | JSONException e) {
		e.printStackTrace();
	}
	return null;}
	
	public static JSONObject readJsonData(String basePath, String fileName, String type) {
		String tileDataJson=readFile(basePath,fileName);
		JSONObject jsonData = new JSONObject(tileDataJson);
                  return jsonData.optJSONObject(type);
                
			}
	
	public static List<JSONObject> readSectionData(String section) {
    	String tileDataPath = "src/main/java/org/set/boardPieces/Sections";
    	String filename = "Section"+section+".json";
    	String sectionInfo = readFile(tileDataPath, filename);
    	JSONArray jsonArray = new JSONArray(sectionInfo);
    	List<JSONObject> parsedData = new ArrayList<>();
    	for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            parsedData.add(jsonObject);
        }
    	return parsedData;
    }
	
	}
	


