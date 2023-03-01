package nicki0.editor.pictures;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class Picture {

	public Picture() {}
	
	//---
	private static GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	private static BufferedImage toCompatibleImage(BufferedImage image) {
		// return if compatible
		if (image.getColorModel().equals(gfxConfig.getColorModel())) {
			return image;
		}
		// image is not optimized, so create a new image that is
		BufferedImage newImage = gfxConfig.createCompatibleImage(
				image.getWidth(), image.getHeight(), image.getTransparency());
		Graphics2D g2d = newImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		
		return newImage; 
	}
	//---
	
	public static BufferedImage loadPicture(String url) {
		BufferedImage bi = null;
		try {
			bi =  ImageIO.read(Picture.class.getResource(url));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toCompatibleImage(bi);
	}
	public static BufferedImage tryLoadPicture(String url) {
		BufferedImage bi = null;
		try {
			if (url == null) return null;
			URL hUrl = Picture.class.getResource(url);
			if (hUrl == null) return null;
			bi =  ImageIO.read(hUrl);
		} catch (IOException e) {
			return null;
		}
		return toCompatibleImage(bi);
	}
	public static BufferedImage loadPicture(URL url) {
		BufferedImage bi = null;
		try {
			return ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toCompatibleImage(bi);
	}
	public static List<BufferedImage> loadPictures(List<URL> lurl) {
		List<BufferedImage> lbi = new ArrayList<BufferedImage>();
		
		try {
			for (URL url : lurl) lbi.add(toCompatibleImage(ImageIO.read(url)));
		} catch (IOException e) {e.printStackTrace();}
		
		return lbi;
	}
	public static List<BufferedImage> loadPicture(List<String> lurl) {
		List<BufferedImage> lbi = new ArrayList<BufferedImage>();
		
		try {
			for (String url : lurl) lbi.add(toCompatibleImage(ImageIO.read(Picture.class.getResource(url))));
		} catch (IOException e) {e.printStackTrace();}
		
		return lbi;
	}
	public static List<BufferedImage> loadPicture(String[] aurl) {
		return loadPicture(Arrays.asList(aurl));
	}
	public static List<BufferedImage> loadPictureFromFile(List<File> list) {
		List<BufferedImage> lbi = new ArrayList<BufferedImage>();
		
		try {
			for (File f : list) lbi.add(toCompatibleImage(ImageIO.read(f)));
		} catch (IOException e) {e.printStackTrace();}
		
		return lbi;
	}
	public static BufferedImage loadPictureFromFile(File f) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(f);
		} catch (IOException e) {e.printStackTrace();}
		return toCompatibleImage(bi);
	}
	public static List<BufferedImage> loadPictureFromFile(File[] fileArray) {
		return loadPictureFromFile(Arrays.asList(fileArray));
	}
	public static List<BufferedImage> loadPicture(String url, int imageCount) {
		List<BufferedImage> result = new ArrayList<>();
		BufferedImage image = loadPicture(url);
		int width = image.getWidth() / imageCount;
		for (int i = 0; i < imageCount; i++) result.add(toCompatibleImage(image.getSubimage(width * i, 0, width, image.getHeight())));
		return result;
	}
}
