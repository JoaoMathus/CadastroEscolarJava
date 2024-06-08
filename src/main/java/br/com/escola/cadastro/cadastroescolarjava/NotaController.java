package br.com.escola.cadastro.cadastroescolarjava;

import br.com.escola.cadastro.cadastroescolarjava.entidades.Aluno;
import br.com.escola.cadastro.cadastroescolarjava.entidades.Bimestre;
import br.com.escola.cadastro.cadastroescolarjava.entidades.Disciplina;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NotaController implements BaseController, EntidadeController {
    @FXML
    private TableView<Aluno> tabelaAlunos;
    @FXML
    private TableColumn<Aluno, String> colunaMatricula;
    @FXML
    private TableColumn<Aluno, String> colunaNome;
    @FXML
    private TableColumn<Aluno, String> colunaTurma;
    @FXML
    private TextField txtMatricula;
    @FXML
    private ChoiceBox<String> escolhaMateria;
    @FXML
    private TextField txtSomatorio1bin;
    @FXML
    private TextField txtSomatorio2bin;
    @FXML
    private TextField txtSomatorio3bin;
    @FXML
    private TextField txtSomatorio4bin;
    @FXML
    private Spinner<Float> spinnerTeste1bin;
    @FXML
    private Spinner<Float> spinnerTeste2bin;
    @FXML
    private Spinner<Float> spinnerTeste3bin;
    @FXML
    private Spinner<Float> spinnerTeste4bin;
    @FXML
    private Spinner<Float> spinnerProva1bin;
    @FXML
    private Spinner<Float> spinnerProva2bin;
    @FXML
    private Spinner<Float> spinnerProva3bin;
    @FXML
    private Spinner<Float> spinnerProva4bin;
    @FXML
    private Spinner<Integer> spinnerParticipacao1bin;
    @FXML
    private Spinner<Integer> spinnerParticipacao2bin;
    @FXML
    private Spinner<Integer> spinnerParticipacao3bin;
    @FXML
    private Spinner<Integer> spinnerParticipacao4bin;

    private SistemaCadastro application;
    public void setApplication(SistemaCadastro application) {
        this.application = application;
        mostrarTodos();
    }

    public void initialize() {
        configurarCelulasDaTabela();
        configurarSelecaoDaTabela();
    }

    @Override
    public void configurarCelulasDaTabela() {
        colunaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaTurma.setCellValueFactory(new PropertyValueFactory<>("numeroTurma"));
    }

    @Override
    public void configurarSelecaoDaTabela() {
        tabelaAlunos.getSelectionModel().selectedItemProperty().addListener((obs, selecaoAntiga, novaSelecao) -> {
            if (novaSelecao != null) {
                txtMatricula.setText(novaSelecao.getMatricula());
            }
        });
    }

    @Override
    @FXML
    public void irParaAluno() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("alunoCad.fxml"));
        BorderPane root = fxmlLoader.load();

        AlunoController alunoController = fxmlLoader.getController();
        alunoController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }

    @Override
    @FXML
    public void irParaProfessor() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("professorCad.fxml"));
        BorderPane root = fxmlLoader.load();

        ProfessorController professorController = fxmlLoader.getController();
        professorController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }

    @Override
    @FXML
    public void irParaTurma() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("turmaCad.fxml"));
        BorderPane root = fxmlLoader.load();

        TurmaController turmaController = fxmlLoader.getController();
        turmaController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }

    @Override
    @FXML
    public void irParaNotas() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("notaCad.fxml"));
        BorderPane root = fxmlLoader.load();

        NotaController notaController = fxmlLoader.getController();
        notaController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }

    @Override
    @FXML
    public void irParaHistorico() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("historico.fxml"));
        BorderPane root = fxmlLoader.load();

        HistoricoController historicoController = fxmlLoader.getController();
        historicoController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }

    @Override
    @FXML
    public void salvar() {

    }

    @Override
    @FXML
    public void deletar() {
    }

    @Override
    @FXML
    public void selecionar() {
        if (txtMatricula.getText().isEmpty() || escolhaMateria.getValue().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao buscar notas");
            alert.setHeaderText("Não foi possível buscar as notas!");
            alert.setContentText("Certifique-se de preencher todos os campos!");

            alert.showAndWait();
            return;
        }


    }

    @Override
    public void limparCampos() {
        txtMatricula.setText("");
        escolhaMateria.setValue(null);
    }

    @Override
    public void mostrarTodos() {
        List<Aluno> listaFinal = new ArrayList<>();
        var alunos = application.getAlunoDao().selecionarTodos();
        var turmas = application.getTurmaDao().selecionarTodos();
        for (var aluno : alunos) {
            for (var turma : turmas) {
                if (aluno.getIdTurma() == turma.getId()) {
                    aluno.setNumeroTurma(turma.getNumeroDaSala());
                    listaFinal.add(aluno);
                }
            }
        }
        tabelaAlunos.setItems(FXCollections.observableArrayList(listaFinal));
    }
}