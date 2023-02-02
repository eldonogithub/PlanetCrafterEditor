package org.Nicki0.editor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.Nicki0.editor.elements.Container;
import org.Nicki0.editor.elements.Item;
import org.Nicki0.editor.elements.Light;
import org.Nicki0.editor.elements.Message;
import org.Nicki0.editor.elements.PlayerAttributes;
import org.Nicki0.editor.elements.Settings;
import org.Nicki0.editor.elements.Statistics;
import org.Nicki0.editor.elements.StoryEvent;
import org.Nicki0.editor.elements.Ti;

public class SaveLoader {
	File file;
	
	Ti ti;
	PlayerAttributes playerAttributes;
	List<Container> containerList = new ArrayList<Container>();
	List<Item> itemList = new ArrayList<Item>();
	Statistics statistics;
	List<Message> messageList = new ArrayList<Message>();
	List<StoryEvent> storyEventList = new ArrayList<StoryEvent>();
	Settings settings;
	Light light;
	
	public SaveLoader() {}
	public SaveLoader(File pFile) {
		file = pFile;
		read();
	}
	private void read() {
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
		playerAttributes = new PlayerAttributes(Float.parseFloat(pos[0]), Float.parseFloat(pos[1]), Float.parseFloat(pos[2]), Float.parseFloat(rot[0]), Float.parseFloat(rot[1]), Float.parseFloat(rot[2]), Float.parseFloat(rot[3]), /*new ArrayList<String>(*/Arrays.asList(unlockedGroups)/*)*/, Double.parseDouble(singleAttribute[3]), Double.parseDouble(singleAttribute[4]), Double.parseDouble(singleAttribute[5]));
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
			int[] pnlsInt = new int[0];
			if (!singleItem[7].equals("")) {
				String[] pnlsStr = singleItem[7].split(",");
				pnlsInt = new int[pnlsStr.length];
				for (int j = 0; j < pnlsInt.length; j++) pnlsInt[j] = Integer.parseInt(pnlsStr[j]);
			}
			itemList.add(new Item(Long.parseLong(singleItem[0]), singleItem[1], Long.parseLong(singleItem[2]), singleItem[3], posx, posy, posz, rot1, rot2, rot3, rot4, singleItem[6], pnlsInt, singleItem[8], singleItem[9], Integer.parseInt(singleItem[10])));
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
				//System.out.println(i + " " + a);
				
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
		
	}
	public Ti getTi() {return ti;}
	public PlayerAttributes getPlayerAttributes() {return playerAttributes;}
	public List<Item> getItems() {return itemList;}
	public List<Container> getContainer() {return containerList;}
	public Statistics getStatistics() {return statistics;}
	public List<Message> getMessages() {return messageList;}
	public List<StoryEvent> getStoryEvents() {return storyEventList;}
	public Settings getSettings() {return settings;}
	public Light getLight() {return light;}
}
