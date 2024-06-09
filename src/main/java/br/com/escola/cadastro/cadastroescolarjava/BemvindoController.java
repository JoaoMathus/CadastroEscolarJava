package br.com.escola.cadastro.cadastroescolarjava;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class BemvindoController extends AbstratoController {
    @FXML
    private ImageView imageView;

    private int currentIndex = 0;
    private final Image[] images = {
            new Image(getClass().getResourceAsStream("assets/image1.png")),
            new Image(getClass().getResourceAsStream("assets/image2.png")),
            new Image(getClass().getResourceAsStream("assets/image3.png")),
            new Image(getClass().getResourceAsStream("assets/image4.png"))
    };

    public void initialize() {


        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentIndex = (currentIndex + 1) % images.length;
                imageView.setImage(images[currentIndex]);
            }
        }).start();



        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5), imageView);
        fadeTransition.setFromValue(0.9);
        fadeTransition.setToValue(0.1);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(Animation.INDEFINITE);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            fadeTransition.playFromStart();
        }), new KeyFrame(Duration.seconds(4)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
