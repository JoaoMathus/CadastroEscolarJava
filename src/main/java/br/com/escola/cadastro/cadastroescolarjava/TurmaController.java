package br.com.escola.cadastro.cadastroescolarjava;

import br.com.escola.cadastro.cadastroescolarjava.entidades.Pessoa;
import br.com.escola.cadastro.cadastroescolarjava.entidades.Turma;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TurmaController extends AbstratoController implements IEntidadeController {
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

    @Override
    public void setApplication(SistemaCadastro application) {
        super.setApplication(application);
        var professores = application.getProfessorDao().selecionarTodos().stream().map(Pessoa::getNome).toList();
        escolhaProfessor.setItems(FXCollections.observableArrayList(professores));
        mostrarTodos();
    }

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
    public void salvar() {
        if (escolhaProfessor.getValue().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao salvar turma");
            alert.setHeaderText("Não foi possível salvar a turma");
            alert.setContentText("Certifique-se de preencher todos os campos!");

            alert.showAndWait();
            return;
        }

        var turmas = getApplication().getTurmaDao().selecionarTodos();
        var turmaExistente = turmas.stream().filter(t -> t.getNumeroDaSala().equals(txtTurma.getText()))
                .toList();
        if (!turmaExistente.isEmpty()) {
            // Atualizar a turma
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Confirma essa atualização?");
            alert.setContentText("Essa turma será atualizada no banco de dados!");

            Optional<ButtonType> result = alert.showAndWait();
            result.ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    var estaTurma = turmaExistente.getFirst();
                    estaTurma.setNomeProfessor(escolhaProfessor.getValue());
                    estaTurma.setSerie(spinnerSerie.getValue());
                    var professor = getApplication().getProfessorDao().selecionarTodos()
                            .stream().filter(p -> p.getNome().equals(escolhaProfessor.getValue()))
                            .toList().getFirst();
                    estaTurma.setIdProfessor(professor.getId());

                    getApplication().getTurmaDao().alterar(estaTurma);
                    mostrarTodos();
                }
            });
            return;
        }

        // Lógica para criar o número da turma
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
        result.ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                var professor = getApplication().getProfessorDao().selecionarTodos()
                        .stream().filter(p -> p.getNome().equals(escolhaProfessor.getValue())).toList();
                var turma = new Turma(txtTurma.getText(), String.valueOf(spinnerSerie.getValue()),
                        professor.getFirst().getId(), professor.getFirst().getNome(),
                        String.valueOf(LocalDate.now().getYear()));

                getApplication().getTurmaDao().inserir(turma);
            }
        });

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
            var listaTurmas = getApplication().getTurmaDao().selecionarTodos();
            var listaFiltrada = listaTurmas.stream().filter(t -> t.getNumeroDaSala().equals(numero)).toList();
            var numeros = listaFiltrada.stream().map(Turma::getNumeroDaSala).toList();
            StringBuilder mensagem = new StringBuilder();
            mensagem.append("Deseja mesmo deletar ");
            if (numeros.size() == 1) {
                mensagem.append("essa turma: ").append(numeros.getFirst()).append("?");
            } else {
                mensagem.append("essas turmas: ");
                for (String s : numeros) {
                    mensagem.append(s);
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
            resposta.ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    listaFiltrada.forEach(a -> getApplication().getTurmaDao().deletar(a.getId()));
                }
            });
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
        spinnerSerie.getValueFactory().setValue("1");
    }

    @Override
    public void mostrarTodos() {
        List<Turma> listaFinal = new ArrayList<>();
        var turmas = getApplication().getTurmaDao().selecionarTodos();
        var professores = getApplication().getProfessorDao().selecionarTodos();
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
