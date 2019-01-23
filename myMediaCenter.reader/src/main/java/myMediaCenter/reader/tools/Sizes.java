package myMediaCenter.reader.tools;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class Sizes {


	/* screen size */
	private static Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	
	public static double SCREEN_X = primaryScreenBounds.getMinX();
	public static double SCREEN_Y = primaryScreenBounds.getMinY();
	public static double SCREEN_WIDTH = primaryScreenBounds.getWidth();
	public static double SCREEN_HEIGHT = primaryScreenBounds.getHeight();
}
