package br.com.escola.cadastro.cadastroescolarjava;

import br.com.escola.cadastro.cadastroescolarjava.entidades.Disciplina;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class HistoricoController extends AbstratoController {
    @FXML
    private TextField txtMatricula;
    @FXML
    private ChoiceBox<String> escolhaMateria;
    @FXML
    private Spinner<Integer> spinnerAno;
    @FXML
    private TextField txtSituacao;
    @FXML
    private TextField txtTeste1bin;
    @FXML
    private TextField txtTeste2bin;
    @FXML
    private TextField txtTeste3bin;
    @FXML
    private TextField txtTeste4bin;
    @FXML
    private TextField txtProva1bin;
    @FXML
    private TextField txtProva2bin;
    @FXML
    private TextField txtProva3bin;
    @FXML
    private TextField txtProva4bin;
    @FXML
    private TextField txtParticipacao1bin;
    @FXML
    private TextField txtParticipacao2bin;
    @FXML
    private TextField txtParticipacao3bin;
    @FXML
    private TextField txtParticipacao4bin;
    @FXML
    private TextField txtSomatorio1bin;
    @FXML
    private TextField txtSomatorio2bin;
    @FXML
    private TextField txtSomatorio3bin;
    @FXML
    private TextField txtSomatorio4bin;

    public void initialize() {
        spinnerAno.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 2100, 200));
        spinnerAno.getValueFactory().setValue(LocalDate.now().getYear());
    }

    @FXML
    public void limparCampos() {
        txtMatricula.setText("");
        spinnerAno.getValueFactory().setValue(LocalDate.now().getYear());
        escolhaMateria.setValue(null);
        txtSituacao.setText("");
        txtTeste1bin.setText("");
        txtTeste2bin.setText("");
        txtTeste3bin.setText("");
        txtTeste4bin.setText("");
        txtProva1bin.setText("");
        txtProva2bin.setText("");
        txtProva3bin.setText("");
        txtProva4bin.setText("");
        txtParticipacao1bin.setText("");
        txtParticipacao2bin.setText("");
        txtParticipacao3bin.setText("");
        txtParticipacao4bin.setText("");
        txtSomatorio1bin.setText("");
        txtSomatorio2bin.setText("");
        txtSomatorio3bin.setText("");
        txtSomatorio4bin.setText("");
    }

    @FXML
    public void selecionar() {
        if (txtMatricula.getText().isEmpty() || escolhaMateria.getValue() == null ||
                escolhaMateria.getValue().isEmpty() || spinnerAno.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao resgatar histórico");
            alert.setHeaderText("Não foi possível resgatar as notas");
            alert.setContentText("Certifique-se de preencher todos os campos!");

            alert.showAndWait();
            return;
        }

        var aluno = getApplication().getAlunoDao().selecionarPorMatricula(txtMatricula.getText());
        if (aluno == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao resgatar histórico");
            alert.setHeaderText("Não foi possível resgatar as notas");
            alert.setContentText("Aluno não existe, ou não há notas para esse aluno!");

            alert.showAndWait();
            return;
        }

        // Resgatando disciplina selecionada
        var disciplinasDoAluno = getApplication().getDisciplinaDao().selecionarTodasPorIdAluno(aluno.getId());
        if (disciplinasDoAluno.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao resgatar histórico");
            alert.setHeaderText("Não foi possível resgatar as notas");
            alert.setContentText("Disciplina não encontrada!");

            alert.showAndWait();
            return;
        }

        // Filtrando pelo nome selecionado e pelo ano
        Disciplina disciplinaSelecionada = null;
        try {
            disciplinaSelecionada = disciplinasDoAluno
                    .stream().filter(d -> d.getAnoLetivo().equals(String.valueOf(spinnerAno.getValue())))
                    .filter(d -> d.getNome().equals(escolhaMateria.getValue()))
                    .toList().getFirst();
        } catch (NoSuchElementException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao resgatar histórico");
            alert.setHeaderText("Não foi possível resgatar as notas");
            alert.setContentText("Ano inválido!");

            alert.showAndWait();
            return;
        }
        // Resgatando bimestres
        var bimestres = getApplication().getBimestreDao().selecionarBimestresPorIdDisciplina(disciplinaSelecionada.getId());
        if (bimestres.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao resgatar histórico");
            alert.setHeaderText("Não foi possível resgatar as notas");
            alert.setContentText("Não há bimestres cadastrados!");

            alert.showAndWait();
            return;
        }

        // Calculando aprovação
        var b1 = bimestres.stream().filter(b -> b.getNumeroBimestre() == 1)
                .toList().getFirst();
        var somatorio1bin = b1.getTeste() + b1.getProva() + b1.getPontoDeParticipacao();

        var b2 = bimestres.stream().filter(b -> b.getNumeroBimestre() == 2)
                .toList().getFirst();
        var somatorio2bin = b2.getTeste() + b2.getProva() + b2.getPontoDeParticipacao();

        var b3 = bimestres.stream().filter(b -> b.getNumeroBimestre() == 3)
                .toList().getFirst();
        var somatorio3bin = b3.getTeste() + b3.getProva() + b3.getPontoDeParticipacao();

        var b4 = bimestres.stream().filter(b -> b.getNumeroBimestre() == 4)
                .toList().getFirst();
        var somatorio4bin = b4.getTeste() + b4.getProva() + b4.getPontoDeParticipacao();

        boolean aprovado = somatorio1bin >= 5 && somatorio2bin >= 5 &&
                somatorio3bin >= 5 && somatorio4bin >= 5;

        // Colocando os dados na tela
        if (aprovado) {
            txtSituacao.setText("Aprovado");
        } else {
            txtSituacao.setText("Reprovado");
        }

        txtTeste1bin.setText(String.valueOf(b1.getTeste()));
        txtProva1bin.setText(String.valueOf(b1.getProva()));
        txtParticipacao1bin.setText(String.valueOf(b1.getPontoDeParticipacao()));
        txtSomatorio1bin.setText(String.valueOf(somatorio1bin));

        txtTeste2bin.setText(String.valueOf(b2.getTeste()));
        txtProva2bin.setText(String.valueOf(b2.getProva()));
        txtParticipacao2bin.setText(String.valueOf(b2.getPontoDeParticipacao()));
        txtSomatorio2bin.setText(String.valueOf(somatorio2bin));

        txtTeste3bin.setText(String.valueOf(b3.getTeste()));
        txtProva3bin.setText(String.valueOf(b3.getProva()));
        txtParticipacao3bin.setText(String.valueOf(b3.getPontoDeParticipacao()));
        txtSomatorio3bin.setText(String.valueOf(somatorio3bin));

        txtTeste4bin.setText(String.valueOf(b4.getTeste()));
        txtProva4bin.setText(String.valueOf(b4.getProva()));
        txtParticipacao4bin.setText(String.valueOf(b4.getPontoDeParticipacao()));
        txtSomatorio4bin.setText(String.valueOf(somatorio4bin));
    }
}
