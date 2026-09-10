/* Author: Melissa Weitekamp
 * Date: 12/7/2025
 * Purpose: Subclass of Vegetable class
 */

package application;

public class FruitVegetable extends Vegetable{
	private boolean needSupport;
	private String sunReq;
	
	public FruitVegetable(String name, String sowingDate, String weekMaturity, 
			String plantDepth, String plantSpacing, String rowSpacing, boolean needSupport, String sunReq) {
		super(name, sowingDate, weekMaturity, plantDepth, plantSpacing, rowSpacing);
		this.needSupport = needSupport;
		this.sunReq = sunReq;
	}
	
	// Vegetable class method overwritten
	@Override
	public String getType() {
		return "Fruit Vegetable";
	}
	
	// Interface
	@Override
	public String getPlantingInstructions() {
		return super.getPlantingInstructions() + String.format(
				"\n- Needs support/trellis: %s\n" +
				"- Sun requirement: %s\n" +
				"- Harvest: When mature but tender. Pick by hand or tool.",
				needSupport ? "Yes" : "No", sunReq);
	}
	
	public boolean needSupport() {
		return needSupport;
	}
	
	public String getSunReq() {
		return sunReq;
	}
}
