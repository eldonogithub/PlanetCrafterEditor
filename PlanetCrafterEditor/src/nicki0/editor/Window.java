package nicki0.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import nicki0.editor.elements.Container;
import nicki0.editor.elements.Item;
import nicki0.editor.elements.Object;
import nicki0.editor.pictures.Picture;
import nicki0.editor.rearrangeable.TeleporterRearrangeableDialog;
import nicki0.editor.window.Buildings;
import nicki0.editor.window.IconPanel;
import nicki0.editor.window.MapPanel;

public class Window {
	public JFrame frame;
	public MapPanel panel;
	private JMenuBar jMenuBar;
	private JMenu[] jMenu;
	private JMenuItem[][] jMenuItem;
	private JLabel jLabelCoordinates;
	private String coordinatesText = "";
	private JLabel currentActionAdvise;
	private JButton[] jMenuBarButtons;
	private JLabel separatorRight;
	
	private int selectedImage = 0;
	private boolean selectedCave = false;
	private File selectedFile = new File( System.getProperty("user.home") + "\\AppData\\LocalLow\\MijuGames\\Planet Crafter\\TESTWORLD.json");
	
	private Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
	private Dimension screenSize = defaultToolkit.getScreenSize();
	private Font defaultFont = javax.swing.UIManager.getDefaults().getFont("Label.font");
	private Dimension lastFrameSize = new Dimension();
	
	public Main main;
	public Modify modify;
	public Buildings buildings;
	public Timer timer;
	
	//public boolean switchShowingNotLootedCrates = false;
	private java.util.Map<String, Boolean> switchState = new TreeMap<>(Comparator.comparing(s->s, String.CASE_INSENSITIVE_ORDER));
	private long showBuildingLastClosed = System.currentTimeMillis();
	
	private static final int MenuFILE = 0;
	//private static final int ItemFILE_OPEN = 0;
	private static final int ItemFILE_EXPORT = 0;
	private static final int ItemFILE_EXIT = 1;
	private static final int MenuFILE_COUNT = 2;
	
	private static final int MenuVIEW = 1;
	//private static final int ItemVIEW_SWITCHMAP = 0;
	//private static final int ItemVIEW_SWITCHCAVE = 1;
	//private static final int ItemVIEW_SHOWBUILDING = 2;
	private static final int ItemVIEW_INVOVERVIEW = 0;
	private static final int ItemVIEW_COMPARETOEARTH = 1;
	private static final int MenuVIEW_COUNT = 2;
	
	private static final int MenuCHECK = 2;
	private static final int ItemCHECK_DOUBLEITEM = 0;
	private static final int ItemCHECK_AREIDSUNIQUE = 1;
	//private static final int ItemCHECK_MISSINGCONTAINER = 2;
	private static final int MenuCHECK_COUNT = 2;
	
	private static final int MenuEDIT = 3;
	private static final int ItemEDIT_INVSIZE = 0;
	private static final int ItemEDIT_SETSUPPLYOFGENINV = 1; // set supply of generating inventories
	private static final int ItemEDIT_CONNECTINVS = 2;
	private static final int ItemEDIT_RESETCONNECTEDINVS = 3;
	private static final int ItemEDIT_MOVEBUILDINGS = 4;
	private static final int ItemEDIT_REARRANGETELEPORTER = 5;
	private static final int MenuEDIT_COUNT = 6;
	
	private static final int MenuCHEAT = 4;
	private static final int ItemCHEAT_SPAWNINTOINV = 0;
	private static final int MenuCHEAT_COUNT = 1;
	
	private static final int MenuHELP = 5;
	private static final int ItemHELP_INFO = 0;
	private static final int ItemHELP_OPENFOLDER = 1;
	private static final int ItemHELP_UPDATE = 2;
	private static final int ItemHELP_RECOMMENDEDMAP = 3;
	private static final int MenuHELP_COUNT = 4;
	
	private static final int ButtonMenu_MAP = 0;
	private static final int ButtonMenu_CAVE = 1;
	private static final int ButtonMenu_SHOWBUILDING = 2;
	private static final int ButtonMenu_COUNT = 3;
	
	
	public Window(Main pMain) {
		main = pMain;
		
		initComponents();
		initListeners();
		
		timer = new Timer(this);
	}
	
	private void initComponents() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(selectedFile.getName());
		//frame.setResizable(false);
		
		panel = new MapPanel(this);
		
		buildings = new Buildings(this);
		
