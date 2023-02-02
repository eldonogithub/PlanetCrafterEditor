package org.Nicki0.editor.window;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.Nicki0.editor.Map;
import org.Nicki0.editor.elements.Item;

public class Buildings {
	JPanel panel;
	List<List<JLabel>> list;
	public List<String> gIDsInList = new ArrayList<>();
	java.util.Map<JLabel, Item> mapLabelToItem;
	
	public Buildings(JPanel pJPanel) {
		panel = pJPanel;
		list = new ArrayList<List<JLabel>>();
		mapLabelToItem = new HashMap<JLabel, Item>();
	}
	
	public void showBuildings(List<Item> pList) {
		if (pList.size() == 0) return;
		
		Component[] comps = panel.getComponents();
		panel.removeAll();
		
		URL url = Map.class.getResource("pictures/GId/" + pList.get(0).getGId() + ".png"); 
		if (url == null) url = Map.defaultBuildingPicture;
		Icon picture = new ImageIcon(url);
		
		List<JLabel> elementList = new ArrayList<JLabel>();
		for (int i = 0; i < pList.size(); i++) {
			if (!pList.get(i).isOnMap()) continue;
			JLabel l = new JLabel(picture);
			
			l.setSize(picture.getIconWidth(), picture.getIconHeight());
			Point pos = Map.getPositionFromCoordinates((int)pList.get(i).posX(), (int)pList.get(i).posZ());
			l.setLocation(pos.x - (picture.getIconWidth()/2), pos.y - (picture.getIconHeight()/2));
			
			panel.add(l);
			l.setVisible(!l.isVisible());
			l.setVisible(!l.isVisible());
			
			mapLabelToItem.put(l, pList.get(i));
			elementList.add(l);
		}
		
		list.add(elementList);
		gIDsInList.add(pList.get(0).getGId());
		
		for (int i = 0; i < comps.length; i++) panel.add(comps[i]);
	}
	public void removeBuildings(String pGId) {
		for (int i = 0; i < gIDsInList.size(); i++) {
			if (gIDsInList.get(i).equalsIgnoreCase(pGId)) {
				for (int j = 0; j < list.get(i).size(); j++) { 
					list.get(i).get(j).setVisible(false);
					panel.remove(list.get(i).get(j));
					
					mapLabelToItem.remove(list.get(i).get(j));
				}
				list.remove(i); 
				gIDsInList.remove(i);
				break;
			}
		}
	}
	public List<Item> objectsAtPosition(int px, int py) {
		List<Item> collidingItems = new ArrayList<Item>();
		
		for (int i = 0; i < list.size(); i++) for (int j = 0; j < list.get(i).size(); j++) {
			Rectangle r = list.get(i).get(j).getBounds();
			if (r.contains(px, py)) collidingItems.add(mapLabelToItem.get(list.get(i).get(j)));
		}
		
		return collidingItems;
	}
}
