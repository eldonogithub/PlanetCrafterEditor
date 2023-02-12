package org.Nicki0.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.Nicki0.editor.elements.Container;
import org.Nicki0.editor.elements.Item;
import org.Nicki0.editor.elements.Light;
import org.Nicki0.editor.elements.Message;
import org.Nicki0.editor.elements.Object;
import org.Nicki0.editor.elements.PlayerAttributes;
import org.Nicki0.editor.elements.Settings;
import org.Nicki0.editor.elements.Statistics;
import org.Nicki0.editor.elements.StoryEvent;
import org.Nicki0.editor.elements.Ti;

public class Modify {
	private Ti ti;
	private PlayerAttributes playerAttributes;
	private List<Item> itemList;
	private List<Container> containerList;
	private Statistics statistics;
	private List<Message> messageList;
	private List<StoryEvent> storyEventList;
	private Settings settings;
	private Light light;
	
	public int inventoryContainer;
	public int equipmentContainer;
	
	private List<Item> notLootedCratesInWorld = null;

	public Modify(Ti pTi, PlayerAttributes pPlayerAttributes, List<Item> pItemList, List<Container> pContainerList, Statistics pStatistics, List<Message> pMessageList, List<StoryEvent> pStoryEventList, Settings pSettings, Light pLight) {
		ti = pTi;
		playerAttributes = pPlayerAttributes;
		itemList = pItemList;
		containerList = pContainerList;
		statistics = pStatistics;
		messageList = pMessageList;
		storyEventList = pStoryEventList;
		settings = pSettings;
		light = pLight;
		// search Special Container
		for (int i = 0; i < containerList.size(); i++) if (containerList.get(i).getId() == 1) {inventoryContainer = i; break;}
		for (int i = 0; i < containerList.size(); i++) if (containerList.get(i).getId() == 2) {equipmentContainer = i; break;}
	}
	public String buildString() {
		long timeNeeded = System.currentTimeMillis();
		String itemBuildStr = formatListItem(itemList);
		System.out.println("items in " + (System.currentTimeMillis() - timeNeeded) + "ms");
		timeNeeded = System.currentTimeMillis();
		String containerBuildStr = formatListContainer(containerList);
		System.out.println("containers in " + (System.currentTimeMillis() - timeNeeded) + "ms");
		
		return "\r" + ti.toString()
		+ "\r@\r" + playerAttributes.toString()
		+ "\r@\r" + itemBuildStr
		+ "\r@\r" + containerBuildStr
		+ "\r@\r" + statistics.toString()
		+ "\r@\r" + formatListMessage(messageList)
		+ "\r@\r" + formatListStoryEvent(storyEventList)
		+ "\r@\r" + settings.toString()
		+ "\r@\r" + light.toString()
		+ "\r@";
	}
	private String formatListItem(List<Item> list) {
		List<String> lst = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {lst.add(list.get(i).toString());}
		return lst.stream().collect(Collectors.joining("|\n", "", ""));
	}
	private String formatListContainer(List<Container> list) {
		List<String> lst = new ArrayList<String>();
		for (Container c : list) lst.add(c.toString());
		return lst.stream().collect(Collectors.joining("|\n", "", ""));
		
	}
	private String formatListMessage(List<Message> list) {
		String str = "";
		for (int i = 0; i < list.size()-1; i++) str = str.concat(list.get(i).toString() + "|\n");
		str = str.concat(list.get(list.size()-1).toString());
		return str;
	}
	private String formatListStoryEvent(List<StoryEvent> list) {
		String str = "";
		for (int i = 0; i < list.size()-1; i++) str = str.concat(list.get(i).toString() + "|\n");
		if (list.size() > 0) str = str.concat(list.get(list.size()-1).toString());
		return str;
	}
	
