package tides;

import java.util.*;

/**
 * This class contains methods that provide information about select terrains 
 * using 2D arrays. Uses floodfill to flood given maps and uses that 
 * information to understand the potential impacts. 
 * Instance Variables:
 *  - a double array for all the heights for each cell
 *  - a GridLocation array for the sources of water on empty terrain 
 * 
 * @author Original Creator Keith Scharz (NIFTY STANFORD) 
 * @author Vian Miranda (Rutgers University)
 * 
 * 
 * @name  Steven John
 * @date  12/13/24
 * @class PD:1
 * 
 */
public class RisingTides {

    // Instance variables
    private double[][] terrain;     // an array for all the heights for each cell
    private GridLocation[] sources; // an array for the sources of water on empty terrain 

    /**
     * DO NOT EDIT!
     * Constructor for RisingTides.
     * @param terrain passes in the selected terrain 
     */
    public RisingTides(Terrain terrain) {
        this.terrain = terrain.heights;
        this.sources = terrain.sources;
    }

    /** 5 points
     * 
     * Find the lowest and highest point of the terrain and output it.
     * 
     * @return double[], with index 0 and index 1 being the lowest and 
     * highest points of the terrain, respectively
     */
    public double[] elevationExtrema() {
    	double[] extrema = {-103.0, 1173.0};
		for(int i = 0; i < terrain.length; i++) {
			for(int j = 0; j < terrain[i].length; j++) {
				if(terrain[i][j] > extrema[1]) {
					extrema[1] = terrain[i][j];
				}else if(terrain[i][j] < extrema[0]) {
					extrema[0] = terrain[i][j];
				}
			}
		}
		return extrema;
    }

    /** 10 points
     * 
     * Implement the floodfill algorithm using the provided terrain and sources.
     * 
     * All water originates from the source GridLocation. If the height of the 
     * water is greater than that of the neighboring terrain, flood the cells. 
     * Repeat iteratively till the neighboring terrain is higher than the water 
     * height.
     * 
     * 
     * @param height of the water
     * @return boolean[][], where flooded cells are true, otherwise false
     */
    public boolean[][] floodedRegionsIn(double height) {
        boolean[][] result = new boolean[terrain.length][terrain[0].length];
        ArrayList<GridLocation> waterSource = new ArrayList<>();

        for (int i = 0; i < sources.length; i++) {
            waterSource.add(sources[i]);
        }
        while (!waterSource.isEmpty()) {

            int row = waterSource.get(0).row;
            int col = waterSource.get(0).col;

            result[row][col] = true;
            if (row > 0 && !result[row - 1][col] && terrain[row - 1][col] <= height) {
                waterSource.add(new GridLocation(row - 1, col));
                result[row - 1][col] = true;
            }
            if (row < terrain.length - 1 && terrain[row + 1][col] <= height && !result[row + 1][col]) {
                result[row + 1][col] = true;
                waterSource.add(new GridLocation(row + 1, col));
            }
            if (col > 0 && terrain[row][col - 1] <= height && !result[row][col - 1]) {
                waterSource.add(new GridLocation(row, col - 1));
                result[row][col - 1] = true;
            }
            if (col < terrain[0].length - 1 && !result[row][col + 1] && terrain[row][col + 1] <= height) {
                result[row][col + 1] = true;
                waterSource.add(new GridLocation(row, col + 1));
            }
            waterSource.remove(0);
        }

        return result;
    }


    /** 5 points
     * 
     * Checks if a given cell is flooded at a certain water height.
     * 
     * @param height of the water
     * @param cell location 
     * @return boolean, true if cell is flooded, otherwise false
     */
    public boolean isFlooded(double height, GridLocation cell) {
    	boolean[][] result = floodedRegionsIn(height);
        if (result[cell.row][cell.col]) {
        	return true;
        }
        else {
        	return false;
        }
    }

