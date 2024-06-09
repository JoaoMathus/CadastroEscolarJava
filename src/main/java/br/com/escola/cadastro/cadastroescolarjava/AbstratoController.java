package br.com.escola.cadastro.cadastroescolarjava;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public abstract class AbstratoController {
    private SistemaCadastro application;

    public AbstratoController() {}

    public SistemaCadastro getApplication() {
        return application;
    }

    public void setApplication(SistemaCadastro application) {
        this.application = application;
    }

    @FXML
    public void irParaAluno() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("alunoCad.fxml"));
        BorderPane root = fxmlLoader.load();

        AlunoController alunoController = fxmlLoader.getController();
        alunoController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }

    @FXML
    public void irParaProfessor() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("professorCad.fxml"));
        BorderPane root = fxmlLoader.load();

        ProfessorController professorController = fxmlLoader.getController();
        professorController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }

    @FXML
    public void irParaTurma() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("turmaCad.fxml"));
        BorderPane root = fxmlLoader.load();

        TurmaController turmaController = fxmlLoader.getController();
        turmaController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }

    @FXML
    public void irParaNotas() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("notaCad.fxml"));
        BorderPane root = fxmlLoader.load();

        NotaController notaController = fxmlLoader.getController();
        notaController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }

    @FXML
    public void irParaHistorico() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("historico.fxml"));
        BorderPane root = fxmlLoader.load();

        HistoricoController historicoController = fxmlLoader.getController();
        historicoController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }

    @FXML
    public void irParaBemvindo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bemVindo.fxml"));
        BorderPane root = fxmlLoader.load();

        BemvindoController bemvindoController = fxmlLoader.getController();
        bemvindoController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }

    @FXML
    public void voltar() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bemVindo.fxml"));
        BorderPane root = fxmlLoader.load();

        BemvindoController bemvindoController = fxmlLoader.getController();
        bemvindoController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }
}