	public void testForDouble() { // TODO count in HashMap -> O(n) instead of O(n^2)
		long timeNeeded = System.currentTimeMillis();
		for (int currentItem = 0; currentItem < itemList.size(); currentItem++) {
			long id = itemList.get(currentItem).getId();
			// in allen Inventaren nach id suchen
			List<Integer> allContainerWithDoubledItem = new ArrayList<Integer>();
			for (int currentInv = 0; currentInv < containerList.size(); currentInv++) {
				List<Long> containerInv = containerList.get(currentInv).getWoIds();
				for (int i = 0; i < containerInv.size(); i++) {
					if (containerInv.get(i) == id) allContainerWithDoubledItem.add(currentInv);
				}
			}
			// Falls in mehr als einem Inventar einmal:
			if (allContainerWithDoubledItem.size() > 1) {
				System.out.println(itemList.get(currentItem).getGId() + " with ID " + id + " is " + allContainerWithDoubledItem.size() + " times in inventories!");
				for (int invWDI : allContainerWithDoubledItem) {
					for (int i = containerList.get(invWDI).getWoIds().size()-1; i >= 0; i--) {
						if (containerList.get(invWDI).getWoIds().get(i) == id) containerList.get(invWDI).getWoIds().remove(i);
					}
				}
				containerList.get(inventoryContainer).push(itemList.get(currentItem));
			}
			// Falls in keinem Inventar
			if (allContainerWithDoubledItem.size() == 0) {
				boolean isInList = false;
				for (String thing : Object.THINGS_AT_0) {
					if (itemList.get(currentItem).getGId().equalsIgnoreCase(thing)) {
						isInList = true; break;
					}
				}
				if (!isInList && !itemList.get(currentItem).isOnMap() && itemList.get(currentItem).getId() > 200000000) {
					System.out.println("Item without Container outside the Map and not picked up from the map: " + itemList.get(currentItem).toString());
				}
			}
		}
		System.out.println("test for Doubles DONE in " + (System.currentTimeMillis()-timeNeeded) + "ms");
	}
	public void testForInventoryWithoutContainer() {
		long neededTime = System.currentTimeMillis();
		for (int currentContainer = 0; currentContainer < containerList.size(); currentContainer++) {
			long currentContainerID = containerList.get(currentContainer).getId();
			boolean containerExists = false;
			for (int currentItem = 0; currentItem < itemList.size(); currentItem++) {
				if (currentContainerID == itemList.get(currentItem).getLiId()) {
					containerExists = true; 
					break;
				}
			}
			if (!containerExists) {
				boolean notFromGame = true;
				for (int i = 0; i < Object.INVENTORIES_WITHOUT_CONTAINER.length; i++) {
					if (currentContainerID == Object.INVENTORIES_WITHOUT_CONTAINER[i]) notFromGame = false;
				}
				if (notFromGame) System.out.println("This Inventory has no Container: " + containerList.get(currentContainer).toString());
			}
		}
		System.out.println("Inventory without Container - Done in " + (System.currentTimeMillis() - neededTime) + "ms");
	}
	
	public List<Item> getItemList() {return itemList;}
	public List<Container> getContainerList() {return containerList;}
	public Ti getTi() {return ti;}
	public PlayerAttributes getPlayerAttributes() {return playerAttributes;}
	public Statistics getStatistics() {return statistics;}
	public List<Message> getMessages() {return messageList;}
	public List<StoryEvent> getStoryEvents() {return storyEventList;}
	public Settings getSettings() {return settings;}
	public Light getLight() {return light;}
	
	public Item getItemFromID(long id) {
		for (int i = 0; i < itemList.size(); i++) {
			if (itemList.get(i).getId() == id) return itemList.get(i);
		}
		return null;
	}
	public Container getContainerFromID(long id) {
		if (id == 0) return null;
		for (int i = 0; i < containerList.size(); i++) {
			if (containerList.get(i).getId() == id) return containerList.get(i);
		}
		return null;
	}
	public String getInventoryStock() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (Item item : itemList) {
			String gId = item.getGId();
			if (map.get(gId) == null) map.put(gId, 0);
			map.put(gId, map.get(gId)+1);
		}
		List<String> invStock = new ArrayList<String>();
		List<String> keys = new ArrayList<String>(map.keySet());
		Collections.sort(keys, Comparator.comparing(s->s, String.CASE_INSENSITIVE_ORDER));
		for (String s : keys) invStock.add(s + (Object.translateGIdName.get(s) != null?" (" + Object.translateGIdName.get(s) + ")":"") + ": " + map.get(s));
		return invStock.stream().collect(Collectors.joining("\n", "", ""));
	}
	public List<Item> getNotLootedCrates() {
		if (notLootedCratesInWorld != null) return notLootedCratesInWorld;
		List<Item> allCratesInWorldList = Object.createAllContainersInWorldList();
		Map<Long, Integer> idToIndex = new HashMap<>();
		List<Integer> indexToBeRemoved = new ArrayList<Integer>();
		for (int i = 0; i < allCratesInWorldList.size(); i++) idToIndex.put(allCratesInWorldList.get(i).getId(), i);
		for (int i = 0; i < itemList.size(); i++) {
			var tmpid = idToIndex.get(itemList.get(i).getId());
			int id = 0;
			if (tmpid != null) id = tmpid; else continue;
			// crate exists is save file => was deconstructed => was empty => looted
			indexToBeRemoved.add(id);
		}
		for (int i = 0; i < containerList.size(); i++) {
			var tmpid = idToIndex.get(containerList.get(i).getId());
			int id = 0;
			if (tmpid != null) id = tmpid; else continue;
			// container exists in save file && is empty => looted but not deconstructed => not important to show
			if (containerList.get(i).getWoIds().size() == 0) indexToBeRemoved.add(id);
		}
		indexToBeRemoved = indexToBeRemoved.stream().distinct().collect(Collectors.toList());
		Collections.sort(indexToBeRemoved);
		Collections.reverse(indexToBeRemoved);
		for (int i : indexToBeRemoved) {allCratesInWorldList.remove(i);}
		notLootedCratesInWorld = allCratesInWorldList;
		return notLootedCratesInWorld;
	}
}

