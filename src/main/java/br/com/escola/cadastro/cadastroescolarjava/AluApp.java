package br.com.escola.cadastro.cadastroescolarjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AluApp extends Application {
    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(AluApp.class.getResource("alunoCad.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Cadastro Escolar");
        primaryStage.setScene(scene);

        AlunoController alunoController = fxmlLoader.getController();
        alunoController.setApplication(this);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}