		jMenuBar = new JMenuBar();
		jMenu = new JMenu[6];
		for (int i = 0; i < jMenu.length; i++) jMenu[i] = new JMenu();
		jMenuItem = new JMenuItem[jMenu.length][];
		jMenuItem[MenuFILE] = new JMenuItem[MenuFILE_COUNT];
		jMenuItem[MenuVIEW] = new JMenuItem[MenuVIEW_COUNT];
		jMenuItem[MenuCHECK] = new JMenuItem[MenuCHECK_COUNT];
		jMenuItem[MenuEDIT] = new JMenuItem[MenuEDIT_COUNT];
		jMenuItem[MenuCHEAT] = new JMenuItem[MenuCHEAT_COUNT];
		jMenuItem[MenuHELP] = new JMenuItem[MenuHELP_COUNT];
		for (int i = 0; i < jMenuItem.length; i++) for (int j = 0; j < jMenuItem[i].length; j++) jMenuItem[i][j] = new JMenuItem();
		
		jMenu[MenuFILE].setText("File");
		jMenuItem[MenuFILE][ItemFILE_EXPORT].setText("Save / Export");
		jMenuItem[MenuFILE][ItemFILE_EXIT].setText("Exit");
		
		jMenu[MenuVIEW].setText("View");
		//jMenuItem[MenuVIEW][ItemVIEW_SWITCHMAP].setText("Switch Map");
		//jMenuItem[MenuVIEW][ItemVIEW_SWITCHCAVE].setText("Switch Cave");
		//jMenuItem[MenuVIEW][ItemVIEW_SHOWBUILDING].setText("Show Building / Item");
		jMenuItem[MenuVIEW][ItemVIEW_INVOVERVIEW].setText("Inventory Overview");
		jMenuItem[MenuVIEW][ItemVIEW_COMPARETOEARTH].setText("Compare Progress to Earth");
		
		jMenu[MenuCHECK].setText("Check");
		jMenuItem[MenuCHECK][ItemCHECK_DOUBLEITEM].setText("Check for doubled Inventory entries of Items");
		jMenuItem[MenuCHECK][ItemCHECK_AREIDSUNIQUE].setText("Check if all Item IDs are unique");
		//jMenuItem[MenuCHECK][ItemCHECK_MISSINGCONTAINER].setText("Check for missing containers for inventories");
		
		jMenu[MenuEDIT].setText("Edit");
		jMenuItem[MenuEDIT][ItemEDIT_INVSIZE].setText("Change Inventory Size");
		jMenuItem[MenuEDIT][ItemEDIT_SETSUPPLYOFGENINV].setText("Set Supply of every generating structure");
		jMenuItem[MenuEDIT][ItemEDIT_CONNECTINVS].setText("Connect inventories");
		jMenuItem[MenuEDIT][ItemEDIT_RESETCONNECTEDINVS].setText("Disconnect connected inventories");
		jMenuItem[MenuEDIT][ItemEDIT_MOVEBUILDINGS].setText("Move Buildings in Y direction");
		jMenuItem[MenuEDIT][ItemEDIT_REARRANGETELEPORTER].setText("Rearrange Teleporter list");
		
		jMenu[MenuCHEAT].setText("Cheat");
		jMenuItem[MenuCHEAT][ItemCHEAT_SPAWNINTOINV].setText("Spawn Item");
		
		jMenu[MenuHELP].setText("?");
		jMenuItem[MenuHELP][ItemHELP_INFO].setText("Info / Controls");
		jMenuItem[MenuHELP][ItemHELP_OPENFOLDER].setText("Open save file Folder");
		jMenuItem[MenuHELP][ItemHELP_UPDATE].setText("Update (currently " + Main.VERSIONEDITOR + ")");
		jMenuItem[MenuHELP][ItemHELP_RECOMMENDEDMAP].setText("Open recommended Map for other Information");
		
