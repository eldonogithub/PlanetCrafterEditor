package nicki0.editor.elements;

public class Settings {
	private String mode;
	private boolean hasPlayedIntro = false;
	
	private boolean emptyLine = false;
	public Settings() {
		emptyLine = true;
	}
	
	public Settings(String pMode, boolean pHasPlayedIntro) {
		mode = pMode;
		hasPlayedIntro = pHasPlayedIntro;
	}
	@Override
	public String toString() {
		return emptyLine?"":"{\"mode\":\"" + mode + "\",\"hasPlayedIntro\":" + hasPlayedIntro + "}";
	}
}
