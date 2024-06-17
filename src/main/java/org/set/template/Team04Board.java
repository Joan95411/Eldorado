package org.set.template;

import java.util.ArrayList;
import java.util.List;

import org.set.boardPieces.Terrain;

public class Team04Board extends Template{
	 
	public Team04Board(int numRows, int numCols, int hexSize) {
		super(numRows, numCols, hexSize);
	}
	
	@Override
	public void initBoard() {
		List<double[]> coordinateList = new ArrayList<>(List.of(
                new double[]{6, 4},
                new double[]{0, 8},
                new double[]{-6, 4}
        ));
    	getLastTerrain().rotate(2);
        for (double[] coordinates : coordinateList) {
            Terrain terrainLatest=getLastTerrain();
            addTerrain(coordinates[0], coordinates[1], terrainLatest);
        }
        
    }

}
