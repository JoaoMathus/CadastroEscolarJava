package br.com.escola.cadastro.cadastroescolarjava;

import br.com.escola.cadastro.cadastroescolarjava.acessobanco.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SistemaCadastro extends Application {
    private Stage primaryStage;
    private ProfessorDao professorDao;
    private TurmaDao turmaDao;
    private AlunoDao alunoDao;
    private DisciplinaDao disciplinaDao;
    private BimestreDao bimestreDao;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(SistemaCadastro.class.getResource("bemVindo.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Cadastro Escolar");
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

        // Iniciando o banco de dados
        professorDao = new ProfessorDao();
        turmaDao = new TurmaDao();
        alunoDao = new AlunoDao();
        disciplinaDao = new DisciplinaDao();
        bimestreDao = new BimestreDao();

        BemvindoController bemvindoController = fxmlLoader.getController();
        bemvindoController.setApplication(this);

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

    public DisciplinaDao getDisciplinaDao() {
        return disciplinaDao;
    }

    public BimestreDao getBimestreDao() {
        return bimestreDao;
    }

    public static void main(String[] args) {
        launch();
    }
}