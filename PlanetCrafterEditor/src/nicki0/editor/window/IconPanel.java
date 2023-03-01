package nicki0.editor.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.awt.Dimension;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;

import nicki0.editor.elements.Object;
import nicki0.editor.pictures.Picture;

public class IconPanel {
	
	public static final double height = 50;
	@SuppressWarnings("unused")
	private static final int colorGrey = 256 / 8 * 7; 
	private static final Color TEXTBACKGROUNDCOLOR = Color.WHITE;//new Color(colorGrey, colorGrey, colorGrey);
	private final Icon heightAdjust;
	private static final BufferedImage tbON = Picture.loadPicture("Default/toggleButtonON.png");
	private static final BufferedImage tbOFF = Picture.loadPicture("Default/toggleButtonOFF.png");
	private static final ImageIcon toggleButtonON = new ImageIcon(tbON.getScaledInstance((int) (Toolkit.getDefaultToolkit().getScreenSize().height / 1080.0 * height/3.0 * tbON.getWidth(null) / tbON.getHeight(null)), (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 1080.0 * height/3.0), Image.SCALE_SMOOTH));
	private static final ImageIcon toggleButtonOFF = new ImageIcon(tbOFF.getScaledInstance((int) (Toolkit.getDefaultToolkit().getScreenSize().height / 1080.0 * height/3.0 * tbOFF.getWidth(null) / tbOFF.getHeight(null)), (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 1080.0 * height/3.0), Image.SCALE_SMOOTH));
	private static final ImageIcon exitNormal = new ImageIcon(Picture.loadPicture("Default/ExitNormal.png"));
	private static final ImageIcon exitHover = new ImageIcon(Picture.loadPicture("Default/ExitHover.png"));
	
	private static final int INSIDERIGHT = 1;
	private static final int INSIDELEFT = 2;
	
	private JDialog dialog;
	private JPanel panel;
	private GridBagConstraints gbc;
	private JScrollPane jsp;
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	private List<List<Component>> columnComponentList;
	private String returnValue;
	
	private Window windowSetModalityOnDispose;
	private ModalExclusionType resetModalExclusionType;
	
	private JButton menuBarLeftButton;
	
	private Rectangle frameBounds;
	private int positionOnFrame;
	
