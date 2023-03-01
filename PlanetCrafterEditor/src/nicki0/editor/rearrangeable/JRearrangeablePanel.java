package nicki0.editor.rearrangeable;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class JRearrangeablePanel extends JPanel {
	private static final long serialVersionUID = 4574433285591802400L;
	
	private List<Component> componentList;
	private JPanel panel = this;
	private Component pressedComponent;
	public JRearrangeablePanel() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		componentList = new ArrayList<Component>();
		pressedComponent = null;
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				pressedComponent = panel.getComponentAt(e.getPoint());
				if (pressedComponent instanceof JRearrangeableLabel) ((JRearrangeableLabel<?>)pressedComponent).setBorder(JRearrangeableLabel.pressedBorder);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if (pressedComponent != null) if (pressedComponent instanceof JRearrangeableLabel) ((JRearrangeableLabel<?>)pressedComponent).setBorder(JRearrangeableLabel.defaultBorder);
				pressedComponent = null;
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (pressedComponent != null) {
					
					Point mp = panel.getMousePosition();
					if (mp == null) return;
					int pressedIndex = componentList.indexOf(pressedComponent);
					assert pressedIndex != -1;
					// find comp where currently
					Component hoverCurrently = null;
					for (Component c : componentList) {
						if (mp.getY() > c.getY() && mp.getY() < c.getY() + c.getHeight()) hoverCurrently = c;
					}
					if (hoverCurrently == null) return;
					int hoverIndex = componentList.indexOf(hoverCurrently);
					if (hoverIndex == -1 || hoverIndex == pressedIndex) return;
					// end find comp where currently
					if (hoverIndex > pressedIndex) {
						Collections.rotate(componentList.subList(pressedIndex, hoverIndex+1), hoverIndex - pressedIndex);
					} else {
						Collections.rotate(componentList.subList(hoverIndex, pressedIndex+1), hoverIndex - pressedIndex);
					}
					reorder(componentList);
				}
			}
		});
	}
	private void reorder(List<Component> lst) {
		super.removeAll();
		for (Component c : lst) {
			super.add(c);
		}
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	@Override
	public Component add(Component component) {
		if (component instanceof JRearrangeableLabel) {
			((JRearrangeableLabel<?>)component).setBorder(JRearrangeableLabel.defaultBorder);
		}
		super.add(component);
		componentList.add(component);
		return component;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getLinkedElementList() {
		List<T> linkedElementList = new ArrayList<T>();
		for (Component c : componentList) {
			if (c instanceof JRearrangeableLabel<?>) linkedElementList.add(((JRearrangeableLabel<T>)c).getLinkedElement());
		}
		return linkedElementList;
	}
}
