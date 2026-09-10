/* Author: Melissa Weitekamp
 * Date: 12/7/2025
 * Purpose: Manages grid layout of the garden cells
 */

package application;

public class GardenGrid {
	private int rows;
    private int cols;
    private GardenCell[][] grid;
    
    public GardenGrid(int rows, int cols) {
    	this.rows = rows;
    	this.cols = cols;
    	grid = new GardenCell[rows][cols];
    	
    	for (int i = 0; i < rows; i++) {
    		for (int j = 0; j < cols; j++) {
    			grid[i][j] = new GardenCell(i, j);
    		}
    	}
    }
    
    // Getters
    public int getRows() {
    	return rows;
    }
    public int getCols() {
    	return cols;
    }
    
    public GardenCell getCell(int row, int col) {
    	if (row >= 0 && row < rows && col >= 0 && col < cols) {
    		return grid[row][col];
    	}
    	return null;
    }
    
    // Plants selected vegetable at specified cell
    public boolean plantVegetable(int row, int col, Vegetable vegetable) {
    	GardenCell cell = getCell(row, col);
    	if (cell != null && !cell.hasVegetable()) {
    		cell.setVegetable(vegetable);
    		return true;
    	}
    	return false;
    }
    
    // Removes vegetable from the cell
    public boolean removeVegetable(int row, int col) {
    	GardenCell cell = getCell(row, col);
    	if (cell != null && cell.hasVegetable()) {
    		cell.clear();
    		return true;
    	}
    	return false;
    }
	
    // Recursive method to count all vegetables in the grid
	public int countVegetablesRecursive(int row, int col, int count) {
		// Base case
        if (row >= this.rows) {
        	return count;
        }
        
		// Increase count
        GardenCell cell = getCell(row, col);
        if (cell != null && cell.hasVegetable()) {
            count++;
        }
        
        // Move to next cell
        if (col + 1 < cols) {
            return countVegetablesRecursive(row, col + 1, count);
        } else {
            return countVegetablesRecursive(row + 1, 0, count);
        }
    }
	
	// Calls recursive method to get vegetable count
	public int getTotalVegetables() {
		return countVegetablesRecursive(0, 0, 0);
	}
}
