package nicki0.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import nicki0.editor.elements.Container;
import nicki0.editor.elements.Item;
import nicki0.editor.elements.Light;
import nicki0.editor.elements.Message;
import nicki0.editor.elements.Object;
import nicki0.editor.elements.PlayerAttributes;
import nicki0.editor.elements.Settings;
import nicki0.editor.elements.Statistics;
import nicki0.editor.elements.StoryEvent;
import nicki0.editor.elements.Ti;

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
	
	private List<String> additionalLinesList;
	
	private Map<Long, Item> idItemMap;
	private Map<Long, Container> idContainerMap;
	private Map<Long, List<Item>> liIdItemMap;
	
	public Container inventoryContainer;
	public Container equipmentContainer;
	
	@Deprecated
	public Container SpaceMultiplierBiomassContainer;
	public Container SpaceMultiplierHeatContainer;
	public Container SpaceMultiplierInsectsContainer;
	public Container SpaceMultiplierOxygenContainer;
	public Container SpaceMultiplierPlantsContainer;
	public Container SpaceMultiplierPressureContainer;
	
	private List<Item> notLootedCratesInWorld = null;

	public Modify(Ti pTi, PlayerAttributes pPlayerAttributes, List<Item> pItemList, List<Container> pContainerList, Statistics pStatistics, List<Message> pMessageList, List<StoryEvent> pStoryEventList, Settings pSettings, Light pLight, List<String> pAdditionalLinesList) {
		ti = pTi;
		playerAttributes = pPlayerAttributes;
		itemList = pItemList;
		containerList = pContainerList;
		statistics = pStatistics;
		messageList = pMessageList;
		storyEventList = pStoryEventList;
		settings = pSettings;
		light = pLight;
		
		additionalLinesList = pAdditionalLinesList;
		
		reloadMaps();
		reloadPreSearchedContainers();
		
		inventoryContainer = idContainerMap.get(1L);
		equipmentContainer = idContainerMap.get(2L);
	}
	public void reloadMaps() {
		// DO NOT ADD MAP GId -> ???, won't work with user input!!!
		Map<Long, Item> tmpidItemMap;
		Map<Long, Container> tmpidContainerMap;
		Map<Long, List<Item>> tmpliIdItemMap;
		
		tmpidItemMap = new HashMap<Long, Item>();
		for (Item i : itemList) tmpidItemMap.put(i.getId(), i);
		idItemMap = tmpidItemMap;
		
		tmpidContainerMap = new HashMap<Long, Container>();
		for (Container c : containerList) tmpidContainerMap.put(c.getId(), c);
		idContainerMap = tmpidContainerMap;
		
		tmpliIdItemMap = new HashMap<Long, List<Item>>();
		for (Item i : itemList) {
			List<Item> entries = tmpliIdItemMap.getOrDefault(i.getLiId(), new ArrayList<Item>());
			entries.add(i);
			tmpliIdItemMap.put(i.getLiId(), entries);
		}
		tmpliIdItemMap.remove(0L); // ignore items without containers (liId = 0) 
		liIdItemMap = tmpliIdItemMap;
	}
	public void reloadPreSearchedContainers() {
		Map<String, List<Container>> spaceMultiplierMap = new HashMap<>();
		for (Item i : itemList) {
			switch (i.getGId()) {
			case Object.SpaceMultiplierBiomass:
				List<Container> h1lst = spaceMultiplierMap.getOrDefault(Object.SpaceMultiplierBiomass, new ArrayList<Container>());
				h1lst.add(getContainerFromID(i.getLiId()));
				spaceMultiplierMap.put(Object.SpaceMultiplierBiomass, h1lst);
				break;
			case Object.SpaceMultiplierHeat:
				List<Container> h2lst = spaceMultiplierMap.getOrDefault(Object.SpaceMultiplierHeat, new ArrayList<Container>());
				h2lst.add(getContainerFromID(i.getLiId()));
				spaceMultiplierMap.put(Object.SpaceMultiplierHeat, h2lst);
				break;
			case Object.SpaceMultiplierInsects:
				List<Container> h3lst = spaceMultiplierMap.getOrDefault(Object.SpaceMultiplierInsects, new ArrayList<Container>());
				h3lst.add(getContainerFromID(i.getLiId()));
				spaceMultiplierMap.put(Object.SpaceMultiplierInsects, h3lst);
				break;
			case Object.SpaceMultiplierOxygen:
				List<Container> h4lst = spaceMultiplierMap.getOrDefault(Object.SpaceMultiplierOxygen, new ArrayList<Container>());
				h4lst.add(getContainerFromID(i.getLiId()));
				spaceMultiplierMap.put(Object.SpaceMultiplierOxygen, h4lst);
				break;
			case Object.SpaceMultiplierPlants:
				List<Container> h5lst = spaceMultiplierMap.getOrDefault(Object.SpaceMultiplierPlants, new ArrayList<Container>());
				h5lst.add(getContainerFromID(i.getLiId()));
				spaceMultiplierMap.put(Object.SpaceMultiplierPlants, h5lst);
				break;
			case Object.SpaceMultiplierPressure:
				List<Container> h6lst = spaceMultiplierMap.getOrDefault(Object.SpaceMultiplierPressure, new ArrayList<Container>());
				h6lst.add(getContainerFromID(i.getLiId()));
				spaceMultiplierMap.put(Object.SpaceMultiplierPressure, h6lst);
				break;
			}
		}
		for (List<Container> clst : spaceMultiplierMap.values()) {
			assert clst.size() < 2;
		}
		if (spaceMultiplierMap.get(Object.SpaceMultiplierBiomass) != null) SpaceMultiplierBiomassContainer = spaceMultiplierMap.get(Object.SpaceMultiplierBiomass).get(0);
		if (spaceMultiplierMap.get(Object.SpaceMultiplierHeat) != null) SpaceMultiplierHeatContainer = spaceMultiplierMap.get(Object.SpaceMultiplierHeat).get(0);
		if (spaceMultiplierMap.get(Object.SpaceMultiplierInsects) != null) SpaceMultiplierInsectsContainer = spaceMultiplierMap.get(Object.SpaceMultiplierInsects).get(0);
		if (spaceMultiplierMap.get(Object.SpaceMultiplierOxygen) != null) SpaceMultiplierOxygenContainer = spaceMultiplierMap.get(Object.SpaceMultiplierOxygen).get(0);
		if (spaceMultiplierMap.get(Object.SpaceMultiplierPlants) != null) SpaceMultiplierPlantsContainer = spaceMultiplierMap.get(Object.SpaceMultiplierPlants).get(0);
		if (spaceMultiplierMap.get(Object.SpaceMultiplierPressure) != null) SpaceMultiplierPressureContainer = spaceMultiplierMap.get(Object.SpaceMultiplierPressure).get(0);
	}
	public String buildString() {
		long timeNeeded = System.currentTimeMillis();
		String itemBuildStr = formatListItem(itemList);
		if (Main.debug) System.out.println("items in " + (System.currentTimeMillis() - timeNeeded) + "ms");
		timeNeeded = System.currentTimeMillis();
		String containerBuildStr = formatListContainer(containerList);
		if (Main.debug) System.out.println("containers in " + (System.currentTimeMillis() - timeNeeded) + "ms");
		
		return "\r" + ti.toString()
		+ "\r@\r" + playerAttributes.toString()
		+ "\r@\r" + itemBuildStr
		+ "\r@\r" + containerBuildStr
		+ "\r@\r" + statistics.toString()
		+ "\r@\r" + formatListMessage(messageList)
		+ "\r@\r" + formatListStoryEvent(storyEventList)
		+ "\r@\r" + settings.toString()
		+ "\r@\r" + light.toString()
		+ "\r@" + additionalLinesList.stream().collect(Collectors.joining("\r@\r", "\r", ""));
	}
	private String formatListItem(List<Item> list) {
		return list.stream().map(Item::toString).collect(Collectors.joining("|\n", "", ""));
	}
	private String formatListContainer(List<Container> list) {
		return list.stream().map(Container::toString).collect(Collectors.joining("|\n", "", ""));
	}
	private String formatListMessage(List<Message> list) {
		return list.stream().map(Message::toString).collect(Collectors.joining("|\n", "", ""));
	}
	private String formatListStoryEvent(List<StoryEvent> list) {
		return list.stream().map(StoryEvent::toString).collect(Collectors.joining("|\n", "", ""));
	}
	
	public void testForDouble() {
		long timeNeeded = System.currentTimeMillis();
		int foundDoubleItem = 0;
		
		Map<Long, List<Container>> countNumberOfItemIDsInContainers = new HashMap<Long, List<Container>>();
		for (Container c : containerList) for (Long l : c.getWoIds()) {
			List<Container> cList = countNumberOfItemIDsInContainers.getOrDefault(l, new ArrayList<Container>());
			cList.add(c);
			countNumberOfItemIDsInContainers.put(l, cList);
		}
		for (Item it : itemList) {
			List<Container> cList = countNumberOfItemIDsInContainers.get(it.getId());
			if (cList != null) {
				if (cList.size() > 1) {
					// is in more than one inventory
					foundDoubleItem++;
					System.out.println(it.getGId() + " with ID " + it.getId() + " is " + cList.size() + " times in inventories!");
					for (Container c : cList) {
						for (int i = c.getWoIds().size()-1; i >= 0; i--) {
							if (c.getWoIds().get(i) == it.getId()) c.getWoIds().remove(i);
						}
					}
					inventoryContainer.push(it);
				}
			} else {
				// not in a inventory
				boolean isInList = false;
				for (String thing : Object.THINGS_AT_0) {
					if (it.getGId().equalsIgnoreCase(thing)) {
						isInList = true; break;
					}
				}
				if (!isInList && !it.isOnMap() && it.getId() > 200000000) {
					System.out.println("Item without Container outside the Map and not picked up from the map: " + it.toString());
				}
			}
		}
		inventoryContainer.adaptSize();
		JOptionPane.showMessageDialog(null, "Items with doubled instances were moved to the player inventory", foundDoubleItem + " item" + (foundDoubleItem==1?"":"s") + " with multiple instances found", JOptionPane.INFORMATION_MESSAGE, null);
		if (Main.debug) System.out.println("test for Doubles DONE in " + (System.currentTimeMillis()-timeNeeded) + "ms");
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
		if (Main.debug) System.out.println("Inventory without Container - Done in " + (System.currentTimeMillis() - neededTime) + "ms");
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
	
	public Map<Long, Item> getIdItemMap() {
		return idItemMap;
	}
	public Map<Long, Container> getIdContainerMap() {
		return idContainerMap;
	}
	public Item getItemFromID(long id) {
		return idItemMap.get(id);
	}
	public Container getContainerFromID(long id) {
		return idContainerMap.get(id);
	}
	public List<Item> getItemsFromLiID(Long pLiID) {
		return liIdItemMap.getOrDefault(pLiID, new ArrayList<Item>());
	}
	public Map<String, Integer> getInventoryStockMap() {
		Map<String, Integer> map = new TreeMap<String, Integer>(Comparator.comparing(s->Object.translateGIdName.getOrDefault(s, s), String.CASE_INSENSITIVE_ORDER));
		for (Item item : itemList) {
			String gId = item.getGId();
			if (!Object.EXCLUDED_GIDs.contains(gId)) map.put(gId, map.getOrDefault(gId, 0)+1);
		}
		return map;
	}
	public String getInventoryStockString() {
		Map<String, Integer> map = getInventoryStockMap();
		List<String> invStock = new ArrayList<String>();
		List<String> keys = new ArrayList<String>(map.keySet());
		for (String s : keys) invStock.add(s + (Object.translateGIdName.get(s) != null?" (" + Object.translateGIdName.get(s) + ")":"") + ": " + map.get(s));
		return invStock.stream().collect(Collectors.joining("\n", "", ""));
	}
	public List<Item> getNotLootedCrates() {
		if (notLootedCratesInWorld != null) return notLootedCratesInWorld;
		List<Item> allCratesInWorldList = Object.allContainersInWorld; // TODO funktioniert das? sonst wieder Object.createAllContainersInWorldList() oder so
		Map<Long, Integer> idToIndex = new HashMap<>();
		List<Integer> indexToBeRemoved = new ArrayList<Integer>();
		for (int i = 0; i < allCratesInWorldList.size(); i++) idToIndex.put(allCratesInWorldList.get(i).getId(), i);
		for (int i = 0; i < itemList.size(); i++) {
			Integer tmpid = idToIndex.get(itemList.get(i).getId());
			int id = 0;
			if (tmpid != null) id = tmpid; else continue;
			// crate exists is save file => was deconstructed => was empty => looted
			indexToBeRemoved.add(id);
		}
		for (int i = 0; i < containerList.size(); i++) {
			Integer tmpid = idToIndex.get(containerList.get(i).getId());
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
	public long getNextContainerID() {
		long highestLiID = 2;
		for (Container c : containerList) {
			if (c.getId() < 10000000) if (c.getId() > highestLiID) highestLiID = c.getId();
		}
		return highestLiID + 1;
	}
	private HashSet<Long> allItemIDs; // only used by getNewItemID
	public long getNewItemID() {
		if (allItemIDs == null) allItemIDs = new HashSet<Long>(itemList.stream().map(Item::getId).collect(Collectors.toSet()));
		for (int i = 0; i < (itemList.size()<5?itemList.size():5); i++) allItemIDs.add(itemList.get(itemList.size() - i - 1).getId());
		for (int i = 0; i < 1000; i++) {
			long randomID = (long) (Math.random() * 9999999) + 200000000;
			if (!allItemIDs.contains(randomID)) {
				return randomID;
			}
		}
		assert false : "1000 newly generated ids were in save file. Impossible for human save files. How?";
		return 0;
	}
}

