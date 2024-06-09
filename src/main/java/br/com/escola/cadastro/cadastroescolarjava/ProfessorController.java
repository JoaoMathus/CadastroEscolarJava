package br.com.escola.cadastro.cadastroescolarjava;

import br.com.escola.cadastro.cadastroescolarjava.entidades.Professor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class ProfessorController extends AbstratoController implements IPessoaController {
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

    @Override
    public void setApplication(SistemaCadastro application) {
        super.setApplication(application);
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
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                dataNascimento.setValue(LocalDate.parse(novaSelecao.getDataNascimento(), dtf));
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

        var professores = getApplication().getProfessorDao().selecionarTodos();
        for (var professor : professores) {
            if (txtCpf.getText().equals(professor.getCpf())) {
                // Atualização de dados.
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmação");
                alert.setHeaderText("Atualização de dados");
                alert.setContentText("Deseja mesmo atualizar esses dados?");

                Optional<ButtonType> result = alert.showAndWait();
                result.ifPresent(buttonType -> {
                    if (buttonType == ButtonType.OK) {
                        var professorAtualizado = new Professor(professor.getId(), txtNome.getText(),
                                txtTelefone.getText(), txtCelular.getText(), dataNascimento.getValue().toString(),
                                txtCpf.getText());
                        getApplication().getProfessorDao().alterar(professorAtualizado);
                        mostrarTodos();
                    }
                });
                return;
            }
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Confirma esse cadastro?");
        alert.setContentText("Um novo professor será salvo no banco de dados");

        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                var p = new Professor(txtNome.getText(), txtTelefone.getText(), txtCelular.getText(),
                        txtCpf.getText(), dataNascimento.getValue().toString());
                getApplication().getProfessorDao().inserir(p);
            }
        });

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
            resposta.ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    List<Professor> professores = getApplication().getProfessorDao().selecionarTodos();
                    for (Professor professor : professores) {
                        if (professor.getCpf().equals(cpf)) {
                            getApplication().getProfessorDao().deletar(professor.getId());
                        }
                    }
                }
            });
        });
    }

    @Override
    @FXML
    public void selecionar() {
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));

        if (txtPesquisa.getText().equalsIgnoreCase("todos")) {
            List<Professor> todosProfessores = getApplication().getProfessorDao().selecionarTodos();
            tabelaProfessores.setItems(FXCollections.observableArrayList(todosProfessores));
            return;
        }

        List<Professor> professoresFiltrados = getApplication().getProfessorDao().selecionarTodos()
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
        tabelaProfessores.setItems(FXCollections.observableArrayList(getApplication().getProfessorDao().selecionarTodos()));
    }
}
