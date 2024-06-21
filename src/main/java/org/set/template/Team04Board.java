package org.set.template;

import java.util.ArrayList;
import java.util.List;

import org.set.boardPieces.BoardPiece;
import org.set.boardPieces.Terrain;
import org.set.boardPieces.Tile;

public class Team04Board extends Template{
	List<double[]> coordinateList;
	public Team04Board(int numRows, int numCols, int hexSize) {
		super(numRows, numCols, hexSize);
	}
	
	public void setCordList(List<double[]> coordinateList){
		this.coordinateList=coordinateList;
	}
	
	@Override
	public void initBoard() {
		
        for (double[] coordinates : coordinateList) {
            Terrain terrainLatest=getLastTerrain();
            addTerrain(coordinates[0], coordinates[1], terrainLatest);
        }
        
    }

	@Override
	public void setConfiguration() {
		// TODO Auto-generated method stub
		coordinateList = new ArrayList<>(List.of(
                new double[]{6, 4},
                new double[]{0, 8},
                new double[]{-6, 4}
        ));

    	getLastTerrain().rotate(2);
	}

}