	public IconPanel() {
		dialog = new JDialog();
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		
		panel = new JPanel(new GridBagLayout());
		
		jsp = new JScrollPane(panel);
		jsp.getVerticalScrollBar().setUnitIncrement(32);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		dialog.add(jsp);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setModal(true);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setUndecorated(true);
		
		panel.setBackground(Color.WHITE);
		
		heightAdjust = new ImageIcon(new BufferedImage(1, (int) (screenSize.height / 1080.0 * height), BufferedImage.TYPE_INT_ARGB));
		
		columnComponentList = new ArrayList<List<Component>>();
		
		panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close");
		panel.getActionMap().put("close", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		
		dialog.addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent e) {
				dialog.setVisible(false);
				dialog.dispose();
			}
			
			@Override
			public void windowGainedFocus(WindowEvent e) {}
		});
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if (windowSetModalityOnDispose != null) windowSetModalityOnDispose.setModalExclusionType(resetModalExclusionType);
			}
		});
	}
	public void show() {
		show(columnComponentList);
	}
	private void show(List<List<Component>> scaledComponents) {
		for (int i = 0; i < scaledComponents.size(); i++) {
			gbc.gridx = i;
			for (int j = 0; j < scaledComponents.get(i).size(); j++) {
				gbc.gridy = j;
				panel.add(scaledComponents.get(i).get(j), gbc);
			}
		}
		
		showUpperBar();
		
		dialog.pack();
		dialog.setSize(dialog.getWidth() + 20, (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 2 /3));
		if (frameBounds != null) {
			int posShiftX = 0;
			switch (positionOnFrame) {
			case INSIDERIGHT:
				posShiftX =  (int) (frameBounds.getWidth() - dialog.getWidth());
				break;
			case INSIDELEFT:
				posShiftX = 0;
				break;
			}
			dialog.setBounds((int) (frameBounds.getX() + posShiftX), (int) frameBounds.getY(), dialog.getWidth(), (int) frameBounds.getHeight());
		}
		
		dialog.setVisible(true);
	}
	private void showUpperBar() {
		JPanel menuBarPanel = new JPanel(new BorderLayout());
		menuBarPanel.setBackground(Color.WHITE);
		if (windowSetModalityOnDispose == null) {
			JButton exitButton = new JButton(exitNormal);
			exitButton.setRolloverIcon(exitHover);
			exitButton.setPressedIcon(exitHover);
			exitButton.setContentAreaFilled(false);
			exitButton.setFocusable(false);
			exitButton.setPreferredSize(new Dimension(exitNormal.getIconWidth(), exitNormal.getIconHeight()));
			exitButton.setBorderPainted(false);
			exitButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.dispose();
				}
			});
			menuBarPanel.add(exitButton, BorderLayout.EAST);
		}
		if (menuBarLeftButton != null) {
			menuBarLeftButton.setContentAreaFilled(false);
			menuBarLeftButton.setFocusable(false);
			menuBarLeftButton.setPreferredSize(new Dimension(menuBarLeftButton.getPreferredSize().width, exitNormal.getIconHeight()));
			menuBarPanel.add(menuBarLeftButton, BorderLayout.WEST);
		}
		if (menuBarPanel.getComponentCount() != 0) dialog.getContentPane().add(menuBarPanel, BorderLayout.NORTH);
	}
	private List<Component> pictureLabelFromGId(List<String> pGId) {
		List<Component> componentList = new ArrayList<Component>();
		for (String s : pGId) {
			JLabel label;
			Icon icon = Object.translateGIdEONCImageIcon.get(s);
			if (icon != null) {
				label = new JLabel(icon);
			} else {
				label = new JLabel(heightAdjust);
				label.setText(s);
			}
			label.setOpaque(true);
			label.setBackground(TEXTBACKGROUNDCOLOR);
			componentList.add(label);
		}
		return componentList;
	}
	private List<Component> pictureButtonFromGId(List<String> pGId) {
		List<Component> componentList = new ArrayList<Component>();
		for (String s : pGId) {
			JButton button;
			Icon icon = Object.translateGIdEONCImageIcon.get(s);
			if (icon != null) {
				button = new JButton(icon);
			} else {
				button = new JButton(heightAdjust); button.setText(s);
			}
			button.setBackground(TEXTBACKGROUNDCOLOR);
			button.setContentAreaFilled(false);
			button.setFocusable(false);
			//button.setBorder(null);
			button.setPreferredSize(new Dimension(button.getIcon().getIconWidth(), button.getIcon().getIconHeight()));
			button.addActionListener(new ActionListener() {
				private final String gId = s;
				@Override
				public void actionPerformed(ActionEvent e) {
					
					returnValue = gId;
					dialog.setVisible(false);
					dialog.dispose();
				}
			});
			componentList.add(button);
		}
		return componentList;
	}
	private List<Component> textFieldFromString(List<String> pText) {
		List<Component> componentList = new ArrayList<Component>();
		for (String s : pText) {
			JTextField jtf = new JTextField(s);
			jtf.setEditable(false);
			jtf.setDisabledTextColor(Color.BLACK);
			jtf.setBackground(null);
			jtf.setBorder(null);
			componentList.add(jtf);
		}
		return componentList;
	}
	private List<Component> toggleButtonFromGId(List<Boolean> pStates) {
		List<JToggleButton> toggleList = new ArrayList<JToggleButton>();
		
		for (boolean b : pStates) {
			JToggleButton jtb = new JToggleButton(toggleButtonOFF, b);
			
			jtb.setBorder(null);
			jtb.setBackground(null);
			jtb.setDisabledIcon(toggleButtonOFF);
			jtb.setSelectedIcon(toggleButtonON);
			jtb.setPressedIcon(toggleButtonON);
			jtb.setContentAreaFilled(false);
			toggleList.add(jtb);
		}
		toggleList.get(0).addItemListener(new ItemListener() {
			List<JToggleButton> tglList = toggleList.subList(1, toggleList.size());
			@Override
			public void itemStateChanged(ItemEvent e) {
				switch (e.getStateChange()) {
				case ItemEvent.SELECTED:
					for (JToggleButton jtb : tglList) jtb.setSelected(true);
					break;
				case ItemEvent.DESELECTED:
					for (JToggleButton jtb : tglList) jtb.setSelected(false);
					break;
				}
			}
		});
		return toggleList.stream().map(Component.class::cast).collect(Collectors.toList());
	}
	private List<Component> space(int pSpace) {
		List<Component> result = new ArrayList<Component>();
		result.add(new JLabel(new ImageIcon(new BufferedImage((int) (screenSize.width / 1920.0 * pSpace), 1, BufferedImage.TYPE_INT_ARGB))));
		return result;
	}
	public String getSelection() {return returnValue;}
	public List<Boolean> getSelectedGIds() {
		for (List<Component> clst : columnComponentList) {
			if (clst.get(0) instanceof JToggleButton) {
				return clst.stream().map(JToggleButton.class::cast).map(JToggleButton::isSelected).collect(Collectors.toList());
			}
		}
		assert false : "No Toggle Button List";
		return null;
	}
	public void appendPictureButton(List<String> pGId) {
		columnComponentList.add(pictureButtonFromGId(pGId));
	}
	public void appendPictureLabel(List<String> pGId) {
		columnComponentList.add(pictureLabelFromGId(pGId));
	}
	public void appendTextField(List<String> pText) {
		columnComponentList.add(textFieldFromString(pText));
	}
	public void appendTextField(ArrayList<Integer> pText) {
		/*List<String> lst = new ArrayList<String>();
		for (int i : pText) lst.add(i + "");
		appendTextField(lst);*/
		appendTextField(pText.stream().map(i -> i.toString()).collect(Collectors.toList()));
	}
	public void appendToggleButton(List<Boolean> pStates) {
		columnComponentList.add(toggleButtonFromGId(pStates));
	}
	public void appendSpace(int pSpace) {
		columnComponentList.add(space(pSpace));
	}
	public void closeOnLeave(Window w) {
		windowSetModalityOnDispose = w;
		resetModalExclusionType = w.getModalExclusionType();
		windowSetModalityOnDispose.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
	}
	/*public JScrollPane getScrollPane() {
		return jsp;
	}*/
	public void showOnRight(Rectangle pWindowBounds) {
		frameBounds = pWindowBounds;
		positionOnFrame = INSIDERIGHT;
	}
	public void showOnLeft(Rectangle pWindowBounds) {
		frameBounds = pWindowBounds;
		positionOnFrame = INSIDELEFT;
	}
	public void setMenuBarLeftButton(JButton pButton, ActionListener pActionListener) {
		menuBarLeftButton = pButton;
		menuBarLeftButton.addActionListener(pActionListener);
	}
	public void setTitle(String s) {
		dialog.setTitle(s);
	}
}
