package br.com.escola.cadastro.cadastroescolarjava.acessobanco;

import br.com.escola.cadastro.cadastroescolarjava.entidades.Turma;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TurmaDao extends AbstratoDao <Turma, Integer> {
    private final String scriptTabela = "CREATE TABLE IF NOT EXISTS turma (\n" +
            "    idTurma INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    capacidade INTEGER,\n" +
            "    serie TEXT,\n" +
            "    numeroDaSala TEXT,\n" +
            "    fk_idProfessor INTEGER,\n" +
            "    FOREIGN KEY (fk_idProfessor) REFERENCES professor(idProfessor)\n" +
            ")";
    private final String insertSql = "INSERT INTO turma (" +
            "capacidade, serie, numeroDaSala, fk_idProfessor) VALUES (" +
            "?, ?, ?, ?)";
    private final String deleteSql = "DELETE FROM turma WHERE idTurma = ?";
    private final String updateSql = "UPDATE turma SET capacidade = ?, " +
            "serie = ?, numeroDaSala = ?, fk_idProfessor = ? WHERE idTurma = ?";
    private final String selectSql = "SELECT * FROM turma WHERE idTurma = ?";
    private final String selectAllSql = "SELECT * FROM turma";

    public TurmaDao() {
        criarTabela();
    }

    @Override
    protected void criarTabela() {
        try (var stmt = conectar().createStatement();) {
            stmt.execute(scriptTabela);
        } catch (SQLException ex) {
            System.err.println("Erro ao criar tabela turma: " + ex.getMessage());
        }
    }

    @Override
    public void inserir(Turma t) {
        // Ver se a turma já existe
        var turmas = selecionarTodos();
        for (var turma : turmas) {
            if (turma.getSerie().equals(t.getSerie()) && turma.getNumeroDaSala().equals(t.getNumeroDaSala()) &&
                    turma.getIdProfessor() == t.getIdProfessor())
                return;
        }
        try (var stmt = conectar().prepareStatement(insertSql)) {
            stmt.setInt(1, t.getCapacidade());
            stmt.setString(2, t.getSerie());
            stmt.setString(3, t.getNumeroDaSala());
            stmt.setInt(4, t.getIdProfessor());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir turma: " + ex.getMessage());
        }
    }

    @Override
    public void deletar(Integer id) {
        try (var stmt = conectar().prepareStatement(deleteSql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro ao deletar turma: " + ex.getMessage());
        }
    }

    @Override
    public void alterar(Turma t) {
        try (var stmt = conectar().prepareStatement(updateSql)) {
            stmt.setInt(1, t.getCapacidade());
            stmt.setString(2, t.getSerie());
            stmt.setString(3, t.getNumeroDaSala());
            stmt.setInt(4, t.getIdProfessor());
            stmt.setInt(5, t.getId());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro ao alterar turma: " + ex.getMessage());
        }
    }

    @Override
    public Turma selecionar(Integer id) {
        Turma t = null;

        try (var stmt = conectar().prepareStatement(selectSql)) {
            stmt.setInt(1, id);
            var r = stmt.executeQuery();
            while (r.next()) {
                t = new Turma(r.getInt("idTurma"), r.getInt("capacidade"),
                        r.getString("serie"), r.getString("numeroDaSala"),
                        r.getInt("fk_idProfessor"));
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao selecionar turma: " + ex.getMessage());
        }
        return t;
    }

    @Override
    public List<Turma> selecionarTodos() {
        List<Turma> lista = new ArrayList<>();

        try (var stmt = conectar().prepareStatement(selectAllSql)) {
            var r = stmt.executeQuery();

            while (r.next()) {
                lista.add(new Turma(r.getInt("idTurma"), r.getInt("capacidade"),
                        r.getString("serie"), r.getString("numeroDaSala"),
                        r.getInt("fk_idProfessor")));
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao selecionar todas as turmas: " + ex.getMessage());
        }

        return lista;
    }
}
