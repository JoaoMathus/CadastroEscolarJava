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
            new Image(getClass().getResourceAsStream("image1.png")),
            new Image(getClass().getResourceAsStream("image2.png")),
            new Image(getClass().getResourceAsStream("image3.png")),
            new Image(getClass().getResourceAsStream("image4.png"))
    };

    public void initialize() {

        // Inicia o loop de imagens
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(6000); // Aguarda 3 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentIndex = (currentIndex + 1) % images.length;
                imageView.setImage(images[currentIndex]);
            }
        }).start();


        // Creating a fade transition
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5), imageView);
        fadeTransition.setFromValue(0.9); // Start fully visible
        fadeTransition.setToValue(0.2);   // End invisible
        fadeTransition.setAutoReverse(true); // Reverse the fade
        fadeTransition.setCycleCount(Animation.INDEFINITE); // Run indefinitely

        // Creating a timeline to control the fade transition
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            fadeTransition.playFromStart(); // Start the fade transition from the beginning
        }), new KeyFrame(Duration.seconds(6))); // Delay between each iteration
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
