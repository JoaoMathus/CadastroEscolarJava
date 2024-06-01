package br.com.escola.cadastro.cadastroescolarjava;

import br.com.escola.cadastro.cadastroescolarjava.entidades.Aluno;
import br.com.escola.cadastro.cadastroescolarjava.entidades.Professor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ProfessorController {
    @FXML
    private MenuItem menuAluno;
    @FXML
    private MenuItem menuProfessor;
    @FXML
    private MenuItem menuTurma;
    @FXML
    private MenuItem menuNota;
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
    private TableColumn<Professor, String> colunaTurma;


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
    protected void salvarProfessor() {
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
            // TODO: Adicionar lógica para resgatar uma turma pelo número (que é o control da tela de cadastro).
            // TODO: e então resgatar a "série" pelo número e adicioná-lo ao objeto do aluno.
            application.getProfessorDao().inserir(txtNome.getText(), dataNascimento.getValue().toString(),
                    txtTelefone.getText(), txtCelular.getText(),
                    txtCpf.getText());
        }

        application.getProfessorDao().selecionarTodos().forEach(System.out::println);
    }

    @FXML
    protected void deletarProfessor() {
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

    @FXML
    protected void selecionarPorCpf() {
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaTurma.setCellValueFactory(new PropertyValueFactory<>("serie"));

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

}
