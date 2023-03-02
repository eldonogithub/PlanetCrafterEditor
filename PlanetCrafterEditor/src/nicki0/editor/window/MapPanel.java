package nicki0.editor.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import nicki0.editor.Main;
import nicki0.editor.Map;
import nicki0.editor.Window;
import nicki0.editor.elements.Container;
import nicki0.editor.elements.Item;
import nicki0.editor.elements.Object;


public class MapPanel extends JPanel {
	private static final long serialVersionUID = 1L; // why???
	private List<BufferedImage> pictureMap;
	private BufferedImage cavePicture;
	private boolean showCave = false;
	private int selectedMapPicture = 0;
	private Window window;
	private EventsOnNextClick evonc;
	
	
	private double scale = 1;
	private double scaleFactor = 1.1;
	private double minScale; // saves minimum scale ()
	private Point2D.Double mapPosition = new Point2D.Double();
	
	private Point mousePositionOnClick = new Point();
	private java.util.Map<Integer, Boolean> mouseKeyIsPressed = new HashMap<Integer, Boolean>();
	
	public List<Item> renderItems;
	public java.util.Map<Rectangle2D.Double, Item> renderItemBoundsMap;
	private boolean showPlayer = false;
	
	private long lastRepaintTime = System.currentTimeMillis();
	@SuppressWarnings("unused")
	private long timesRepainted = 0;
	
	private Point lastMousePosition = new Point();
	private Point2D.Double rectangleAtPosition;
	
	private int nextClickEvent = 0;
	private static final int showChangeContainerSizeOnNextClick = 1;
	private static final int showInventoryContentOnNextClick = 2;
	private static final int showResetConnectedInvsOnNextClick = 3;
	private static final int showMoveBuildingP1OnNextClick = 4;
	private static final int showMoveBuildingP2OnNextClick = 5;
	private static final int showConnectInvsP1OnNextClick = 6;
	private static final int showConnectInvsP2OnNextClick = 7;
	
