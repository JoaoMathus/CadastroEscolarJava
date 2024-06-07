package br.com.escola.cadastro.cadastroescolarjava;

import javafx.scene.control.TextField;

public interface EntidadeController {
    void salvar();
    void deletar();
    void selecionar();
    void limparCampos();
    void mostrarTodos();
    void configurarCelulasDaTabela();

    // Quando clicar em uma linha da tabela,
    // os dados serão printados nos campos
    // para permitir atualização dos dados
    void configurarSelecaoDaTabela();
}
