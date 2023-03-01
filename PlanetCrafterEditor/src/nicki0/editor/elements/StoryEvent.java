package nicki0.editor.elements;

public class StoryEvent {
	private String storyEvent;
	public StoryEvent(String pStoryEvent) {
		storyEvent = pStoryEvent;
	}
	@Override
	public String toString() {
		return "{\"stringId\":\"" + storyEvent + "\"}";
	}
}
