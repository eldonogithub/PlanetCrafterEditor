package nicki0.editor.rearrangeable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;

import nicki0.editor.elements.Item;
import nicki0.editor.pictures.Picture;

public class TeleporterRearrangeableDialog extends JDialog {
	private static final long serialVersionUID = 4429897320032778252L;
	
	private static final ImageIcon exitNormal = new ImageIcon(Picture.loadPicture("Default/ExitNormal.png"));
	private static final ImageIcon exitHover = new ImageIcon(Picture.loadPicture("Default/ExitHover.png"));
	private static final ImageIcon okNormal = new ImageIcon(Picture.loadPicture("Default/OKNormal.png"));
	private static final ImageIcon okHover = new ImageIcon(Picture.loadPicture("Default/OKHover.png"));
	
	private JScrollPane jsp;
	private JDialog dialog = this;
	private JRearrangeablePanel panel;
	
	private List<Item> returnList = null;
	
	public TeleporterRearrangeableDialog(List<Item> itemList) {
		super();
		
		panel = new JRearrangeablePanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		jsp = new JScrollPane(panel);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.getContentPane().add(jsp);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setUndecorated(true);
		
		makeTransparent();
		
		for (Item i : itemList) {
			JRearrangeableLabel<Item> e = new JRearrangeableLabel<Item>("<html>" + (i.getText().equals("...")?((int)(i.getX() + 0.5) + " : " + (int)(i.getY() + 0.5) + " : " + (int)(i.getZ() + 0.5)):i.getText()) + "</html>", i);
			e.setOpaque(true);
			e.setBackground(Color.WHITE);
			panel.add(e);
		}
		
		panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close");
		panel.getActionMap().put("close", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
	}
	public void show(Rectangle pWindowBounds) {
		this.getContentPane().add(addOkExitButton(), BorderLayout.NORTH);
			
		this.pack();
		this.setBounds((int) (pWindowBounds.getX() + pWindowBounds.getWidth() - dialog.getWidth() - 20), (int) pWindowBounds.getY(), this.getWidth() + 20, (int) pWindowBounds.getHeight());
		this.setVisible(true);
	}
	
	private void makeTransparent() {
		panel.setOpaque(true);
		panel.setBackground(new Color(0,0,0,0));//Color.WHITE
		jsp.setOpaque(true);
		jsp.setBackground(new Color(0,0,0,0));
		jsp.setBorder(null);
		this.setBackground(new Color(0,0,0,0));
	}
	
	private JPanel addOkExitButton() {
		JPanel upperPanel = new JPanel(new BorderLayout());
		upperPanel.setBackground(Color.WHITE);
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.setBackground(Color.WHITE);
		JButton exitButton = new JButton(exitNormal);
		JButton okButton = new JButton(okNormal);
		exitButton.setRolloverIcon(exitHover);
		okButton.setRolloverIcon(okHover);
		exitButton.setPressedIcon(exitHover);
		okButton.setPressedIcon(okHover);
		exitButton.setContentAreaFilled(false);
		okButton.setContentAreaFilled(false);
		exitButton.setFocusable(false);
		okButton.setFocusable(false);
		exitButton.setPreferredSize(new Dimension(exitNormal.getIconWidth(), exitNormal.getIconHeight()));
		okButton.setPreferredSize(new Dimension(okNormal.getIconWidth(), okNormal.getIconHeight()));
		exitButton.setBorderPainted(false);
		okButton.setBorderPainted(false);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				returnList = panel.getLinkedElementList();
				dialog.dispose();
			}
		});
		buttonPanel.add(exitButton, BorderLayout.EAST);
		buttonPanel.add(okButton, BorderLayout.WEST);
		upperPanel.add(buttonPanel, BorderLayout.EAST);
		
		return upperPanel;
	}
	
	public List<Item> getOutput() {
		return returnList;
	}
}
