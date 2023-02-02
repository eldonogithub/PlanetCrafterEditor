package org.Nicki0.editor.elements;

public class Item {
	private long id;
	private String gId;
	private long liId;
	private String liGrps; // ????
	private float posX;
	private float posY;
	private float posZ;
	private float rot1;
	private float rot2;
	private float rot3;
	private float rot4;
	private String wear; // ???
	private int[] plns; // fenster und böden (panels)
	private String color;
	private String text;
	private int grwth;
	
	public Item(String pItemType) {// TODO Put into Inventory
		id = 0; // TODO check for new id
		gId = pItemType;
		liId = 0; 
		liGrps = "";
		posX = 0;
		posY = 0;
		posZ = 0;
		rot1 = 0;
		rot2 = 0;
		rot3 = 0;
		rot4 = 0;
		wear = "0";
		plns = new int[0];
		color = "";
		text = "";
		grwth = 0;
	}
	public Item(long pId, String pGId, long pLiId, String pLiGrps, float pPosX, float pPosY, float pPosZ, float pRot1, float pRot2, float pRot3, float pRot4, String pWear, int[] pPlns, String pColor, String pText, int pGrwth) {
		id = pId;
		gId = pGId;
		liId = pLiId;
		liGrps = pLiGrps;
		posX = pPosX;
		posY = pPosY;
		posZ = pPosZ;
		rot1 = pRot1;
		rot2 = pRot2;
		rot3 = pRot3;
		rot4 = pRot4;
		wear = pWear;
		plns = pPlns;
		color = pColor;
		text = pText;
		grwth = pGrwth;
	}
	@Override
	public String toString() {
		String plnsStr = "";
		for (int i = 0; i < plns.length; i++) plnsStr = plnsStr + plns[i] + ((i < plns.length-1)?",":"");
		return "{\"id\":" + id +
				",\"gId\":\"" + gId +
				"\",\"liId\":" + liId +
				",\"liGrps\":\"" + liGrps +
				"\",\"pos\":\"" + formatFloat(posX) + "," + formatFloat(posY) + "," + formatFloat(posZ) +
				"\",\"rot\":\"" + formatFloat(rot1) + "," + formatFloat(rot2) + "," + formatFloat(rot3) + "," + formatFloat(rot4) +
				"\",\"wear\":" + wear +
				",\"pnls\":\"" + plnsStr +
				"\",\"color\":\"" + color +
				"\",\"text\":\"" + text +
				"\",\"grwth\":" + grwth + "}";
	}
	private String formatFloat(float f) {
		if (f == (long)f) return (long)f + "";
		return f + "";
	}
	public boolean isOnMap() {
		return !(posX == 0 && posY == 0 && posZ == 0 && rot1 == 0 && rot2 == 0 && rot3 == 0 && rot4 == 0);
	}
	public long getId() {return id;}
	public String getGId() {return gId;}
	public long getLiId() {return liId;}
	public float posX() {return posX;}
	public float poxY() {return posY;}
	public float posZ() {return posZ;}
	public String getText() {return text;}
}
