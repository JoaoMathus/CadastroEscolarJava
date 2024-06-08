package br.com.escola.cadastro.cadastroescolarjava.acessobanco;

import br.com.escola.cadastro.cadastroescolarjava.entidades.Aluno;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDao extends AbstratoDao <Aluno, Integer> {
    private final String scriptTabela = "CREATE TABLE IF NOT EXISTS aluno (\n" +
            "    idAluno INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    nome TEXT,\n" +
            "    dataNascimento TEXT,\n" +
            "    matricula TEXT,\n" +
            "    telefoneDoResponsavel TEXT,\n" +
            "    celularDoResponsavel TEXT,\n" +
            "    cpfDoResponsavel TEXT,\n" +
            "    cpf TEXT, \n" +
            "    tipoSanguineo TEXT,\n" +
            "    fk_idTurma INTEGER,\n" +
            "    FOREIGN KEY (fk_idTurma) REFERENCES turma(idTurma) ON DELETE CASCADE\n" +
            ")";
    private final String insertSql = "INSERT INTO aluno (" +
            "nome, dataNascimento, matricula, telefoneDoResponsavel, celularDoResponsavel," +
            "cpfDoResponsavel, cpf, tipoSanguineo, fk_idTurma) VALUES (" +
            "?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String deleteSql = "DELETE FROM aluno WHERE " +
            "idAluno = ?";
    private final String updateSql = "UPDATE aluno " +
            "SET nome = ?," +
            "dataNascimento = ?," +
            "matricula = ?," +
            "telefoneDoResponsavel = ?," +
            "celularDoResponsavel = ?," +
            "cpfDoResponsavel = ?," +
            "cpf = ?," +
            "tipoSanguineo = ?," +
            "fk_idTurma = ? " +
            "WHERE idAluno = ?";
    private final String selectSql = "SELECT * FROM aluno WHERE idAluno = ?";
    private final String selectAllSql = "SELECT * FROM aluno";


    public AlunoDao() {
        criarTabela();
    }

    @Override
    protected void criarTabela() {
        try (var stmt = conectar().createStatement()) {
            stmt.execute(scriptTabela);
        } catch (SQLException ex) {
            System.err.println("Erro na criação da tabela aluno: " + ex.getMessage());
        }
    }

    @Override
    public void inserir(Aluno a) {
        // Ver se o aluno já está inserido
        var alunos = selecionarTodos();
        for (var aluno : alunos) {
            if (aluno.getMatricula().equals(a.getMatricula()) && aluno.getCpf().equals(a.getCpf()) &&
                    aluno.getNome().equals(a.getNome()) && aluno.getTipoSanguineo().equals(a.getTipoSanguineo()) &&
                    aluno.getDataNascimento().equals(a.getDataNascimento()))
                return;
        }
        try (var stmt = conectar().prepareStatement(insertSql)) {
            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getDataNascimento());
            stmt.setString(3, a.getMatricula());
            stmt.setString(4, a.getTelefone());
            stmt.setString(5, a.getCelular());
            stmt.setString(6, a.getCpfDoResponsavel());
            stmt.setString(7, a.getCpf());
            stmt.setString(8, a.getTipoSanguineo());
            stmt.setInt(9, a.getIdTurma());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro na inserção de aluno: " + ex.getMessage());
        }
    }

    @Override
    public void deletar(Integer id) {
        try (var stmt = conectar().prepareStatement(deleteSql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro em deletar aluno: " + ex.getMessage());
        }
    }

    @Override
    public void alterar(Aluno a) {
        try (var stmt = conectar().prepareStatement(updateSql)) {
            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getDataNascimento());
            stmt.setString(3, a.getMatricula());
            stmt.setString(4, a.getTelefone());
            stmt.setString(5, a.getCelular());
            stmt.setString(6, a.getCpfDoResponsavel());
            stmt.setString(7, a.getCpf());
            stmt.setString(8, a.getTipoSanguineo());
            stmt.setInt(9, a.getIdTurma());
            stmt.setInt(10, a.getId());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro atualizando o aluno: " + ex.getMessage());
        }
    }

    @Override
    public Aluno selecionar(Integer id) {
        Aluno a = null;
        try (var stmt = conectar().prepareStatement(selectSql)) {
            stmt.setInt(1, id);
            var r = stmt.executeQuery();
            while (r.next()) {
                a = new Aluno(r.getInt("idAluno"), r.getString("nome"),
                        r.getString("dataNascimento"), r.getString("matricula"),
                        r.getString("telefoneDoResponsavel"), r.getString("celularDoResponsavel"),
                        r.getString("cpfDoResponsavel"), r.getString("cpf"),
                        r.getString("tipoSanguineo"), r.getInt("fk_idTurma"));
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao selecionar aluno: " + ex.getMessage());
        }

        return a;
    }

    @Override
    public List<Aluno> selecionarTodos() {
        List<Aluno> lista = new ArrayList<>();

        try (var stmt = conectar().createStatement()) {
            var r = stmt.executeQuery(selectAllSql);

            while (r.next()) {
                lista.add(new Aluno(r.getInt("idAluno"), r.getString("nome"),
                        r.getString("dataNascimento"), r.getString("matricula"),
                        r.getString("telefoneDoResponsavel"), r.getString("celularDoResponsavel"),
                        r.getString("cpfDoResponsavel"), r.getString("cpf"),
                        r.getString("tipoSanguineo"), r.getInt("fk_idTurma")));
            }
        } catch (SQLException ex) {
            System.err.println("Erro em selecionar todos os alunos: " + ex.getMessage());
        }

        return lista;
    }
}
