package org.Nicki0.editor.elements;

public class Settings {
	private String mode;
	boolean hasPlayedIntro = false;
	public Settings(String pMode, boolean pHasPlayedIntro) {
		mode = pMode;
		hasPlayedIntro = pHasPlayedIntro;
	}
	@Override
	public String toString() {
		return "{\"mode\":\"" + mode + "\",\"hasPlayedIntro\":" + hasPlayedIntro + "}";
	}
}
