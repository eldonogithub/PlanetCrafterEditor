package nicki0.editor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main {
	public static final String VERSIONGAME = "v0.7.008";
	public static final String VERSIONEDITOR = "V0.3.2";
	public static final boolean debug = false;
	/**
	 * Version 0.3.2
	 * bug with ,"," solved
	 * slimmed save files (https://github.com/akarnokd/ThePlanetCrafterMods#perf-reduce-save-size) can be loaded.
	 * removed unobtainable items from cheat menu
	 * 
	 * Version 0.3.1
	 * 0.7 loot crates
	 * 
	 * Version 0.3
	 * ZOOM
	 * Higher Picture Quality
	 * Pictures for Buildings / Items
	 * Improvements in runtime of doubled items checking
	 * Check for unique Item IDs
	 * edit container size
	 * set supply of generating buildings
	 * cheat Items, put Rockets into multiplier inventory
	 * Inventory overview (and with MMB)
	 * Compare Progress to Earth now in Window
	 * New save file loading system
	 * (Dis-)Connect Inventories
	 * Rearrange Teleporter
	 * Move Buildings in Y direction
	 * Info Text, recommended map, open save file folder
	 * 
	 * Version 0.2
	 * Change: Shows not looted crates
	 * 
	 * Version 0.1
	 * Check save files for doubled items in containers
	 * Locations of items on the map
	 * Check for missing containers for inventories
	 * Show the content of a specific container on command line
	 * Initial overview over all items and buildings in your save file on the command line
	 * Initial comparison of Oxygen, Heat, Pressure, Plants and Insects to Earth on the command line
	 */
	/* TODO
	 * (In German... sorry)
	 * TODO additional sections afer @ in another variable to support mods
	 * TODO ," beim splitten einer Zeile funktioniert nicht, falls , am Ende des Textes steht
	 * TODO Standardwerte bei nicht vorhandenen Einträgen einfügen
	 * 
	 * TODO Info / Control
	 * TODO update map to v0.7.x
	 * TODO Autocrafter (inkubatoren) supply hinzufügen: was die gerade bauen
	 * 
	 * 
	 * todo:
	 * ??? Rotation von Pod4x (complicated...) <= pnls format herausfinden
	 * Mehr Standardsupply eintragen
	 * NEUE LOOT CRATES EINFÜGEN
	 * 
	 * Neu:
	 * 
	 * Rework:
	 * alles, was auf der Kommandozeile ausgegeben wird, in Fenster packen
	 * 
	 * */
	static Main main;
	
	private Window window;
	private Modify modify;
	//private ParallelThread loadingThread;
	public Main() {
		//loadingThread = new ParallelThread();
		//loadingThread.start();
		
		window = new Window(this);
		window.showFileChooser();
		long timeStamp = System.currentTimeMillis();
		SaveLoader saveLoader = new SaveLoader(window.getSelectedFile());
		if (debug) System.out.println("Loadtime for save: " + (System.currentTimeMillis() - timeStamp) + "ms");
		modify = new Modify(saveLoader.getTi(), saveLoader.getPlayerAttributes(), saveLoader.getItems(), saveLoader.getContainer(), saveLoader.getStatistics(), saveLoader.getMessages(), saveLoader.getStoryEvents(), saveLoader.getSettings(), saveLoader.getLight(), saveLoader.getAdditionalLinesList());
		window.setModify(modify);
		
		window.show();
		if (debug) System.out.println("End of Main - Done");
	}
	
	public static Main start() { // doesnt really work
		if (main == null) main = new Main();
		return main;
	}
	public static void main(String[] args) {start();}
	
	public void saveFile() {
		String nameExtension = new SimpleDateFormat("yyyy.MM.dd_HH-mm-ss").format(Calendar.getInstance().getTime()); // remember to update (info / control) menu item
		File file = new File(window.getSelectedFile().getAbsolutePath().replaceAll("\\.json", "") + "_modified_" + nameExtension + ".json");
		try {
			file.createNewFile();
			Writer writer = new FileWriter(file);
			
			long timeNeeded = System.currentTimeMillis();
			String newSaveFileContent = modify.buildString();
			
			if (newSaveFileContent.endsWith("\r")) newSaveFileContent = newSaveFileContent.substring(0, newSaveFileContent.length()-1);
				
			if (debug) System.out.println("Done building String in " + (System.currentTimeMillis() - timeNeeded) + "ms");
			
			writer.write(newSaveFileContent);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
