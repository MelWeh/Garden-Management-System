/* Author: Melissa Weitekamp
 * Date: 12/7/2025
 * Purpose: Manages each garden cell
 */

package application;

public class GardenCell {
	private int row;
	private int col;
	private Vegetable vegetable;
	
	public GardenCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	// Getters
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public Vegetable getVegetable() {
		return vegetable;
	}
	
	// Setters
	public void setVegetable(Vegetable vegetable) {
		this.vegetable = vegetable;
	}
	
	public void clear() {
		this.vegetable = null;
	}
	
	public boolean hasVegetable() {
		return vegetable != null;
	}
	
	@Override
	public String toString() {
		return hasVegetable() ? vegetable.getName() : "Empty";
	}
}
