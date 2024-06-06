package br.com.escola.cadastro.cadastroescolarjava;

import java.io.IOException;

public interface BaseController {
    void irParaAluno() throws IOException;
    void irParaProfessor() throws IOException;
    void irParaTurma() throws IOException;
    void irParaNotas() throws IOException;
    void irParaHistorico() throws IOException;
}
