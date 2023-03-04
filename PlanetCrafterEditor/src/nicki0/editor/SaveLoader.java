package nicki0.editor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nicki0.editor.elements.Container;
import nicki0.editor.elements.Item;
import nicki0.editor.elements.Light;
import nicki0.editor.elements.Message;
import nicki0.editor.elements.PlayerAttributes;
import nicki0.editor.elements.Settings;
import nicki0.editor.elements.Statistics;
import nicki0.editor.elements.StoryEvent;
import nicki0.editor.elements.Ti;

public class SaveLoader {
	private File file;
	
	private Ti ti;
	private PlayerAttributes playerAttributes;
	private List<Item> itemList = new ArrayList<Item>();
	private List<Container> containerList = new ArrayList<Container>();
	private Statistics statistics;
	private List<Message> messageList = new ArrayList<Message>();
	private List<StoryEvent> storyEventList = new ArrayList<StoryEvent>();
	private Settings settings;
	private Light light;
	
	private List<String> additionalLinesList = new ArrayList<String>();
	
	public SaveLoader() {}
	public SaveLoader(File pFile) {
		assert pFile != null;
		file = pFile;
		read();
	}
	private void read() {
		String wholeFileString = "";
		StringBuilder sb = new StringBuilder();
		try {
			Files.lines(Paths.get(file.getPath())).forEach(s -> sb.append(s));
		} catch (IOException e) {
			e.printStackTrace();
		}
		wholeFileString = sb.toString();
		
		String[] allSections = wholeFileString.split("@", -1);
		
		int amountOfSensibleSections = 9;
		assert allSections.length > amountOfSensibleSections : "Number of Sections: 10 > " + allSections.length;
		for (int sectionNumber = 0; sectionNumber < allSections.length; sectionNumber++) {
			List<Map<String, String>> splittedSection = ((sectionNumber < amountOfSensibleSections)?readSection(allSections[sectionNumber]):new ArrayList<>());
			
			// Load data in correct vars
			Map<String, String> m; // map for every Line
			switch (sectionNumber) {
			// ti;
			case 0:
				if (allSections[sectionNumber].equalsIgnoreCase("")) {ti = new Ti(); break;}
				assert splittedSection.size() == 1;
				m = splittedSection.get(0);
				ti = new Ti(
						m.getOrDefault("unitOxygenLevel", "0.0"), 
						m.getOrDefault("unitHeatLevel", "0.0"), 
						m.getOrDefault("unitPressureLevel", "0.0"), 
						m.getOrDefault("unitPlantsLevel", "0.0"), 
						m.getOrDefault("unitInsectsLevel", "0.0"), 
						m.getOrDefault("unitAnimalsLevel", "0.0")
						);
				break;
			// playerAttributes;
			case 1:
				if (allSections[sectionNumber].equalsIgnoreCase("")) {playerAttributes = new PlayerAttributes(); break;}
				assert splittedSection.size() == 1;
				m = splittedSection.get(0);
				List<Double> pos = splitDouble(m.getOrDefault("playerPosition", "414,18,585"));
				List<Double> rot = splitDouble(m.getOrDefault("playerRotation", "0,0,0,1"));
				playerAttributes = new PlayerAttributes(
						pos.get(0).floatValue(), 
						pos.get(1).floatValue(), 
						pos.get(2).floatValue(), 
						rot.get(0).floatValue(), 
						rot.get(1).floatValue(), 
						rot.get(2).floatValue(), 
						rot.get(3).floatValue(), 
						splitList(m.getOrDefault("unlockedGroups", "")), 
						Double.parseDouble(m.getOrDefault("playerGaugeOxygen", "100.0")), 
						Double.parseDouble(m.getOrDefault("playerGaugeThirst", "100.0")),
						Double.parseDouble(m.getOrDefault("playerGaugeHealth", "100.0"))
						);
				break;
			// itemList
			case 2:
				if (allSections[sectionNumber].equalsIgnoreCase("")) {break;}
				for (Map<String, String> mp : splittedSection) {
					List<Double> ps = splitDouble(mp.getOrDefault("pos", "0,0,0"));
					List<Double> rt = splitDouble(mp.getOrDefault("rot", "0,0,0,0"));
					itemList.add(new Item(
							Long.parseLong(mp.get("id")), 
							mp.get("gId"), 
							Long.parseLong(mp.getOrDefault("liId", "0")), 
							mp.getOrDefault("liGrps", ""),
							ps.get(0).floatValue(), 
							ps.get(1).floatValue(), 
							ps.get(2).floatValue(), 
							rt.get(0).floatValue(), 
							rt.get(1).floatValue(), 
							rt.get(2).floatValue(), 
							rt.get(3).floatValue(),
							Long.parseLong(mp.getOrDefault("wear", "0")), 
							splitIntegerArr(mp.getOrDefault("pnls", "")), 
							mp.getOrDefault("color", ""), 
							mp.getOrDefault("text", ""), 
							Integer.parseInt(mp.getOrDefault("grwth", "0"))
							));
				}
				break;
			// containerList
			case 3:
				if (allSections[sectionNumber].equalsIgnoreCase("")) {break;}
				for (Map<String, String> mp : splittedSection) {
					List<String> demandGrps = new ArrayList<String>();
					List<String> supplyGrps = new ArrayList<String>();
					if (mp.containsKey("demandGrps") && mp.containsKey("supplyGrps")) {
						demandGrps = splitList(mp.get("demandGrps"));
						supplyGrps = splitList(mp.get("supplyGrps"));
					}
					containerList.add(new Container(
							Long.parseLong(mp.get("id")), 
							splitLong(mp.getOrDefault("woIds", "")), 
							Integer.parseInt(mp.getOrDefault("size", "35")), 
							demandGrps, 
							supplyGrps, 
							Long.parseLong(mp.getOrDefault("priority", "0"))
							));
				}
				break;
			// statistics;
			case 4:
				if (allSections[sectionNumber].equalsIgnoreCase("")) {statistics = new Statistics(); break;}
				assert splittedSection.size() == 1;
				m = splittedSection.get(0);
				statistics = new Statistics(Integer.parseInt(
						m.getOrDefault("craftedObjects", "0")), 
						Integer.parseInt(m.getOrDefault("totalSaveFileLoad", "0")), 
						Integer.parseInt(m.getOrDefault("totalSaveFileTime", "0"))
						);
				break;
			// messageList
			case 5:
				if (allSections[sectionNumber].equalsIgnoreCase("")) {break;}
				for (Map<String, String> mp : splittedSection) {
					messageList.add(new Message(
							mp.get("stringId"), 
							Boolean.parseBoolean(mp.get("isRead"))
							));
				}
				break;
			// storyEventList
			case 6:
				if (allSections[sectionNumber].equalsIgnoreCase("")) {break;}
				for (Map<String, String> mp : splittedSection) {
					storyEventList.add(new StoryEvent(
							mp.get("stringId")
							));
				}
				break;
			// settings;
			case 7:
				if (allSections[sectionNumber].equalsIgnoreCase("")) {settings = new Settings(); break;}
				assert splittedSection.size() == 1;
				m = splittedSection.get(0);
				settings = new Settings(
						m.getOrDefault("mode", "Standard"), 
						Boolean.parseBoolean(m.getOrDefault("hasPlayedIntro", "false"))
						);
				break;
			// light;
			case 8:
				light = new Light(allSections[sectionNumber].replaceAll("\\|", "\\|\n"));
				break;
			// for next lines without
			case 20:
			case 19:
			case 18:
			case 17:
			case 16:
			case 15:
			case 14:
			case 13:
			case 12:
			case 11:
			case 10:
			case 9:
				additionalLinesList.add(allSections[sectionNumber].replaceAll("\\|", "\\|\n"));
				break;
			}
		}
	}
	private List<Map<String, String>> readSection(String wholeSection) {
		assert wholeSection != null : "String null";
		List<Map<String, String>> allLinesList = new ArrayList<Map<String, String>>();
		
		//remove first { and last } 
		if (wholeSection.startsWith("{") && wholeSection.endsWith("}")) {
			// section is probably JSON...
			wholeSection = wholeSection.substring(1, wholeSection.length()-1);
		}
		
		String[] allLines = wholeSection.split("\\}\\|\\{");
		
		for (int lineNumber = 0; lineNumber < allLines.length; lineNumber++) {
			allLinesList.add(readLine(allLines[lineNumber]));
		}
		
		return allLinesList;
	}
	private Map<String, String> readLine(String wholeLine) {
		assert wholeLine != null;
		Map<String, String> lineMap = new HashMap<String, String>();
		
		// split at ," because every " in a ingame text is a \" and every new keyword starts with a "
		wholeLine = wholeLine.replaceAll(",\"", "|\"");
		if (wholeLine.contains("|\"|\"")) wholeLine = wholeLine.replaceAll("\\|\"\\|\"", ",\"|\"");
		
		String[] allKeys = wholeLine.split("\\|");
		
		for (int keyNumber = 0; keyNumber < allKeys.length; keyNumber++) {
			if (allKeys[keyNumber].equals("")) continue;
			String[] keyAndValue = allKeys[keyNumber].split(":", 2);
			assert keyAndValue != null;
			assert keyAndValue.length == 2 : "No Key-Value-Pair in " + wholeLine;
			// keys usually do not contain " but they always start and end with "
			keyAndValue[0] = keyAndValue[0].replaceAll("\"", "");
			if (keyAndValue[1].startsWith("\"") && keyAndValue[1].endsWith("\"")) keyAndValue[1] = keyAndValue[1].substring(1, keyAndValue[1].length()-1);
			lineMap.put(keyAndValue[0], keyAndValue[1]);
		}
		
		return lineMap;
	}
	private List<Double> splitDouble(String s) {
		assert s != null;
		if (s.equals("")) return new ArrayList<Double>();
		return splitList(s).stream().mapToDouble(Double::parseDouble).boxed().collect(Collectors.toList());
	}
	private List<String> splitList(String s) {
		return Arrays.stream(s.split(",")).collect(Collectors.toList());
	}
	private List<Long> splitLong(String s) {
		assert s != null;
		if (s.equals("")) return new ArrayList<Long>();
		return splitList(s).stream().mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
	}
	private int[] splitIntegerArr(String s) {
		assert s != null;
		if (s.equals("")) return new int[0];
		return splitList(s).stream().map(n -> Integer.parseInt(n)).mapToInt(Integer::intValue).toArray();
	}
	/*
	private void read() {//DO NOT USE!!! DOES NOT WORK WITH v0.7.* but is double the speed... :(
		String all = "";
		try {
			all = Files.readString(Path.of(file.getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		all = all.replaceAll("[\r\n]", "");
		String[] splitAT = all.split("@");
		
		//ti0
		String allTi = splitAT[0].replaceAll("(\\{\"unitOxygenLevel\":|\\})", "");
		allTi = allTi.replaceAll("(,\"unitHeatLevel\":|,\"unitPressureLevel\":|,\"unitPlantsLevel\":|,\"unitInsectsLevel\":|,\"unitAnimalsLevel\":)", "@");
		String[] singleTi = allTi.split("@");
		ti = new Ti(singleTi[0], singleTi[1], singleTi[2], singleTi[3], singleTi[4], singleTi[5]);
		//playerAttributes1
		String allAttributes = splitAT[1].replaceAll("(\\{\"playerPosition\":\"|\\})", "");
		allAttributes = allAttributes.replaceAll("(\",\"playerRotation\":\"|\",\"unlockedGroups\":\"|\",\"playerGaugeOxygen\":|,\"playerGaugeThirst\":|,\"playerGaugeHealth\":)", "@");
		String[] singleAttribute = allAttributes.split("@");
		String[] pos = singleAttribute[0].split(",");
		String[] rot = singleAttribute[1].split(",");
		String[] unlockedGroups = singleAttribute[2].split(",");
		playerAttributes = new PlayerAttributes(Float.parseFloat(pos[0]), Float.parseFloat(pos[1]), Float.parseFloat(pos[2]), Float.parseFloat(rot[0]), Float.parseFloat(rot[1]), Float.parseFloat(rot[2]), Float.parseFloat(rot[3]), Arrays.asList(unlockedGroups), Double.parseDouble(singleAttribute[3]), Double.parseDouble(singleAttribute[4]), Double.parseDouble(singleAttribute[5]));
		//items2
		String allItems = splitAT[2].replaceAll("(\\{|\\})", "");
		//allItems;//hier string weiter auseinandernehmen
		allItems = allItems.replaceAll("(\"id\":)", "");
		allItems = allItems.replaceAll("(\"id\":|,\"gId\":\"|\",\"liId\":|,\"liGrps\":\"|\",\"pos\":\"|\",\"rot\":\"|\",\"wear\":|,\"pnls\":\"|\",\"color\":\"|\",\"text\":\"|\",\"grwth\":)", "@");
		
		String[] allItemsArray = allItems.split("\\|");
		for (int i = 0; i < allItemsArray.length; i++) {
			String[] singleItem = allItemsArray[i].split("@");
			// pos rot pnls
			String[] posStr = singleItem[4].split(",");
			float posx = Float.parseFloat(posStr[0]);
			float posy = Float.parseFloat(posStr[1]);
			float posz = Float.parseFloat(posStr[2]);
			String[] floatStr = singleItem[5].split(",");
			float rot1 = Float.parseFloat(floatStr[0]);
			float rot2 = Float.parseFloat(floatStr[1]);
			float rot3 = Float.parseFloat(floatStr[2]);
			float rot4 = Float.parseFloat(floatStr[3]);
			long wear = Long.parseLong(singleItem[6]);
			int[] pnlsInt = new int[0];
			if (!singleItem[7].equals("")) {
				String[] pnlsStr = singleItem[7].split(",");
				pnlsInt = new int[pnlsStr.length];
				for (int j = 0; j < pnlsInt.length; j++) pnlsInt[j] = Integer.parseInt(pnlsStr[j]);
			}
			itemList.add(new Item(Long.parseLong(singleItem[0]), singleItem[1], Long.parseLong(singleItem[2]), singleItem[3], posx, posy, posz, rot1, rot2, rot3, rot4, wear, pnlsInt, singleItem[8], singleItem[9], Integer.parseInt(singleItem[10])));
		}
		
		
		//container3
		String allContainers = splitAT[3].replaceAll("(\\{|\\})", "");
		String[] allContainersArr = allContainers.split("\\|");
		for (String s : allContainersArr) {
			String[] attribs = s.split(",\"");
			
			long id = 0;
			List<Long> woIds = new ArrayList<Long>();
			int size = 0;
			for (int i = 0; i < attribs.length; i++) {
				String a = attribs[i].replaceAll("\"", "");
				a = a.replaceAll("(id:|woIds:|size:)", "");
				
				switch (i) {
				case 0:
					id = Integer.parseInt(a);
				break;
				case 1:
					if (a.equalsIgnoreCase("") || a == null) break;
					String[] woIdsString = a.split(",");
					for (String hWoIds : woIdsString) woIds.add(Long.parseLong(hWoIds));
				break;
				case 2:
					size = Integer.parseInt(a);
				break;
				}
			}
			containerList.add(new Container(id, woIds, size)); 
		}
		//statistics4
		String allStatistics = splitAT[4].replaceAll("(\\{\"craftedObjects\":|\\})", "");
		allStatistics = allStatistics.replaceAll("(,\"totalSaveFileLoad\":|,\"totalSaveFileTime\":)", "@");
		String[] splitStatistics = allStatistics.split("@");
		statistics = new Statistics(Integer.parseInt(splitStatistics[0]), Integer.parseInt(splitStatistics[1]), Integer.parseInt(splitStatistics[2]));
		//message5
		String allMessages = splitAT[5].replaceAll("(\\{\"stringId\":\"|\\})", "");
		allMessages = allMessages.replaceAll("\",\"isRead\":", "@");
		String[] singleMessage = allMessages.split("\\|");
		for (int i = 0; i < singleMessage.length; i++) {
			String[] splitMessage = singleMessage[i].split("@");
			messageList.add(new Message(splitMessage[0], Boolean.parseBoolean(splitMessage[1])));
		}
		//story6
		if (splitAT[6] != "") {
			String allStory = splitAT[6].replaceAll("(\\{\"stringId\":\"|\"\\})", "");
			String[] splitStory = allStory.split("\\|");
			for (String s : splitStory) storyEventList.add(new StoryEvent(s));
		}
		//settings7
		String allSettings = splitAT[7].replaceAll("(\\{|\\}|\"mode\":\")", "");
		allSettings = allSettings.replaceAll("\",\"hasPlayedIntro\":", "@");
		String[] splitSettings = allSettings.split("@");
		settings = new Settings(splitSettings[0], Boolean.parseBoolean(splitSettings[1]));
		//?? Lighting or sth 8
		light = new Light(splitAT[8].replaceAll("\\|", "\\|\n"));
		
	}*/
	public Ti getTi() {return ti;}
	public PlayerAttributes getPlayerAttributes() {return playerAttributes;}
	public List<Item> getItems() {return itemList;}
	public List<Container> getContainer() {return containerList;}
	public Statistics getStatistics() {return statistics;}
	public List<Message> getMessages() {return messageList;}
	public List<StoryEvent> getStoryEvents() {return storyEventList;}
	public Settings getSettings() {return settings;}
	public Light getLight() {return light;}
	
	public List<String> getAdditionalLinesList() {
		return additionalLinesList;
	}
}
