package org.set.boardPieces;

public class TerrainStrip extends Terrain{
	private static int stripCount = 0;
	public TerrainStrip() {
        super();
        int index = ++stripCount;
        this.name = "TerrainStrip_" + index;
        this.pieceCount = 16;
    }
}
