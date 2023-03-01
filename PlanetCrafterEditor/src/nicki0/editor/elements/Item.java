package nicki0.editor.elements;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Item {
	private long id;
	private String gId;
	private long liId;
	private String liGrps; // Thing mined by ore extractor or created by or autocrafters incubators 
	private float posX;
	private float posY;
	private float posZ;
	private float rot1;
	private float rot2;
	private float rot3;
	private float rot4;
	private long wear; // ???
	private int[] plns; // fenster und böden (panels)
	private String color;
	private String text;
	private int grwth;
	
	private boolean real = true;
	
	private int pictureNumber = 1;
	private List<BufferedImage> imageList;
	private double widthScale; // size in z direction
	private boolean scalePicture = true;
	
	/**
	 * Creates Item that is placed by the game. Do not use for items in save file!!!
	 * @param pId
	 * @param pGId
	 * @param pLiId
	 * @param pPosX
	 * @param pPosY
	 * @param pPosZ
	 * @param pComment
	 */
	public Item(long pId, String pGId, long pLiId, float pPosX, float pPosY, float pPosZ, String pComment) {
		id = pId;
		gId = pGId + Object.LOOTCRATEGID;
		liId = pLiId;
		posX = pPosX;
		posY = pPosY;
		posZ = pPosZ;
		text = pComment;
		
		// initialize remaining variables:
		liGrps = "";
		rot1 = 0;
		rot2 = 0;
		rot3 = 0;
		rot4 = 0;
		wear = 0;
		plns = new int[0];
		color = "";
		text = "";
		grwth = 0;
		
		
		real = false;
		
		widthScale = 100; // if scalePicture == false => widthScale is coordinate-width in minimal scaled map
		scalePicture = false;
		
		commonInit();
	}
	public Item(long pId, String pGId, long pLiId, String pLiGrps, float pPosX, float pPosY, float pPosZ, float pRot1, float pRot2, float pRot3, float pRot4, long pWear, int[] pPlns, String pColor, String pText, int pGrwth) {
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
		
		widthScale = Object.getCoordinateWidthOfGId(gId); // TODO list with all widthScale for pictures where width and height are in coordinate size. make picture right size regarding width
		scalePicture = true;
		
		commonInit();
	}
	
	private void commonInit() {
		pictureNumber = Object.getNumberOfPicture(gId);
		if (pictureNumber == 1) {
			imageList = new ArrayList<BufferedImage>();
			imageList.add(Object.getItemPicture(gId));
		} else {
			imageList = Object.getItemPictureList(gId);
			assert imageList.size() == pictureNumber : "Missing Texture for pictureNumber > 1";
		}
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
		String s = f + "";
		if (s.contains("E-")) {
			String[] sa = s.split("E-");
			if (sa[1].length() == 1) {
				s = s.replaceAll("E-", "E-0");
			}
		}
		return s;
	}
	public boolean isOnMap() {
		return !(posX == 0 && posY == 0 && posZ == 0 && rot1 == 0 && rot2 == 0 && rot3 == 0 && rot4 == 0) && !(posX == -500 && posY == -500 && posZ == -500 && rot1 == 0 && rot2 == 0 && rot3 == 0 && rot4 == 1);
	}
	public long getId() {return id;}
	public String getGId() {return gId;}
	public long getLiId() {return liId;}
	public float posX() {return posX;}
	public float posY() {return posY;}
	public float posZ() {return posZ;}
	public float getX() {return posX;}
	public float getY() {return posY;}
	public float getZ() {return posZ;}
	public String getText() {return text;}
	public String getPositionAsString() {return "(" + posX() + " : " + posY() + " : " + posZ() + ")";}
	public BufferedImage getPicture(long pId) {return imageList.get((int)(pId % pictureNumber));}
	public double getWidthScale() {return widthScale;}
	public boolean scalePicture() {return scalePicture;}
	public boolean hasInventory() {return liId != 0;}
	public void setLiId(long pLiId) {
		assert pLiId != 0;
		liId = pLiId;
	}
	public void setY(float pY) {posY = pY;}
	public boolean isReal() {return real;}
}
