package org.Nicki0.editor;

import java.awt.Point;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Map {
	public static Icon[] pictureMap = {new ImageIcon(Map.class.getResource("pictures/map_0.6.008.png")), 
			new ImageIcon(Map.class.getResource("pictures/map_0.6.008_plants.png"))};
	public static Icon pictureCave = new ImageIcon(Map.class.getResource("pictures/cave_0.6.008.png"));
	public static URL defaultBuildingPicture = Map.class.getResource("pictures/GId/dot.png"); //dot or Square
	
	public Map() {}
	
	public static Point getCoordinatesFromPosition(int px, int py) {
		int x = (int) ((-1625f/339f)*(((float)py)-588f));
		int z = (int) ((-2210f/463f)*(((float)px)-628f));
		return new Point(x, z);
	}
	public static Point getPositionFromCoordinates(int px, int pz) {
		int x = (int) (((float)pz)*(-463f/2210f)+625);
		int y = (int) (((float)px)*(-339f/1625f)+588);
		return new Point(x, y);
	}
	/*public static Point getCoordinatesFromMousePosition(int px, int py) {
		return getCoordinatesFromPosition(px-8, py-54);
	}*/
	public static Point adaptMousePosition(Point p) {return new Point(p.x-8, p.y-54);}
}
