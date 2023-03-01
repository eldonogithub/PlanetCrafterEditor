package nicki0.editor.window;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import nicki0.editor.JCommands;
import nicki0.editor.Main;
import nicki0.editor.Map;
import nicki0.editor.Window;
import nicki0.editor.elements.Container;
import nicki0.editor.elements.Item;
import nicki0.editor.elements.Object;

public class EventsOnNextClick {
	private Window window;
	private MapPanel panel;
	
	private Point2D.Double coordinateLastClickedOn;
	private Item lastClickedBuilding;
	private Container lastClickedContainer;
	
	public EventsOnNextClick(Window pWindow, MapPanel pPanel) {
		window = pWindow;
		panel = pPanel;
	}
	protected void showChangeContainerSizeOnNextClick(List<Item> clickedOnBuilding) {
		if (clickedOnBuilding.size() == 0) return;
		List<Container> clickedOnContainers = new ArrayList<Container>();
		
		for (Item i : clickedOnBuilding) {
			if (i != null) if (i.getLiId() != 0) {
				Container c = window.modify.getIdContainerMap().get(i.getLiId());
				if (c != null) {
					clickedOnContainers.add(c);
				}
			}
		}
		if (clickedOnContainers.size() == 0) JOptionPane.showMessageDialog(null, "No Building with an Inventory in this position");
		else if (clickedOnContainers.size() > 1) JOptionPane.showMessageDialog(null, "There is more than one Buildings with an Inventory in this position");
		else if (clickedOnContainers.size() == 1) {
			Container c = clickedOnContainers.get(0);
			panel.resetNextClickEvent();
			int minSize = c.getWoIds().size();
			String newSize = JOptionPane.showInputDialog("Input new size (current size: " + c.getSize() + ", minimum size: " + minSize + ")");
			if (JCommands.isInt(newSize)) {
				int newSizeInt = Integer.parseInt(newSize);
				c.setSize(newSizeInt);
				//JOptionPane.showMessageDialog(null, "New Inventory size: " + c.getSize());
			}
		}
	}
	protected void showInventoryContentOnNextClick(List<Item> clickedOnBuilding) {
		if (clickedOnBuilding == null) return;
		if (clickedOnBuilding.isEmpty()) return;
		java.util.Map<String, Integer> stockMap = new TreeMap<String, Integer>(Comparator.comparing(s->s, String.CASE_INSENSITIVE_ORDER));
		java.util.Map<Boolean, Integer> countInventorieAmount = new HashMap<Boolean, Integer>();
		clickedOnBuilding.stream().filter(i -> i != null).filter(i -> !(!i.isReal() && window.getInventoryStock(i.getLiId()).isEmpty())).filter(i -> i.getLiId() != 0).map(Item::getLiId).map(i -> window.getInventoryStock(i)).forEach(m -> {m.keySet().forEach(k -> stockMap.put(k, m.get(k) + stockMap.getOrDefault(k, 0))); countInventorieAmount.put(true, countInventorieAmount.getOrDefault(true, 0)+1);});
		
		IconPanel ip = new IconPanel();
		ip.closeOnLeave(window.frame);
		if (countInventorieAmount.isEmpty()) return;
		if (!stockMap.isEmpty()) {
			ip.setTitle(countInventorieAmount.get(true) + " Inventor" + (countInventorieAmount.get(true)>1?"ies":"y"));
			ip.appendPictureLabel(new ArrayList<>(stockMap.keySet()));
			ip.appendTextField(stockMap.keySet().stream().map(s -> Object.translateGIdName.getOrDefault(s,s)).collect(Collectors.toList()));
			ip.appendSpace(10);
			ip.appendTextField(new ArrayList<>(stockMap.values()));
		} else {
			ip.appendTextField(Arrays.asList("Empty"));
		}
		ip.showOnRight(window.panel.getBoundsOnScreen());
		panel.resetNextClickEvent();
		ip.show();
	}
	protected void showResetConnectedInvsOnNextClick(List<Item> clickedOnBuilding) {
		if (clickedOnBuilding.size() == 0) return;
		List<Container> clickedOnContainers = new ArrayList<Container>();
		Item clickedOnItem = null; // ONLY USE IF clickedOnContainers.size() == 1
		for (Item i : clickedOnBuilding) {
			if (i != null) if (i.getLiId() != 0) {
				Container c = window.modify.getIdContainerMap().get(i.getLiId());
				if (c != null) {
					clickedOnContainers.add(c);
				clickedOnItem = i;
				}
			}
		}
		if (clickedOnContainers.size() == 0) JOptionPane.showMessageDialog(null, "No Building with an Inventory in this position");
		else if (clickedOnContainers.size() > 1) JOptionPane.showMessageDialog(null, "There is more than one Buildings with an Inventory in this position");
		else if (clickedOnContainers.size() == 1) {
			panel.resetNextClickEvent();
			int pressedButton = JOptionPane.showConfirmDialog(null, "Do you really want to disconnect all Buildings with this inventory?", "Disconnect inventory " + clickedOnContainers.get(0).getId(), JOptionPane.OK_CANCEL_OPTION);
			if (pressedButton == JOptionPane.OK_OPTION) {
				Container clickedOnContainer = clickedOnContainers.get(0);
				List<Item> connectedBuildings = window.modify.getItemsFromLiID(clickedOnContainer.getId());
				if (connectedBuildings.size() > 1) {
					List<Container> containerList = window.modify.getContainerList();
					for (Item i : connectedBuildings) {
						if (i.getId() != clickedOnItem.getId()) {
							long nextContainerID = window.modify.getNextContainerID();
							containerList.add(new Container(nextContainerID, clickedOnContainer.getSize(), clickedOnContainer.getDemandGrps(), clickedOnContainer.getSupplyGrps(), clickedOnContainer.getPriority()));
							i.setLiId(nextContainerID);
						}
					}
					window.modify.reloadMaps();
				}
			}
			
		}
	}
	
