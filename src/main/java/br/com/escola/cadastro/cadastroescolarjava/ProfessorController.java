package br.com.escola.cadastro.cadastroescolarjava;

import br.com.escola.cadastro.cadastroescolarjava.entidades.Professor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ProfessorController implements BaseController, PessoaController {
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtCelular;
    @FXML
    private TextField txtCpf;
    @FXML
    private DatePicker dataNascimento;
    @FXML
    private TextField txtPesquisa;
    @FXML
    private TableView<Professor> tabelaProfessores;
    @FXML
    private TableColumn<Professor, String> colunaCpf;
    @FXML
    private TableColumn<Professor, String> colunaNome;
    @FXML
    private TableColumn<Professor, String> colunaCelular;


    private SistemaCadastro application;
    public void setApplication(SistemaCadastro application) {
        this.application = application;
        mostrarTodos();
    }

    // Começo de tudo...
    // Inicialização e configuração do controller.
    public void initialize() {
        aplicarValidacao(txtTelefone);
        aplicarValidacao(txtCelular);
        aplicarValidacao(txtCpf);
        configurarCelulasDaTabela();
        configurarSelecaoDaTabela();
        configurarCelulaDeData();
    }

    @Override
    public void configurarSelecaoDaTabela() {
        tabelaProfessores.getSelectionModel().selectedItemProperty().addListener((obs, selecaoAntiga, novaSelecao) -> {
            if (novaSelecao != null) {
                txtNome.setText(novaSelecao.getNome());
                txtCpf.setText(novaSelecao.getCpf());
                txtTelefone.setText(novaSelecao.getTelefone());
                txtCelular.setText(novaSelecao.getCelular());
            }
        });
    }

    @Override
    public void configurarCelulaDeData() {
        dataNascimento.setDayCellFactory(d -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean vazio) {
                super.updateItem(item, vazio);
                setDisable(item.isAfter(LocalDate.now()));
            }
        });
    }

    @Override
    public void configurarCelulasDaTabela() {
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));
    }

    @Override
    public void aplicarValidacao(TextField tf) {
        tf.textProperty().addListener((observable, valorAntigo, valorNovo) -> {
            // Limitando a apenas valores numéricos
            if (!valorNovo.matches("\\d*")) {
                tf.setText(valorNovo.replaceAll("[^\\d]", ""));
            }

            // Limitando ao número máximo de valores na entrada
            if (tf.textProperty().getValue().length() > 11) {
                tf.setText(valorAntigo);
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
        if (txtNome.getText().isEmpty() || dataNascimento.getValue().toString().isEmpty() ||
                txtTelefone.getText().isEmpty() || txtCelular.getText().isEmpty() ||
                txtCpf.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao salvar professor");
            alert.setHeaderText("Não foi possível salvar o professor");
            alert.setContentText("Certifique-se de preencher todos os campos!");

            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Confirma esse cadastro?");
        alert.setContentText("Um novo professor será salvo no banco de dados");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            var p = new Professor(txtNome.getText(), txtTelefone.getText(), txtCelular.getText(),
                    txtCpf.getText(), dataNascimento.getValue().toString());
            application.getProfessorDao().inserir(p);
        }

        limparCampos();
        mostrarTodos();
    }

    @Override
    @FXML
    public void deletar() {
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Deletar um professor");
        dialogo.setHeaderText("Digite o cpf do professor que você quer deletar");
        dialogo.setContentText("Cpf:");

        Optional<String> resultado = dialogo.showAndWait();
        resultado.ifPresent(cpf -> {
            Alert confirma = new Alert(Alert.AlertType.CONFIRMATION);
            confirma.setTitle("Deletar professor");
            confirma.setHeaderText("Deseja mesmo deletar esse professor?");
            confirma.setContentText("O professor será apagado do banco de dados");

            Optional<ButtonType> resposta = confirma.showAndWait();
            if (resposta.get() == ButtonType.OK) {
                List<Professor> professores = application.getProfessorDao().selecionarTodos();
                for (Professor professor : professores) {
                    if (professor.getCpf().equals(cpf)) {
                        application.getProfessorDao().deletar(professor.getId());
                    }
                }
            }
        });
    }

    @Override
    @FXML
    public void selecionar() {
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));

        if (txtPesquisa.getText().equalsIgnoreCase("todos")) {
            List<Professor> todosProfessores = application.getProfessorDao().selecionarTodos();
            tabelaProfessores.setItems(FXCollections.observableArrayList(todosProfessores));
            return;
        }

        List<Professor> professoresFiltrados = application.getProfessorDao().selecionarTodos()
                .stream().filter(professor -> professor.getCpf().equals(txtPesquisa.getText()))
                .toList();

        ObservableList<Professor> listaObservavel = FXCollections.observableArrayList(professoresFiltrados);

        tabelaProfessores.setItems(listaObservavel);
    }

    @Override
    public void limparCampos() {
        txtNome.setText("");
        txtTelefone.setText("");
        txtCelular.setText("");
        txtCpf.setText("");
        txtPesquisa.setText("");
    }

    @Override
    public void mostrarTodos() {
        tabelaProfessores.setItems(FXCollections.observableArrayList(application.getProfessorDao().selecionarTodos()));
    }
}
