package org.set.boardPieces;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Util {
	private static final String allowedDirectory = "src/main/java/org/set/";

	public static Color getColorFromString(String colorName) {
		Map<String, Color> colorMap = new HashMap<>();
		colorMap.put("gray", Color.GRAY);
		colorMap.put("JOKER", Color.GRAY);
		colorMap.put("red", Color.RED);
		colorMap.put("yellow", Color.YELLOW);
		colorMap.put("green", Color.GREEN);
		colorMap.put("blue", Color.CYAN);
		colorMap.put("pink", Color.PINK);
		colorMap.put("black", Color.BLACK);
		colorMap.put("cyan", Color.CYAN);
		colorMap.put("purple", Color.BLUE);
		colorMap.put("MAGENTA", Color.MAGENTA);
		colorMap.put("ORANGE", Color.ORANGE);
		return colorMap.getOrDefault(colorName.toLowerCase(), Color.WHITE);
	}

	public static JSONObject readJsonData(String basePath, String fileName, String type) {
		try {
			String sanitizedFileName = FilenameUtils.getName(fileName);
			Path fullPath = Paths.get(basePath, sanitizedFileName);

			if (Files.exists(fullPath) && Files.isRegularFile(fullPath)) {
				String tileDataJson = new String(Files.readAllBytes(fullPath));
				JSONObject jsonData = new JSONObject(tileDataJson);
				JSONObject result = jsonData.optJSONObject(type);
				return result;
			} else {
				System.err.println("Tile data file not found or is not a regular file.");
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject getFile(String filePath, String fileName, String specific) throws FileNotFoundException {
		String sanitizedFileName = FilenameUtils.getName(fileName);
		File file = new File(filePath, sanitizedFileName);

		if (!isPathWithinAllowedDirectory(file)) {
			throw new FileNotFoundException("Invalid file path: " + file.getPath());
		}

		if (!file.exists()) {
			throw new FileNotFoundException("File not found: " + file.getPath());
		}

		return Util.readJsonData(filePath, sanitizedFileName, specific);
	}

	private static boolean isPathWithinAllowedDirectory(File file) {
		try {
			String canonicalFilePath = file.getCanonicalPath();
			String canonicalAllowedDirPath = new File(allowedDirectory).getCanonicalPath();

			return canonicalFilePath.startsWith(canonicalAllowedDirPath);
		} catch (IOException e) {
			return false;
		}
	}
}
