package br.com.escola.cadastro.cadastroescolarjava.acessobanco;

import br.com.escola.cadastro.cadastroescolarjava.entidades.Professor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDao extends AbstratoDao <Professor, Integer> {
    protected final String scriptTabela = "CREATE TABLE IF NOT EXISTS professor (\n" +
            "    idProfessor INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    nome TEXT,\n" +
            "    cpf TEXT,\n" +
            "    telefone TEXT,\n" +
            "    celular TEXT,\n" +
            "    dataNascimento TEXT\n" +
            ")";
    private final String insertSql = "INSERT INTO professor (" +
            "nome, cpf, telefone, celular, dataNascimento) VALUES (" +
            "?, ?, ?, ?, ?)";
    private final String deleteSql = "DELETE FROM professor WHERE idProfessor = ?";
    private final String updateSql = "UPDATE professor SET nome = ?, " +
            "cpf = ?, telefone = ?, celular = ?, dataNascimento = ? " +
            "WHERE idProfessor = ?";
    private final String selectSql = "SELECT * FROM professor WHERE idProfessor = ?";
    private final String selectAllSql = "SELECT * FROM professor";

    public ProfessorDao() {
        criarTabela();
    }

    @Override
    protected void criarTabela() {
        try (var stmt = conectar().createStatement()) {
            stmt.execute(scriptTabela);
        } catch (SQLException ex) {
            System.err.println("Erro na criação da tabela professor: " + ex.getMessage());
        }
    }

    @Override
    public void inserir(Professor p) {
        // Ver se o professor já está no banco
        var professores = selecionarTodos();
        for (var professor : professores) {
            if (professor.getNome().equals(p.getNome()) && professor.getCpf().equals(p.getCpf()) &&
                    professor.getDataNascimento().equals(p.getDataNascimento()))
                return ;
        }
        try (var stmt = conectar().prepareStatement(insertSql)) {
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCpf());
            stmt.setString(3, p.getTelefone());
            stmt.setString(4, p.getCelular());
            stmt.setString(5, p.getDataNascimento());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir professor: " + ex.getMessage());
        }
    }

    @Override
    public void deletar(Integer idProfessor) {
        try (var stmt = conectar().prepareStatement(deleteSql)) {
            stmt.setInt(1, idProfessor);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro ao deletar professor: " + ex.getMessage());
        }
    }

    @Override
    public void alterar(Professor p) {
        try (var stmt = conectar().prepareStatement(updateSql)) {
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCpf());
            stmt.setString(3, p.getTelefone());
            stmt.setString(4, p.getCelular());
            stmt.setString(5, p.getDataNascimento());
            stmt.setInt(6, p.getId());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro ao alterar professor: " + ex.getMessage());
        }
    }

    @Override
    public Professor selecionar(Integer id) {
        Professor p = new Professor();

        try (var stmt = conectar().prepareStatement(selectSql)) {
            var r = stmt.executeQuery();
            while (r.next()) {
                p.setId(r.getInt("idProfessor"));
                p.setNome(r.getString("nome"));
                p.setTelefone(r.getString("telefone"));
                p.setCelular(r.getString("celular"));
                p.setDataNascimento(r.getString("dataNascimento"));
                p.setCpf(r.getString("cpf"));
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao selecionar professor: " + ex.getMessage());
        }
        return p;
    }

    @Override
    public List<Professor> selecionarTodos() {
        List<Professor> lista = new ArrayList<>();
        try (var stmt = conectar().createStatement()) {
            var r = stmt.executeQuery(selectAllSql);
            while (r.next()) {
                lista.add(new Professor(r.getInt("idProfessor"), r.getString("nome"),
                        r.getString("telefone"), r.getString("celular"),
                        r.getString("dataNascimento"), r.getString("cpf")));
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao selecionar todos os professores: " + ex.getMessage());
        }
        return lista;
    }
}
