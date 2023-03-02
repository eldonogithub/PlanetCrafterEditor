package nicki0.editor;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;

import nicki0.editor.pictures.Picture;

public class Map {
	// small map
	/*private static String[] pictureMapStrings = {"map/map_0.6.008.png","map/map_0.6.008_plants.png"};
	public static BufferedImage pictureCave = Picture.loadPicture("map/cave_0.6.008.png");
	public static List<BufferedImage> pictureMap = Picture.loadPicture(pictureMapStrings);
	static Point2D.Double pixel0 = new Point2D.Double(628,588);
	static Point2D.Double pixelA = new Point2D.Double(165,249);
	static Point2D.Double coordA = new Point2D.Double(2210,1625); // x grows to the left, y grow upward*/
	
	// medium map (4k)
	private static String[] pictureMapStrings = {"map/DRY4k.png", "map/WET4k.png"};
	public static BufferedImage pictureCave = Picture.loadPicture("map/CAVE4k.png");
	public static List<BufferedImage> pictureMap = Picture.loadPicture(pictureMapStrings);
	private static Point2D.Double pixel0 = new Point2D.Double(2477.0,2478.0);//v0.7.007
	private static Point2D.Double pixelA = new Point2D.Double(659.0,1127.0);//v0.7.007
	private static Point2D.Double coordA = new Point2D.Double(2202.036,1635.441);//v0.7.007
	//private static Point2D.Double pixel0 = new Point2D.Double(2514.0,2517.0);//v0.6.008
	//private static Point2D.Double pixelA = new Point2D.Double(669.0,1147.0);//v0.6.008
	//private static Point2D.Double coordA = new Point2D.Double(2202.036,1635.441);//v0.6.008
	
	// large map (path changed)
	/*private static java.io.File[] pictureMapStrings = {new java.io.File("C:\\Users\\\\Desktop\\planetcraftermap\\DRY.png"), new java.io.File("C:\\Users\\\\Desktop\\planetcraftermap\\WET.png")};
	public static BufferedImage pictureCave = Picture.loadPictureFromFile(new java.io.File("C:\\Users\\\\Desktop\\planetcraftermap\\CAVE.png"));
	public static List<BufferedImage> pictureMap = Picture.loadPictureFromFile(pictureMapStrings);
	static Point2D.Double pixel0 = new Point2D.Double(10055.0,10068.0);
	static Point2D.Double pixelA = new Point2D.Double(2679.0,4590.0);
	static Point2D.Double coordA = new Point2D.Double(2202.036,1635.441);*/
	
	
	public static URL defaultBuildingPicture = Map.class.getResource("pictures/Default/missingTexture.png"); //dot or Square
	public static BufferedImage picturePlayer = Picture.loadPicture("Default/Player.png");
	
	//private Window window;
	
	//---
	// For small map:
	
	
	
	private static double vector0Ay = -coordA.y/(pixel0.y-pixelA.y);
	private static double vector0Ax = -coordA.x/(pixel0.x-pixelA.x);
	
	private static Dimension pictureSize = new Dimension(pictureMap.get(0).getWidth(null), pictureMap.get(0).getHeight(null));
	
	//---
	
	public Map(Window pWindow) {
		//window = pWindow;
	}
	public static void ping() {} // does nothing. For creating the static objects
	
	public static Point2D.Double getPositionFromCoordinates(double px, double pz) {
		double x = pz / vector0Ax + pixel0.x;
		double y = px / vector0Ay + pixel0.y;
		return new Point2D.Double(x, y);
	}
	public static Point2D.Double getPositionFromCoordinates(Point2D pCoordinates) {
		return getPositionFromCoordinates(pCoordinates.getX(), pCoordinates.getY());
	}
	/**
	 * 
	 * @param px between 0 and 1 where 0 is on the left
	 * @param py between 0 and 1 where 0 is on the top;
	 * @return ingame coordinates
	 */
	public static Point2D.Double getCoordinatesFromPosition(double px, double py) {
		double pixelx = px * pictureSize.width;
		double pixely = py * pictureSize.height;
		double x = vector0Ay * (pixely-pixel0.y); // -pixel0.y to translate the mouse to 0 0 coords so it can be translated by the vector0Ax
		double z = vector0Ax * (pixelx-pixel0.x);
		return new Point2D.Double(x,z);
	}
	public static Point2D.Double getCoordinatesFromPosition(Point2D pPosition) {
		return getCoordinatesFromPosition(pPosition.getX(), pPosition.getY());
	}
	/*public static Point getCoordinatesFromMousePosition(int px, int py) {
		return getCoordinatesFromPosition(px-8, py-54);
	} // old version
	public Point adaptMousePosition(Point p) {
		Point posFrame = window.frame.getLocationOnScreen();
		Point posPanel = window.panel.getLocationOnScreen();
		int offsetX = posFrame.x - posPanel.x;
		int offsetY = posFrame.y - posPanel.y;
		return new Point(p.x + offsetX, p.y + offsetY);
	}*/ // not needed anymore because the mouse listener is now in the panel
	/*public Dimension getMouseOffset() {
		Point posFrame = window.frame.getLocationOnScreen();
		Point posPanel = window.panel.getLocationOnScreen();
		return new Dimension(posFrame.x - posPanel.x, posFrame.y - posPanel.y);
	}*/
	public static double getPixelPerCoordinate() {return Math.abs(1d/(vector0Ax));}
	
}
