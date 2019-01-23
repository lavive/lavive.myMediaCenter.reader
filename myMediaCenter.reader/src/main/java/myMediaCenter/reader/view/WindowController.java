package myMediaCenter.reader.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * Window controller class
 * 
 * @author lavive
 *
 */
public class WindowController implements Initializable {
	
	/* Attributes */
	@FXML
	private BorderPane mainPane;
	
	@FXML
	private MediaView mediaView;
	
	private Media media;
	
	private MediaPlayer mediaPlayer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/* set sizes */	    
	    final DoubleProperty width = mediaView.fitWidthProperty();
	    final DoubleProperty height = mediaView.fitHeightProperty();
	    
	    width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
	    height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
	    
	    mediaView.setPreserveRatio(true);
	    //mediaView.getTransforms().add(new Rotate(90,Sizes.SCREEN_WIDTH/2,Sizes.SCREEN_HEIGHT/2));

	}
	
	/* setter */
	public void setPathMedia(String pathMedia) {		
		/* set Media */
		media = new Media(new File(pathMedia).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		mediaView.setMediaPlayer(mediaPlayer);
	}
	
}
