package br.com.escola.cadastro.cadastroescolarjava;

import br.com.escola.cadastro.cadastroescolarjava.entidades.Aluno;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
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
    @FXML
    private TextField txtMatricula;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtCelular;
    @FXML
    private TextField txtCpfResponsavel;
    @FXML
    private DatePicker dataNascimento;
    @FXML
    private ChoiceBox<String> escolhaTipoSanguineo;

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

    @FXML
    protected void salvarAluno() {
        Aluno a = new Aluno(1, txtNome.getText(), dataNascimento.getValue().toString(), txtMatricula.getText(),
                txtTelefone.getText(), txtCelular.getText(), txtCpfResponsavel.getText(), escolhaTipoSanguineo.getValue().toString(),
                "3", false, 1);

        System.out.println(a.toString());
    }
}