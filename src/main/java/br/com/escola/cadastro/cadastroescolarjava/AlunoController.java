package br.com.escola.cadastro.cadastroescolarjava;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;

public class AlunoController {
    @FXML
    private MenuItem menuAluno;
    @FXML
    private MenuItem menuProfessor;
    @FXML
    private MenuItem menuTurma;
    @FXML
    private MenuItem menuNota;

    private AluApp application;

    public void setApplication(AluApp application) {
        this.application = application;
    }

    @FXML
    protected void irParaAluno() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("alunoCad.fxml"));
        AnchorPane root = fxmlLoader.load();

        AlunoController alunoController = fxmlLoader.getController();
        alunoController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }

    @FXML
    protected void irParaProfessor() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("professorCad.fxml"));
        AnchorPane root = fxmlLoader.load();

        ProfessorController professorController = fxmlLoader.getController();

        professorController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }
}