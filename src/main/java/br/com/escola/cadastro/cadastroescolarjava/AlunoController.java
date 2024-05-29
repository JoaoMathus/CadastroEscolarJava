package br.com.escola.cadastro.cadastroescolarjava;

import br.com.escola.cadastro.cadastroescolarjava.entidades.Aluno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @FXML
    private TextField txtPesquisa;
    @FXML
    private TableView tabelaAlunos;
    @FXML
    private TableColumn<Aluno, String> colunaMatricula;
    @FXML
    private TableColumn<Aluno, String> colunaNome;
    @FXML
    private TableColumn<Aluno, String> colunaTurma;

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
        if (txtNome.getText().isEmpty() || dataNascimento.getValue().toString().isEmpty() ||
                txtMatricula.getText().isEmpty() || txtTelefone.getText().isEmpty() ||
                txtCelular.getText().isEmpty() || txtCpfResponsavel.getText().isEmpty() ||
                escolhaTipoSanguineo.getValue().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao salvar aluno");
            alert.setHeaderText("Não foi possível salvar o aluno");
            alert.setContentText("Certifique-se de preencher todos os campos!");

            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Confirma esse cadastro?");
        alert.setContentText("Um novo aluno será salvo no banco de dados");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // TODO: Adicionar lógica para resgatar uma turma pelo número (que é o control da tela de cadastro).
            // TODO: e então resgatar a "série" pelo número e adicioná-lo ao objeto do aluno.
            application.getAlunoDao().inserir(txtNome.getText(), dataNascimento.getValue().toString(),
                    txtMatricula.getText(), txtTelefone.getText(), txtCelular.getText(), txtCpfResponsavel.getText(),
                    escolhaTipoSanguineo.getValue(), "Segunda", 1);
        }

        application.getAlunoDao().selecionarTodos().forEach(System.out::println);
    }

    @FXML
    protected void deletarAluno() {
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Deletar um aluno");
        dialogo.setHeaderText("Digite a matrícula do aluno que você quer deletar");
        dialogo.setContentText("Matrícula:");

        Optional<String> resultado = dialogo.showAndWait();
        resultado.ifPresent(matricula -> {
            Alert confirma = new Alert(Alert.AlertType.CONFIRMATION);
            confirma.setTitle("Deletar aluno");
            confirma.setHeaderText("Deseja mesmo deletar esse aluno?");
            confirma.setContentText("O aluno será apagado do banco de dados");

            Optional<ButtonType> resposta = confirma.showAndWait();
            if (resposta.get() == ButtonType.OK) {
                List<Aluno> alunos = application.getAlunoDao().selecionarTodos();
                for (Aluno aluno : alunos) {
                    if (aluno.getMatricula().equals(matricula)) {
                        application.getAlunoDao().deletar(aluno.getId());
                    }
                }
            }
        });
    }

    @FXML
    protected void selecionarPorMatricula() {
        colunaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaTurma.setCellValueFactory(new PropertyValueFactory<>("serie"));

        List<Aluno> alunosFiltrados = application.getAlunoDao().selecionarTodos()
                .stream().filter(aluno -> aluno.getMatricula().equals(txtPesquisa.getText()))
                .toList();

        ObservableList<Aluno> listaObservavel = FXCollections.observableArrayList(alunosFiltrados);

        tabelaAlunos.setItems(listaObservavel);
    }
}