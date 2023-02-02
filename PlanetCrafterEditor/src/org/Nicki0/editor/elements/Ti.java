package org.Nicki0.editor.elements;

public class Ti {
	private String unitOxygenLevel;
	private String unitHeatLevel;
	private String unitPressureLevel;
	private String unitPlantsLevel;
	private String unitInsectsLevel;
	private String unitAnimalsLevel;
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
		return "{\"unitOxygenLevel\":" + unitOxygenLevel + ",\"unitHeatLevel\":" + unitHeatLevel + ",\"unitPressureLevel\":" + unitPressureLevel + ",\"unitPlantsLevel\":" + unitPlantsLevel + ",\"unitInsectsLevel\":" + unitInsectsLevel + ",\"unitAnimalsLevel\":" + unitAnimalsLevel + "}";
	}
	public String comparedToEarth() {
		/*{"unitOxygenLevel":7929625246171136.0,
		 * "unitHeatLevel":683114347102208.0,
		 * "unitPressureLevel":284248518950912.0,
		 * "unitPlantsLevel":51377766137856.0,
		 * "unitInsectsLevel":51478030605877250.0,
		 * "unitAnimalsLevel":0.0}*/
		//String.format( "%.2f", dub )
		return    "Oxygen: " + String.format("%.2f", Double.parseDouble(unitOxygenLevel) / 10000000000000d) + "%\n"
				+ "Heat: " + String.format("%.1f", (Double.parseDouble(unitHeatLevel) - 273150000000000d) / 1000000000000d) + "°C\n"
				+ "Pressure: " + String.format("%.2f", Double.parseDouble(unitPressureLevel) / 101300000000000d) + " Bar\n"
				+ "Plant: " + String.format("%.4f", Double.parseDouble(unitPlantsLevel) / 4500000000000000d) + "%\n"
				+ "Insect: " + String.format("%.2f", Double.parseDouble(unitInsectsLevel) / 20000000000000d) + "%\n"
				+ "Animal: " + unitAnimalsLevel + "g No Value to compare";
	}
}