	protected void showMoveBuildingP1OnNextClick(MouseEvent e) {
		coordinateLastClickedOn = Map.getCoordinatesFromPosition(panel.getPositionFromMouse(e.getPoint()));
		panel.rectangleAtPosition(coordinateLastClickedOn);
		panel.showMoveBuildingP2OnNextClick();
	}
	protected void showMoveBuildingP2OnNextClick(MouseEvent e) {
		panel.resetNextClickEvent();
		panel.rectangleAtPosition(null);
		panel.repaint();
		
		List<Item> itemsToMove = new ArrayList<Item>();
		Point2D.Double coordOfMouse = Map.getCoordinatesFromPosition(panel.getPositionFromMouse(e.getPoint()));
		
		Rectangle2D.Double areaToMove = new Rectangle2D.Double((coordinateLastClickedOn.getX() < coordOfMouse.getX()) ? coordinateLastClickedOn.getX() : coordOfMouse.getX(), 
				(coordinateLastClickedOn.getY() < coordOfMouse.getY()) ? coordinateLastClickedOn.getY() : coordOfMouse.getY(),
						Math.abs(coordinateLastClickedOn.getX() - coordOfMouse.getX()), Math.abs(coordinateLastClickedOn.getY() - coordOfMouse.getY()));
		
		for (Item i : panel.renderItems) {
			if (areaToMove.contains(new Point2D.Double(i.getX(), i.getZ())) && i.isOnMap() && i.isReal()) {
				itemsToMove.add(i);
			}
		}
		String inputString = JOptionPane.showInputDialog(null, "<html>Enter by how much the Buildings / Items should be moved upward (use - for downward)<br>(For Reference: Foundations are 5 coordinates high):</html>");
		if (inputString != null) if (JCommands.isFloat(inputString)) {
			float heightChange = Float.parseFloat(inputString);
			for (Item i : itemsToMove) {
				i.setY(i.getY() + heightChange);
			}
		}
	}
	protected void showConnectInvsP1OnNextClick(List<Item> clickedOnBuilding) {
		if (clickedOnBuilding.size() == 0) return;
		List<Container> clickedOnContainers = new ArrayList<Container>();
		Item clickedOnItem = null; // ONLY USE IF clickedOnContainers.size() == 1
		for (Item i : clickedOnBuilding) {
			if (i != null) if (i.getLiId() != 0) {
				Container c = window.modify.getIdContainerMap().get(i.getLiId());
				if (c != null) {
					clickedOnContainers.add(c);
				clickedOnItem = i;
				}
			}
		}
		if (clickedOnContainers.size() == 0) JOptionPane.showMessageDialog(null, "No Building with an Inventory in this position");
		else if (clickedOnContainers.size() > 1) JOptionPane.showMessageDialog(null, "There is more than one Buildings with an Inventory in this position");
		else if (clickedOnContainers.size() == 1) {
			if (clickedOnItem.isReal()) {
				lastClickedBuilding = clickedOnItem;
				lastClickedContainer = clickedOnContainers.get(0);
				panel.showConnectInvsP2OnNextClick();
			} else {
				JOptionPane.showMessageDialog(null, "This Inventory is from a game placed Container and can't be connected");
			}
		}
	}
	protected void showConnectInvsP2OnNextClick(List<Item> clickedOnBuilding) {
		if (clickedOnBuilding.size() == 0) return;
		List<Container> clickedOnContainers = new ArrayList<Container>();
		Item clickedOnItem = null; // ONLY USE IF clickedOnContainers.size() == 1
		for (Item i : clickedOnBuilding) {
			if (i != null) if (i.getLiId() != 0) {
				Container c = window.modify.getIdContainerMap().get(i.getLiId());
				if (c != null) {
					clickedOnContainers.add(c);
				clickedOnItem = i;
				}
			}
		}
		if (clickedOnContainers.size() == 0) JOptionPane.showMessageDialog(null, "No Building with an Inventory in this position");
		else if (clickedOnContainers.size() > 1) JOptionPane.showMessageDialog(null, "There is more than one Buildings with an Inventory in this position");
		else if (clickedOnContainers.size() == 1) {
			if (clickedOnItem.isReal()) {
				panel.resetNextClickEvent();
				if (clickedOnItem.getLiId() == lastClickedBuilding.getLiId()) {
					JOptionPane.showMessageDialog(null, "Containers are already connected");
					return;
				}
				int pressedButton = JOptionPane.showConfirmDialog(null, "Do you really want to connect these Inventories?", "Connect inventory " + clickedOnItem.getLiId() + " to " + lastClickedBuilding.getLiId(), JOptionPane.OK_CANCEL_OPTION);
				if (pressedButton != JOptionPane.OK_OPTION) return;
				
				Container nowClickedContainer = clickedOnContainers.get(0);
				lastClickedContainer.pushWoIds(nowClickedContainer.getWoIds());
				lastClickedContainer.setSize(lastClickedContainer.getSize() + nowClickedContainer.getSize());
				for (Item i : window.modify.getItemsFromLiID(nowClickedContainer.getId())) {
					i.setLiId(lastClickedContainer.getId());
				}
				if (Main.debug) System.out.println("Container removed: " + window.modify.getContainerList().remove(nowClickedContainer));
				
				lastClickedBuilding = null;
				window.modify.reloadMaps();
			} else {
				JOptionPane.showMessageDialog(null, "This Inventory is from a game placed Container and can't be connected");
			}
		}
	}
}
