package org.Nicki0.editor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	/**
	 * Version 0.2
	 * Change: Shows not looted crates
	 */
	static Main main;
	
	Window window;
	Modify modify;
	public Main() {		
		java.util.Map<String, String> m = org.Nicki0.editor.elements.Object.translateGIdName;
		window = new Window(this);
		window.showFileChooser();
		long timeStamp = System.currentTimeMillis();
		SaveLoader saveLoader = new SaveLoader(window.getSelectedFile());
		System.out.println("Loadtime for save: " + (System.currentTimeMillis() - timeStamp) + "ms");
		modify = new Modify(saveLoader.getTi(), saveLoader.getPlayerAttributes(), saveLoader.getItems(), saveLoader.getContainer(), saveLoader.getStatistics(), saveLoader.getMessages(), saveLoader.getStoryEvents(), saveLoader.getSettings(), saveLoader.getLight());
		window.setModify(modify);
		
		window.show();
		System.out.println("End of Main - Done");
		System.out.println(modify.getTi().comparedToEarth());
		System.out.println("----All Items--->\n" + modify.getInventoryStock() + "\n<---All Items----");
	}
	public static Main start() { // doesnt really work
		if (main == null) main = new Main();
		return main;
	}
	public static void main(String[] args) {start();}
	
	public void saveFile() {
		File file = new File(window.getSelectedFile().getAbsolutePath().replaceAll("\\.json", "") + "_modified_" + System.currentTimeMillis() + ".json");
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			
			long timeNeeded = System.currentTimeMillis();
			String newSaveFileContent = modify.buildString().replaceAll("E-", "E-0").replaceAll("E-010", "E-10");
			System.out.println("Done building String in " + (System.currentTimeMillis() - timeNeeded) + "ms");
			
			writer.write(newSaveFileContent);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
