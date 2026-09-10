/* Author: Melissa Weitekamp
 * Date: 12/7/2025
 * Purpose: Abstract class. Implements Plantable interface
 */

package application;

public abstract class Vegetable implements Plantable {
	protected String name;
	protected String sowingDate;
	protected String weeksToMaturity;
	protected String plantingDepth;
	protected String plantSpacing;
	protected String rowSpacing;
	
	public Vegetable(String name, String sowingDate, String weekMaturity, 
			String plantDepth, String plantSpacing, String rowSpacing) {
		this.name = name;
		this.sowingDate = sowingDate;
		this.weeksToMaturity = weekMaturity;
		this.plantingDepth = plantDepth;
		this.plantSpacing = plantSpacing;
		this.rowSpacing = rowSpacing;
	}
	
	// Getters
	public String getName() {
		return name;
	}
	public String getSowingDate() {
		return sowingDate;
	}
	public String getWeeksToMaturity() {
		return weeksToMaturity;
	}
	public String getPlantingDepth() {
		return plantingDepth;
	}
	public String getPlantSpacing() {
		return plantSpacing;
	}
	public String getRowSpacing() {
		return rowSpacing;
	}
	
	// Abstract method
	protected abstract String getType();
	
	@Override
	public String plant() {
		return String.format("Planting %s (%s)", name, getType());
	}
	
	// Returns planting instructions for vegetables
	@Override
	public String getPlantingInstructions() {
		return String.format("%s Planting Instructions:\n" +
				"- Type: %s\n" +
				"- Best sown: %s\n" +
				"- Matures in: %s weeks\n" +
				"- Planting depth: %s\n" +
				"- Plant spacing: %s\n" +
				"- Row spacing: %s",
				name, getType(), sowingDate, weeksToMaturity,
				plantingDepth, plantSpacing, rowSpacing);
	}
	
	@Override
	public String toString() {
		return name + " (" + getType() + ")";
	}
}

