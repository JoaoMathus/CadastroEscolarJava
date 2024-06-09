package br.com.escola.cadastro.cadastroescolarjava;

import javafx.scene.control.TextField;

public interface IPessoaController extends IEntidadeController {
    // Aplica a validação do input dos campos numéricos
    void aplicarValidacao(TextField tf);

    // Evitar datas de nascimento impossíveis...
    // Sem viajantes do futuro no nosso sistema.
    void configurarCelulaDeData();
}
