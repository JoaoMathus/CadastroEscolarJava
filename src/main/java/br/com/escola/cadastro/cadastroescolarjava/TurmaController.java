package br.com.escola.cadastro.cadastroescolarjava;

import br.com.escola.cadastro.cadastroescolarjava.entidades.Pessoa;
import br.com.escola.cadastro.cadastroescolarjava.entidades.Professor;
import br.com.escola.cadastro.cadastroescolarjava.entidades.Turma;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TurmaController implements BaseController, EntidadeController {
    @FXML
    private Spinner<String> spinnerSerie;
    @FXML
    private ChoiceBox<String> escolhaProfessor;
    @FXML
    private TextField txtTurma;
    @FXML
    private TableView<Turma> tabelaDeTurmas;
    @FXML
    private TableColumn<Turma, String> colunaTurma;
    @FXML
    private TableColumn<Turma, String> colunaProfessor;

    private SistemaCadastro application;
    public void setApplication(SistemaCadastro application) {
        this.application = application;
        var professores = application.getProfessorDao().selecionarTodos().stream().map(Pessoa::getNome).toList();
        escolhaProfessor.setItems(FXCollections.observableArrayList(professores));
        mostrarTodos();
    }

    // Começo de tudo...
    // Inicializando e configurando o controller.
    public void initialize() {
        configurarCelulasDaTabela();
        configurarSelecaoDaTabela();

        String[] series = new String[] {"1", "2", "3", "4", "5"};
        spinnerSerie.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList(series)));
    }

    @Override
    @FXML
    public void configurarCelulasDaTabela() {
        colunaTurma.setCellValueFactory(new PropertyValueFactory<>("numeroDaSala"));
        colunaProfessor.setCellValueFactory(new PropertyValueFactory<>("nomeProfessor"));
    }

    @Override
    @FXML
    public void configurarSelecaoDaTabela() {
        tabelaDeTurmas.getSelectionModel().selectedItemProperty().addListener((obs, selecaoAntiga, novaSelecao) -> {
            if (novaSelecao != null) {
                txtTurma.setText(novaSelecao.getNumeroDaSala());
                spinnerSerie.setPromptText(novaSelecao.getSerie());
                escolhaProfessor.setValue(novaSelecao.getNomeProfessor());
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
        if (escolhaProfessor.getValue().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao salvar turma");
            alert.setHeaderText("Não foi possível salvar a turma");
            alert.setContentText("Certifique-se de preencher todos os campos!");

            alert.showAndWait();
            return;
        }

        // Lógica para criar o número da turma
        var turmas = application.getTurmaDao().selecionarTodos();
        var turmasFiltradas = turmas.stream().filter(t -> t.getSerie().equals(spinnerSerie.getValue()))
                .toList();
        StringBuilder numero = new StringBuilder();
        if (turmasFiltradas.isEmpty()) {
            numero.append(spinnerSerie.getValue()).append("001");
        } else {
            numero.append(spinnerSerie.getValue()).append(String.format("%03d", Integer.parseInt(turmasFiltradas.getLast().getNumeroDaSala().substring(1))+1));
        }
        txtTurma.setText(numero.toString());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Confirma esse cadastro?");
        alert.setContentText("Uma nova turma será salva no banco de dados");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            var professor = application.getProfessorDao().selecionarTodos()
                    .stream().filter(p -> p.getNome().equals(escolhaProfessor.getValue())).toList();
            var turma = new Turma(txtTurma.getText(), String.valueOf(spinnerSerie.getValue()), professor.getFirst().getId(), professor.getFirst().getNome());

            application.getTurmaDao().inserir(turma);
        }

        limparCampos();
        mostrarTodos();
    }

    @Override
    @FXML
    public void deletar() {
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Deletar uma turma");
        dialogo.setHeaderText("Digite o número da turma que você quer deletar");
        dialogo.setContentText("Número da sala:");

        Optional<String> resultado = dialogo.showAndWait();
        resultado.ifPresent(numero -> {
            var listaTurmas = application.getTurmaDao().selecionarTodos();
            var listaFiltrada = listaTurmas.stream().filter(t -> t.getNumeroDaSala().equals(numero)).toList();
            var numeros = listaFiltrada.stream().map(Turma::getNumeroDaSala).toList();
            StringBuilder mensagem = new StringBuilder();
            System.out.println(listaFiltrada);
            mensagem.append("Deseja mesmo deletar ");
            if (numeros.size() == 1) {
                mensagem.append("essa turma: " + numeros.getFirst() + "?");
            } else {
                mensagem.append("essas turmas: ");
                for (int i = 0; i < numeros.size(); i++) {
                    mensagem.append(numeros.get(i));
                    mensagem.append(", ");
                }
                mensagem.append(numeros.getLast());
                mensagem.append("?");
            }

            Alert confirma = new Alert(Alert.AlertType.CONFIRMATION);
            confirma.setTitle("Deletar turma");
            confirma.setHeaderText(mensagem.toString());
            confirma.setContentText("A turma será apagada do banco de dados");

            Optional<ButtonType> resposta = confirma.showAndWait();
            if (resposta.get() == ButtonType.OK) {
                listaFiltrada.forEach(a -> application.getTurmaDao().deletar(a.getId()));
            }
        });

        limparCampos();
        mostrarTodos();
    }

    @Override
    @FXML
    public void selecionar() {
    }

    @Override
    public void limparCampos() {
        txtTurma.setText("");
        escolhaProfessor.setValue(null);
    }

    @Override
    public void mostrarTodos() {
        List<Turma> listaFinal = new ArrayList<>();
        var turmas = application.getTurmaDao().selecionarTodos();
        var professores = application.getProfessorDao().selecionarTodos();
        for (var turma : turmas) {
            for (var professor : professores) {
                if (turma.getIdProfessor() == professor.getId()) {
                    turma.setNomeProfessor(professor.getNome());
                    listaFinal.add(turma);
                }
            }
        }
        tabelaDeTurmas.setItems(FXCollections.observableArrayList(listaFinal));
    }
}
