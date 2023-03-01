package nicki0.editor.elements;

public class Ti {
	private String unitOxygenLevel;
	private String unitHeatLevel;
	private String unitPressureLevel;
	private String unitPlantsLevel;
	private String unitInsectsLevel;
	private String unitAnimalsLevel;
	
	private static double ppqTo1 = Math.pow(1000, -5); // ppq to 1 (or 100%)
	private static double pKToK = Math.pow(1000, -4); // pK to K
	private static double nPaToPa = Math.pow(1000, -3); // nPa to Pa
	private static double gToGt = Math.pow(1000, -5); // g to Gt
	
	private static double oxygenLevelEarth = 1d;//1=100%  // = 0.20946d; // = 20.946%, source: https://en.wikipedia.org/wiki/Atmosphere_of_Earth
	private static double heatLevelEarth = 273.15d; // 273.15 K = 0°C
	private static double pressureLevelEarth = 101300; // = 1013 hPa
	private static double plantsLevelEarth = 550d / 0.3d; // 550 Gt dry mass (~30%), source: https://en.wikipedia.org/wiki/Biomass_(ecology)
	private static double insectsLevelEarth = 0.2 / 0.3; // 0.2 Gt, source: https://www.visualcapitalist.com/all-the-biomass-of-earth-in-one-graphic/
	private static double animalsLevelEarth = 2.389 / 0.3d; // 2.589 - 0.2(insect), source: https://www.visualcapitalist.com/all-the-biomass-of-earth-in-one-graphic/
	
	private boolean emptyLine = false;
	public Ti() {
		emptyLine = true;
	}
	
	public Ti(String pUOL, String pUHL, String pUPrL, String pUPlL, String pUIL, String pUAL) {
		unitOxygenLevel = pUOL;
		unitHeatLevel = pUHL;
		unitPressureLevel = pUPrL;
		unitPlantsLevel = pUPlL;
		unitInsectsLevel = pUIL;
		unitAnimalsLevel = pUAL;
	}
	@Override
	public String toString() {
		return emptyLine?"":"{\"unitOxygenLevel\":" + unitOxygenLevel + ",\"unitHeatLevel\":" + unitHeatLevel + ",\"unitPressureLevel\":" + unitPressureLevel + ",\"unitPlantsLevel\":" + unitPlantsLevel + ",\"unitInsectsLevel\":" + unitInsectsLevel + ",\"unitAnimalsLevel\":" + unitAnimalsLevel + "}";
	}
	public String comparedToEarth() {
		return emptyLine?"Terraforming progress missing in save file":
				  "Oxygen: " + String.format("%.2f", Double.parseDouble(unitOxygenLevel) * ppqTo1 / oxygenLevelEarth * 100) + "%\n"
				+ "Heat: " + String.format("%.1f", (Double.parseDouble(unitHeatLevel)) * pKToK - heatLevelEarth) + "°C\n"
				+ "Pressure: " + String.format("%.2f", Double.parseDouble(unitPressureLevel) * nPaToPa / pressureLevelEarth) + " Bar\n"
				+ "Plant: " + String.format("%.4f", Double.parseDouble(unitPlantsLevel) * gToGt / plantsLevelEarth * 100) + "%\n"
				+ "Insect: " + String.format("%.2f", Double.parseDouble(unitInsectsLevel) * gToGt / insectsLevelEarth * 100) + "%\n"
				+ "Animal: " + String.format("%.4f", Double.parseDouble(unitAnimalsLevel) * gToGt / animalsLevelEarth * 100) + "%";
	}
	public double getTI() {return emptyLine?0:Double.parseDouble(unitOxygenLevel) + Double.parseDouble(unitHeatLevel) + Double.parseDouble(unitPressureLevel) + Double.parseDouble(unitPlantsLevel) + Double.parseDouble(unitInsectsLevel) + Double.parseDouble(unitAnimalsLevel);} 
}
