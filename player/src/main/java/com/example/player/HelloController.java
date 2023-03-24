package com.example.player;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    void btnclick(ActionEvent event) {
        System.out.println("Hi");
    }

    MediaPlayer player;
    @FXML
    private MediaView mediaView;

    @FXML
    private Button nextBtn;

    @FXML
    private Button playbtn;

    @FXML
    private Button preBtn;

    @FXML
    private Slider timeSlider;



    @FXML
    void openSongMenu(ActionEvent event) {

        try {
            System.out.println("Open song click");
            FileChooser chooser=new FileChooser();
            File file=chooser.showOpenDialog(null);
            Media m=new Media(file.toURI().toURL().toString());

            if(player!=null)
            {
                player.dispose();
            }

            player=new MediaPlayer(m);

            mediaView.setMediaPlayer(player);

            player.setOnReady(()->{
                timeSlider.setMin(0);
                timeSlider.setMax(player.getMedia().getDuration().toSeconds());
                System.out.println(player.getMedia().getDuration().toSeconds());
                timeSlider.setValue(0);
                try {
                    playbtn.setGraphic(new ImageView(new Image(new FileInputStream("src/main/java/icons/play.png"))));

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

            });

            //listener for player

            player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {

                    timeSlider.setValue(player.getCurrentTime().toSeconds());
                }
            });

            timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    if(timeSlider.isPressed())
                    {
                        player.seek(new Duration(timeSlider.getValue()*1000));
                    }

                }
            });
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void play(ActionEvent event) {

        MediaPlayer.Status status=player.getStatus();
        if(status==MediaPlayer.Status.PLAYING)
        {
            player.pause();
            try {
                playbtn.setGraphic(new ImageView(new Image(new FileInputStream("src/main/java/icons/play.png"))));

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else
        {
            player.play();
            try {
                playbtn.setGraphic(new ImageView(new Image(new FileInputStream("src/main/java/icons/pause.png"))));

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            playbtn.setGraphic(new ImageView(new Image(new FileInputStream("src/main/java/icons/play.png"))));
            nextBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/main/java/icons/next.png"))));
            preBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/main/java/icons/previous.png"))));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void nextBtnClick(ActionEvent event) {

        player.seek(new Duration((player.getCurrentTime().toSeconds()+10)*1000));
    }


    @FXML
    void preBtnClick(ActionEvent event) {
        player.seek(new Duration((player.getCurrentTime().toSeconds()-10)*1000));
    }
}