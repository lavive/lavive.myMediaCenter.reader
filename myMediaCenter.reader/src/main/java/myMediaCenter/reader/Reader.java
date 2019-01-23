package myMediaCenter.reader;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import myMediaCenter.reader.tools.StageSettings;
import myMediaCenter.reader.view.WindowController;

public class Reader extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		/* get the main view */
		final URL fxmlURL = getClass().getResource("view/Window.fxml");  
		final FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);  
		final BorderPane rootLayout = (BorderPane) fxmlLoader.load(); 
		
		/* set scene */
		final WindowController controller = fxmlLoader.getController();
		controller.setPathMedia("/home/vivien/Images/test1.mp4");
		Scene scene = new Scene(rootLayout);
		scene.setFill(Color.BLACK);
		
		/* set stage */
        StageSettings.setFitScreen(primaryStage, false);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
        Application.launch(Reader.class, args);
	}

}
