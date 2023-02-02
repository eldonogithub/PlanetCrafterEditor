package org.Nicki0.editor.elements;

import java.util.List;

public class PlayerAttributes {
	private float posX;
	private float posY;
	private float posZ;
	private float rot1;
	private float rot2;
	private float rot3;
	private float rot4;
	private List<String> unlockedGroups;
	private double playerGaugeOxygen;
	private double playerGaugeThirst;
	private double playerGaugeHealth;
	
	public PlayerAttributes(float pPosX, float pPosY, float pPosZ, float pRot1, float pRot2, float pRot3, float pRot4, List<String> pUnlockedGroups, 
			double pPlayerGaugeOxygen, double pPlayerGaugeThirst, double pPlayerGaugeHealth) {
		posX = pPosX;
		posY = pPosY;
		posZ = pPosZ;
		rot1 = pRot1;
		rot2 = pRot2;
		rot3 = pRot3;
		rot4 = pRot4;
		unlockedGroups = pUnlockedGroups;
		playerGaugeOxygen = pPlayerGaugeOxygen;
		playerGaugeThirst = pPlayerGaugeThirst;
		playerGaugeHealth = pPlayerGaugeHealth;
	}
	@Override
	public String toString() {
		String unlockedGroupString = "";
		for (int i = 0; i < unlockedGroups.size(); i++) {
			unlockedGroupString = unlockedGroupString + unlockedGroups.get(i) + ((i < unlockedGroups.size()-1)?",":"");
		}
		return "{\"playerPosition\":\"" + formatFloat(posX)
				+ "," + formatFloat(posY)
				+ "," + formatFloat(posZ)
				+ "\",\"playerRotation\":\"" + formatFloat(rot1)
				+ "," + formatFloat(rot2)
				+ "," + formatFloat(rot3)
				+ "," + formatFloat(rot4)
				+ "\",\"unlockedGroups\":\"" + unlockedGroupString
				+ "\",\"playerGaugeOxygen\":" + playerGaugeOxygen
				+ ",\"playerGaugeThirst\":" + playerGaugeThirst
				+ ",\"playerGaugeHealth\":" + playerGaugeHealth
				+ "}";
	}
	private String formatFloat(float f) {
		if (f == (long)f) return (long)f + "";
		return f + "";
	}
}
