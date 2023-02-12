package org.Nicki0.editor;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import org.Nicki0.editor.elements.Container;
import org.Nicki0.editor.elements.Item;
import org.Nicki0.editor.elements.Object;
import org.Nicki0.editor.window.Buildings;

public class Window {
	JFrame frame;
	public JPanel panel;
	JMenuBar jMenuBar;
	JMenu[] jMenu;
	JMenuItem[][] jMenuItem;
	JLabel jLabelPicture;
	JLabel jLabelCoordinates;
	JLabel jLabelCave;
	
	int selectedImage = 0;
	Icon[] pictureMap = Map.pictureMap;
	boolean selectedCave = false;
	private File selectedFile = new File( System.getProperty("user.home") + "\\AppData\\LocalLow\\MijuGames\\Planet Crafter\\TESTWORLD.json");
	
	Main main;
	Modify modify;
	Buildings buildings;
	
	public boolean switchShowingNotLootedCrates = false;
	
	public Window(Main pMain) {
		main = pMain;
		
		initComponents();
		initListeners();
	}
	
	private void initComponents() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(selectedFile.getName());
		frame.setResizable(false);
		
		panel = new JPanel(null);
		
		buildings = new Buildings(this);
		
		jMenuBar = new JMenuBar();
		jMenu = new JMenu[4];
		for (int i = 0; i < jMenu.length; i++) jMenu[i] = new JMenu();
		jMenuItem = new JMenuItem[jMenu.length][];
		jMenuItem[0] = new JMenuItem[3];
		jMenuItem[1] = new JMenuItem[6];
		jMenuItem[2] = new JMenuItem[2];
		jMenuItem[3] = new JMenuItem[1];
		for (int i = 0; i < jMenuItem.length; i++) for (int j = 0; j < jMenuItem[i].length; j++) jMenuItem[i][j] = new JMenuItem();
		
		jMenu[0].setText("File");
		//jMenuItem[0][0].setText("Open (deactivated)");
		jMenuItem[0][1].setText("Save / Export");
		jMenuItem[0][2].setText("Exit");
		
		jMenu[1].setText("View");
		jMenuItem[1][0].setText("Switch Picture");
		jMenuItem[1][1].setText("Switch Cave");
		jMenuItem[1][2].setText("Show Building / Item");
		jMenuItem[1][3].setText("Remove Building / Item");
		jMenuItem[1][4].setText("Inventory Overview");
		jMenuItem[1][5].setText("Show not looted Crates");
		
		jMenu[2].setText("Check");
		jMenuItem[2][0].setText("Check for double Inventory entries of Items");
		jMenuItem[2][1].setText("Check for missing containers for inventories");
		
		jMenu[3].setText("?");
		jMenuItem[3][0].setText("Update");
		
		jLabelPicture = new JLabel(pictureMap[selectedImage]);
		jLabelPicture.setSize(jLabelPicture.getIcon().getIconWidth(), jLabelPicture.getIcon().getIconHeight());
		jLabelPicture.setLocation(0, 0);
		
		jLabelCoordinates = new JLabel("0 : 0 : 0");
		jLabelCoordinates.setSize(200, 100);
		
		jLabelCave = new JLabel(Map.pictureCave);
		jLabelCave.setBounds(-271, -35, jLabelCave.getIcon().getIconWidth(), jLabelCave.getIcon().getIconHeight());
		jLabelCave.setVisible(selectedCave);
		
