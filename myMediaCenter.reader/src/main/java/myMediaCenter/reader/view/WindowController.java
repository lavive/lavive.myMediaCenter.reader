package myMediaCenter.reader.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

/**
 * Window controller class
 * 
 * @author lavive
 *
 */
public class WindowController implements Initializable {
	
	/* Attributes */
	/* from view fxml */
	@FXML
	private BorderPane mainPane;
	
	@FXML
	private MediaView mediaView;
	
	@FXML
    private Slider timeSlider;
	
	@FXML
    private Label playTime;
	
	@FXML
    private Slider volumeSlider;
	
	@FXML
    private HBox mediaBar;
	
	@FXML
	private Button playButton;
	
	@FXML
	private Label timeLabel;
	
	@FXML
	private Label volumeLabel;
	
	/* media */
	private Media media;	
	private MediaPlayer mediaPlayer;
	
	/* controls */
    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/* set sizes */	    
	    final DoubleProperty width = mediaView.fitWidthProperty();
	    final DoubleProperty height = mediaView.fitHeightProperty();
	    
	    width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
	    height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
	    
	    mediaView.setPreserveRatio(true);
	    //mediaView.getTransforms().add(new Rotate(90,Sizes.SCREEN_WIDTH/2,Sizes.SCREEN_HEIGHT/2));
		timeSlider.setMinWidth(50);
		timeSlider.setMaxWidth(Double.MAX_VALUE);
		playTime.setPrefWidth(130);
		playTime.setMinWidth(50);
		volumeSlider.setPrefWidth(70);
		volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
		volumeSlider.setMinWidth(30);
	}
	
	@FXML
	public void play_pause() {
		 Status status = mediaPlayer.getStatus();		 
	        if (status == Status.UNKNOWN  || status == Status.HALTED){
	            // don't do anything in these states
	            return;
	        }	 
	        if ( status == Status.PAUSED
	          || status == Status.READY
	          || status == Status.STOPPED){
	            // rewind the movie if we're sitting at the end
	            if (atEndOfMedia) {
	            	 mediaPlayer.seek(mediaPlayer.getStartTime());
	                atEndOfMedia = false;
	            }
	            mediaPlayer.play();
	        } else {
	            mediaPlayer.pause();
	        }
	}
	
//	
//	mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() 
//    {
//        public void invalidated(Observable ov) {
//            updateValues();
//        }
//    });

	private void playing() {
        if (stopRequested) {
        	mediaPlayer.pause();
            stopRequested = false;
        } else {
            playButton.setText("||");
        }
	}
//	mediaPlayer.setOnPlaying(new Runnable() {
//        public void run() {
//            if (stopRequested) {
//                mp.pause();
//                stopRequested = false;
//            } else {
//                playButton.setText("||");
//            }
//        }
//    });

	private void onPause() {
        playButton.setText(">");
	}
//    mp.setOnPaused(new Runnable() {
//        public void run() {
//            System.out.println("onPaused");
//            playButton.setText(">");
//        }
//    });

//    mp.setOnReady(new Runnable() {
//        public void run() {
//            duration = mp.getMedia().getDuration();
//            updateValues();
//        }
//    });
    private void onReady() {
        duration = mediaPlayer.getMedia().getDuration();
        updateValues();
    }

//    mp.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
//    mp.setOnEndOfMedia(new Runnable() {
//        public void run() {
//            if (!repeat) {
//                playButton.setText(">");
//                stopRequested = true;
//                atEndOfMedia = true;
//            }
//        }
//   });
    private void onEndOfMedia() {
        if (!repeat) {
            playButton.setText(">");
            stopRequested = true;
            atEndOfMedia = true;
        }
    }
//    timeSlider.valueProperty().addListener(new InvalidationListener() {
//        public void invalidated(Observable ov) {
//           if (timeSlider.isValueChanging()) {
//           // multiply duration by percentage calculated by slider position
//              mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
//           }
//        }
//    });
    
    private void timeListener() {
        if (timeSlider.isValueChanging()) {
        // multiply duration by percentage calculated by slider position
        	mediaPlayer.seek(duration.multiply(timeSlider.getValue() / 100.0));
        }
    }
