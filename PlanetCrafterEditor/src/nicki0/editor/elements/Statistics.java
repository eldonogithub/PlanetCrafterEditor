package nicki0.editor.elements;

public class Statistics {
	private int craftedObjects;
	private int totalSaveFileLoad;
	private int totalSaveFileTime;
	
	private boolean emptyLine = false;
	public Statistics() {
		emptyLine = true;
	}
	
	public Statistics(int pCraftedObjects, int pTotalSaveFileLoad, int pTotalSaveFileTime) {
		craftedObjects = pCraftedObjects;
		totalSaveFileLoad = pTotalSaveFileLoad;
		totalSaveFileTime = pTotalSaveFileTime;
	}
	@Override
	public String toString() {
		return emptyLine?"":"{\"craftedObjects\":" + craftedObjects + ",\"totalSaveFileLoad\":" + totalSaveFileLoad + ",\"totalSaveFileTime\":" + totalSaveFileTime + "}";
	}
}
