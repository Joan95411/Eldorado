package org.set.boardPieces;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;


public class Terrain extends BoardPiece {
    private static int terrainCount = 0;
    private static final TileType[] SPECIAL_COLOR_RANGE = { TileType.BaseCamp, TileType.Discard, TileType.Mountain, TileType.Cave };
    private static final double GREEN_PROBABILITY = 0.3;
    private static final double specialColorProbability = 0.1;
    public Tile axisTile;
    
    public Terrain() {
        super();
        int index = ++terrainCount;
        this.name = "Terrain_" + index;
        this.pieceCount = 37;
    }

    public static void resetWinningCount() {
        terrainCount = 0;
    }

    @Override
    public void randomizeTiles() {
        Random random = new Random();
        for (Tile tile : tiles) {
            // Randomly select color
        	TileType color = getRandomColor(random);
        	tile.setType(color);

            if(tile.getType()==TileType.Cave || tile.getType()==TileType.Mountain) {
            	tile.setPoints(0);
            }else {
            int points = random.nextInt(POINTS_MAX - POINTS_MIN + 1) + POINTS_MIN;
            tile.setPoints(points);}
        }
    }


    private TileType getRandomColor(Random random) {
        double rand = random.nextDouble();
        if (rand < GREEN_PROBABILITY) {
            // Mostly green
            return TileType.Machete;
        } else {
            double randSpecial = random.nextDouble();

            if (randSpecial < specialColorProbability) {
                // Randomly select a special color with lower probability
                int index = random.nextInt(SPECIAL_COLOR_RANGE.length);
                return SPECIAL_COLOR_RANGE[index];
            } else {
                // Randomly select from the standard color range
                int index = random.nextInt(COLOR_RANGE.length);
                return COLOR_RANGE[index];
            }
        }
    }
    
    public void reFillTile(String section) {
    	List<JSONObject> parsedData= Util.readSectionData(section);
    	for (JSONObject jsonObject : parsedData) {
            // Extract q and r values
            int q = jsonObject.getInt("q");
            int r = jsonObject.getInt("r");
            String tileType = jsonObject.getString("tileType");
            int power = jsonObject.getInt("power");
            for (Tile tile:tiles) {
            	if(tile.getQ()==q && tile.getR()==r) {
            		tile.setType(Util.getTileTypeFromString(tileType));
            		tile.setPoints(power);
            	}
            }
    	}
    	
    }
    
    public void rotate(int degree) {
    	findAxisTile();
        List<int[]> originalCoordinates = new ArrayList<>();
        for (Tile tile : tiles) {
            originalCoordinates.add(new int[]{tile.getX(), tile.getY(),tile.getRow(),tile.getCol()});
        }

        // Rotate each tile and find the closest original coordinate
        for (Tile tile : tiles) {
        	if(degree==0) {
        		break;
        	}
        	else {
        	int[] newCoordinate=tile.rotate(axisTile.getX(), axisTile.getY(), degree);
            int[] closestCoordinate = findClosestCoordinate(originalCoordinates, newCoordinate[0], newCoordinate[1]);
            if (closestCoordinate != null) {
                // Update tile's position to the closest original coordinate
                tile.setRow(closestCoordinate[2]);
                tile.setCol(closestCoordinate[3]);
            }}
        }
        
    }
    private int[] findClosestCoordinate(List<int[]> coordinates, int x, int y) {
        double minDistance = Double.MAX_VALUE;
        int[] closestCoordinate = null;
        for (int[] coordinate : coordinates) {
            double distance = Math.sqrt(Math.pow(coordinate[0] - x, 2) + Math.pow(coordinate[1] - y, 2));
            if (distance < minDistance) {
                minDistance = distance;
                closestCoordinate = coordinate;
            }
        }
        return closestCoordinate;
    }
    private void findAxisTile() {
    	for(Tile tile:tiles) {
    		if(tile.getQ()==0 && tile.getR()==0) {
    			this.axisTile=tile;
    		}
    	}
    }
    
    @Override
    public void draw(Graphics2D g2d, int hexSize) {
        for (Tile tile : tiles) {
        	Color color=tile.getColor();
            Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 150); 
            tile.drawTile(g2d, hexSize,transparentColor,Color.BLACK);
        }
    }

  


    @Override
    public Terrain clone(double addRow, double addCol,int hexSize) {
    	Terrain clonedTerrain = new Terrain();
        int addX = (int)(addCol * 1.5 * hexSize);
        int addY = (int)(addRow *  Math.sqrt(3) * hexSize);
        if (addCol % 2 == 1) {
        	addY += (int) (Math.sqrt(3) / 2 * hexSize);
        }
        
        for (Tile tile : tiles) {
      	int newX = tile.getX()+addX;
      	int newY = tile.getY()+addY;
      	int[] closestCoordinate = TileDataDic.findClosestCoordinate(newX, newY);
      	
        Tile clonedTile = tile.clone();
          if (closestCoordinate != null) {
              
      	clonedTile.setRow(closestCoordinate[0]);
      	clonedTile.setCol(closestCoordinate[1]);
      	clonedTile.setX(closestCoordinate[2]);
      	clonedTile.setY(closestCoordinate[3]);
      	clonedTile.setType(tile.getType());
          clonedTile.setPoints(tile.getPoints());
          clonedTile.setQ(tile.getQ());
          clonedTile.setR(tile.getR());
          clonedTerrain.addTile(clonedTile);
          }
      }
      return clonedTerrain;
    }

}
