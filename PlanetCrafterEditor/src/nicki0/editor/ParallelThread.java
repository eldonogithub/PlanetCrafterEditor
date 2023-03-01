package nicki0.editor;

import nicki0.editor.elements.Object;

public class ParallelThread extends Thread {
	
	public ParallelThread() {
		super();
	}
	
	@Override
	public void run() {
		new ParallelThreadObject().start();
		new ParallelThreadMap().start();
	}
}
class ParallelThreadMap extends Thread {
	@Override
	public void run() {
		Map.ping();
	}
}
class ParallelThreadObject extends Thread {
	@Override
	public void run() {
		Object.ping();
	}
}