    /** 5 points
     * 
     * Given the water height and a GridLocation find the difference between 
     * the chosen cells height and the water height.
     * 
     * If the return value is negative, the Driver will display "meters below"
     * If the return value is positive, the Driver will display "meters above"
     * The value displayed will be positive.
     * 
     * @param height of the water
     * @param cell location
     * @return double, representing how high/deep a cell is above/below water
     */
    public double heightAboveWater(double height, GridLocation cell) {
    	return (terrain[cell.row][cell.col] - height);
    }

    /** 5 points
     * 
     * Total land available (not underwater) given a certain water height.
     * 
     * @param height of the water
     * @return int, representing every cell above water
     */
    public int totalVisibleLand(double height) {
    	int visibleLandCount = 0;
    	boolean[][] flooded = floodedRegionsIn(height);
    	for (int row = 0; row < flooded.length; row++) {
    		for (int col = 0; col < flooded[0].length; col++) {
    			if (!flooded[row][col]) {
    				visibleLandCount++;
    			}
    		}
    	}
    	
    	return visibleLandCount;
    	}


    /** 5 points
     * 
     * Given 2 heights, find the difference in land available at each height. 
     * 
     * If the return value is negative, the Driver will display "Will gain"
     * If the return value is positive, the Driver will display "Will lose"
     * The value displayed will be positive.
     * 
     * @param height of the water
     * @param newHeight the future height of the water
     * @return int, representing the amount of land lost or gained
     */
    public int landLost(double height, double newHeight) {
    	return (totalVisibleLand(newHeight) - totalVisibleLand(height));
    }

    /** 10 points
     * 
     * Count the total number of islands on the flooded terrain.
     * 
     * Parts of the terrain are considered "islands" if they are completely 
     * surround by water in all 8-directions. Should there be a direction (ie. 
     * left corner) where a certain piece of land is connected to another 
     * landmass, this should be considered as one island. A better example 
     * would be if there were two landmasses connected by one cell. Although 
     * seemingly two islands, after further inspection it should be realized 
     * this is one single island. Only if this connection were to be removed 
     * (height of water increased) should these two landmasses be considered 
     * two separate islands.
     * 
     * @param height of the water
     * @return int, representing the total number of islands
     */
    public int numOfIslands(double height) {
        boolean[][] result = floodedRegionsIn(height);
        WeightedQuickUnionUF land = new WeightedQuickUnionUF(terrain.length, terrain[0].length);

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[0].length; col++) {
                if (!result[row][col]) { 
                    if (row > 0 && !result[row - 1][col]) {
                        land.union(new GridLocation(row, col), new GridLocation(row - 1, col));
                    }
                    if (row < result.length - 1 && !result[row + 1][col]) {
                        land.union(new GridLocation(row, col), new GridLocation(row + 1, col));
                    }
                    if (col > 0 && !result[row][col - 1]) {
                        land.union(new GridLocation(row, col), new GridLocation(row, col - 1));
                    }
                    if (col < result[0].length - 1 && !result[row][col + 1]) {
                        land.union(new GridLocation(row, col), new GridLocation(row, col + 1));
                    }
                    if (row > 0 && col > 0 && !result[row - 1][col - 1]) {
                        land.union(new GridLocation(row, col), new GridLocation(row - 1, col - 1));
                    }
                    if (row < result.length - 1 && col > 0 && !result[row + 1][col - 1]) {
                        land.union(new GridLocation(row, col), new GridLocation(row + 1, col - 1));
                    }
                    if (row > 0 && col < result[0].length - 1 && !result[row - 1][col + 1]) {
                        land.union(new GridLocation(row, col), new GridLocation(row - 1, col + 1));
                    }
                    if (row < result.length - 1 && col < result[0].length - 1 && !result[row + 1][col + 1]) {
                        land.union(new GridLocation(row, col), new GridLocation(row + 1, col + 1));
                    }
                }
            }
        }

        Map<GridLocation, GridLocation> rootMap = new HashMap<>();
        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[0].length; col++) {
                if (!result[row][col]) {
                    GridLocation root = land.find(new GridLocation(row, col));
                    rootMap.put(root, root);
                }
            }
        }
        return rootMap.size();
    }
}
