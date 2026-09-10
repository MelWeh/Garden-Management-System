/* Author: Melissa Weitekamp
 * Date: 12/7/2025
 * Purpose: Class that manages garden operations
 */

package application;
import java.io.IOException;
import java.util.*;

public class GardenManager {
	private GardenGrid grid;
	private List<Vegetable> vegetables;
	private Stack<UndoAction> undoStack;
	private Queue<String> actionQueue;
	private FileHandler fileHandler;
	
	public GardenManager() {
		vegetables = new ArrayList<>();
		undoStack = new Stack<>();
		actionQueue = new LinkedList<>();
		fileHandler = new FileHandler();
	}
	
	// Inner class for undo action: uses Stack ADT to track changes
	private static class UndoAction {
		String type; // PLANT or REMOVE
		int row, col;
		Vegetable vegetable;
		
		UndoAction(String type, int row, int col, Vegetable vegetable) {
			this.type = type;
			this.row = row;
			this.col = col;
			this.vegetable = vegetable;
		}
	}
	
	// Getters
	public GardenGrid getGrid() {
		return grid;
	}
	public List<Vegetable> getVegetables() {
		return vegetables;
	}
	
	public void createGarden(int rows, int cols) {
		this.grid = new GardenGrid(rows, cols);
		addToActionQueue("Created garden: " + rows + "x" + cols);
	}
	
	public boolean plant(int row, int col, Vegetable veg) {
		if (grid.plantVegetable(row, col, veg)) {
			undoStack.push(new UndoAction("REMOVE", row, col, veg));
			addToActionQueue("Planted " + veg.getName() + " at (" + row + "," + col + ")");
			return true;
		}
		return false;
	}
	
	public boolean remove(int row, int col) {
		GardenCell cell = grid.getCell(row, col);
		if (cell.hasVegetable()) {
			Vegetable veg = cell.getVegetable();
			if (grid.removeVegetable(row, col)) {
				undoStack.push(new UndoAction("PLANT", row, col, veg));
				addToActionQueue("Removed " + veg.getName() + " from (" + row + "," + col + ")");
				return true;
			}
		}
		return false;
	}
	
	public void undo() {
		if (!undoStack.isEmpty()) {
			UndoAction action = undoStack.pop();
			
			if ("REMOVE".equals(action.type)) {
				// Remove the vegetable
				grid.removeVegetable(action.row, action.col);
				addToActionQueue("UNDO: Removed " + action.vegetable.getName());
				
			} else if ("PLANT".equals(action.type)) {
				// Replant the vegetable
				grid.plantVegetable(action.row, action.col, action.vegetable);
				addToActionQueue("UNDO: Replanted " + action.vegetable.getName());
			}
		}
	}
	
	// Queue operation: adds action to history queue
	private void addToActionQueue(String action) {
		actionQueue.offer(action);
		if (actionQueue.size() > 20) {
			actionQueue.poll();
		}
	}
	
	// Action history
	public String getActionHistory() {
		StringBuilder sb = new StringBuilder("=== Action History ===\n");
		for (String action : actionQueue) {
			sb.append("* ").append(action).append("\n");
		}
		return sb.toString();
	}
	
	// Returns count of vegetables in each grid
	public int getTotalVegetables() {
		if (grid != null) {
			return grid.getTotalVegetables();
		}
		return 0;
	}
	
	// Loads vegetable file
	public void loadVegetableFile(String filename) throws IOException {
		vegetables = fileHandler.loadVegetableFile(filename);
		addToActionQueue("Loaded " + vegetables.size() + " vegetables from file");
	}
	
	// Save garden file
	public void saveGarden(String filename) throws IOException {
		fileHandler.saveGarden(grid, filename);
        addToActionQueue("Saved garden to " + filename);
	}
}
