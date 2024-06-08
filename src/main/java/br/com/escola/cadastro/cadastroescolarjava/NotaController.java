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
    private Spinner<Double> spinnerTeste1bin;
    @FXML
    private Spinner<Double> spinnerTeste2bin;
    @FXML
    private Spinner<Double> spinnerTeste3bin;
    @FXML
    private Spinner<Double> spinnerTeste4bin;
    @FXML
    private Spinner<Double> spinnerProva1bin;
    @FXML
    private Spinner<Double> spinnerProva2bin;
    @FXML
    private Spinner<Double> spinnerProva3bin;
    @FXML
    private Spinner<Double> spinnerProva4bin;
    @FXML
    private Spinner<Double> spinnerParticipacao1bin;
    @FXML
    private Spinner<Double> spinnerParticipacao2bin;
    @FXML
    private Spinner<Double> spinnerParticipacao3bin;
    @FXML
    private Spinner<Double> spinnerParticipacao4bin;

    private SistemaCadastro application;
    public void setApplication(SistemaCadastro application) {
        this.application = application;
        mostrarTodos();
    }

    public void initialize() {
        configurarCelulasDaTabela();
        configurarSelecaoDaTabela();

        spinnerTeste1bin.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 3, 0));
        spinnerTeste2bin.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 3, 0));
        spinnerTeste3bin.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 3, 0));
        spinnerTeste4bin.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 3, 0));
        spinnerProva1bin.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 6, 0));
        spinnerProva2bin.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 6, 0));
        spinnerProva3bin.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 6, 0));
        spinnerProva4bin.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 6, 0));
        spinnerParticipacao1bin.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, 0));
        spinnerParticipacao2bin.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, 0));
        spinnerParticipacao3bin.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, 0));
        spinnerParticipacao4bin.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, 0));
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
        if (txtMatricula.getText().isEmpty() || escolhaMateria.getValue().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao salvar notas");
            alert.setHeaderText("Não foi possível salvar as notas");
            alert.setContentText("Certifique-se de preencher todos os campos!");

            alert.showAndWait();
            return;
        }

        var aluno = application.getAlunoDao().selecionarPorMatricula(txtMatricula.getText());
        if (aluno == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao salvar notas");
            alert.setHeaderText("Não foi possível salvar as notas");
            alert.setContentText("Aluno não existe!");

            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Confirma essas notas?");
        alert.setContentText("As notas serão gravadas no banco de dados.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            var disciplinas = application.getDisciplinaDao().selecionarTodasPorIdAluno(aluno.getId());
            if (disciplinas.isEmpty()) {
                String[] nomeDasDisciplinas = new String[]{
                        "Matemática", "História", "Português",
                        "Inglês", "Geografia", "Ciências", "Redação",
                        "Artes"
                };

                for (var nome : nomeDasDisciplinas) {
                    var d = new Disciplina();
                    d.setNome(nome);
                    d.setAprovado(false);
                    d.setAnoLetivo(String.valueOf(LocalDate.now().getYear()));
                    d.setIdAluno(aluno.getId());
                    application.getDisciplinaDao().inserir(d);
                }
                disciplinas = application.getDisciplinaDao().selecionarTodasPorIdAluno(aluno.getId());
            }
            var estaDisciplina = disciplinas.stream().filter(d -> d.getNome().equals(escolhaMateria.getValue()))
                    .toList().getFirst();
            var estesBimestres = application.getBimestreDao().selecionarBimestresPorIdDisciplina(estaDisciplina
                    .getId());
            if (estesBimestres.isEmpty()) {
                // Salvar os bimestres
                // Bimestre 1
                var b1 = new Bimestre();
                b1.setIdDisciplina(estaDisciplina.getId());
                b1.setNumeroBimestre(1);
                b1.setTeste(spinnerTeste1bin.getValue());
                b1.setProva(spinnerProva1bin.getValue());
                b1.setPontoDeParticipacao(spinnerParticipacao1bin.getValue());
                application.getBimestreDao().inserir(b1);

                // Bimestre 2
                var b2 = new Bimestre();
                b2.setIdDisciplina(estaDisciplina.getId());
                b2.setNumeroBimestre(2);
                b2.setTeste(spinnerTeste2bin.getValue());
                b2.setProva(spinnerProva2bin.getValue());
                b2.setPontoDeParticipacao(spinnerParticipacao2bin.getValue());
                application.getBimestreDao().inserir(b2);

                // Bimestre 3
                var b3 = new Bimestre();
                b3.setIdDisciplina(estaDisciplina.getId());
                b3.setNumeroBimestre(3);
                b3.setTeste(spinnerTeste3bin.getValue());
                b3.setProva(spinnerProva3bin.getValue());
                b3.setPontoDeParticipacao(spinnerParticipacao3bin.getValue());
                application.getBimestreDao().inserir(b3);

                // Bimestre 4
                var b4 = new Bimestre();
                b4.setIdDisciplina(estaDisciplina.getId());
                b4.setNumeroBimestre(4);
                b4.setTeste(spinnerTeste4bin.getValue());
                b4.setProva(spinnerProva4bin.getValue());
                b4.setPontoDeParticipacao(spinnerParticipacao4bin.getValue());
                application.getBimestreDao().inserir(b4);

                limparCampos();
            } else {
                // Bimestre 1
                var b1 = estesBimestres.stream().filter(b -> b.getNumeroBimestre() == 1).toList().getFirst();
                b1.setTeste(spinnerTeste1bin.getValue());
                b1.setProva(spinnerProva1bin.getValue());
                b1.setPontoDeParticipacao(spinnerParticipacao1bin.getValue());
                application.getBimestreDao().alterar(b1);

                // Bimestre 2
                var b2 = estesBimestres.stream().filter(b -> b.getNumeroBimestre() == 2).toList().getFirst();
                b2.setTeste(spinnerTeste2bin.getValue());
                b2.setProva(spinnerProva2bin.getValue());
                b2.setPontoDeParticipacao(spinnerParticipacao2bin.getValue());
                application.getBimestreDao().alterar(b2);

                // Bimestre 3
                var b3 = estesBimestres.stream().filter(b -> b.getNumeroBimestre() == 3).toList().getFirst();
                b3.setTeste(spinnerTeste3bin.getValue());
                b3.setProva(spinnerProva3bin.getValue());
                b3.setPontoDeParticipacao(spinnerParticipacao3bin.getValue());
                application.getBimestreDao().alterar(b3);

                // Bimestre 4
                var b4 = estesBimestres.stream().filter(b -> b.getNumeroBimestre() == 4).toList().getFirst();
                b4.setTeste(spinnerTeste4bin.getValue());
                b4.setProva(spinnerProva4bin.getValue());
                b4.setPontoDeParticipacao(spinnerParticipacao4bin.getValue());
                application.getBimestreDao().alterar(b4);

                limparCampos();
            }
        }
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

        var aluno = application.getAlunoDao().selecionarPorMatricula(txtMatricula.getText());
        if (aluno == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao buscar notas");
            alert.setHeaderText("Não foi possível buscar as notas!");
            alert.setContentText("Aluno não existe!");

            alert.showAndWait();
            return;
        }

        var disciplinasDoAluno = application.getDisciplinaDao().selecionarTodasPorIdAluno(aluno.getId());
        if (disciplinasDoAluno.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao buscar notas");
            alert.setHeaderText("Não foi possível buscar as notas!");
            alert.setContentText("Não existe essa disciplina cadastrada no aluno!");

            alert.showAndWait();
            return;
        }

        var estaDisciplina = disciplinasDoAluno.stream().filter(d -> d.getNome().equals(escolhaMateria.getValue()))
                .toList().getFirst();
        var bimestres = application.getBimestreDao().selecionarBimestresPorIdDisciplina(estaDisciplina.getId());
        if (bimestres.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao buscar notas");
            alert.setHeaderText("Não foi possível buscar as notas!");
            alert.setContentText("Não há bimestres cadastrados!");

            alert.showAndWait();
            return;
        }

        for (var b : bimestres) {
            if (b.getNumeroBimestre() == 1) {
                spinnerTeste1bin.getValueFactory().setValue(b.getTeste());
                spinnerProva1bin.getValueFactory().setValue(b.getProva());
                spinnerParticipacao1bin.getValueFactory().setValue(b.getPontoDeParticipacao());
                txtSomatorio1bin.setText(String.valueOf(b.getTeste() + b.getProva() + b.getPontoDeParticipacao()));
            }
            if (b.getNumeroBimestre() == 2) {
                spinnerTeste2bin.getValueFactory().setValue(b.getTeste());
                spinnerProva2bin.getValueFactory().setValue(b.getProva());
                spinnerParticipacao2bin.getValueFactory().setValue(b.getPontoDeParticipacao());
                txtSomatorio2bin.setText(String.valueOf(b.getTeste() + b.getProva() + b.getPontoDeParticipacao()));
            }
            if (b.getNumeroBimestre() == 3) {
                spinnerTeste3bin.getValueFactory().setValue(b.getTeste());
                spinnerProva3bin.getValueFactory().setValue(b.getProva());
                spinnerParticipacao3bin.getValueFactory().setValue(b.getPontoDeParticipacao());
                txtSomatorio3bin.setText(String.valueOf(b.getTeste() + b.getProva() + b.getPontoDeParticipacao()));
            }
            if (b.getNumeroBimestre() == 4) {
                spinnerTeste4bin.getValueFactory().setValue(b.getTeste());
                spinnerProva4bin.getValueFactory().setValue(b.getProva());
                spinnerParticipacao4bin.getValueFactory().setValue(b.getPontoDeParticipacao());
                txtSomatorio4bin.setText(String.valueOf(b.getTeste() + b.getProva() + b.getPontoDeParticipacao()));
            }
        }
    }

    @Override
    public void limparCampos() {
        txtMatricula.setText("");
        escolhaMateria.setValue(null);
        spinnerTeste1bin.getValueFactory().setValue(0.0);
        spinnerTeste2bin.getValueFactory().setValue(0.0);
        spinnerTeste3bin.getValueFactory().setValue(0.0);
        spinnerTeste4bin.getValueFactory().setValue(0.0);
        spinnerProva1bin.getValueFactory().setValue(0.0);
        spinnerProva2bin.getValueFactory().setValue(0.0);
        spinnerProva3bin.getValueFactory().setValue(0.0);
        spinnerProva4bin.getValueFactory().setValue(0.0);
        spinnerParticipacao1bin.getValueFactory().setValue(0.0);
        spinnerParticipacao2bin.getValueFactory().setValue(0.0);
        spinnerParticipacao3bin.getValueFactory().setValue(0.0);
        spinnerParticipacao4bin.getValueFactory().setValue(0.0);
        txtSomatorio1bin.setText("");
        txtSomatorio2bin.setText("");
        txtSomatorio3bin.setText("");
        txtSomatorio4bin.setText("");
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