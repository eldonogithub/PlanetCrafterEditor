package nicki0.editor.rearrangeable;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class JRearrangeableLabel<T> extends JLabel {
	private static final long serialVersionUID = 8326942939798637727L;
	protected static final Border pressedBorder = BorderFactory.createLoweredBevelBorder();
	protected static final Border defaultBorder = BorderFactory.createEtchedBorder();
	
	private T linkedElement = null;
	
	public JRearrangeableLabel(String pText) {
		super(pText);
	}
	
	public JRearrangeableLabel(String pText, T pLinkedElement) {
		super(pText);
		linkedElement = pLinkedElement;
	}
	
	public T getLinkedElement() {
		return linkedElement;
	}
}
