/* Author: Melissa Weitekamp
 * Date: 12/7/2025
 * Purpose: Handles and parses through specified file. Saves and writes result to a file
 */

package application;
import java.util.*;
import java.io.*;

public class FileHandler {
	public List<Vegetable> loadVegetableFile(String filename) throws IOException {
		List<Vegetable> vegetables = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			reader.readLine(); // Read header of file
			String line;
			
			while ((line = reader.readLine()) != null) {
               line = line.trim();
               if (line.isEmpty()) continue;
               
               // Split by comma
               String[] parts = line.split(",\\s");
               
               if (parts.length >= 7) {
            	   String name = parts[0].trim();
            	   String type = parts[1].trim();
            	   String sowingDate = parts[2].trim();
            	   String weeksToMaturity = parts[3].trim();
            	   String plantingDepth = parts[4].trim();
            	   String plantSpacing = parts[5].trim();
            	   String rowSpacing = parts[6].trim();
            	   
            	   Vegetable vegetable = createVegByType(name, type, sowingDate, weeksToMaturity,
            			   plantingDepth, plantSpacing, rowSpacing);
            	   
            	   if (vegetable != null) {
            		   vegetables.add(vegetable);
            	   }
               }
			}
		} catch (FileNotFoundException e) {
			throw new IOException("Vegetable file not found: " + filename);
		} catch (IOException e) {
			throw new IOException("Error reading vegetable file: " + e.getMessage());
		}
		
		return vegetables;
	}
	
	// Initializes vegetable types
	private Vegetable createVegByType(String name, String type, String sowingDate, String weeksToMaturity, String plantingDepth, 
			String plantSpacing, String rowSpacing) {
		
		switch (type.toLowerCase()) {
			case "root":
				boolean canIntercrop = canRootIntercrop(name);
				return new RootVegetable(name, sowingDate, weeksToMaturity, plantingDepth, plantSpacing, rowSpacing,
						12, canIntercrop);
				
			case "fruit":
				boolean needSupport = fruitNeedSupport(name);
				return new FruitVegetable(name, sowingDate, weeksToMaturity, plantingDepth, plantSpacing, rowSpacing,
						needSupport, "Full Sun");
				
			default:
				// Anonymous class for abstract Vegetable class
				return new Vegetable(name, sowingDate, weeksToMaturity, plantingDepth, plantSpacing, rowSpacing) {
					@Override
					protected String getType() {
						return type.substring(0, 1).toUpperCase() + type.substring(1) + " Vegetable";
					}
					
					@Override
					public String getPlantingInstructions() {
						return super.getPlantingInstructions();
					}
				};
		}
	}
	
	private boolean canRootIntercrop(String name) {
		String lowerName = name.toLowerCase();
		
		// Carrots, radishes, and onions are good for intercropping
		return lowerName.contains("carrot") || lowerName.contains("radish") ||
				lowerName.contains("onion");
	}
	
	private boolean fruitNeedSupport(String name) {
		String lowerName = name.toLowerCase();
		
		// Tomatoes, cucumbers, peppers, and eggplants need support
		return lowerName.contains("tomato") || lowerName.contains("pepper") ||
				lowerName.contains("eggplant") || lowerName.contains("cucumber");
	}
	
	// Writes data from Garden grid into file of each vegetables placement
	public void saveGarden(GardenGrid grid, String filename) throws IOException {
		try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
			writer.println("Row, Col, Vegetable");
			
			for (int i = 0; i < grid.getRows(); i++) {
				for (int j = 0; j < grid.getCols(); j++) {
					GardenCell cell = grid.getCell(i, j);
					if (cell.hasVegetable()) {
						writer.println(i + ", " + j + ", " + cell.getVegetable().getName());
					}
				}
			}
		}
	}
}
