package org.set.template;


import org.json.JSONObject;
import org.set.boardPieces.BoardPiece;
import org.set.boardPieces.IntegrationWith03Board;
import org.set.boardPieces.Tile;
import org.set.boardPieces.TileDataDic;
import org.set.boardPieces.TileType;
import org.set.boardPieces.Util;
import org.set.boardPieces.WinningPiece;

public class Team03Board extends Template{

	private String pathString;
	public Team03Board(int numRows, int numCols, int hexSize) {
		super(numRows, numCols, hexSize);
	}
	public void setPathString(String pathString) {
        this.pathString = pathString;
    }
	
	@Override
	public void setConfiguration() {
		// TODO Auto-generated method stub
		setPathString("Serpentine");
		IntegrationWith03Board game03= new IntegrationWith03Board(pathString);
        pathInfo=game03.Get03Path();
        game03.Get03Frame(); //only if you want to compare our board to their board visually
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
            	 boardPieces.remove(getFirstTerrain().getName()); //remove the template
            	 if(getAllBlockades().size()>0) {
            	 boardPieces.remove(getFirstBlockade().getName()); }
             }
             }
             String blockColor = jsonObject.optString("blockColor","noBlock");
             if(blockColor!="noBlock") {
            	 getLastBlockade().setColor(Util.getTileTypeFromString(blockColor));
            	 getLastBlockade().setPoints(1);
             }
             if(sectionType.startsWith("ElDorado")) {
            	WinningPiece wps=getLastWinningPiece();
            	Tile axisTile=getLastTerrain().axisTile;
            	int[] temp=TileDataDic.tilesMap.get(axisTile.getRow()+","+axisTile.getCol());
            	wps.rotate(rotation, temp[0], temp[1]);
            	if(sectionType.equals("ElDorado")) {
            		wps.setColor(TileType.Paddle);
            	}
             }else {

                 getLastTerrain().reFillTile(sectionType);
                 getLastTerrain().rotate(rotation);
             }
    	 }
    	 
        
    }
	
}
