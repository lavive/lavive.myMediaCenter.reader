package myMediaCenter.reader.tools;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Set Stage
 * 
 * @author lavive
 *
 */
public class StageSettings {
	
	public static void setFitScreen(Stage stage,boolean fullscreen) {
		// Set Style
		stage.setFullScreen(fullscreen);
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		
        // Set Stage boundaries to visible bounds of the main screen
		stage.setX(Sizes.SCREEN_X);
		stage.setY(Sizes.SCREEN_Y);
		stage.setWidth(Sizes.SCREEN_WIDTH);
		stage.setHeight(Sizes.SCREEN_HEIGHT);
		
	}

}