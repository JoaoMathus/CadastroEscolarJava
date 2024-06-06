package br.com.escola.cadastro.cadastroescolarjava.acessobanco;

import br.com.escola.cadastro.cadastroescolarjava.entidades.Disciplina;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDao extends AbstratoDao <Disciplina, Integer> {
    private final String scriptTabela = "CREATE TABLE IF NOT EXISTS disciplina (\n" +
            "    idDisciplina INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    nome TEXT,\n" +
            "    anoLetivo TEXT,\n" +
            "    aprovado INTEGER,\n" +
            "    fk_idAluno INTEGER,\n" +
            "    FOREIGN KEY(fk_idAluno) REFERENCES aluno(idAluno)\n" +
            ")";
    private final String insertSql = "INSERT INTO disciplina (" +
            "nome, anoLetivo, aprovado, fk_idAluno) VALUES (" +
            "?, ?, ?, ?)";
    private final String deleteSql = "DELETE FROM disciplina WHERE idDisciplina = ?";
    private final String updateSql = "UPDATE disciplina SET nome = ?," +
            "anoLetivo = ?, aprovado = ?, fk_idAluno = ? WHERE idDisciplina = ?)";
    private final String selectSql = "SELECT * FROM disciplina WHERE idDisciplina = ?";
    private final String selectAllSql = "SELECT * FROM disciplina";

    public DisciplinaDao() {
        criarTabela();
    }

    @Override
    protected void criarTabela() {
        try (var stmt = conectar().createStatement()) {
            stmt.execute(scriptTabela);
        } catch (SQLException ex) {
            System.err.println("Erro ao criar tabela disciplina: " + ex.getMessage());
        }
    }

    @Override
    public void inserir(Disciplina d) {
        // Ver se essa disciplina já existe
        var disciplinas = selecionarTodos();
        for (var disciplina : disciplinas) {
            if (disciplina.getNome().equals(d.getNome()) && disciplina.getIdAluno() == d.getIdAluno())
                return;
        }
        try (var stmt = conectar().prepareStatement(insertSql)) {
            stmt.setString(1, d.getNome());
            stmt.setString(2, d.getAnoLetivo());
            stmt.setBoolean(3, d.isAprovado());
            stmt.setInt(4, d.getId());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir disciplina: " + ex.getMessage());
        }
    }

    @Override
    public void deletar(Integer id) {
        try (var stmt = conectar().prepareStatement(deleteSql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro ao deletar disciplina: " + ex.getMessage());
        }
    }

    @Override
    public void alterar(Disciplina d) {
        try (var stmt = conectar().prepareStatement(updateSql)) {
            stmt.setString(1, d.getNome());
            stmt.setString(2, d.getAnoLetivo());
            stmt.setBoolean(3, d.isAprovado());
            stmt.setInt(4, d.getIdAluno());
            stmt.setInt(5, d.getId());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro ao alterar disciplina: " + ex.getMessage());
        }
    }

    @Override
    public Disciplina selecionar(Integer id) {
        Disciplina d = null;

        try (var stmt = conectar().prepareStatement(selectSql)) {
            stmt.setInt(1, id);
            var r = stmt.executeQuery();
            while (r.next()) {
                d = new Disciplina(r.getInt("idDisciplina"), r.getString("nome"),
                        r.getString("anoLetivo"), r.getBoolean("aprovado"),
                        r.getInt("fk_idAluno"));
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao selecionar disciplina: " + ex.getMessage());
        }
        return d;
    }

    @Override
    public List<Disciplina> selecionarTodos() {
        List<Disciplina> lista = new ArrayList<>();

        try (var stmt = conectar().createStatement()) {
            var r = stmt.executeQuery(selectAllSql);
            while (r.next()) {
                lista.add(new Disciplina(r.getInt("idDisciplina"),
                        r.getString("nome"), r.getString("anoLetivo"),
                        r.getBoolean("aprovado"), r.getInt("fk_idAluno")));
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao selecionar todas as disciplinas: " + ex.getMessage());
        }
        return lista;
    }
}
