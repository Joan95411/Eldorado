package org.set.template;

import org.json.JSONObject;
import org.set.boardPieces.IntegrationWith03Board;
import org.set.boardPieces.Tile;
import org.set.boardPieces.TileDataDic;
import org.set.boardPieces.TileType;
import org.set.boardPieces.WinningPiece;

public class Team03Board extends Template{

	public Team03Board(int numRows, int numCols, int hexSize) {
		super(numRows, numCols, hexSize);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void loadTileData() {
        TileDataDic tdd = new TileDataDic(numRows, numCols, hexSize);
        boardPieces.put(tdd.terrainA.getName(), tdd.terrainA);
        boardPieces.put(tdd.wpa.getName(), tdd.wpa);
        IntegrationWith03Board game03= new IntegrationWith03Board();
        pathInfo=game03.Get03Path();
        	
    }
	
	@Override
	public void initBoard() {
    	
    	 for (int i = 0; i < pathInfo.length(); i++) {
             JSONObject jsonObject = pathInfo.getJSONObject(i);
             double addRow = jsonObject.getDouble("addRow");
             double addCol = jsonObject.getDouble("addCol");
             String sectionType = jsonObject.getString("sectionType");
             int rotation = jsonObject.getInt("rotation");
             
             if(addRow!=0 || addCol!=0) {
             addTerrain(addRow, addCol, getLastTerrain());
             if(i==0) {
            	 boardPieces.remove("Terrain_1"); //remove the template
            	 boardPieces.remove("Blockade_1"); 
             }
             }

             if(sectionType.startsWith("ElDorado")) {
            	WinningPiece wps=getLastWinningPiece();
            	Tile axisTile=getLastTerrain().axisTile;
            	int[] temp=TileDataDic.tilesMap.get(axisTile.getRow()+","+axisTile.getCol());
            	wps.rotate(rotation, temp[0], temp[1]);
            	if(sectionType.equals("ElDoradoTwo")) {
            		wps.setColor(TileType.Paddle);
            	}
             }else {

                 getLastTerrain().reFillTile(sectionType);
                 getLastTerrain().rotate(rotation);
             }
    	 }
        
        
    }
}
