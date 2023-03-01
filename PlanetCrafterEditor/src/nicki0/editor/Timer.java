package nicki0.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Timer implements ActionListener{
	public long counterGIF = 0L;
	private javax.swing.Timer timerGIF = new javax.swing.Timer(100, this);
	private javax.swing.Timer timerUpdateGUI = new javax.swing.Timer(50, this);
	private Window window; 
	
	public Timer(Window pWindow) {
		window = pWindow;
		timerGIF.start();
		timerUpdateGUI.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==timerGIF) {
			counterGIF++;
			window.timerGifEvent();
		}
		if (e.getSource()==timerUpdateGUI) {
			window.setUpdatedCoordinates();
		}
	}
}
