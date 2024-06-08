package br.com.escola.cadastro.cadastroescolarjava.acessobanco;

import br.com.escola.cadastro.cadastroescolarjava.entidades.Aluno;
import br.com.escola.cadastro.cadastroescolarjava.entidades.Bimestre;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BimestreDao extends AbstratoDao <Bimestre, Integer> {
    protected String scriptTabela = "CREATE TABLE IF NOT EXISTS bimestre (" +
            "idBimestre INTEGER PRIMARY KEY AUTOINCREMENT," +
            "numeroBimestre INTEGER," +
            "teste REAL," +
            "prova REAL," +
            "pontoDeParticipacao REAL," +
            "fk_idDisciplina INTEGER," +
            "FOREIGN KEY (fk_idDisciplina) REFERENCES disciplina(idDisciplina) ON DELETE CASCADE" +
            ")";
    protected String insertSql = "INSERT INTO bimestre (numeroBimestre, teste, prova, pontoDeParticipacao," +
            "fk_idDisciplina) VALUES (?, ?, ?, ?, ?)";
    protected String deleteSql = "DELETE FROM bimestre WHERE idBimestre = ?";
    protected String updateSql = "UPDATE bimestre " +
            "SET numeroBimestre = ?," +
            "teste = ?," +
            "prova = ?," +
            "pontoDeParticipacao = ?," +
            "fk_idDisciplina = ? " +
            "WHERE idBimestre = ?";
    protected String selectSql = "SELECT * FROM bimestre WHERE idBimestre = ?";
    protected String selectAllSql = "SELECT * FROM bimestre";

    public BimestreDao() {
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
    public void inserir(Bimestre b) {
        try (var stmt = conectar().prepareStatement(insertSql)) {
            stmt.setInt(1, b.getNumeroBimestre());
            stmt.setDouble(2, b.getTeste());
            stmt.setDouble(3, b.getProva());
            stmt.setDouble(4, b.getPontoDeParticipacao());
            stmt.setInt(5, b.getIdDisciplina());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro na inserção de bimestre: " + ex.getMessage());
        }
    }

    @Override
    public void deletar(Integer id) {
        try (var stmt = conectar().prepareStatement(deleteSql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro em deletar bimestre: " + ex.getMessage());
        }
    }

    @Override
    public void alterar(Bimestre b) {
        try (var stmt = conectar().prepareStatement(updateSql)) {
            stmt.setInt(1, b.getNumeroBimestre());
            stmt.setDouble(2, b.getTeste());
            stmt.setDouble(3, b.getProva());
            stmt.setDouble(4, b.getPontoDeParticipacao());
            stmt.setInt(5, b.getIdDisciplina());
            stmt.setInt(6, b.getId());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro atualizando o bimestre: " + ex.getMessage());
        }
    }

    @Override
    public Bimestre selecionar(Integer id) {
        Bimestre b = null;
        try (var stmt = conectar().prepareStatement(selectSql)) {
            stmt.setInt(1, id);
            var r = stmt.executeQuery();
            while (r.next()) {
                b = new Bimestre(r.getInt("idBimestre"), r.getInt("numeroBimestre"),
                        r.getDouble("teste"), r.getDouble("prova"),
                        r.getDouble("pontoDeParticipacao"), r.getInt("fk_idDisciplina"));
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao selecionar bimestre: " + ex.getMessage());
        }

        return b;
    }

    @Override
    public List<Bimestre> selecionarTodos() {
        List<Bimestre> listaBimestres = new ArrayList<>();

        try (var stmt = conectar().createStatement()) {
            var r = stmt.executeQuery(selectAllSql);

            while (r.next()) {
                listaBimestres.add(new Bimestre(r.getInt("idBimestre"),
                        r.getInt("numeroBimestre"), r.getDouble("teste"),
                        r.getDouble("prova"), r.getDouble("pontoDeParticipacao"),
                        r.getInt("fk_idDisciplina")));
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao selecionar todos os bimestres: " + ex.getMessage());
        }

        return listaBimestres;
    }

    public List<Bimestre> selecionarBimestresPorIdDisciplina(Integer id) {
        List<Bimestre> lista = new ArrayList<>();

        try (var stmt = conectar().prepareStatement("SELECT * FROM bimestre WHERE fk_idDisciplina = ?")) {
            stmt.setInt(1, id);
            var r = stmt.executeQuery();

            while (r.next()) {
                lista.add(new Bimestre(r.getInt("idBimestre"),
                        r.getInt("numeroBimestre"), r.getDouble("teste"),
                        r.getDouble("prova"), r.getDouble("pontoDeParticipacao"),
                        r.getInt("fk_idDisciplina")));
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao resgatar bimestres por disciplina: " + ex.getMessage());
        }

        return lista;
    }
}
