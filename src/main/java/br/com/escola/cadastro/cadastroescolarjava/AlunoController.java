package br.com.escola.cadastro.cadastroescolarjava;

import br.com.escola.cadastro.cadastroescolarjava.entidades.Aluno;
import br.com.escola.cadastro.cadastroescolarjava.entidades.Pessoa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

// TODO: Validar input das outras telas (CPF, datas)!!!
// TODO: Aplicar limites do input dos números de telefone e celular e de cpf das telas!
// TODO: Na confirmação de apagar, mostrar o nome do aluno.
public class AlunoController {
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
    private TableView<Aluno> tabelaAlunos;
    @FXML
    private TableColumn<Aluno, String> colunaMatricula;
    @FXML
    private TableColumn<Aluno, String> colunaNome;
    @FXML
    private TableColumn<Aluno, String> colunaTurma;

    static int numeroAlunos = 0;

    private SistemaCadastro application;

    public void setApplication(SistemaCadastro application) {
        this.application = application;
        mostrarTodos();
    }

    public void initialize() {
        aplicarValidacao(txtTelefone);
        aplicarValidacao(txtCelular);
        aplicarValidacao(txtCpfResponsavel);

        // Iniciando as colunas da tabela
        colunaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaTurma.setCellValueFactory(new PropertyValueFactory<>("serie"));

        // Quando clicar em uma linha da tabela,
        // os dados serão printados nos campos
        // para permitir atualização dos dados
        tabelaAlunos.getSelectionModel().selectedItemProperty().addListener((obs, selecaoAntiga, novaSelecao) -> {
            if (novaSelecao != null) {
                txtNome.setText(novaSelecao.getNome());
                txtMatricula.setText(novaSelecao.getMatricula());
                txtTelefone.setText(novaSelecao.getTelefone());
                txtCelular.setText(novaSelecao.getCelular());
                txtCpfResponsavel.setText(novaSelecao.getCpfDoResponsavel());
            }
        });
    }

    // Aplica a validação do input dos campos numéricos
    private void aplicarValidacao(TextField tf) {
        tf.textProperty().addListener((observable, valorAntigo, valorNovo) -> {
            if (!valorNovo.matches("\\d*")) {
                tf.setText(valorNovo.replaceAll("[^\\d]", ""));
            }
        });
    }


@FXML
    protected void irParaAluno() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("alunoCad.fxml"));
        BorderPane root = fxmlLoader.load();

        AlunoController alunoController = fxmlLoader.getController();
        alunoController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }

    @FXML
    protected void irParaProfessor() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("professorCad.fxml"));
        BorderPane root = fxmlLoader.load();

        ProfessorController professorController = fxmlLoader.getController();
        professorController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }

    @FXML
    protected void irParaTurma() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("turmaCad.fxml"));
        BorderPane root = fxmlLoader.load();

        TurmaController turmaController = fxmlLoader.getController();
        turmaController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }

    @FXML
    protected void irParaNotas() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("notaCad.fxml"));
        BorderPane root = fxmlLoader.load();

        NotaController notaController = fxmlLoader.getController();
        notaController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }

    @FXML
    protected void irParaHistorico() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("historico.fxml"));
        BorderPane root = fxmlLoader.load();

        HistoricoController historicoController = fxmlLoader.getController();
        historicoController.setApplication(application);

        application.getPrimaryStage().getScene().setRoot(root);
    }

    @FXML
    protected void salvarAluno() throws IOException {
        if (txtNome.getText().isEmpty() || dataNascimento.getValue().toString().isEmpty() ||
                txtTelefone.getText().isEmpty() || txtCelular.getText().isEmpty() ||
                txtCpfResponsavel.getText().isEmpty() || escolhaTipoSanguineo.getValue().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao salvar aluno");
            alert.setHeaderText("Não foi possível salvar o aluno");
            alert.setContentText("Certifique-se de preencher todos os campos!");

            alert.showAndWait();
            numeroAlunos--;
            return;
        }

        // Aplica o algoritmo para gerar a matrícula
        calcularMatricula();

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
        } else {
            numeroAlunos--;
        }

        limparTudo();
        mostrarTodos();
    }

    @FXML
    protected void deletarAluno() {
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Deletar um aluno");
        dialogo.setHeaderText("Digite a matrícula do aluno que você quer deletar");
        dialogo.setContentText("Matrícula:");

        Optional<String> resultado = dialogo.showAndWait();
        resultado.ifPresent(matricula -> {
            List<Aluno> listaAlunos = application.getAlunoDao().selecionarTodos()
                    .stream().filter(a -> a.getMatricula().equals(matricula)).toList();
            List<String> nomes = listaAlunos.stream().map(Pessoa::getNome).toList();
            StringBuilder mensagem = new StringBuilder();
            mensagem.append("Deseja mesmo deletar ");
            if (nomes.size() == 1) {
                mensagem.append("esse aluno: " + nomes.getFirst() + "?");
            } else {
                mensagem.append("esses alunos: ");
                for (int i = 0; i < nomes.size(); i++) {
                    mensagem.append(nomes.get(i));
                    mensagem.append(", ");
                }
                mensagem.append(nomes.getLast());
                mensagem.append("?");
            }

            Alert confirma = new Alert(Alert.AlertType.CONFIRMATION);
            confirma.setTitle("Deletar aluno");
            confirma.setHeaderText(mensagem.toString());
            confirma.setContentText("O aluno será apagado do banco de dados");

            Optional<ButtonType> resposta = confirma.showAndWait();
            if (resposta.get() == ButtonType.OK) {
                listaAlunos.forEach(a -> application.getAlunoDao().deletar(a.getId()));
            }
        });

        limparTudo();
        mostrarTodos();
    }

    @FXML
    protected void selecionarPorMatricula() {
        if (txtPesquisa.getText().equalsIgnoreCase("todos")) {
            mostrarTodos();
            return;
        }

        List<Aluno> alunosFiltrados = application.getAlunoDao().selecionarTodos()
                .stream().filter(aluno -> aluno.getMatricula().equals(txtPesquisa.getText()))
                .toList();

        ObservableList<Aluno> listaObservavel = FXCollections.observableArrayList(alunosFiltrados);

        tabelaAlunos.setItems(listaObservavel);
    }

    @FXML
    protected void calcularMatricula() {
        if (dataNascimento.getValue().toString().isEmpty() || txtCpfResponsavel.getText().isEmpty()) {
            return;
        }

        String mes;

        if (dataNascimento.getValue().getMonthValue() < 10)
            mes = "0" + dataNascimento.getValue().getMonthValue();
        else
            mes = Integer.toString(dataNascimento.getValue().getMonthValue());

        txtMatricula.setText(dataNascimento.getValue().getYear() + mes +
                txtCpfResponsavel.getText().substring(8) + numeroAlunos++);
    }

    private void limparTudo() {
        txtMatricula.setText("");
        txtNome.setText("");
        txtTelefone.setText("");
        txtCelular.setText("");
        txtTelefone.setText("");
        txtCpfResponsavel.setText("");
        dataNascimento.setValue(null);
        escolhaTipoSanguineo.setValue("");
    }

    private void mostrarTodos() {
        tabelaAlunos.setItems(FXCollections.observableArrayList(application.getAlunoDao().selecionarTodos()));
    }
}