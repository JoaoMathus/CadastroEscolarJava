package br.com.escola.cadastro.cadastroescolarjava;

import br.com.escola.cadastro.cadastroescolarjava.entidades.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlunoController extends AbstratoController implements IPessoaController {
    @FXML
    private TextField txtMatricula;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtCelular;
    @FXML
    private ChoiceBox<String> escolhaTurma;
    @FXML
    private TextField txtCpf;
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

    @Override
    public void setApplication(SistemaCadastro application) {
        super.setApplication(application);
        mostrarTodos();
        var turmasDesteAno = application.getTurmaDao().selecionarTodos()
                .stream().filter(t -> t.getAnoCriada().equals(
                        String.valueOf(LocalDate.now().getYear())
                )).toList();
        var numeroTurmas = turmasDesteAno
                .stream().map(Turma::getNumeroDaSala).toList();
        escolhaTurma.setItems(FXCollections.observableArrayList(numeroTurmas));
    }

    // Inicializando e configurando o controller.
    public void initialize() {
        aplicarValidacao(txtTelefone);
        aplicarValidacao(txtCelular);
        aplicarValidacao(txtCpfResponsavel);
        aplicarValidacao(txtCpf);
        configurarCelulasDaTabela();
        configurarSelecaoDaTabela();
        configurarCelulaDeData();
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
                txtNome.setText(novaSelecao.getNome());
                txtMatricula.setText(novaSelecao.getMatricula());
                txtTelefone.setText(novaSelecao.getTelefone());
                txtCelular.setText(novaSelecao.getCelular());
                txtCpfResponsavel.setText(novaSelecao.getCpfDoResponsavel());
                var turmas = getApplication().getTurmaDao().selecionarTodos();
                var turmaFiltrada = turmas.stream().filter(t -> t.getId() == novaSelecao.getIdTurma()).toList().getFirst();
                escolhaTurma.setValue(turmaFiltrada.getNumeroDaSala());
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                dataNascimento.setValue(LocalDate.parse(novaSelecao.getDataNascimento(), dtf));
                escolhaTipoSanguineo.setValue(novaSelecao.getTipoSanguineo());
                txtCpf.setText(novaSelecao.getCpf());
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
                txtCpfResponsavel.getText().isEmpty() || escolhaTipoSanguineo.getValue().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao salvar aluno");
            alert.setHeaderText("Não foi possível salvar o aluno");
            alert.setContentText("Certifique-se de preencher todos os campos!");

            alert.showAndWait();
            return;
        }

        var alunos = getApplication().getAlunoDao().selecionarTodos();
        for (var aluno : alunos) {
            if (aluno.getCpf().equals(txtCpf.getText())) {
                // Atualização de dados.
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmação");
                alert.setHeaderText("Atualização de dados");
                alert.setContentText("Deseja mesmo atualizar esses dados?");

                Optional<ButtonType> result = alert.showAndWait();
                result.ifPresent(buttonType -> {
                    if (buttonType == ButtonType.OK) {
                        // Atualizando o aluno no banco de dados
                        var turma = getApplication().getTurmaDao().selecionarTodos()
                                .stream().filter(t -> t.getNumeroDaSala().equals(escolhaTurma.getValue())).toList()
                                .getFirst();
                        var alunoAtualizado = new Aluno(aluno.getId(), txtNome.getText(),
                                dataNascimento.getValue().toString(), txtMatricula.getText(),
                                txtTelefone.getText(), txtCelular.getText(), txtCpf.getText(),
                                txtCpfResponsavel.getText(), escolhaTipoSanguineo.getValue(),
                                turma.getId());
                        getApplication().getAlunoDao().alterar(alunoAtualizado);
                        mostrarTodos();
                    }
                });
                return;
            }
        }

        // Novo aluno a ser inserido no banco de dados

        // Aplica o algoritmo para gerar a matrícula
        calcularMatricula();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Confirma esse cadastro?");
        alert.setContentText("Um novo aluno será salvo no banco de dados");

        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                var turmas = getApplication().getTurmaDao().selecionarTodos();
                int idTurma = -1;
                String numeroTurma = "";
                for (var turma : turmas) {
                    if (turma.getNumeroDaSala().equals(escolhaTurma.getValue())) {
                        idTurma = turma.getId();
                        numeroTurma = turma.getNumeroDaSala();
                    }
                }
                var aluno = new Aluno(txtNome.getText(), txtTelefone.getText(), txtCelular.getText(),
                        txtCpf.getText(), dataNascimento.getValue().toString(), txtMatricula.getText(),
                        idTurma, numeroTurma, escolhaTipoSanguineo.getValue(), txtCpfResponsavel.getText());

                // Inserindo o aluno
                getApplication().getAlunoDao().inserir(aluno);
            }
        });

        limparCampos();
        mostrarTodos();
    }

    @Override
    @FXML
    public void deletar() {
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Deletar um aluno");
        dialogo.setHeaderText("Digite a matrícula do aluno que você quer deletar");
        dialogo.setContentText("Matrícula:");

        Optional<String> resultado = dialogo.showAndWait();
        resultado.ifPresent(matricula -> {
            // Caso houver vários alunos com a mesma matrícula (quem sabe...),
            // todos podem ser deletados
            List<Aluno> listaAlunos = getApplication().getAlunoDao().selecionarTodos()
                    .stream().filter(a -> a.getMatricula().equals(matricula)).toList();
            List<String> nomes = listaAlunos.stream().map(Pessoa::getNome).toList();
            StringBuilder mensagem = new StringBuilder();

            mensagem.append("Deseja mesmo deletar ");
            if (nomes.size() == 1) {
                mensagem.append("esse aluno: ").append(nomes.getFirst()).append("?");
            } else {
                mensagem.append("esses alunos: ");
                for (String nome : nomes) {
                    mensagem.append(nome);
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
            resposta.ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    listaAlunos.forEach(a -> getApplication().getAlunoDao().deletar(a.getId()));
                }
            });
        });

        limparCampos();
        mostrarTodos();
    }

    @Override
    @FXML
    public void selecionar() {
        if (txtPesquisa.getText().equalsIgnoreCase("todos")) {
            mostrarTodos();
            return;
        }

        List<Aluno> alunosFiltrados = getApplication().getAlunoDao().selecionarTodos()
                .stream().filter(aluno -> aluno.getMatricula().equals(txtPesquisa.getText()))
                .toList();

        ObservableList<Aluno> listaObservavel = FXCollections.observableArrayList(alunosFiltrados);

        tabelaAlunos.setItems(listaObservavel);
    }

    @FXML
    protected void calcularMatricula() {
        String ano = Integer.toString(LocalDate.now().getYear());
        String mes;
        int horario;
        String horaCadastrado;

        if (LocalDate.now().getMonthValue() < 10)
            mes = "0" + LocalDate.now().getMonthValue();
        else
            mes = Integer.toString(LocalDate.now().getMonthValue());

        horario = LocalDateTime.now().getHour();
        if (horario < 10) {
            horaCadastrado = "0" + horario;
        } else {
            horaCadastrado = Integer.toString(horario);
        }

        txtMatricula.setText(ano + mes + horaCadastrado + txtCpf.getText().substring(8));
    }

    @Override
    public void limparCampos() {
        txtMatricula.setText("");
        txtNome.setText("");
        txtTelefone.setText("");
        txtCelular.setText("");
        txtTelefone.setText("");
        txtCpfResponsavel.setText("");
        dataNascimento.setValue(null);
        escolhaTipoSanguineo.setValue("");
        txtCpf.setText("");
        escolhaTurma.setValue(null);
    }

    @Override
    public void mostrarTodos() {
        List<Aluno> listaFinal = new ArrayList<>();
        var alunos = getApplication().getAlunoDao().selecionarTodos();
        var turmas = getApplication().getTurmaDao().selecionarTodos();
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