package br.com.escola.cadastro.cadastroescolarjava;

import br.com.escola.cadastro.cadastroescolarjava.acessobanco.AlunoDao;
import br.com.escola.cadastro.cadastroescolarjava.acessobanco.ProfessorDao;
import br.com.escola.cadastro.cadastroescolarjava.acessobanco.TurmaDao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AluApp extends Application {
    private Stage primaryStage;
    private ProfessorDao professorDao;
    private TurmaDao turmaDao;
    private AlunoDao alunoDao;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(AluApp.class.getResource("alunoCad.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Cadastro Escolar");
        primaryStage.setScene(scene);

        // Iniciando o banco de dados
        professorDao = new ProfessorDao();
        turmaDao = new TurmaDao();
        alunoDao = new AlunoDao();

        AlunoController alunoController = fxmlLoader.getController();
        alunoController.setApplication(this);

        primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ProfessorDao getProfessorDao() {
        return professorDao;
    }

    public TurmaDao getTurmaDao() {
        return turmaDao;
    }

    public AlunoDao getAlunoDao() {
        return alunoDao;
    }

    public static void main(String[] args) {
        launch();
    }
}