package br.com.escola.cadastro.cadastroescolarjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class BemvindoApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("bemVindo.fxml"));

        primaryStage.setScene(new Scene(root, 1280, 800));
        primaryStage.setTitle("Loop de Imagens");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