		jMenuItem[MenuEDIT][ItemEDIT_INVSIZE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK));
		jMenuItem[MenuEDIT][ItemEDIT_CONNECTINVS].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
		jMenuItem[MenuEDIT][ItemEDIT_MOVEBUILDINGS].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.ALT_DOWN_MASK));
		
		currentActionAdvise = new JLabel();
		
		jMenuBarButtons = new JButton[ButtonMenu_COUNT];
		for (int i = 0; i < jMenuBarButtons.length; i++) jMenuBarButtons[i] = new JButton();
		
		double preferredJMenuHeight = jMenu[0].getPreferredSize().getHeight();
		BufferedImage buttonMenuMap_image = Picture.loadPicture("Default/mapIcon.png");
		jMenuBarButtons[ButtonMenu_MAP].setToolTipText("Switch dry - green Map");
		jMenuBarButtons[ButtonMenu_MAP].setIcon(new ImageIcon(buttonMenuMap_image.getScaledInstance((int) (preferredJMenuHeight * buttonMenuMap_image.getWidth(null) / (double)buttonMenuMap_image.getHeight(null)), (int) preferredJMenuHeight, Image.SCALE_SMOOTH)));
		jMenuBarButtons[ButtonMenu_MAP].setBackground(null);
		jMenuBarButtons[ButtonMenu_MAP].setBorder(null);
		jMenuBarButtons[ButtonMenu_MAP].setContentAreaFilled(false);
		BufferedImage buttonMenuCave_image = Picture.loadPicture("Default/caveIcon.png");
		jMenuBarButtons[ButtonMenu_CAVE].setToolTipText("Show/Hide Caves/Interior space");
		jMenuBarButtons[ButtonMenu_CAVE].setIcon(new ImageIcon(buttonMenuCave_image.getScaledInstance((int) (preferredJMenuHeight * buttonMenuCave_image.getWidth(null) / (double)buttonMenuCave_image.getHeight(null)), (int) preferredJMenuHeight, Image.SCALE_SMOOTH)));
		jMenuBarButtons[ButtonMenu_CAVE].setBackground(null);
		jMenuBarButtons[ButtonMenu_CAVE].setBorder(null);
		jMenuBarButtons[ButtonMenu_CAVE].setContentAreaFilled(false);
		BufferedImage buttonMenuSHOWBUILDING_image = Picture.loadPicture("Default/showItemIcon.png");
		jMenuBarButtons[ButtonMenu_SHOWBUILDING].setToolTipText("Show not looted Crates / Items / Buildings");
		jMenuBarButtons[ButtonMenu_SHOWBUILDING].setIcon(new ImageIcon(buttonMenuSHOWBUILDING_image.getScaledInstance((int) (preferredJMenuHeight * buttonMenuSHOWBUILDING_image.getWidth(null) / (double)buttonMenuSHOWBUILDING_image.getHeight(null)), (int) preferredJMenuHeight, Image.SCALE_SMOOTH)));
		jMenuBarButtons[ButtonMenu_SHOWBUILDING].setBackground(null);
		jMenuBarButtons[ButtonMenu_SHOWBUILDING].setBorder(null);
		jMenuBarButtons[ButtonMenu_SHOWBUILDING].setContentAreaFilled(false);
		
		separatorRight = new JLabel(Main.VERSIONGAME + "   ");
		separatorRight.setHorizontalAlignment(SwingConstants.RIGHT);
		separatorRight.setPreferredSize(new Dimension(140, 1));
		
		jLabelCoordinates = new JLabel("0 : 0 : 0");
		//jLabelCoordinates.setSize(400, 100);
		jLabelCoordinates.setForeground(Color.WHITE);
		
		buildScreen();
	}
	
	private void buildScreen() {
		frame.getContentPane().add(panel);
		
		frame.setJMenuBar(jMenuBar);
		for (int i = 0; i < jMenu.length; i++) {
			jMenuBar.add(jMenu[i]);
			for (int j = 0; j < jMenuItem[i].length; j++) jMenu[i].add(jMenuItem[i][j]);
		}
		jMenuBar.add(currentActionAdvise);
		jMenuBar.add(Box.createHorizontalGlue());
		
		for (JButton jb : jMenuBarButtons) jMenuBar.add(jb);
		jMenuBar.add(separatorRight);
		
		((JPanel)frame.getGlassPane()).setLayout(new BorderLayout());
		((JPanel)frame.getGlassPane()).add(jLabelCoordinates, BorderLayout.SOUTH);
		
		frame.setSize(screenSize.width/2, screenSize.height*2/3);
		frame.setLocation((int)(screenSize.getWidth() - frame.getWidth())/2, (int)(screenSize.getHeight() - frame.getHeight())/2);
		panel.setScale(0.000001);
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
				if (f.isDirectory()) return true;
				return false;
			}
		});
		jFileChooser.setAcceptAllFileFilterUsed(false);
		while (true) {
			int returnVal = jFileChooser.showOpenDialog(new JFrame());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				if (jFileChooser.getSelectedFile() != null) {
					selectedFile = jFileChooser.getSelectedFile();
					if (!selectedFile.exists()) continue;
					frame.setTitle(selectedFile.getName());
				}
			} else if (returnVal == JFileChooser.CANCEL_OPTION) {
				System.exit(0);
			}
			break;
		}
	}
	public void show() {
		frame.getGlassPane().setVisible(true);
		frame.setVisible(true);
	}
	public void setModify(Modify pModify) {modify = pModify; if (modify.getTi().getTI() > 200000000) switchPicture();}
	public File getSelectedFile() {
		return selectedFile;
	}
	private void switchPicture() {
		selectedImage = (selectedImage + 1)%Map.pictureMap.size();
		panel.selectMapPicture(selectedImage);
	}
	private void switchCave() {
		selectedCave = !selectedCave;
		panel.showCave(selectedCave);
	}
	private void showBuilding() {
		java.util.Map<String, Boolean> newSwitchState = new TreeMap<>(Comparator.comparing(s->s, String.CASE_INSENSITIVE_ORDER));
		List<String> switchableGIDs = modify.getItemList().stream().filter(i -> i.isOnMap()).map(Item::getGId).filter(g -> !Object.EXCLUDED_GIDs.contains(g)).distinct().collect(Collectors.toList());
		newSwitchState.put("_all", switchState.getOrDefault("_all", false));
		newSwitchState.put("_Player", switchState.getOrDefault("_Player", false));
		newSwitchState.put("_Loot Crates", switchState.getOrDefault("_Loot Crates", false));
		for (String s : switchableGIDs) {
			newSwitchState.put(s, switchState.getOrDefault(s, false));
		}
		switchState = newSwitchState;
		IconPanel ip = new IconPanel();
		ip.closeOnLeave(frame);
		List<String> switchStateGIDs = new ArrayList<String>(switchState.keySet());
		ip.appendPictureLabel(switchStateGIDs.stream().map(s -> s.startsWith("_")?s.substring(1):s).collect(Collectors.toList()));
		ip.appendToggleButton(new ArrayList<Boolean>(switchState.values()));
		ip.showOnRight(panel.getBoundsOnScreen());
		ip.show();
		
		List<Boolean> resultSwitchState = ip.getSelectedGIds();
		assert resultSwitchState.size() == switchState.values().size();
		
		for (int i = 0; i < resultSwitchState.size(); i++) {
			switchState.put(switchStateGIDs.get(i), resultSwitchState.get(i));
		}
		switchState.remove("_all");
		buildings.showBuildings(switchState);
		showBuildingLastClosed = System.currentTimeMillis();
	}
	public void updateCoordinates(String newText) {
		coordinatesText = newText;
	}
	public void setUpdatedCoordinates() {
		if (!jLabelCoordinates.getText().equals(coordinatesText)) {
			jLabelCoordinates.setText(coordinatesText);
		}
	}
	public void setActionAdvise(String s) {
		currentActionAdvise.setText(s);
	}
	public java.util.Map<String, Integer> getInventoryStock(long id) {
		java.util.Map<String, Integer> hmap = new TreeMap<String, Integer>(Comparator.comparing(s->s, String.CASE_INSENSITIVE_ORDER));
		Container c = modify.getContainerFromID(id);
		if (c == null) return hmap;
		List<Long> woIdsToFind = c.getWoIds();
		for (Long l : woIdsToFind) {
			Item item = modify.getItemFromID(l);
			if (item != null) {
				String gId = item.getGId();
				if (!Object.EXCLUDED_GIDs.contains(gId)) hmap.put(gId, hmap.getOrDefault(gId, 0)+1);
			}
		}
		return hmap;
	}
	public String getInventoryStockString(long id) {
		Container c = modify.getContainerFromID(id);
		if (c == null) return "Inventory " + id + " not Found\n";
		java.util.Map<String, Integer> hmap = getInventoryStock(id);
		String invStockStr = "";
		invStockStr = c.getWoIds().size() + " / " + c.getSize() + "\n";
		for (String s : hmap.keySet()) invStockStr = invStockStr + "   " + s + (Object.translateGIdName.get(s) != null?" (" + Object.translateGIdName.get(s) + ")":"") + ": " + hmap.get(s) + "\n";
		return invStockStr;
	}
	private void initListeners() {
		panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "resetONC");
		panel.getActionMap().put("resetONC", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.resetNextClickEvent();
			}
		});
		// Open
		//jMenuItem[MenuFILE][ItemFILE_OPEN].addActionListener(new ActionListener() {
		//	@Override
		//	public void actionPerformed(ActionEvent e) {/*showFileChooser();*/}
		//});
		// Save / Export
		jMenuItem[MenuFILE][ItemFILE_EXPORT].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				main.saveFile();
			}
		});
		// Exit
		jMenuItem[MenuFILE][ItemFILE_EXIT].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {System.exit(0);}
		});
		// switch picture
		/*jMenuItem[MenuVIEW][ItemVIEW_SWITCHMAP].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switchPicture();
			}
		});
		// switch cave
		jMenuItem[MenuVIEW][ItemVIEW_SWITCHCAVE].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switchCave();
			}
		});
		// Show Building / Item
		jMenuItem[MenuVIEW][ItemVIEW_SHOWBUILDING].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showBuilding();
			}
		});*/
		// Inventory Overview
		
		jMenuItem[MenuVIEW][ItemVIEW_INVOVERVIEW].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				java.lang.Object[] options = {"All Items in Save File", "Player Inventory", "Equipment", "Building/Container Inventory"};
				int selectedButton = JOptionPane.showOptionDialog(null, "Click with Middle Mouse Button on Container(s) to see it's / their combined inventory", "Choose Inventory", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				if (selectedButton == 0) {
					//if (Main.debug) System.out.println("----All Items--->\n" + modify.getInventoryStockString() + "\n<---All Items----");
					IconPanel ip = new IconPanel();
					ip.setTitle("All Items in Save File");
					java.util.Map<String, Integer> stockMap = modify.getInventoryStockMap();
					ip.appendPictureLabel(new ArrayList<>(stockMap.keySet()));
					ip.appendTextField(stockMap.keySet().stream().map(s -> Object.translateGIdName.getOrDefault(s,s)).collect(Collectors.toList()));
					ip.appendSpace(10);
					ip.appendTextField(new ArrayList<>(stockMap.values()));
					ip.showOnLeft(panel.getBoundsOnScreen());
					ip.show();
				} else if (selectedButton == 1 || selectedButton == 2) {
					IconPanel ip = new IconPanel();
					ip.setTitle(selectedButton==1?"Player Inventory":"Equipment");
					java.util.Map<String, Integer> stockMap = getInventoryStock(selectedButton); // they have conveniently and coincidentally the same number
					ip.appendPictureLabel(new ArrayList<>(stockMap.keySet()));
					ip.appendTextField(stockMap.keySet().stream().map(s -> Object.translateGIdName.getOrDefault(s,s)).collect(Collectors.toList()));
					ip.appendSpace(10);
					ip.appendTextField(new ArrayList<>(stockMap.values()));
					ip.setMenuBarLeftButton(new JButton("Change size"), new ActionListener() {
						long selButton = selectedButton;
						@Override
						public void actionPerformed(ActionEvent e) {
							Container changeSizeContainer = modify.getIdContainerMap().get(selButton);
							int minSize = changeSizeContainer.getWoIds().size();
							String newSize = JOptionPane.showInputDialog("Input new size (current size: " + changeSizeContainer.getSize() + ", minimum size: " + minSize + ")");
							if (JCommands.isInt(newSize)) {
								int newSizeInt = Integer.parseInt(newSize);
								changeSizeContainer.setSize(newSizeInt);
							}
						}
					});
					ip.showOnLeft(panel.getBoundsOnScreen());
					ip.show();
				} else if (selectedButton == 3) {
					panel.showInventoryContentOnNextClick();
				}
			}
		});
		// Show Compare to Earth
		jMenuItem[MenuVIEW][ItemVIEW_COMPARETOEARTH].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, modify.getTi().comparedToEarth());
			}
		});
		// Check for Doubles
		jMenuItem[MenuCHECK][ItemCHECK_DOUBLEITEM].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				modify.testForDouble();
			}
		});
		// Check for missing containers for invs
		/*jMenuItem[MenuCHECK][ItemCHECK_MISSINGCONTAINER].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				modify.testForInventoryWithoutContainer();
			}
		});*/
		// Check if all Item IDs are unique
		jMenuItem[MenuCHECK][ItemCHECK_AREIDSUNIQUE].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HashSet<Long> doubledIDs = new HashSet<Long>();
				HashSet<Long> allItemIDs = new HashSet<Long>();
				for (Item i : modify.getItemList()) if (!allItemIDs.add(i.getId())) doubledIDs.add(i.getId());
				if (doubledIDs.isEmpty()) JOptionPane.showMessageDialog(null, "All Item IDs are unique");
				else JOptionPane.showMessageDialog(null, "<html>" + doubledIDs.size() + " Item IDs are not unique:<br>" + doubledIDs.stream().map(s -> s.toString()).collect(Collectors.joining("<br>", "", "")) + "</html>");
			}
		});
		// Change Inventory size on next click
		jMenuItem[MenuEDIT][ItemEDIT_INVSIZE].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.showChangeContainerSizeOnNextClick();
			}
		});
		// Make all generating inventories supply
		jMenuItem[MenuEDIT][ItemEDIT_SETSUPPLYOFGENINV].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int chosenButton = JOptionPane.showConfirmDialog(null, "The following Buildings will be set to supply their produced Items:\n" + Object.translateGIdToSupplied.keySet().stream().map(l -> Object.translateGIdName.getOrDefault(l, l) + ": " + Object.getSupplyOfBuilding(l).stream().map(s -> Object.translateGIdName.getOrDefault(s, s)).collect(Collectors.joining(", ", "", ""))).collect(Collectors.joining("\n", "", "")) + "", "Set Supply of generating inventories", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (chosenButton == JOptionPane.OK_OPTION) for (Container c : modify.getContainerList()) {
					HashSet<String> supplyMap = new HashSet<String>(c.getSupplyGrps());
					for (Item i : modify.getItemsFromLiID(c.getId())) {
						for (String s : Object.getSupplyOfBuilding(i.getGId())) supplyMap.add(s);
					}
					if (!supplyMap.isEmpty()) c.setSupplyGrps(new ArrayList<String>(supplyMap));
				}
			}
		});
		// Connect Inventories
		jMenuItem[MenuEDIT][ItemEDIT_CONNECTINVS].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.showConnectInvsP1OnNextClick();
			}
		});
		// Reset connected inventory
		jMenuItem[MenuEDIT][ItemEDIT_RESETCONNECTEDINVS].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.showResetConnectedInvsOnNextClick();
			}
		});
		// Move Buildings in Area (e.g. click on two edges and show rectangle)
		jMenuItem[MenuEDIT][ItemEDIT_MOVEBUILDINGS].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.showMoveBuildingP1OnNextClick();
			}
		});
		// Rearrange Teleporter List
		jMenuItem[MenuEDIT][ItemEDIT_REARRANGETELEPORTER].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Item> teleporterList = new ArrayList<Item>();
				List<Integer> teleporterIndexList = new ArrayList<Integer>();
				for (int i = 0; i < modify.getItemList().size(); i++) if (modify.getItemList().get(i).getGId().equals("Teleporter1")) {
					teleporterList.add(modify.getItemList().get(i));
					teleporterIndexList.add(i);
				}
				if (teleporterList.size() == 0) {
					JOptionPane.showMessageDialog(null, "No Teleporters in Save File");
					return;
				}
				TeleporterRearrangeableDialog dialog = new TeleporterRearrangeableDialog(teleporterList);
				dialog.show(panel.getBoundsOnScreen());
				List<Item> output = dialog.getOutput();
				if (output == null) return;
				assert teleporterIndexList.size() == output.size();
				for (int i = 0; i < output.size(); i++) {
					modify.getItemList().set(teleporterIndexList.get(i), output.get(i));
				}
			}
		});
		// Spawn Item
		jMenuItem[MenuCHEAT][ItemCHEAT_SPAWNINTOINV].addActionListener(new ActionListener() {// change filter in second from Object.cheatableItemTypes 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				IconPanel ip = new IconPanel();
				List<String> lst = Object.translateGIdName.keySet().stream().filter(s -> Object.cheatableItemTypes.contains(Object.getType(s))).filter(s -> Object.getItemPictureOrNull(s) != null).collect(Collectors.toList());
				ip.appendPictureButton(lst);
				ip.appendSpace(10);
				ip.appendTextField(lst.stream().map(s -> Object.translateGIdName.getOrDefault(s, s)).collect(Collectors.toList()));
				ip.showOnLeft(panel.getBoundsOnScreen());
				ip.show();
				String selection = ip.getSelection();
				if (selection == null) return;
				String amountStr = JOptionPane.showInputDialog(null, "Amount (\u2264 10000)", "1");
				if (amountStr == null) return;
				boolean override = false;
				if (amountStr.startsWith("override_")) {
					amountStr = amountStr.substring(9);
					override = true;
				}
				if (JCommands.isInt(amountStr)) {
					int amount = Integer.parseInt(amountStr);
					if (amount > 10000 && !override) {JOptionPane.showMessageDialog(null, "Amount too large"); return;}
					if (amount < 0 && !override) {JOptionPane.showMessageDialog(null, "Amount too small");}
					int type = Object.getType(selection);
					if (Object.cheatableItemTypes.contains(type)) {
						for (int i = 0; i < amount; i++) {
							long newID = modify.getNewItemID();
							if (newID == 0) {continue;}
							long newInventoryID = 0;
							if (selection.equalsIgnoreCase("Drone1")) {
								newInventoryID = modify.getNextContainerID();
								modify.getContainerList().add(new Container(newInventoryID, 1, new ArrayList<String>(), new ArrayList<String>(), 0));
							}
							Item cheatItem = new Item(newID, selection, newInventoryID, "", 0, 0, 0, 0, 0, 0, 0, 0, new int[0], "", "", 0);
							modify.getItemList().add(cheatItem);
							if (type != Object.TypeROCKET) {
								modify.inventoryContainer.push(cheatItem);
							} else if (type == Object.TypeROCKET) {
								switch (selection) {
								case "RocketBiomass1":
									if (modify.SpaceMultiplierPlantsContainer == null) {
										modify.SpaceMultiplierPlantsContainer = new Container(modify.getNextContainerID(), new ArrayList<Long>(), amount + 10000, new ArrayList<String>(), new ArrayList<String>(), 0);
										modify.getContainerList().add(modify.SpaceMultiplierPlantsContainer);
										modify.getItemList().add(new Item(modify.getNewItemID(), Object.SpaceMultiplierPlants, modify.SpaceMultiplierPlantsContainer.getId(), "", -500, -500, -500, 0, 0, 0, 1, 0, new int[0], "", "", 0));
									}
									modify.SpaceMultiplierPlantsContainer.push(cheatItem);
									break;
								case "RocketHeat1":
									if (modify.SpaceMultiplierHeatContainer == null) {
										modify.SpaceMultiplierHeatContainer = new Container(modify.getNextContainerID(), new ArrayList<Long>(), amount + 10000, new ArrayList<String>(), new ArrayList<String>(), 0);
										modify.getContainerList().add(modify.SpaceMultiplierHeatContainer);
										modify.getItemList().add(new Item(modify.getNewItemID(), Object.SpaceMultiplierHeat, modify.SpaceMultiplierHeatContainer.getId(), "", -500, -500, -500, 0, 0, 0, 1, 0, new int[0], "", "", 0));
									}
									modify.SpaceMultiplierHeatContainer.push(cheatItem);
									break;
								case "RocketInsects1":
									if (modify.SpaceMultiplierInsectsContainer == null) {
										modify.SpaceMultiplierInsectsContainer = new Container(modify.getNextContainerID(), new ArrayList<Long>(), amount + 10000, new ArrayList<String>(), new ArrayList<String>(), 0);
										modify.getContainerList().add(modify.SpaceMultiplierInsectsContainer);
										modify.getItemList().add(new Item(modify.getNewItemID(), Object.SpaceMultiplierInsects, modify.SpaceMultiplierInsectsContainer.getId(), "", -500, -500, -500, 0, 0, 0, 1, 0, new int[0], "", "", 0));
									}
									modify.SpaceMultiplierInsectsContainer.push(cheatItem);
									break;
								case "RocketOxygen1":
									if (modify.SpaceMultiplierOxygenContainer == null) {
										modify.SpaceMultiplierOxygenContainer = new Container(modify.getNextContainerID(), new ArrayList<Long>(), amount + 10000, new ArrayList<String>(), new ArrayList<String>(), 0);
										modify.getContainerList().add(modify.SpaceMultiplierOxygenContainer);
										modify.getItemList().add(new Item(modify.getNewItemID(), Object.SpaceMultiplierOxygen, modify.SpaceMultiplierOxygenContainer.getId(), "", -500, -500, -500, 0, 0, 0, 1, 0, new int[0], "", "", 0));
									}
									modify.SpaceMultiplierOxygenContainer.push(cheatItem);
									break;
								case "RocketPressure1":
									if (modify.SpaceMultiplierPressureContainer == null) {
										modify.SpaceMultiplierPressureContainer = new Container(modify.getNextContainerID(), new ArrayList<Long>(), amount + 10000, new ArrayList<String>(), new ArrayList<String>(), 0);
										modify.getContainerList().add(modify.SpaceMultiplierPressureContainer);
										modify.getItemList().add(new Item(modify.getNewItemID(), Object.SpaceMultiplierPressure, modify.SpaceMultiplierPressureContainer.getId(), "", -500, -500, -500, 0, 0, 0, 1, 0, new int[0], "", "", 0));
									}
									modify.SpaceMultiplierPressureContainer.push(cheatItem);
									break;
								}
							}
						}
						if (type == Object.TypeROCKET) {
							modify.SpaceMultiplierPlantsContainer.adaptSize();
							modify.SpaceMultiplierHeatContainer.adaptSize();
							modify.SpaceMultiplierInsectsContainer.adaptSize();
							modify.SpaceMultiplierOxygenContainer.adaptSize();
							modify.SpaceMultiplierPressureContainer.adaptSize();
						}
						modify.inventoryContainer.adaptSize();
						modify.reloadMaps();
					}
					// WHEN ADDING BUILDINGS, REMEMBER PNLS!!!!!!!!!; Antennas have inventories!!!
				}
			}
		});
		// INFO / Controls
		jMenuItem[MenuHELP][ItemHELP_INFO].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String infoText = "<html>"
						+ "MMB: Show Inventory of clicked on Building <br>"
						+ "ESC: Cancel command / function"
						+ "Upper right icons:<br>"
						+ " - Compass: Switch between wasteland and terraformed map<br>"
						+ " - Cave: Show Cave and Interior overlay<br>"
						+ " - Flashlight: Show Buildings, Items and loot crates (from " + Object.lootCrateVersion + ")<br>"
						+ "Move in Y Direction only applies to visible Buildings / Items<br>"
						+ "Instructions for functions are displayed in the menu bar<br>"
						+ "Dialogs without [x] can be closed by clicking somewhere else<br>"
						+ "Any functionality regarding Inventories might change their size.<br>"
						+ "You can change their size back when you have emptied the inventories.<br>"
						+ "<br>"
						+ "Exported Save files are named [old file name]_modified_yyyy.MM.dd_HH-mm-ss.json<br>"
						+ "You are supposed to change it to another name yourself to prevent accidental data loss.<br>"
						+ "To change the name just rename the file.<br>"
						+ "<br>"
						+ "FPS drops: try resizing to a smaller window<br>"
						+ "Building: everything that is only placable into the world<br>"
						+ "Item: everything that can be found inside an Inventory<br>"
						+ "<br>"
						+ "All ingame pictures belong to Miju Games.<br>"
						+ "Map and Cave/Interior overlay made by Fishshake.</html>";
				JOptionPane.showMessageDialog(null, infoText, "Info / Controls", JOptionPane.PLAIN_MESSAGE); // TODO
			}
		});
		// Open Save File Folder
		jMenuItem[MenuHELP][ItemHELP_OPENFOLDER].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.open(selectedFile.getParentFile());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		// Update
		jMenuItem[MenuHELP][ItemHELP_UPDATE].addActionListener(new ActionListener() {
			
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
		// Open Map from fistshake
		jMenuItem[MenuHELP][ItemHELP_RECOMMENDEDMAP].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				URL url;
				try {
					url = new URL("https://map.fistshake.net/PlanetCrafter/");
					JCommands.openWebpage(url);
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
			}
		});
		// Window Listener (resize)
		frame.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {}
			
			@Override
			public void componentResized(ComponentEvent e) {
				jLabelCoordinates.setLocation(5, frame.getHeight()-jLabelCoordinates.getHeight()-25);
				double scale = frame.getHeight() / (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
				jLabelCoordinates.setFont(defaultFont.deriveFont((float) (defaultFont.getSize() * (scale>=1?scale:1d))));
				lastFrameSize.getSize();
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {}
			
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
		// JMenuBar Buttons
		// menu bar map -> clicks on menu item
		jMenuBarButtons[ButtonMenu_MAP].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switchPicture();
			}
		});
		// menu bar cave -> clicks on menu item
		jMenuBarButtons[ButtonMenu_CAVE].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switchCave();
			}
		});
		jMenuBarButtons[ButtonMenu_SHOWBUILDING].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (showBuildingLastClosed + 100 < System.currentTimeMillis()) showBuilding();
			}
		});
	}
	public void timerGifEvent() {
		if (buildings.showLootCrates) panel.repaint();
	}
}