	public MapPanel(Window pWindow) {
		super(new BorderLayout());
		window = pWindow;
		evonc = new EventsOnNextClick(window, this);
		pictureMap = Map.pictureMap;
		cavePicture = Map.pictureCave;
		renderItems = new ArrayList<Item>();
		renderItemBoundsMap = new HashMap<Rectangle2D.Double, Item>();
		
		initListeners();
		
		setBackground(Color.BLACK);
	}
	private void initListeners() {
		addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				zoom(e);
			}
		});
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				mouseKeyIsPressed.put(e.getButton(), false);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				mouseKeyIsPressed.put(e.getButton(), true);
				mousePositionOnClick = e.getPoint();
				
				Point p = e.getPoint();
				List<Item> clickedOnBuilding = window.buildings.objectsAtPosition(p.x, p.y);
				
				switch (e.getButton()) {
				case MouseEvent.BUTTON1: {
					switch (nextClickEvent) {
					case showChangeContainerSizeOnNextClick: {
						window.setActionAdvise("Click on a Container to change its size");
						evonc.showChangeContainerSizeOnNextClick(clickedOnBuilding);
						break;
					}
					case showInventoryContentOnNextClick: {
						window.setActionAdvise("Click on a Container to see its content");
						evonc.showInventoryContentOnNextClick(clickedOnBuilding);
						break;
					}
					case showResetConnectedInvsOnNextClick: {
						window.setActionAdvise("Click on a Container to disconnect all inventories");
						evonc.showResetConnectedInvsOnNextClick(clickedOnBuilding);
						break;
					}
					case showMoveBuildingP1OnNextClick: {
						window.setActionAdvise("Click on the first corner of the area that should be moved up-/downward");
						evonc.showMoveBuildingP1OnNextClick(e);
						break;
					}
					case showMoveBuildingP2OnNextClick: {
						window.setActionAdvise("Click on the second corner of the area that should be moved up-/downward");
						evonc.showMoveBuildingP2OnNextClick(e);
						break;
					}
					case showConnectInvsP1OnNextClick: {
						window.setActionAdvise("Click on the first Building / Container to connect the inventories");
						evonc.showConnectInvsP1OnNextClick(clickedOnBuilding);
						break;
					}
					case showConnectInvsP2OnNextClick: {
						window.setActionAdvise("Click on the second Building / Container to connect the inventories");
						evonc.showConnectInvsP2OnNextClick(clickedOnBuilding);
						break;
					}
					}
					
					for (Item i : clickedOnBuilding) {
						Container c = window.modify.getContainerFromID(i.getLiId());
						String inv = "";
						if (c != null) inv = window.getInventoryStockString(c.getId()).replaceAll("([0-9]* / [0-9]*\n|   )","").replaceAll("\n", "; ");
						if (Main.debug) System.out.println(i.getGId() + " " + i.getText() + inv + " at Position: " + i.getPositionAsString());
					}
					break;
				}
				case MouseEvent.BUTTON2: {
					if (clickedOnBuilding == null) break;
					if (clickedOnBuilding.isEmpty()) break;
					java.util.Map<String, Integer> stockMap = new TreeMap<String, Integer>(Comparator.comparing(s->s, String.CASE_INSENSITIVE_ORDER));
					java.util.Map<Boolean, Integer> countInventorieAmount = new HashMap<Boolean, Integer>();
					clickedOnBuilding.stream().filter(i -> i != null).filter(i -> !(!i.isReal() && window.getInventoryStock(i.getLiId()).isEmpty())).filter(i -> i.getLiId() != 0).map(Item::getLiId).map(i -> window.getInventoryStock(i)).forEach(m -> {m.keySet().forEach(k -> stockMap.put(k, m.get(k) + stockMap.getOrDefault(k, 0))); countInventorieAmount.put(true, countInventorieAmount.getOrDefault(true, 0)+1);});
					
					IconPanel ip = new IconPanel();
					ip.closeOnLeave(window.frame);
					if (countInventorieAmount.isEmpty()) break;
					if (!stockMap.isEmpty()) {
						ip.setTitle(countInventorieAmount.get(true) + " Inventor" + (countInventorieAmount.get(true)>1?"ies":"y"));
						ip.appendPictureLabel(new ArrayList<>(stockMap.keySet()));
						ip.appendTextField(stockMap.keySet().stream().map(s -> Object.translateGIdName.getOrDefault(s,s)).collect(Collectors.toList()));
						ip.appendSpace(10);
						ip.appendTextField(new ArrayList<>(stockMap.values()));
					} else {
						ip.appendTextField(Arrays.asList("Empty"));
					}
					ip.showOnRight(window.panel.getBoundsOnScreen());
					ip.show();
					break;
				}
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				lastMousePosition = e.getPoint();
				
				Point2D.Double coord = Map.getCoordinatesFromPosition(getPositionFromMouse(e.getPoint()));
				window.updateCoordinates(String.format("%.2f", coord.x)  + " : ? : " + String.format("%.2f", coord.y)/* + " Mouse: " + e.getPoint()*/);
				
				if (rectangleAtPosition != null) repaint();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				lastMousePosition = e.getPoint();
				
				if (mouseKeyIsPressed.getOrDefault(1, false) || mouseKeyIsPressed.getOrDefault(2, false) || mouseKeyIsPressed.getOrDefault(3, false)) {
					mapPosition.x += e.getX() -  mousePositionOnClick.x;
					mapPosition.y += e.getY() - mousePositionOnClick.y;
					
					mousePositionOnClick = e.getPoint();
					repaint();
				}
			}
		});
	}

	protected void paintComponent(Graphics g) {
		if (Main.debug) System.out.println(System.currentTimeMillis() - lastRepaintTime + " : " + (1000 / (System.currentTimeMillis() - lastRepaintTime)));
		if (Main.debug) lastRepaintTime = System.currentTimeMillis();
		//System.out.print((timesRepainted++) + ", ");
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		RenderingHints defaultRenderingHints = g2.getRenderingHints();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		
		BufferedImage currentMapPicture = pictureMap.get(selectedMapPicture);
		makeMapFit(currentMapPicture);
		AffineTransform at = AffineTransform.getTranslateInstance(mapPosition.x,mapPosition.y);
		at.scale(scale, scale);
		Rectangle screenRect = this.getVisibleRect();
		
		g2.drawRenderedImage(currentMapPicture, at);
		if (showCave) g2.drawRenderedImage(cavePicture, at);
		
		g2.setRenderingHints(defaultRenderingHints); // resetz rendering hints. Other method: //g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		// show all items
		java.util.Map<Rectangle2D.Double, Item> newRenderItemBoundsMap = new HashMap<Rectangle2D.Double, Item>();
		for (Item i : renderItems) {
			BufferedImage bi = i.getPicture(window.timer.counterGIF);
			Point2D.Double itemPosition = Map.getPositionFromCoordinates(i.posX(), i.posZ());
			double pixelScale = Map.getPixelPerCoordinate() * i.getWidthScale()/bi.getWidth();
			double imageScale = (i.scalePicture()?scale:minScale)*pixelScale;
			double imageWidth = imageScale*bi.getWidth();
			double imageHeight = imageScale*bi.getHeight();
			Rectangle2D.Double rectPos = new Rectangle2D.Double(mapPosition.x + (itemPosition.x) * scale - imageWidth/2, mapPosition.y + (itemPosition.y) * scale - imageHeight/2, imageWidth, imageHeight);
			
			if (!screenRect.intersects(rectPos)) continue;
			
			AffineTransform pos = AffineTransform.getTranslateInstance(rectPos.x, rectPos.y);
			pos.scale(imageScale, imageScale);
			g2.drawRenderedImage(bi, pos);
			
			newRenderItemBoundsMap.put(rectPos, i);
		}
		// show Player
		if (showPlayer) {
			BufferedImage bi = Map.picturePlayer;
			Point2D.Double itemPosition = Map.getPositionFromCoordinates(window.modify.getPlayerAttributes().getX(), window.modify.getPlayerAttributes().getZ());
			double pixelScale = Map.getPixelPerCoordinate() * 2/bi.getWidth();
			double imageScale = scale*pixelScale;
			double imageWidth = imageScale*bi.getWidth();
			double imageHeight = imageScale*bi.getHeight();
			Rectangle2D.Double rectPos = new Rectangle2D.Double(mapPosition.x + (itemPosition.x) * scale - imageWidth/2, mapPosition.y + (itemPosition.y) * scale - imageHeight/2, imageWidth, imageHeight);
			if (screenRect.intersects(rectPos)) {
				AffineTransform pos = AffineTransform.getTranslateInstance(rectPos.x, rectPos.y);
				pos.scale(imageScale, imageScale);
				g2.drawRenderedImage(bi, pos);
			}
		}
		
		// rectangle for e.g. move buildings up/down
		if (nextClickEvent != showMoveBuildingP2OnNextClick) rectangleAtPosition = null;
		if (rectangleAtPosition != null) {
			Point mousePos = lastMousePosition;//this.getMousePosition();
			if (mousePos != null) {
				Point2D p = Map.getPositionFromCoordinates(rectangleAtPosition.x, rectangleAtPosition.y);
				int hx = (int) (mapPosition.x + p.getX() * scale);
				int hy = (int) (mapPosition.y + p.getY() * scale);
				int hpx = (mousePos.x < hx) ? mousePos.x : hx;
				int hpy = (mousePos.y < hy) ? mousePos.y : hy;
				g2.setColor(Color.RED);
				g2.drawRect(hpx, hpy, Math.abs((int) (mousePos.getX() - hx)), Math.abs((int) (mousePos.getY() - hy)));
			}
		}
		
		renderItemBoundsMap = newRenderItemBoundsMap;
	}
	private void makeMapFit(BufferedImage image) {
		if (image.getWidth()*scale < this.getWidth()) {
			scale *= this.getWidth() / (image.getWidth() * scale);
			mapPosition.y = (this.getHeight() - image.getHeight() * scale) / 2;
			minScale = scale;
		}
		if (image.getHeight()*scale < this.getHeight()) {
			scale *= this.getHeight() / (image.getHeight() * scale);
			mapPosition.x = (this.getWidth() - image.getWidth() * scale) / 2;
			minScale = scale;
		}
		if (mapPosition.y > 0) mapPosition.y = 0;
		if (mapPosition.y + image.getHeight()*scale < this.getHeight()) mapPosition.y = this.getHeight() - (image.getHeight() * scale);
		if (image.getWidth() * scale < this.getWidth()) {
			if (mapPosition.x < 0) mapPosition.x = 0;
			if (mapPosition.x + image.getWidth()*scale > this.getWidth()) mapPosition.x = this.getWidth() - (image.getWidth() * scale);
		} else {
			if (mapPosition.x > 0) mapPosition.x = 0;
			if (mapPosition.x + image.getWidth()*scale < this.getWidth()) mapPosition.x = this.getWidth() - (image.getWidth() * scale);
		}
	}
	public Rectangle getBoundsOnScreen() {
		Rectangle r = this.getBounds();
		Point onScreen = this.getLocationOnScreen(); 
		r.x += onScreen.x;
		r.y += onScreen.y;
		return r;
	}
	public void showCave(boolean pShowCave) {
		showCave = pShowCave;
		repaint();
	}
	public void selectMapPicture(int pictureID) {
		selectedMapPicture = pictureID;
		repaint();
	}
	
	public void setScale(double pScale) {
		scale = pScale;
		repaint();
	}
	public double getScale() {return scale;}
	public Dimension getMapSize() {return new Dimension((int) (pictureMap.get(selectedMapPicture).getWidth(null) * scale), (int) (pictureMap.get(selectedMapPicture).getHeight(null) * scale));}
	/*public void changeScale(double pChange) {
		scale = getChangeScale(pChange);
		repaint();
	}*/
	private double getChangeScale(double pChange) {
		return scale * Math.pow(scaleFactor, pChange);
	}
	/*public void setPosition(Point pMapPosition) {
		mapPositionX = pMapPosition.x;
		mapPositionY = pMapPosition.y;
		repaint();
	}*/
	/*public void setPosition(double px, double py) {
		mapPosition = new Point2D.Double(px, py);
		repaint();
	}*/
	public Point2D.Double getPosition() {
		return mapPosition;
	}
	public Point2D getPositionFromMouse(Point2D pMousePos) {
		double partx = (pMousePos.getX() - mapPosition.getX()) / (pictureMap.get(selectedMapPicture).getWidth() * scale);
		double party = (pMousePos.getY() - mapPosition.getY()) / (pictureMap.get(selectedMapPicture).getHeight() * scale);
		return new Point2D.Double(partx, party);
	}
	private void zoom(MouseWheelEvent e) {
		zoom(e.getPoint(), -e.getPreciseWheelRotation());
	}
	private void zoom(Point pMouseLocation, double pChange) {
		double newScale = getChangeScale(pChange);
		double x = pMouseLocation.x - (newScale/scale * (double)(pMouseLocation.x - mapPosition.x));
		double y = pMouseLocation.y - (newScale/scale * (double)(pMouseLocation.y - mapPosition.y));
		mapPosition = new Point2D.Double(x,y);
		setScale(newScale);
		repaint();
	}
	public void setItemsToShow(List<Item> pItemList) {
		renderItems = pItemList;
	}
	public void showPlayer(boolean pShowPlayer) {
		showPlayer = pShowPlayer;
	}
	public void resetNextClickEvent() {
		window.setActionAdvise("");
		nextClickEvent = 0;
	}
	public void showChangeContainerSizeOnNextClick() {
		nextClickEvent = nextClickEvent == showChangeContainerSizeOnNextClick ? 0 : showChangeContainerSizeOnNextClick;
		if (nextClickEvent != 0) window.setActionAdvise("Click on a Container to change its size");
		else window.setActionAdvise("");
	}
	public void showInventoryContentOnNextClick() {
		window.setActionAdvise("Click on a Container to see its content");
		nextClickEvent = showInventoryContentOnNextClick;
	}
	public void showResetConnectedInvsOnNextClick() {
		window.setActionAdvise("Click on a Container to disconnect all inventories");
		nextClickEvent = showResetConnectedInvsOnNextClick;
	}
	public void showMoveBuildingP1OnNextClick() {
		window.setActionAdvise("Click on the first corner of the area that should be moved up-/downward");
		nextClickEvent = showMoveBuildingP1OnNextClick;
	}
	protected void showMoveBuildingP2OnNextClick() {
		window.setActionAdvise("Click on the second corner of the area that should be moved up-/downward");
		nextClickEvent = showMoveBuildingP2OnNextClick;
	}
	public void showConnectInvsP1OnNextClick() {
		window.setActionAdvise("Click on the first Building / Container to connect the inventories");
		nextClickEvent = showConnectInvsP1OnNextClick;
	}
	protected void showConnectInvsP2OnNextClick() {
		window.setActionAdvise("Click on the second Building / Container to connect the inventories");
		nextClickEvent = showConnectInvsP2OnNextClick;
	}
	protected void rectangleAtPosition(Point2D.Double pPoint) {
		rectangleAtPosition = pPoint;
	}
}
