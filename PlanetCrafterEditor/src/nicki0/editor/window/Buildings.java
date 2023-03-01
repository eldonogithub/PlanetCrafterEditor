package nicki0.editor.window;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

import nicki0.editor.Window;
import nicki0.editor.elements.Item;
import nicki0.editor.elements.Object;

public class Buildings {
	private Window window;
	
	private boolean messageLootedCrateShown = false;
	
	public boolean showLootCrates = false;
	
	public Buildings(Window pWindow) {
		window = pWindow;
	}
	
	public void showBuildings(Map<String, Boolean> pSwitchState) {
		showLootCrates = pSwitchState.get("_Loot Crates");
		List<Item> tmpItemList = window.modify.getItemList().stream().filter(Item::isOnMap).filter(i -> pSwitchState.getOrDefault(i.getGId(), false)).collect(Collectors.toList());
		List<Item> notLootedCrates = window.modify.getNotLootedCrates();
		if (showLootCrates) {
			if (!notLootedCrates.isEmpty()) {
				 tmpItemList = Stream.concat(notLootedCrates.stream(), tmpItemList.stream()).collect(Collectors.toList());
			} else {
				if (!messageLootedCrateShown) {
					JOptionPane.showMessageDialog(null, "All Loot Crates looted");
					messageLootedCrateShown = true;
				}
				showLootCrates = false;
			}
		}
		
		window.panel.showPlayer(pSwitchState.get("_Player"));
		
		// render enlarged pictures first
		int pos = 0;
		for (int i = 0; i < tmpItemList.size(); i++) {
			if (Object.getCoordinateWidthOfGIdIsEnlarged(tmpItemList.get(i).getGId())) {
				Collections.rotate(tmpItemList.subList(pos, i+1), 1);
				pos++;
			}
		}
		
		window.panel.setItemsToShow(tmpItemList);
		window.panel.repaint();
	}
	public List<Item> objectsAtPosition(double px, double py) {
		List<Item> collidingItems = new ArrayList<Item>();
		
		for (Rectangle2D.Double rec : window.panel.renderItemBoundsMap.keySet()) {
			if (rec.contains(px, py)) collidingItems.add(window.panel.renderItemBoundsMap.get(rec));
		}
		
		return collidingItems;
	}
}
