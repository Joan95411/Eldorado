package org.set;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

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

	

}
