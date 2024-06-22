package org.set.boardPieces;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.json.JSONArray;
import org.json.JSONObject;
import org.utwente.Main;
import org.utwente.Board.Board;
import org.utwente.Board.Path;
import org.utwente.game.controller.GameController;
import org.utwente.Board.Blockade.Blockade;
import org.utwente.Section.Section;

public class IntegrationWith03Board {
	public Board board;
	public Main mainPanel;
	public GameController gameController;
	
public  IntegrationWith03Board(String pathString) {
	
	Path path= convertStringToPath(pathString);
		mainPanel = new Main(path);
        gameController = mainPanel.getGameController();
        board=gameController.getGame().getBoard();
	 
	
}
private Path convertStringToPath(String pathString) {
    switch (pathString.toLowerCase()) {
    case "hillsofgold":
        return Path.HillsOfGold;    
    case "homestretch":
            return Path.HomeStretch;
        case "swamplands":
            return Path.Swamplands;
        case "windingpaths":
            return Path.WindingPaths;
        case "witchcauldron":
            return Path.WitchCauldron;
        case "serpentine":
            return Path.Serpentine;
        default:
            return Path.Swamplands;
    }
}
	
	public JSONArray Get03Path(String path) {
		JSONArray pathInfo=Util.readPathData(path);
		for (Blockade block:board.getBlockades()) {
			String sectionType1 = block.getSection1().getSectionType().toString();
            String sectionType2 = block.getSection2().getSectionType().toString();
            String color = block.getTileType().toString();
            System.out.println(sectionType1 + " " + sectionType2);
            for (int i = 0; i < pathInfo.length() - 1; i++) {
                JSONObject section1 = pathInfo.getJSONObject(i);
                JSONObject section2 = pathInfo.getJSONObject(i + 1);
                if (section1.getString("sectionType").equals(sectionType1)
                        && section2.getString("sectionType").equals(sectionType2)) {
                    section2.put("blockColor", color);
                    double addRow = section2.getDouble("addRow");
                    double addCol = section2.getDouble("addCol");
                    if (addRow > 1) {
                        section2.put("addRow", addRow + 1);
                    } else if (addRow < -1) {
                        section2.put("addRow", addRow - 1);
                    } else if (addCol > 1) {
                        section2.put("addCol", addCol + 1);
                    } else if (addCol < -1) {
                        section2.put("addCol", addCol - 1);
                    }
                }
            }
        }
        System.out.println(pathInfo);
        return pathInfo;
    }

    public void Get03Frame() {

        JFrame frame = new JFrame(gameController.getGame().getGameName());
        gameController.getGame().placePlayersStart();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        frame.add(scrollPane);
        frame.setSize(1250, 650);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
