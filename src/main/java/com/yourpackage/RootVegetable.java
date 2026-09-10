/* Author: Melissa Weitekamp
 * Date: 12/7/2025
 * Purpose: Subclass of Vegetable
 */

package application;

public class RootVegetable extends Vegetable{
	private int depthRequired;
	private boolean canIntercrop;
	
	// Explicit constructor
	public RootVegetable(String name, String sowingDate, String weekMaturity, 
			String plantDepth, String plantSpacing, String rowSpacing, int depthRequired,
			boolean canIntercrop) {
		super(name, sowingDate, weekMaturity, plantDepth, plantSpacing, rowSpacing);
		this.depthRequired = depthRequired;
		this.canIntercrop = canIntercrop;
	}
	
	// Method overriding
	@Override
	public String getType() {
		return "Root Vegetable";
	}
	
	// Interface
	@Override
	public String getPlantingInstructions() {
		return super.getPlantingInstructions() + String.format(
				"\n- Depth required: %d inches\n" +
				"- Can be intercropped: %s\n" +
				"- Harvest: Loosen soil around and pull gently",
				depthRequired, canIntercrop ? "Yes" : "No");
	}
	
	public int getDepthRequired() {
		return depthRequired;
	}
	
	public boolean canBeIntercrop() {
		return canIntercrop;
	}
}
