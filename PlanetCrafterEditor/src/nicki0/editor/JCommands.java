package nicki0.editor;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class JCommands {
	public static boolean openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	            return true;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}

	public static boolean openWebpage(URL url) {
	    try {
	        return openWebpage(url.toURI());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public static boolean isNumber(String s) {
		boolean isNumber = true;
		try {Double.parseDouble(s);} catch (NumberFormatException nfe) {isNumber = false;}
		return isNumber;
	}
	public static boolean isDouble(String s) {
		boolean isNumber = true;
		try {Double.parseDouble(s);} catch (NumberFormatException nfe) {isNumber = false;}
		return isNumber;
	}
	public static boolean isFloat(String s) {
		boolean isNumber = true;
		try {Float.parseFloat(s);} catch (NumberFormatException nfe) {isNumber = false;}
		return isNumber;
	}
	public static boolean isLong(String s) {
		boolean isNumber = true;
		try {Long.parseLong(s);} catch (NumberFormatException nfe) {isNumber = false;}
		return isNumber;
	}
	public static boolean isInt(String s) {
		boolean isNumber = true;
		try {Integer.parseInt(s);} catch (NumberFormatException nfe) {isNumber = false;}
		return isNumber;
	}
}
