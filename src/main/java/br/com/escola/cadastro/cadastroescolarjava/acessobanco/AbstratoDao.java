package br.com.escola.cadastro.cadastroescolarjava.acessobanco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstratoDao <E, K> {
    protected final String arquivoBanco = "dados.db";
    protected String scriptTabela;
    protected String insertSql;
    protected String deleteSql;
    protected String updateSql;
    protected String selectSql;
    protected String selectAllSql;

    protected Connection conectar() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + arquivoBanco);
    }

    protected abstract void criarTabela();

    public abstract void inserir(E entidade);
    public abstract void deletar(K chave);
    public abstract void alterar(E entidade);
    public abstract E selecionar(K chave);
    public abstract List<E> selecionarTodos();
}
