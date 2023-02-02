package org.Nicki0.editor.elements;

public class Message {
	private String message;
	private boolean isRead;
	public Message(String pMessage, boolean pIsRead) {
		message = pMessage;
		isRead = pIsRead;
	}
	@Override
	public String toString() {
		return "{\"stringId\":\"" + message + "\",\"isRead\":" + isRead + "}";
	}
}
