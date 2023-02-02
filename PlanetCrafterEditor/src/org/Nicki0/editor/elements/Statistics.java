package org.Nicki0.editor.elements;

public class Statistics {
	private int craftedObjects;
	private int totalSaveFileLoad;
	private int totalSaveFileTime;
	public Statistics(int pCraftedObjects, int pTotalSaveFileLoad, int pTotalSaveFileTime) {
		craftedObjects = pCraftedObjects;
		totalSaveFileLoad = pTotalSaveFileLoad;
		totalSaveFileTime = pTotalSaveFileTime;
	}
	@Override
	public String toString() {
		return "{\"craftedObjects\":" + craftedObjects + ",\"totalSaveFileLoad\":" + totalSaveFileLoad + ",\"totalSaveFileTime\":" + totalSaveFileTime + "}";
	}
}