		buildScreen();
	}
	
	private void buildScreen() {
		frame.add(panel);
		frame.setJMenuBar(jMenuBar);
		for (int i = 0; i < jMenu.length; i++) {
			jMenuBar.add(jMenu[i]);
			for (int j = 0; j < jMenuItem[i].length; j++) jMenu[i].add(jMenuItem[i][j]);
			jMenu[0].remove(jMenuItem[0][0]);
		}
		panel.add(jLabelCoordinates);
		panel.add(jLabelCave);
		panel.add(jLabelPicture);
		
		frame.setSize(jLabelPicture.getIcon().getIconWidth() + 16 -1, jLabelPicture.getIcon().getIconHeight() + 16 + 46 -1);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.getWidth() - frame.getWidth())/2, (int)(screenSize.getHeight() - frame.getHeight())/2);
		
		
		jLabelCoordinates.setLocation(0, frame.getHeight()-jLabelCoordinates.getHeight()-25);
	}
	protected void showFileChooser() {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setDoubleBuffered(true);
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		String stdPath = System.getProperty("user.home") + "\\AppData\\LocalLow\\MijuGames\\Planet Crafter";
		jFileChooser.setCurrentDirectory(new File(stdPath));
		jFileChooser.addChoosableFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				return "Savefile";
			}
			
			@Override
			public boolean accept(File f) {
				if (f.getName().equalsIgnoreCase("Backup.json")) return false;
				if (f.getName().endsWith(".json")) return true;
				return false;
			}
		});
		jFileChooser.setAcceptAllFileFilterUsed(false);
		int returnVal = jFileChooser.showOpenDialog(new JFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (jFileChooser.getSelectedFile() != null) {
				selectedFile = jFileChooser.getSelectedFile();
				frame.setTitle(selectedFile.getName());
			}
		} else if (returnVal == JFileChooser.CANCEL_OPTION) {
			System.exit(0);
		}
	}
	public void show() {frame.setVisible(true);}
	public void setModify(Modify pModify) {modify = pModify;}
	public File getSelectedFile() {
		return selectedFile;
	}
	private void switchPicture() {
		selectedImage = (selectedImage + 1)%pictureMap.length;
		jLabelPicture.setIcon(pictureMap[selectedImage]);
	}
	private void switchCave() {
		selectedCave = !selectedCave;
		jLabelCave.setVisible(selectedCave);
	}
	private void updateCoordinates(MouseEvent e) {
		Point p = Map.adaptMousePosition(e.getPoint());
		Point pos = Map.getCoordinatesFromPosition(p.x, p.y);
		jLabelCoordinates.setText(pos.x + " : ? : " + pos.y);
		//System.out.println(e.getX() + " : " + e.getY());
	}
	private String getInventoryStock(long id) {
		String invStockStr = "";
		java.util.Map<String, Integer> map = new HashMap<String, Integer>();
		Container c = modify.getContainerFromID(id);
		if (c == null) return "Inventory " + id + " not Found\n";
		List<Long> woIdsToFind = c.getWoIds();
		for (Long l : woIdsToFind) {
			Item item = modify.getItemFromID(l);
			if (item != null) {
				String gId = item.getGId();
				if (map.get(gId) == null) map.put(gId, 0);
				map.put(gId, map.get(gId)+1);
			}
		}
		invStockStr = woIdsToFind.size() + " / " + c.getSize() + "\n";
		for (String s : map.keySet()) invStockStr = invStockStr + "   " + s + (Object.translateGIdName.get(s) != null?" (" + Object.translateGIdName.get(s) + ")":"") + ": " + map.get(s) + "\n";
		return invStockStr;
	}
	private void initListeners() {
		// Open
		jMenuItem[0][0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {/*showFileChooser();*/}
		});
		// Save / Export
		jMenuItem[0][1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				main.saveFile();
			}
		});
		// Exit
		jMenuItem[0][2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {System.exit(0);}
		});
		// switch picture
		jMenuItem[1][0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switchPicture();
			}
		});
		// switch cave
		jMenuItem[1][1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switchCave();
			}
		});
		// Show Building / Item
		jMenuItem[1][2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String buildingToShow = JOptionPane.showInputDialog("Building to show:"
						+ "\n  Container1\n  Container2\n  EscapePod\n  pod\n  Pod4x\n  Teleporter1\n  AutoCrafter\n  EnergyGenerator6\n  OreExtractor3\n  Biodome\n  Drill4\n  Heater5\n  GasExtractor1\n  Beehive2\n  WaterCollector2\n  ButterflyFarm2\n  TreeSpreader0\n  TreeSpreader1\n  TreeSpreader2\n  GrassSpreader1\n  SeedSpreader1\n  SeedSpreader2\n  ... etc.");
				if (buildingToShow != null) buildings.showBuildings(Object.allItemsOfType(buildingToShow, modify.getItemList()), buildingToShow);
			}
		});
		// Remove Building / Item
		jMenuItem[1][3].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String remBuildStr = "\n all";
				for (String s : buildings.gIDsInList) remBuildStr = remBuildStr + "\n  " + s;
				
				String buildingToRemove = JOptionPane.showInputDialog("Building to remove:" + remBuildStr);
				if (buildingToRemove == null) return;
				if (!buildingToRemove.equalsIgnoreCase("all")) buildings.removeBuildings(buildingToRemove);
				else for (String s : buildings.gIDsInList) {buildings.removeBuildings(s);}
			}
		});
		// Inventory Overview
		jMenuItem[1][4].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				long id = 1;
				boolean loop = true;
				while (loop) {
					loop = false;
					
					String inventoryStock = getInventoryStock(id);
					
					String input = JOptionPane.showInputDialog(null, inventoryStock + "Input other woId: ", "Inventory Overview for " + id, JOptionPane.PLAIN_MESSAGE);
					boolean isNumber = true;
					long inputLong = 1;
					try {
						inputLong = Long.parseLong(input);
					} catch (NumberFormatException nfe) {
						isNumber = false;
					}
					if (input != null && isNumber) {
						loop = true;
						id = inputLong;
					}
				}
			}
		});
		// Show Not Looted Crates
		jMenuItem[1][5].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Item> notLootedCratesInWorld = modify.getNotLootedCrates();
				if (notLootedCratesInWorld.size() == 0) {
					JOptionPane.showMessageDialog(null, "All Loot Crates looted!");
					return;
				}
				switchShowingNotLootedCrates = !switchShowingNotLootedCrates;
				if (switchShowingNotLootedCrates) buildings.showBuildings(notLootedCratesInWorld, Object.LOOTCRATEGID);
				else buildings.removeBuildings("loot crates");
			}
		});
		// Check for Doubles
		jMenuItem[2][0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				main.modify.testForDouble();
			}
		});
		// Check for missing containers for invs
		jMenuItem[2][1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				main.modify.testForInventoryWithoutContainer();
			}
		});
		// Update
		jMenuItem[3][0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				URL url;
				try {
					url = new URL("https://github.com/mcnicki2002/PlanetCrafterEditor");
					JCommands.openWebpage(url);
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
			}
		});
		// Mouse
		frame.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				updateCoordinates(e);
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {}
		});
		frame.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				switch (e.getButton()) {
				case MouseEvent.BUTTON1:
					Point p = Map.adaptMousePosition(e.getPoint());
					List<Item> clickedOnBuilding = buildings.objectsAtPosition(p.x, p.y);
					
					for (Item i : clickedOnBuilding) {
						Container c = modify.getContainerFromID(i.getLiId());
						String inv = "";
						if (c != null) inv = getInventoryStock(c.getId()).replaceAll("([0-9]* / [0-9]*\n|   )","").replaceAll("\n", "; ");
						System.out.println(i.getGId() + " " + i.getText() + inv + " at Position: " + i.getPositionAsString());
					}
					break;
				case MouseEvent.BUTTON2:
					// TODO vllt. teleport funktionalität
					break;
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
	}
}
