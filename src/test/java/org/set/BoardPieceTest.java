package org.set;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.set.boardPieces.Tile;
import org.set.boardPieces.TileDataDic;
import org.set.boardPieces.TileType;
import org.set.boardPieces.Util;
import org.set.boardPieces.WinningPiece;
import org.set.template.Team04Board;
import org.set.template.Template;

class BoardPieceTest {

	private Template board;
    
    @BeforeEach
    public void setUp() {
    	board = new Team04Board(35, 40, 25); 
    }
    
    @Test
    public void testtestwitchcauldron() {
    	board.boardPieces.clear();
    	board.loadTileData();
    	board.pathInfo=Util.readPathData("WitchCauldron");
    	initBoard();
    	Tile tile=board.getLastWinningPiece().getTiles().get(0);
		assertEquals(14,tile.getRow());
		assertEquals(10,tile.getCol());
    }
    
    @Test
    public void testwindingpaths() {
    	board.boardPieces.clear();
    	board.loadTileData();
    	board.pathInfo=Util.readPathData("WindingPaths");
    	initBoard();
    	Tile tile=board.getLastWinningPiece().getTiles().get(0);
		assertEquals(4,tile.getRow());
		assertEquals(33,tile.getCol());
    }
    
    @Test
    public void testHillsOfGold() {
    	board.boardPieces.clear();
    	board.loadTileData();
    	board.pathInfo=Util.readPathData("HillsOfGold");
    	initBoard();
    	Tile tile=board.getLastWinningPiece().getTiles().get(0);
		assertEquals(17,tile.getRow());
		assertEquals(31,tile.getCol());
    }
    @Test
    public void testSerpentine() {
    	board.boardPieces.clear();
    	board.loadTileData();
    	board.pathInfo=Util.readPathData("Serpentine");
    	initBoard();
    	Tile tile=board.getLastWinningPiece().getTiles().get(0);
		assertEquals(13,tile.getRow());
		assertEquals(24,tile.getCol());
    }
    private void initBoard() {
    	JSONArray pathInfo=board.pathInfo;
    	for (int i = 0; i < pathInfo.length(); i++) {
            JSONObject jsonObject = pathInfo.getJSONObject(i);
            double addRow = jsonObject.getDouble("addRow");
            double addCol = jsonObject.getDouble("addCol");
            String sectionType = jsonObject.getString("sectionType");
            int rotation = jsonObject.getInt("rotation");
            
            if(addRow!=0 || addCol!=0) {
            	board.addTerrain(addRow, addCol, board.getLastTerrain());
            
            if(i==0) {
            	board.boardPieces.remove(board.getFirstTerrain().getName()); //remove the template
           	 if(board.getAllBlockades().size()>0) {
           		board.boardPieces.remove(board.getFirstBlockade().getName()); }
            }
            }
            String blockColor = jsonObject.optString("blockColor","noBlock");
            if(blockColor!="noBlock") {
            	board.getLastBlockade().setColor(Util.getTileTypeFromString(blockColor));
            	board.getLastBlockade().setPoints(1);
            }
            if(sectionType.startsWith("ElDorado")) {
           	WinningPiece wps=board.getLastWinningPiece();
           	Tile axisTile=board.getLastTerrain().axisTile;
           	int[] temp=TileDataDic.tilesMap.get(axisTile.getRow()+","+axisTile.getCol());
           	wps.rotate(rotation, temp[0], temp[1]);
           	if(sectionType.equals("ElDorado")) {
           		wps.setColor(TileType.Paddle);
           	}
            }else {

            	board.getLastTerrain().reFillTile(sectionType);
            	board.getLastTerrain().rotate(rotation);
            }
   	 }
    }
}
