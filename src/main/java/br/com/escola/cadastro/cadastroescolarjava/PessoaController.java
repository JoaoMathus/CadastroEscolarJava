package br.com.escola.cadastro.cadastroescolarjava;

import javafx.scene.control.TextField;

public interface PessoaController {
    void salvar();
    void deletar();
    void selecionar();
    void limparCampos();
    void mostrarTodos();
    void configurarCelulasDaTabela();

    // Evitar datas de nascimento impossíveis...
    // Sem viajantes do futuro no nosso sistema.
    void configurarCelulaDeData();

    // Quando clicar em uma linha da tabela,
    // os dados serão printados nos campos
    // para permitir atualização dos dados
    void configurarSelecaoDaTabela();

    // Aplica a validação do input dos campos numéricos
    void aplicarValidacao(TextField tf);
}
