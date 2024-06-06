package br.com.escola.cadastro.cadastroescolarjava.entidades;

public class Bimestre {
    private int id;
    private float teste;
    private float prova;
    private float pontoDeParticipacao;
    private int idAluno;

    public Bimestre() {}

    public Bimestre(int id, float teste, float prova, float pontoDeParticipacao, int idAluno) {
        this.id = id;
        this.teste = teste;
        this.prova = prova;
        this.pontoDeParticipacao = pontoDeParticipacao;
        this.idAluno = idAluno;
    }

    public int getId() {
        return id;
    }

    public float getTeste() {
        return teste;
    }

    public void setTeste(float teste) {
        this.teste = teste;
    }

    public float getProva() {
        return prova;
    }

    public void setProva(float prova) {
        this.prova = prova;
    }

    public float getPontoDeParticipacao() {
        return pontoDeParticipacao;
    }

    public void setPontoDeParticipacao(float pontoDeParticipacao) {
        this.pontoDeParticipacao = pontoDeParticipacao;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    @Override
    public String toString() {
        return "Bimestre{" +
                "id=" + id +
                ", teste=" + teste +
                ", prova=" + prova +
                ", pontoDeParticipacao=" + pontoDeParticipacao +
                ", idAluno=" + idAluno +
                '}';
    }
}
