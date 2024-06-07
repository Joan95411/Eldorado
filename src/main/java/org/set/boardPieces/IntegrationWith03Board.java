package org.set.boardPieces;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.json.JSONArray;
import org.json.JSONObject;
import org.utwente.Main;
import org.utwente.Board.Board;
import org.utwente.Board.Path;
import org.utwente.game.controller.GameController;
import org.utwente.Board.Blockade.Blockade;

public class IntegrationWith03Board {
	public Board board;
public  IntegrationWith03Board() {
		
		Main mainPanel = new Main(Path.HillsOfGold);
        GameController gameController = mainPanel.getGameController();
        JFrame frame = new JFrame(gameController.getGame().getGameName());
        gameController.getGame().placePlayersStart();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        frame.add(scrollPane);

        frame.setSize(mainPanel.getPreferredSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        board=gameController.getGame().getBoard();
//		System.out.println(gameController.getGame().getBoard().getPath().name());
//		System.out.println(gameController.getGame().getBoard().getBlockades().get(0).getSection1().getSectionType());
//		System.out.println(gameController.getGame().getBoard().getBlockades().get(0).getSection2().getSectionType());
	
}
	public JSONArray Get03Path() {
		String path= board.getPath().name();
		JSONArray pathInfo=Util.readPathData(path);
    	List<String[]> blockNeighbor=Get03BlocksNeighbor();
    	for (String[] blockade : blockNeighbor) {
            String sectionType1 = blockade[0];
            String sectionType2 = blockade[1];
            System.out.println(sectionType1+" "+sectionType2);
            for (int i = 0; i < pathInfo.length()-1; i++) {
                JSONObject section1 = pathInfo.getJSONObject(i);
                JSONObject section2 = pathInfo.getJSONObject(i+1);
                if (section1.getString("sectionType").equals(sectionType1) && section2.getString("sectionType").equals(sectionType2)) {
                	 
                    double addRow = section2.getDouble("addRow");
                    double addCol = section2.getDouble("addCol");
                    if(addRow>1) {
                    section2.put("addRow", addRow + 1);}
                    else if(addRow<-1) {
                    section2.put("addRow", addRow - 1);}
                    else if(addCol>1){
                    	section2.put("addCol", addCol +1);
                    }else if(addCol<-1){
                    	section2.put("addCol", addCol -1);
                    }
                }
    	}
    	}
    	System.out.println(pathInfo);
	return pathInfo;
}
	public List<String[]> Get03BlocksNeighbor(){
		List<String[]> pairs = new ArrayList<>();
		for (Blockade block:board.getBlockades()) {
			String sectionType1 = block.getSection1().getSectionType().toString();
            String sectionType2 = block.getSection2().getSectionType().toString();
            pairs.add(new String[]{sectionType1, sectionType2});
		}
		return pairs;
	}
}