//    volumeSlider.valueProperty().addListener(new InvalidationListener() {
//        public void invalidated(Observable ov) {
//           if (volumeSlider.isValueChanging()) {
//               mp.setVolume(volumeSlider.getValue() / 100.0);
//           }
//        }
//    });
    private void volumeListener() {
        if (volumeSlider.isValueChanging()) {
        	mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
        }
    }
    
    private void updateValues() {
    	  if (playTime != null && timeSlider != null && volumeSlider != null) {
    		  Platform.runLater(new Runnable() {
    			  public void run() {
    				  Duration currentTime = mediaPlayer.getCurrentTime();
    				  playTime.setText(formatTime(currentTime, duration));
    				  timeSlider.setDisable(duration.isUnknown());
    				  if (!timeSlider.isDisabled() 
    						  && duration.greaterThan(Duration.ZERO) 
    						  && !timeSlider.isValueChanging()) {
    					  timeSlider.setValue(currentTime.divide(duration.toMillis()).toMillis()
    							  * 100.0);
    				  }
	    	          if (!volumeSlider.isValueChanging()) {
	    	        	  volumeSlider.setValue((int)Math.round(mediaPlayer.getVolume() 
	    	                  * 100));
	    	          }
    			  }
    	     });
    	  }
    }
    
    private static String formatTime(Duration elapsed, Duration duration) {
    	int intElapsed = (int)Math.floor(elapsed.toSeconds());
    	int elapsedHours = intElapsed / (60 * 60);
    	if (elapsedHours > 0) {
    	    intElapsed -= elapsedHours * 60 * 60;
    	}
    	int elapsedMinutes = intElapsed / 60;
    	int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 
    	                                - elapsedMinutes * 60;
    	 
    	if (duration.greaterThan(Duration.ZERO)) {
    	    int intDuration = (int)Math.floor(duration.toSeconds());
    	    int durationHours = intDuration / (60 * 60);
    	    if (durationHours > 0) {
    	         intDuration -= durationHours * 60 * 60;
    	    }
    	    int durationMinutes = intDuration / 60;
    	    int durationSeconds = intDuration - durationHours * 60 * 60 - 
    	    						            durationMinutes * 60;
    	    if (durationHours > 0) {
    	        return String.format("%d:%02d:%02d/%d:%02d:%02d", 
    	           elapsedHours, elapsedMinutes, elapsedSeconds,
    	           durationHours, durationMinutes, durationSeconds);
    	    } else {
    	        return String.format("%02d:%02d/%02d:%02d",
    	          elapsedMinutes, elapsedSeconds,durationMinutes, 
    	                durationSeconds);
    	    }
   	    } else {
    	    if (elapsedHours > 0) {
    	         return String.format("%d:%02d:%02d", elapsedHours, 
    	                    elapsedMinutes, elapsedSeconds);
    	    } else {
    	         return String.format("%02d:%02d",elapsedMinutes, 
    	                    elapsedSeconds);
    	    }
    	}
    }
    
    
	/* setter */
	public void setPathMedia(String pathMedia) {		
		/* set Media */
		media = new Media(new File(pathMedia).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		mediaView.setMediaPlayer(mediaPlayer);
		
		/* add listeners */
		mediaPlayer.currentTimeProperty().addListener((o) -> updateValues());
		mediaPlayer.setOnPlaying(() -> playing());
		mediaPlayer.setOnPaused(() -> onPause());
		mediaPlayer.setOnReady(() -> onReady());
		mediaPlayer.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
		mediaPlayer.setOnEndOfMedia(() -> onEndOfMedia());
		timeSlider.valueProperty().addListener((o) -> timeListener());
		volumeSlider.valueProperty().addListener((o) -> volumeListener());
	}
	
}
