package org.set.boardPieces;
import org.apache.commons.io.FilenameUtils;
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
        colorMap.put("discard", Color.RED);
        colorMap.put("joker", Color.GRAY);
        colorMap.put("red", Color.RED);
        colorMap.put("basecamp", Color.GRAY);
        colorMap.put("yellow", Color.YELLOW);
        colorMap.put("coin", Color.YELLOW);
        colorMap.put("eldorado", Color.YELLOW);
        colorMap.put("green", Color.GREEN);
        colorMap.put("machete", Color.GREEN);  
        colorMap.put("blue", Color.CYAN);
        colorMap.put("paddle", Color.CYAN);
        colorMap.put("pink", Color.PINK);
        colorMap.put("black", Color.BLACK);
        colorMap.put("mountain", Color.BLACK);
        colorMap.put("cave", new Color(102,51,0));
        colorMap.put("cyan", Color.CYAN);
        colorMap.put("purple", Color.BLUE);
        colorMap.put("magenta", Color.MAGENTA);
        colorMap.put("orange", Color.ORANGE);
        colorMap.put("white", Color.WHITE);
        colorMap.put("start", Color.GREEN);
        colorMap.put("winning", Color.GREEN);

        return colorMap.getOrDefault(colorName.toLowerCase(), Color.WHITE);
    }
	
	public static TileType getTileTypeFromString(String colorName) {
        Map<String, TileType> colorToTileTypeMap = new HashMap<>();
        colorToTileTypeMap.put("basecamp", TileType.BaseCamp);
        colorToTileTypeMap.put("coin", TileType.Coin);
        colorToTileTypeMap.put("discard", TileType.Discard);
        colorToTileTypeMap.put("machete", TileType.Machete);
        colorToTileTypeMap.put("paddle", TileType.Paddle);
        colorToTileTypeMap.put("mountain", TileType.Mountain);
        colorToTileTypeMap.put("cave", TileType.Cave);
        colorToTileTypeMap.put("start", TileType.Start);
        colorToTileTypeMap.put("winning", TileType.Winning);
        colorToTileTypeMap.put("eldorado", TileType.Eldorado);
        return colorToTileTypeMap.getOrDefault(colorName.toLowerCase(), TileType.Default);
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
	        // Sanitize the file name to prevent path traversal
	        String sanitizedFileName = FilenameUtils.getName(fileName);

	        // Construct the full path
	        Path fullPath = Paths.get(basePath, sanitizedFileName).normalize();

	        // Check if the file exists and is a regular file
	        if (Files.exists(fullPath) && Files.isRegularFile(fullPath)) {
	            // Read the file content
	            return new String(Files.readAllBytes(fullPath));
	        } else {
	            // Handle if the file does not exist or is not a regular file
	            System.err.println("Data file not found or is not a regular file.");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;
	}


	
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
	public static JSONArray readPathData(String pathType) {
    	String tileDataPath = "src/main/java/org/set/boardPieces/Sections";
    	String filename = "Path.json";
    	JSONObject jsonData = Util.readJsonData(tileDataPath, filename);
        JSONArray pathArray = jsonData.optJSONArray(pathType);
    	
    	return pathArray;
    }
	
	
	}
	


